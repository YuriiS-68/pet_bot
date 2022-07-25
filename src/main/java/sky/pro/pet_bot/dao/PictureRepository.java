package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sky.pro.pet_bot.model.Picture;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {
    Picture findPictureByReportId(Long id);

    Picture findPictureById(Long id);

    @Query(value = "select file_size from pictures where report_id = :id", nativeQuery = true)
    Integer getFileSizeByReportId(@Param(value = "id") Long id);
}
