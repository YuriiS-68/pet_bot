package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.Message;
import org.springframework.transaction.annotation.Transactional;
import sky.pro.pet_bot.model.User;

import java.util.Collection;

public interface UserServiceInterface {
    boolean existChatId(Long chatId);

    User saveUser(Message message);

    User addUser(User user);

    void deleteUser(User user);

    void updateUser(User user);

    void updateUserTypeShelter(User user);

    void updateUserMessageId(Long chatId, Integer messageId);

    void updateUserVolunteerId(Long userId, Long volunteerId);

    User getUserById(Long id);

    Collection<User> getAllUsers();
}
