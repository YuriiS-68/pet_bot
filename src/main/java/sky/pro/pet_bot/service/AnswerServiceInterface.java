package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.model.Answer;

/**
 * Интерфейс, содержащий методы для работы с общими ответами
 */
@Service
public interface AnswerServiceInterface {
    Answer getMappingAnswer(Message message);
}
