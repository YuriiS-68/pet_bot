package sky.pro.pet_bot.service;

import org.springframework.data.repository.query.Param;
import sky.pro.pet_bot.model.Report;
import sky.pro.pet_bot.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * нтерфейс, содержащий методы для работы с отчетами пользователя
 */
public interface ReportServiceInterface {
    Report saveReport(User user);

    Long getIdLastReportIdByUserId(Long userId);

    LocalDateTime getTimeLastReportByUserId( Long userId);

    String getValueLastReportTextColumn(Long reportId);

    Report getLastReportByUserId(Long userId);

    void updateReport(Long id, String description);

    boolean isExistReportByUserId(Long userId);

    Report getReportById(Long id);

    boolean addReport (Report report);

    Report updateReport (Report report);

    public void deleteReport(Long id);

    Collection<Report> getAllReports ();
}
