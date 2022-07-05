package sky.pro.pet_bot.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.pet_bot.dao.PictureRepository;
import sky.pro.pet_bot.model.Pet;
import sky.pro.pet_bot.model.Picture;
import sky.pro.pet_bot.model.Report;
import sky.pro.pet_bot.service.PictureServiceInterface;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class PictureServiceInterfaceImpl implements PictureServiceInterface {

    @Value("${path.to.pictures.folder}")
    private String picturesDir;

    private final PetServiceInterfaceImpl petServiceInterface;
    private final ReportServiceInterfaceImpl reportService;
    private final PictureRepository pictureRepository;

    public PictureServiceInterfaceImpl(PetServiceInterfaceImpl petServiceInterface, ReportServiceInterfaceImpl reportService,
                                       PictureRepository pictureRepository) {
        this.petServiceInterface = petServiceInterface;
        this.reportService = reportService;
        this.pictureRepository = pictureRepository;
    }

    public void uploadPetPic (Long petId, MultipartFile picFile) throws IOException {
        Pet pet = petServiceInterface.getPetById(petId);

        Path filePatch = Path.of(picturesDir, pet + "." + getExtensions (picFile.getOriginalFilename()));
        Files.createDirectories(filePatch.getParent());
        Files.deleteIfExists(filePatch);
        try (
                InputStream is = picFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePatch, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Picture picture = findPictureByPetId(petId);
        picture.setPet(pet);
        picture.setFilePath(filePatch.toString());
        picture.setFileSize((int) picFile.getSize());
        picture.setMediaType(picFile.getContentType());
        pictureRepository.save(picture);
    }

    public void uploadReportPic (Long reportId, MultipartFile picFile) throws IOException {
        Report report = reportService.getReportById(reportId);

        Path filePatch = Path.of(picturesDir, report + "." + getExtensions (picFile.getOriginalFilename()));
        Files.createDirectories(filePatch.getParent());
        Files.deleteIfExists(filePatch);
        try (
                InputStream is = picFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePatch, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        /*Picture picture = findPictureByReportId(reportId);
        picture.setReport(report);
        picture.setFilePath(filePatch.toString());
        picture.setFileSize((int) picFile.getSize());
        picture.setMediaType(picFile.getContentType());
        pictureRepository.save(picture);*/
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);}

    public Picture findPictureByPetId(Long petId) {
        return pictureRepository.findByPetId(petId).orElse(new Picture());
    }

    public Picture findPictureByReportId (Long reportId) {
        return pictureRepository.findByReportsId(reportId).orElse(new Picture());
    }
}
