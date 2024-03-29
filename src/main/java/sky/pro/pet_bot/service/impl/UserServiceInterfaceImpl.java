package sky.pro.pet_bot.service.impl;

import com.pengrad.telegrambot.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sky.pro.pet_bot.dao.UserRepository;
import sky.pro.pet_bot.exception.AlreadyExistException;
import sky.pro.pet_bot.model.Pet;
import sky.pro.pet_bot.model.User;
import sky.pro.pet_bot.service.PetServiceInterface;
import sky.pro.pet_bot.service.UserServiceInterface;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceInterfaceImpl implements UserServiceInterface {

    private final Logger logger = LoggerFactory.getLogger(UserServiceInterfaceImpl.class);
    private final UserRepository userRepository;

    private final PetServiceInterface petService;

    public UserServiceInterfaceImpl(UserRepository userRepository, PetServiceInterface petService) {
        this.userRepository = userRepository;
        this.petService = petService;
    }

    @Override
    public User findUserByChatId(Long chatId) {
        return userRepository.getUserByChatId(chatId);
    }

    @Override
    public boolean existChatId(Long chatId) {
        logger.info("Method existChatId is run: {}", chatId);
        return userRepository.existsByChatId(chatId);
    }

    public void setTypeShelterUserAndUpdate(User user, String input){
        if (input.equals("DOG SHELTER")){
            user.setTypeShelter(User.TypeShelters.DOG_SHELTER);
        } else {
            user.setTypeShelter(User.TypeShelters.CAT_SHELTER);
        }
    }

    public User getContactsDataUser(Long chatId, String input){
        logger.info("Run method getContactsDataUser {}, {}:", chatId, input);

        User user = userRepository.getUserByChatId(chatId);
        Pattern pattern = Pattern.compile("([a-zA-ZА-Яа-я]+) ([a-zA-ZА-Яа-я]+)\\n(\\w+@\\w+\\.\\w+)" +
                "\\n((\\d{1,3}?|\\+\\d\\s?|\\+?|\\+\\d{3}\\s?|\\(?|-?)?\\d{2}(\\(?\\d{2}\\)?[\\- ]?)?[\\d\\- ]{7,10})");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()){
            String firstName = matcher.group(2);
            user.setFirstName(firstName);
            String lastName = matcher.group(1);
            user.setLastName(lastName);
            String email = matcher.group(3);
            user.setEmail(email);
            String phoneNumber = matcher.group(4);
            user.setPhoneNumber(phoneNumber);
        }
        return user;
    }

    @Override
    public User saveUser(Message message) {
        User user = new User();
        user.setChatId(message.chat().id());
        user.setMessageId(message.messageId());
        user.setName(message.from().username());
        if (message.location() != null) {
            user.setLocation(message.location().toString());
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public User addUser(User user) {
        if (userRepository.existsByChatId(user.getChatId())){
            String errorMessage = "This user " + user.getName() + " already exist in DB";
            logger.error(errorMessage);
            throw new AlreadyExistException(errorMessage);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user.getId(), user.getLastName(), user.getFirstName(), user.getEmail(),
                user.getPhoneNumber());
    }

    @Override
    public User editUser(User user) {
        if (existChatId(user.getChatId())) {
            return userRepository.save(user);
        }
        return null;
    }

    @Transactional
    @Override
    public void updateUserTypeShelter(User user) {
        userRepository.updateUser(user.getId(), user.getTypeShelter());
    }
    @Transactional
    @Override
    public void updateUserMessageId(Long userId, Integer messageId){
        userRepository.updateUserMessageId(userId, messageId);
    }

    @Transactional
    @Override
    public void updateUserVolunteerId(Long userId, Long volunteerId){
        userRepository.updateUserVolunteerId(userId, volunteerId);
    }

    public boolean checkTypePetOfUser(User user){
        Collection<Pet> pets = petService.getPetsByUserId(user.getId());

        if (pets.isEmpty()){
            return false;
        }

        for (Pet pet : pets) {
            if (user.getTypeShelter().toString().contains(pet.getType().toString())){
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public Collection<User> getUsersWithPet(){
        return userRepository.getUsersWithPet();
    }

    @Override
    public Collection<User> getAllUsers() {
        logger.info("Method getAllUsers is start");
        return userRepository.findAll();
    }
}
