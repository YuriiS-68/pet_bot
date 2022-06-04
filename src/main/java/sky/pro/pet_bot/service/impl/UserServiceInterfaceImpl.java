package sky.pro.pet_bot.service.impl;

import com.pengrad.telegrambot.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.dao.UserRepository;
import sky.pro.pet_bot.model.User;
import sky.pro.pet_bot.service.UserServiceInterface;

import java.util.Collection;
import java.util.Objects;

@Service
public class UserServiceInterfaceImpl implements UserServiceInterface {

    private final Logger logger = LoggerFactory.getLogger(UserServiceInterfaceImpl.class);
    private final UserRepository userRepository;

    public UserServiceInterfaceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existChatId(Long chatId) {
        logger.info("Method existChatId is run: {}", chatId);
        return userRepository.existsByChatId(chatId);
    }

    @Override
    public User saveUser(Message message) {
        User user = new User();
        user.setChatId(message.chat().id());
        user.setName(message.from().username());
        if (message.location() != null) {
            user.setLocation(message.location().toString());
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }
}
