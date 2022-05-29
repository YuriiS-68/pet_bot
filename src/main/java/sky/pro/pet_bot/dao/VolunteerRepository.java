package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.pet_bot.model.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer,Long> {
}
