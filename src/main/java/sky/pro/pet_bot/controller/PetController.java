package sky.pro.pet_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.pet_bot.model.Pet;
import sky.pro.pet_bot.model.Picture;
import sky.pro.pet_bot.service.PetServiceInterface;
import sky.pro.pet_bot.service.PictureServiceInterface;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/pet")
@Tag(name = "Контроллер питомцев", description = "добавление, обновление, удаление и другие операции с питомцами")
public class PetController {

private final PetServiceInterface petService;
private final PictureServiceInterface pictureService;

    public PetController(PetServiceInterface petService, PictureServiceInterface pictureService) {
        this.petService = petService;
        this.pictureService = pictureService;
    }

    @Operation(
            summary = "Поиск питомца",
            description = "Позволяет найти питомца по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный питомец"
                    )
            }
    )
    @GetMapping ("/{id}")
    public Pet getPetById (@PathVariable Long id){
        return petService.getPetById(id);
    }

    @Operation(
            summary = "Добавление питомца",
            description = "Позволяет добавить питомца в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый питомец добавлен"
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity <Pet> addPet (@RequestBody Pet pet){
        return ResponseEntity.ok(petService.addUserPet(pet));
    }

    @Operation(
            summary = "Загрузить фото питомца",
            description = "Позволяет загрузить фото питомца в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фото питомца загружено"
                    )
            }
    )
    @GetMapping(value = "/{id}/picture-from-file")
    public void downloadPicture(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Picture picture = pictureService.findPictureByPetId(id);
        Path path = Path.of(picture.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(picture.getMediaType());
            response.setContentLength((int) picture.getFileSize());
            is.transferTo(os);
        }
    }

    @Operation(
            summary = "Обновить фото питомца",
            description = "Позволяет обновить фото питомца в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фото питомца обновлено"
                    )
            }
    )
    @PostMapping(value = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPicture (@PathVariable Long id, @RequestParam MultipartFile picture) throws IOException{
        pictureService.uploadPetPicture(id, picture);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обновить питомца",
            description = "Позволяет обновить данные питомца в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные питомца обновлены"
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity <Pet> updatePet (@RequestBody Pet pet){
        return ResponseEntity.ok(petService.updatePet(pet));
    }

    @Operation(
            summary = "Удалит питомца",
            description = "Позволяет удалить питомца из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Питомец удалён"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <Pet> deletePet (@PathVariable Long id){
    petService.deletePetById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить список всех питомцев",
            description = "Позволяет получить всех питомцев находящихся в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список питомцев получен"
                    )
            }
    )
    @GetMapping("/getAll")
    public Collection<Pet> getAllPets (){
        return petService.getAllPets();
    }

}
