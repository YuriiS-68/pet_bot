package sky.pro.pet_bot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.model.User;
import sky.pro.pet_bot.model.Volunteer;
import sky.pro.pet_bot.service.MessageHandlerServiceInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

@Service
public class MessageHandlerServiceInterfaceImpl implements MessageHandlerServiceInterface {
    private final Logger logger = LoggerFactory.getLogger(MessageHandlerServiceInterfaceImpl.class);

    private final Properties messagesProperties;
    private final TelegramBot telegramBot;
    private final UserServiceInterfaceImpl userServiceInterface;
    private final VolunteerServiceInterfaceImpl volunteerServiceInterface;
    private final CreateKeyboardMenuImpl createKeyboardMenu;

    private final Map<String, String> mapChooseMenu = Map.of(
            "ABOUT OUR DOG SHELTER", "ABOUT_SHELTER_DOG",
            "ABOUT OUR CAT SHELTER", "ABOUT_SHELTER_CAT",
            "SHELTER DOG OPENING HOURS", "OPENING_HOURS_DOG",
            "SHELTER CAT OPENING HOURS", "OPENING_HOURS_CAT",
            "SECURITY OF DOG SHELTER", "SECURITY_DOG_SHELTER",
            "SECURITY OF CAT SHELTER", "SECURITY_CAT_SHELTER",
            "SAFETY ADVICE OF DOG SHELTER", "SAFETY_ADVICE_DOG_SHELTER",
            "SAFETY ADVICE OF CAT SHELTER", "SAFETY_ADVICE_CAT_SHELTER"
    );

    private final Map<String, String> mapGetPetMenu = Map.of(
            "RULES MEETING", "RULES_MEETING",
            "LIST DOCUMENT", "LIST_DOCUMENT",
            "LIST TRANSPORTING", "LIST_TRANSPORTING",
            "LIST SETTING UP PUPPY", "LIST_SETTING_UP_PUPPY",
            "LIST SETTING UP KITTY", "LIST_SETTING_UP_KITTY",
            "LIST SETTING UP PET", "LIST_SETTING_UP_PET",
            "LIST SETTING UP PET DISABILITY", "LIST_SETTING_UP_PET_DISABILITY",
            "TIPS DOG BREEDER", "TIPS_DOG_BREEDER",
            "TRUSTED CYNOLOGISTS", "TRUSTED_CYNOLOGISTS"
    );

