package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.model.Answer;

@Service
public interface AnswerServiceInterface {
    Answer getMappingAnswer(Message message);
}
