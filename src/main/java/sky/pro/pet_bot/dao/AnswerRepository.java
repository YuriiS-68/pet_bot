package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.pet_bot.model.Answer;

/**
 * репозиторий для хранения стандартных ответов пользователю
 */

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