    public MessageHandlerServiceInterfaceImpl(TelegramBot telegramBot, UserServiceInterfaceImpl userServiceInterface,
                                              VolunteerServiceInterfaceImpl volunteerServiceInterface,
                                              CreateKeyboardMenuImpl createKeyboardMenu) throws IOException {
        this.telegramBot = telegramBot;
        this.userServiceInterface = userServiceInterface;
        this.volunteerServiceInterface = volunteerServiceInterface;
        this.createKeyboardMenu = createKeyboardMenu;
        this.messagesProperties = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("/messages.properties"),
                StandardCharsets.UTF_8));
    }

    @Override
    public void handleMessage(Message message, Update update) {
        if (update.message() != null && update.message().text().equals(messagesProperties.getProperty("START_CMD"))){
            Long chatId = message.chat().id();
            User currentUser = userServiceInterface.getUserByChatId(chatId);

            if (!userServiceInterface.existChatId(chatId)){
                sendMenu(chatId, messagesProperties.getProperty("GREETINGS_MESSAGE"), createKeyboardMenu.startMenu());
                try{
                    userServiceInterface.saveUser(message);
                }catch (IllegalFormatException | DateTimeParseException e){
                    logger.error("Saving to DB failed:", e);
                }
            } else {
                sendMenu(chatId, currentUser.getId(), messagesProperties.getProperty("CHOOSE_SHELTER"),
                        createKeyboardMenu.startMenu());
            }
        } else {
            GetUpdatesResponse updatesResponse = telegramBot.execute(new GetUpdates());
            List<Update> callbackUpdates = updatesResponse.updates();
            callbackUpdates.forEach(callbackUpdate -> {
                CallbackQuery callbackQuery = callbackUpdate.callbackQuery();
                if (callbackQuery != null){
                    Long callbackChatId = callbackQuery.message().chat().id();
                    User currentUserCallback = userServiceInterface.getUserByChatId(callbackChatId);

                    if (callbackQuery.data().equals("DOG SHELTER") || callbackQuery.data().equals("CAT SHELTER")){
                        userServiceInterface.setTypeShelterUserAndUpdate(currentUserCallback, callbackQuery.data());
                        userServiceInterface.updateUserTypeShelter(currentUserCallback);
                        logger.info("DOG SHELTER callbackQuery.id() = {}", callbackQuery.id());
                        logger.info("Block callbackQuery messageId = {}", currentUserCallback.getMessageId());

                        sendMenu(callbackChatId, callbackQuery.data(),
                                createKeyboardMenu.chooseMenu(callbackQuery.data()));
                        deleteMessage(callbackChatId, currentUserCallback.getMessageId());
                    }

                    if ( callbackQuery.data().equals("INFO ABOUT DOG SHELTER")
                            || callbackQuery.data().equals("INFO ABOUT CAT SHELTER")){
                        logger.info("INFO ABOUT callbackQuery.id() = {}", callbackQuery.id());
                        sendMenu(callbackChatId, callbackQuery.data(),
                                createKeyboardMenu.shelterMenu(callbackQuery.data()));
                    }

                    if (mapChooseMenu.containsKey(callbackQuery.data())){
                        sendMessage(callbackChatId,
                                messagesProperties.getProperty(mapChooseMenu.get(callbackQuery.data())));
                    }

                    if (callbackQuery.data().equals("LEAVE YOUR CONTACT DETAILS")){
                        sendMessageReply(callbackChatId,
                                messagesProperties.getProperty("REQUEST_DATA_USER"), new ForceReply());
                    }

                    if (callbackQuery.data().equals("CALL VOLUNTEER")){
                        Volunteer freeVolunteer = volunteerServiceInterface.getFreeVolunteerByStatusAndUserId(
                                currentUserCallback.getId(), Volunteer.VolunteersStatus.valueOf("FREE"));
                        logger.info("CurrentUserCallback id: {}", currentUserCallback.getId());
                        logger.info("Found free volunteer by status and id: {}", freeVolunteer);

                        if (freeVolunteer != null){
                            sendMessage(callbackChatId, callVolunteer(freeVolunteer));
                            volunteerServiceInterface.updateVolunteerStatus(freeVolunteer.getId(), Volunteer.VolunteersStatus.valueOf("BUSY"));
                            logger.info("Worked the condition freeVolunteer != null: {}", freeVolunteer);

                        } else if (volunteerServiceInterface.findFreeVolunteers(currentUserCallback.getId(), Volunteer.VolunteersStatus.valueOf("FREE")).isEmpty()
                                || !volunteerServiceInterface.findFreeVolunteers(currentUserCallback.getId(), Volunteer.VolunteersStatus.valueOf("BUSY")).isEmpty()){

                            Volunteer volunteer = volunteerServiceInterface.getFreeVolunteerByStatus("FREE");
                            if (volunteer == null){
                                sendMessage(callbackChatId, messagesProperties.getProperty("VOLUNTEERS_IS_BUSY"));
                            } else {
                                logger.info("Worked the condition volunteer == null: {}", volunteer);
                                sendMessage(callbackChatId, callVolunteer(volunteer));
                                userServiceInterface.updateUserVolunteerId(currentUserCallback.getId(), volunteer.getId());
                                volunteerServiceInterface.updateVolunteerStatus(volunteer.getId(), Volunteer.VolunteersStatus.valueOf("BUSY"));
                            }
                        }
                    }

                    if (callbackQuery.data().equals("HOW TAKE DOG") || callbackQuery.data().equals("HOW TAKE CAT")){
                        logger.info("Block create menuGetPet was started callbackQuery.data(): {}", callbackQuery.data());
                        sendMenu(callbackChatId, callbackQuery.data(),
                                createKeyboardMenu.getPetMenu(callbackQuery.data()));
                    }

                    if (mapGetPetMenu.containsKey(callbackQuery.data())){
                        sendMessage(callbackChatId,
                                messagesProperties.getProperty(mapGetPetMenu.get(callbackQuery.data())));
                    }

                    if (callbackQuery.data().equals("DOG REPORTS") || callbackQuery.data().equals("CAT REPORTS")){
                        sendMenu(callbackChatId, callbackQuery.data(), createKeyboardMenu.petManageMenu());
                    }

                    if (callbackQuery.data().equals("DAILY REPORT FORM")){
                        sendMessageReply(callbackChatId,
                                messagesProperties.getProperty("DAILY_REPORT_FORM"), new ForceReply());
                    }
                }
            });

            if (update.message() != null && update.message().text() != null){
                User userForUpdate = userServiceInterface.getContactsDataUser(update.message().chat().id(), update.message().text());
                userServiceInterface.updateUser(userForUpdate);
                sendMessage(update.message().chat().id(), messagesProperties.getProperty("SAVED_CONTACTS"));
            }
        }
    }



    private void deleteMessage(Long chatId, Integer messageId){
        logger.info("Method deleteMessage has been run: {}, {}", chatId, messageId);
        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
        BaseResponse response = telegramBot.execute(deleteMessage);
        logger.info("response: {}", response);
    }

    private void sendMenu(Long chatId, Long userId, String text, InlineKeyboardMarkup keyboard){
        logger.info("Method sendMenu 4parameters has been run: {}, {}, {}", chatId, text, keyboard);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);

        sendResponse(request, sendResponse -> userServiceInterface.updateUserMessageId(userId, sendResponse.message().messageId()));
    }

    private void sendMenu(Long chatId,String text, InlineKeyboardMarkup keyboard){
        logger.info("Method sendMenu 3parameters has been run: {}, {}, {}", chatId, text, keyboard);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);

        sendResponse(request);
    }

    private void sendMessageReply(Long chatId, String text, Keyboard keyboard){
        logger.info("Method sendMessageReply has been run: {}, {}, {}", chatId, text, keyboard);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(keyboard)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);

        sendResponse(request);
    }

    private void sendMessage(Long chatId, String text){
        logger.info("Method sendMessage has been run: {}, {}", chatId, text);

        SendMessage request = new SendMessage(chatId, text)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);

        sendResponse(request);
    }

    private SendResponse sendResponse(SendMessage request){
        SendResponse sendResponse = telegramBot.execute(request);
        if (!sendResponse.isOk()){
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            logger.info("code of error: {}", codeError);
            logger.info("description -: {}", description);
        }
        return sendResponse;
    }

    private void sendResponse(SendMessage request, Consumer<SendResponse> callback){
        SendResponse sendResponse = sendResponse(request);
        callback.accept(sendResponse);
    }

    private String callVolunteer(Volunteer volunteer){
        StringBuilder stringBuilder = new StringBuilder("Call volunteer ");
        return stringBuilder.append(volunteer.getName()).append(" ").append(volunteer.getPhoneNumber()).toString();
    }
}
