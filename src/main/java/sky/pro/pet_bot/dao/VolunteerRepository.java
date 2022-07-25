package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sky.pro.pet_bot.model.Volunteer;

import java.util.Collection;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer getVolunteerById(Long id);
    boolean existsVolunteerByNameAndPhoneNumber(String name, String phoneNumber);
    long deleteVolunteerById(Long id);
    @Query(value = "select * from volunteers v where v.status = :status limit 1", nativeQuery = true)
    Volunteer getVolunteerByStatus(@Param(value = "status") String status);
    @Query(value = "select v.* from volunteers v inner join users u on u.volunteer_id = v.id where u.id = :userId and v.status = :status", nativeQuery = true)
    Volunteer getVolunteerByStatusAndUserId(@Param(value = "userId") Long userId, @Param(value = "status") String status);
    @Query(value = "select v.* from volunteers v inner join users u on u.volunteer_id = v.id where u.id = :userId", nativeQuery = true)
    Collection<Volunteer> getVolunteersByUserId(@Param(value = "userId") Long userId);
    @Query(value = "select v.* from volunteers v inner join users u on u.volunteer_id = v.id where u.id = :userId and v.status = :status", nativeQuery = true)
    Collection<Volunteer> getVolunteersByStatusAndUserId(@Param(value = "userId") Long userId, @Param(value = "status") String status);
    @Modifying
    @Query("update Volunteer v set v.status = :status where v.id = :id")
    void updateVolunteerStatus(@Param(value = "id") Long id, @Param(value = "status") Volunteer.VolunteersStatus status);

}
