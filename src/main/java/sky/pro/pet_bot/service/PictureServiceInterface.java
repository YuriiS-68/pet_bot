package sky.pro.pet_bot.service;

import org.springframework.web.multipart.MultipartFile;
import sky.pro.pet_bot.model.Picture;

import java.io.IOException;

public interface PictureServiceInterface {
    void uploadPetPicture (Long petId, MultipartFile picFile) throws IOException;

    Picture findPictureByPetId(Long petId);

    Picture findPictureByReportId (Long reportId);
}
