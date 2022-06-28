package sky.pro.pet_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.dao.AnswerRepository;
import sky.pro.pet_bot.model.Answer;
import sky.pro.pet_bot.service.AnswerServiceInterface;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class AnswerServiceInterfaceImpl implements AnswerServiceInterface {

    private final AnswerRepository answerRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceInterfaceImpl.class);

    public AnswerServiceInterfaceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Answer addAnswer(Answer answer) {
        logger.info("Method addAnswer starts");
        return answerRepository.save(answer);
    }

    @Override
    public Answer getAnswerById(Long id) {
        logger.info("Method getAnswerById is started");
        return answerRepository.findById(id).get();
    }

    @Override
    public Collection<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Answer update(Answer answer) {
        return answerRepository.save(answer);
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
