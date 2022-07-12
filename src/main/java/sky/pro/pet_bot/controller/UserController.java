package sky.pro.pet_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.pet_bot.model.User;
import sky.pro.pet_bot.service.UserServiceInterface;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/user")
@Tag(name = "Контроллер пользователей", description = "добавление, обновление, удаление и другие операции с пользователями")
public class UserController {

    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Поиск пользователя",
            description = "Позволяет найти пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный пользователь"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User foundUser = userService.getUserById(id);
        if (foundUser == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundUser);
    }

    @Operation(
            summary = "Добавление нового пользователя",
            description = "Позволяет добавить пользователя в базу данных"
    )
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User addUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(addUser);
    }

    @Operation(
            summary = "Обновить пользователя",
            description = "Позволяет обновить данные пользователя находящегося в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь обновлен"
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User foundUser = userService.getUserById(user.getId());
        if (foundUser == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundUser);
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет удалить пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь удалён"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@RequestBody User user){
        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить всех пользователей",
            description = "Позволяет получить всех действительных пользователей из базы данных"
    )
    @GetMapping("/getAll")
    public Collection<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
