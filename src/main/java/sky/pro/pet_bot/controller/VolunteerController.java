package sky.pro.pet_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.pet_bot.model.Volunteer;
import sky.pro.pet_bot.service.VolunteerServiceInterface;

import java.util.Collection;

@RestController
@RequestMapping("/volunteer")
@Tag(name = "Контроллер волонтеров", description = "добавление, обновление, удаление и другие операции с волонтерами")
public class VolunteerController {

    private final VolunteerServiceInterface volunteerService;

    public VolunteerController(VolunteerServiceInterface volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Поиск волонтера",
            description = "Позволяет найти волонтера по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный волонтер"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id){
        return ResponseEntity.ok(volunteerService.getVolunteerById(id));
    }

    @Operation(
            summary = "Добавление волонтера",
            description = "Позволяет добавить волонтера в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Волонтер добавлен"
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Boolean> addVolunteer (@RequestBody Volunteer volunteer){
        return ResponseEntity.ok(volunteerService.addVolunteer(volunteer.getName(), volunteer.getPhoneNumber()));
    }

    @Operation(
            summary = "Обновление волонтера",
            description = "Позволяет обновить данные волонтера в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Волонтер обновлен"
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity<Volunteer> updateVolunteer (@RequestBody Volunteer volunteer){
        Volunteer findVolunteer = volunteerService.getVolunteerById(volunteer.getId());
        if (findVolunteer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteerService.updateVolunteer(findVolunteer));
    }

    @Operation(
            summary = "Удаление волонтера",
            description = "Позволяет удалить волонтера из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Волонтер удален"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Volunteer> deleteVolunteer (@PathVariable Long id){
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить список всех волонтеров",
            description = "Позволяет получить всех волонтеров находящихся в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список волонтеров получен"
                    )
            }
    )
    @GetMapping("/getAll")
    public Collection<Volunteer> getAllVolunteers(){
        return volunteerService.getAllVolunteers();
    }
}
