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
                    sendStartMenu(chatId, GREETINGS_MESSAGE);
                    //userServiceInterface.saveUser(message);
                }

                sendStartMenu(chatId, CHOOSE_SHELTER);


            } else {
                GetUpdatesResponse updatesResponse = telegramBot.execute(new GetUpdates());
                List<Update> callbackUpdates = updatesResponse.updates();
                callbackUpdates.forEach(callbackUpdate -> {
                    CallbackQuery callbackQuery = callbackUpdate.callbackQuery();
                    Long callbackChatId = callbackQuery.message().chat().id();
                    String callbackUserName = callbackQuery.message().from().username();
                    sendChooseMenu(callbackChatId, callbackQuery.data());
                });

                /*GetUpdatesResponse updatesChooseMenuResponse = telegramBot.execute(new GetUpdates());
                List<Update> callbackChooseMenuUpdates = updatesChooseMenuResponse.updates();
                callbackChooseMenuUpdates.forEach(callbackChooseMenuUpdate -> {
                    CallbackQuery callbackQuery = callbackChooseMenuUpdate.callbackQuery();
                    Long callbackChatId = callbackQuery.message().chat().id();
                });*/

                try{
                    //save user
                }catch (IllegalFormatException | DateTimeParseException e){

                    logger.error("Saving to DB failed:", e);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendStartMenu(long chatId, String text){
        logger.info("Method sendMessage has been run: {}, {}", chatId, text);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(createKeyboardMenu.startKeyboard())
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);

        sendResponse(request);
    }

    private void sendChooseMenu(long chatId, String kindPet){
        logger.info("Method sendMessage has been run: {}, {}", chatId, kindPet);

        SendMessage request = new SendMessage(chatId, kindPet)
                .replyMarkup(createKeyboardMenu.createChooseMenu(kindPet))
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
