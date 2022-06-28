package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.pet_bot.model.Picture;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {
    Optional<Picture> findByPetId(Long petId);

    Optional <Picture> findByAnswerId(Long answerId);
}
