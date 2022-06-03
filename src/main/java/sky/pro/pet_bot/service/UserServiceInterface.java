package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.Message;
import sky.pro.pet_bot.model.User;

import java.util.Collection;

public interface UserServiceInterface {
    boolean existChatId(Long chatId);
    User saveUser(Message message);
    User getUserById(Long id);
    Collection<User> getAllUsers();
}
