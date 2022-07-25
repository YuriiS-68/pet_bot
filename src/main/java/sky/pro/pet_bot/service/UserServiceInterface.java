package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.Message;
import sky.pro.pet_bot.model.User;

import java.util.Collection;

public interface UserServiceInterface {

    User findUserByChatId(Long chatId);
    boolean existChatId(Long chatId);

    boolean checkTypePetOfUser(User user);

    User saveUser(Message message);

    User addUser(User user);

    User getContactsDataUser(Long chatId, String input);

    void setTypeShelterUserAndUpdate(User user, String input);

    void deleteUser(User user);

    void updateUser(User user);

    User editUser(User user);

    void updateUserTypeShelter(User user);

    void updateUserMessageId(Long chatId, Integer messageId);

    void updateUserVolunteerId(Long userId, Long volunteerId);

    User getUserById(Long id);

    public Collection<User> getUsersWithPet();

    Collection<User> getAllUsers();
}
