package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.pet_bot.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    boolean existsByUserId(Long id);
}
