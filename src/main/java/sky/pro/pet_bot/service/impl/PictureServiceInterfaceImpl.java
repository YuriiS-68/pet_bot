package sky.pro.pet_bot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.dao.PictureRepository;
import sky.pro.pet_bot.model.Picture;
import sky.pro.pet_bot.model.Report;
import sky.pro.pet_bot.model.User;
import sky.pro.pet_bot.service.PictureServiceInterface;
import sky.pro.pet_bot.service.ReportServiceInterface;
import sky.pro.pet_bot.service.UserServiceInterface;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;

import static com.datical.liquibase.ext.init.InitProjectUtil.getExtension;

@Service
@Transactional
public class PictureServiceInterfaceImpl implements PictureServiceInterface {

    private final Logger logger = LoggerFactory.getLogger(PictureServiceInterfaceImpl.class);

    @Value("${path.to.pictures}")
    private String picturesDir;

    private final TelegramBot telegramBot;
    private final UserServiceInterface userService;
    private final ReportServiceInterface reportService;
    private final PictureRepository pictureRepository;

    public PictureServiceInterfaceImpl(TelegramBot telegramBot, UserServiceInterface userService,
                                       ReportServiceInterface reportService, PictureRepository pictureRepository) {
        this.telegramBot = telegramBot;
        this.userService = userService;
        this.reportService = reportService;
        this.pictureRepository = pictureRepository;
    }

    public Picture addPicture(Message message, User user) throws IOException {
        Picture picture = new Picture();
        Report lastReport = reportService.getLastReportByUserId(user.getId());
        picture.setReport(lastReport);
        picture.setFilePath(getPhoto(message).filePath());
        picture.setFileSize(getPhoto(message).fileSize());
        picture.setMediaType(getMediaTypePicture(getPhoto(message).filePath()));
        picture.setPreview(generatePicturePreview(message));
        logger.info("Picture is ready for save: {}", picture);
        pictureRepository.save(picture);
        return picture;
    }

    @Override
    public File downloadFile(Message message) {
        File file = null;
        try {
            URL url = new URL(path());
            InputStream inputStream = url.openStream();
            String path = pathToPictureDir(message);
            file = new File(path);
            file.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[10000];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public byte[] generatePicturePreview(Message message) throws IOException {
        logger.info("Path to file: {}", pathToPictureDir(message));
        URL url = new URL(path());
        try (
                InputStream inputStream = url.openStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 1024);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bufferedInputStream);

            int height = image.getHeight() / (image.getWidth() / 10);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(getPhoto(message).filePath()), byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    @Override
    public Integer getFileSizeByReportId(Long reportId) {
        return pictureRepository.getFileSizeByReportId(reportId);
    }

    public Picture findPictureById (Long pictureId) {
        return pictureRepository.findPictureById(pictureId);
    }

    @Override
    public Picture findPictureByReportId(Long reportId) {
        return pictureRepository.findPictureByReportId(reportId);
    }

    private String getMediaTypePicture(String input){
        int index = input.lastIndexOf('.');
        return input.substring(index + 1);
    }

    private String pathToPictureDir(Message message) {
        String filePath = getPhoto(message).filePath();
        String pathToFile = null;
        try {
            String directoryPath = picturesDir;
            java.io.File directory = new java.io.File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            pathToFile = directoryPath + userService.findUserByChatId(message.chat().id()).getId() + "."
                    + LocalDate.now() + "."
                    + filePath.substring(filePath.lastIndexOf("/") + 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Output path: {}", pathToFile);
        return pathToFile;
    }

    private String getPicturePathForDownload(String input){
        logger.info("String input: {}", input);
        if (input != null){
            String str1 = input.substring(0, 7);
            String str2 = input.substring(9);
            String newInput = str1 + "C" + str2;
            String result = "";
            for (Character ch : newInput.toCharArray()) {
                if (ch == '\\') {
                    ch = '/';
                }
                result += ch;

            }
            return result;
        }
        String errorMessage = "Wrong file path";
        logger.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }

    private com.pengrad.telegrambot.model.File getPhoto(Message message){
        logger.info("Run method getPhoto array photo[] have length: {}", message.photo().length);
        GetFile request = new GetFile(message.photo()[0].fileId());
        GetFileResponse getFileResponse = telegramBot.execute(request);
        logger.info("FilePath from telegramBot.execute(request): {}", getFileResponse.file().filePath());
        return getFileResponse.file();
    }

    private String path(){
        return "https://api.telegram.org/file/bot" + telegramBot.getToken() + "/photos/file_0.jpg";
    }
}
