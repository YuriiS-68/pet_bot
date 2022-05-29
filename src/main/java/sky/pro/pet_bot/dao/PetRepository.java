package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.pet_bot.model.Pet;

public interface PetRepository extends JpaRepository<Pet,Long> {
}
