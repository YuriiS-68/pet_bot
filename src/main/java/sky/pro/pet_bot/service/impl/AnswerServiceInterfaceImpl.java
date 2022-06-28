package sky.pro.pet_bot.service.impl;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.model.Answer;
import sky.pro.pet_bot.service.AnswerServiceInterface;

@Service
public class AnswerServiceInterfaceImpl implements AnswerServiceInterface {
    @Override
    public Answer getMappingAnswer(Message message) {
        return null;
    }
}
