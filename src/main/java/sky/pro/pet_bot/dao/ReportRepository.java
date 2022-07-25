package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sky.pro.pet_bot.model.Report;
import sky.pro.pet_bot.model.Volunteer;

import java.time.LocalDateTime;

/**
 * репозиторий для хранения отчетов владельца животного
 */

public interface ReportRepository extends JpaRepository<Report,Long> {
    boolean existsById(Report report);

    boolean existsReportByUserId(Long id);

    @Query(value = "select time_sending_report from reports where user_id = :id order by time_sending_report desc limit 1", nativeQuery = true)
    LocalDateTime getTimeLastReportByUserId(@Param(value = "id") Long id);

    @Query(value = "select r.report_text from reports r where id = :id order by time_sending_report desc limit 1", nativeQuery = true)
    String getValueLastReportTextColumn(@Param(value = "id") Long id);

    @Query(value = "select * from pet_bot.public.reports where user_id = :id order by time_sending_report desc limit 1", nativeQuery = true)
    Report getLastReportByUserId(Long id);

    @Query(value = "select id from reports where user_id = :id order by time_sending_report desc limit 1", nativeQuery = true)
    Long getIdLastReportByUserId(Long id);

    @Modifying
    @Query("update Report r set r.reportText = :description where r.id = :id")
    void updateReport(@Param(value = "id") Long id, @Param(value = "description") String description);

    long deleteReportById(Long id);
}
