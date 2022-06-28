package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.pet_bot.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
