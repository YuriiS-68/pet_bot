package sky.pro.pet_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import sky.pro.pet_bot.model.Picture;
import sky.pro.pet_bot.service.PictureServiceInterface;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/picture")
@Tag(name = "Контроллер фото", description = "добавление, обновление, удаление и другие операции с фото")
public class PictureController {
    private final PictureServiceInterface pictureService;

    public PictureController(PictureServiceInterface pictureService) {
        this.pictureService = pictureService;
    }

    @Operation(
            summary = "Загрузить фото",
            description = "Позволяет загрузить фото питомца из файла",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фото загружено"
                    )
            }
    )
    @GetMapping(value = "/{id}/picture-from-file")
    public void downloadPicture(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Picture picture = pictureService.findPictureById(id);
        Path path = Path.of(picture.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(picture.getMediaType());
            response.setContentLength((int) picture.getFileSize());
            is.transferTo(os);
        }
    }
}
