package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.pet_bot.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
