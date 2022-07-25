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
    @Query(value = "select * from pet_bot.public.volunteers as v where v.status = :status limit 1", nativeQuery = true)
    Volunteer getVolunteerByStatus(@Param(value = "status") String status);
    @Query(value = "select v.id, v.name, v.phone_number, v.status from pet_bot.public.volunteers as v inner join users as u on " +
            "u.volunteer_id = v.id where u.id = :userId and v.status = :status", nativeQuery = true)
    Volunteer getVolunteerByStatusAndUserId(@Param(value = "userId") Long userId, @Param(value = "status") String status);
    @Query(value = "select v.id, v.name, v.phone_number, v.status from pet_bot.public.volunteers as v inner join users as u on " +
            "u.volunteer_id = v.id where u.id = :userId and v.status = :status", nativeQuery = true)
    Collection<Volunteer> findVolunteersByUserId(@Param(value = "userId") Long userId, @Param(value = "status") String status);
    @Modifying
    @Query("update Volunteer v set v.status = :status where v.id = :id")
    void updateVolunteerStatus(@Param(value = "id") Long id, @Param(value = "status") Volunteer.VolunteersStatus status);

}
