package sky.pro.pet_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final String START_CMD = "/start";
    private static final String GREETINGS_MESSAGE = "Welcome to Pet Finder!\nChoose one of the items on this menu:";
    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
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
            Long chatId = message.chat().id();
            if (update.message() != null && update.message().text().equals(START_CMD)){
                sendMenu(chatId);
            } else {
                try{

                }catch (IllegalFormatException | DateTimeParseException e){

                    logger.error("Saving to DB failed:", e);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMenu(long chatId){
        logger.info("Method sendMessage has been run: {}, {}", chatId, TelegramBotUpdatesListener.GREETINGS_MESSAGE);

        SendMessage request = new SendMessage(chatId, TelegramBotUpdatesListener.GREETINGS_MESSAGE)
                .replyMarkup(createKeyboard())
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);

        SendResponse sendResponse = telegramBot.execute(request);
        if (!sendResponse.isOk()){
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            logger.info("code of error: {}", codeError);
            logger.info("description -: {}", description);
        }
    }

    private InlineKeyboardMarkup createKeyboard(){
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[][]{
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Узнать информацию о приюте").callbackData("1")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Как взять собаку из приюта").callbackData("2")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Прислать отчет о питомце").callbackData("3")
                        },
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Позвать волонтера").callbackData("4")
                        }
                });
    }
}
