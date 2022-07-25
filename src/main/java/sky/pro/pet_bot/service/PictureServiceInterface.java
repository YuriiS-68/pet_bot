package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.Message;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.pet_bot.model.Picture;
import sky.pro.pet_bot.model.User;

import java.io.File;
import java.io.IOException;

public interface PictureServiceInterface {

    Picture addPicture(Message message, User user) throws IOException;

    File downloadFile(Message message);

    byte[] generatePicturePreview(Message message) throws IOException;

    Integer getFileSizeByReportId(Long reportId);

    Picture findPictureById (Long pictureId);

    Picture findPictureByReportId(Long reportId);
}
