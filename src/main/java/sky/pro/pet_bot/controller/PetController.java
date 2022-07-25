package sky.pro.pet_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.pet_bot.model.Pet;
import sky.pro.pet_bot.service.PetServiceInterface;

import java.util.Collection;

@RestController
@RequestMapping("/pet")
@Tag(name = "Контроллер питомцев", description = "добавление, обновление, удаление и другие операции с питомцами")
public class PetController {

private final PetServiceInterface petService;

    public PetController(PetServiceInterface petService) {
        this.petService = petService;
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
