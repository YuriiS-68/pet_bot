package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sky.pro.pet_bot.model.Pet;

import java.util.Collection;


@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    boolean existsByUserId(Long id);

    @Query(value = "select p.* from pets p inner join users u on p.user_id = u.id where u.id = :userId", nativeQuery = true)
    Collection<Pet> getPetsByUserId (@Param(value = "userId") Long userId);
}
