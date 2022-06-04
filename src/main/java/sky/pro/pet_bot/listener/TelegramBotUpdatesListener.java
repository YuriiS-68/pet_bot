package sky.pro.pet_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.model.User;
import sky.pro.pet_bot.service.impl.CreateKeyboardMenuImpl;
import sky.pro.pet_bot.service.impl.UserServiceInterfaceImpl;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final String START_CMD = "/start";
    private static final String GREETINGS_MESSAGE = "Welcome to Pet Finder!\nChoose one of the items on this menu:";
    private static final String CHOOSE_SHELTER = "Make your choice from the menu below:";

    private static final String ABOUT_SHELTER_DOG = "Наш приют бла бла бла... для собак";
    private static final String ABOUT_SHELTER_CAT = "Наш приют бла бла бла... для кошек";


    private static final String OPENING_HOURS_DOG = "Адрес приюта\nНи дом и ни улица\nРаботаем круглосуточно";
    private static final String OPENING_HOURS_CAT = "Адрес приюта\nНи дом и ни улица\nРаботаем c 07:00 до 21:00";

    private final TelegramBot telegramBot;
    private final UserServiceInterfaceImpl userServiceInterface;
    private final CreateKeyboardMenuImpl createKeyboardMenu;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, UserServiceInterfaceImpl userServiceInterface, CreateKeyboardMenuImpl createKeyboardMenu) {
        this.telegramBot = telegramBot;
        this.userServiceInterface = userServiceInterface;
        this.createKeyboardMenu = createKeyboardMenu;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            Message message = update.message();

            if (update.message() != null && update.message().text().equals(START_CMD)){
                Long chatId = message.chat().id();

                if (!userServiceInterface.existChatId(chatId)){
                    sendMenu(chatId, GREETINGS_MESSAGE, createKeyboardMenu.startKeyboard());
                    try{
                        userServiceInterface.saveUser(message);
                    }catch (IllegalFormatException | DateTimeParseException e){
                        logger.error("Saving to DB failed:", e);
                    }
                } else {
                    sendMenu(chatId, CHOOSE_SHELTER, createKeyboardMenu.startKeyboard());
                }

            } else {
                GetUpdatesResponse updatesResponse = telegramBot.execute(new GetUpdates());
                List<Update> callbackUpdates = updatesResponse.updates();
                callbackUpdates.forEach(callbackUpdate -> {
                    CallbackQuery callbackQuery = callbackUpdate.callbackQuery();
                    Long callbackChatId = callbackQuery.message().chat().id();

                    if (callbackQuery.data().equals("DOG SHELTER") || callbackQuery.data().equals("CAT SHELTER")){
                        sendMenu(callbackChatId, callbackQuery.data(),
                                createKeyboardMenu.createChooseMenu(callbackQuery.data()));
                    }

                    if (callbackQuery.data().equals("INFO ABOUT DOG SHELTER") || callbackQuery.data().equals("INFO ABOUT CAT SHELTER")){
                        sendMenu(callbackChatId, callbackQuery.data(),
                                createKeyboardMenu.menuShelterKeyboard(callbackQuery.data()));
                    }

                    if (callbackQuery.data().equals("ABOUT OUR DOG SHELTER")){
                        logger.info("Pushed button: {}", callbackQuery.data());
                        sendMessage(callbackChatId, ABOUT_SHELTER_DOG);
                    }
                    if (callbackQuery.data().equals("ABOUT OUR CAT SHELTER")){
                        logger.info("Pushed button: {}", callbackQuery.data());
                        sendMessage(callbackChatId, ABOUT_SHELTER_CAT);
                    }

                    if (callbackQuery.data().equals("SHELTER DOG OPENING HOURS")){
                        logger.info("Pushed button: {}", callbackQuery.data());
                        sendMessage(callbackChatId, OPENING_HOURS_DOG);
                    }
                    if (callbackQuery.data().equals("SHELTER CAT OPENING HOURS")){
                        logger.info("Pushed button: {}", callbackQuery.data());
                        sendMessage(callbackChatId, OPENING_HOURS_CAT);
                    }

                });
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMenu(Long chatId, String text, InlineKeyboardMarkup keyboard){
        logger.info("Method sendMessage has been run: {}, {}, {}", chatId, text, keyboard);

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

    private void sendResponse(SendMessage request){
        SendResponse sendResponse = telegramBot.execute(request);
        if (!sendResponse.isOk()){
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            logger.info("code of error: {}", codeError);
            logger.info("description -: {}", description);
        }
    }
}
