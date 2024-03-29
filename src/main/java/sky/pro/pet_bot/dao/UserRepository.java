package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sky.pro.pet_bot.model.User;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long id);

    User getUserByChatId(Long chatId);

    boolean existsByChatId(Long chatId);

    @Query(value = "select u.* from users u inner join pets p on u.id = p.user_id", nativeQuery = true)
    Collection<User> getUsersWithPet();

    @Modifying
    @Query("update User u set u.lastName = :lastName, u.firstName = :firstName, u.email = :email, u.phoneNumber = :phoneNumber where u.id = :id")
    void updateUser(@Param(value = "id") Long id, @Param(value = "lastName") String lastName,
                    @Param(value = "firstName") String firstName, @Param(value = "email") String email,
                    @Param(value = "phoneNumber") String phoneNumber);
    @Modifying
    @Query("update User u set u.typeShelter = :typeShelter where u.id = :id")
    void updateUser(@Param(value = "id") Long id, @Param(value = "typeShelter") User.TypeShelters typeShelter);
    @Modifying
    @Query("update User u set u.messageId = :messageId where u.id = :id")
    void updateUserMessageId(@Param(value = "id") Long id, @Param(value = "messageId") Integer messageId);
    @Modifying
    @Query("update User u set u.volunteer.id = :volunteerId where u.id = :userId")
    void updateUserVolunteerId(@Param(value = "userId") Long userId, @Param(value = "volunteerId") Long volunteerId);
}
