package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

public interface MessageHandlerServiceInterface {
    void handleMessage(Message message, Update update);
}
