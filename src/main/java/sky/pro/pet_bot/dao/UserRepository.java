package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sky.pro.pet_bot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User getUserById(Long id);
    boolean existsByChatId(Long chatId);
}
