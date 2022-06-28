package sky.pro.pet_bot.service;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.model.Answer;
import sky.pro.pet_bot.model.Pet;

import java.util.Collection;


public interface AnswerServiceInterface {
   Answer addAnswer(Answer answer);

    Answer getAnswerById(Long id);

    Collection<Answer> getAllAnswers();

}
