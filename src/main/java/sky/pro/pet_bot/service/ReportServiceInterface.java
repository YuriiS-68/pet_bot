package sky.pro.pet_bot.service;

import sky.pro.pet_bot.model.Report;

import java.util.Collection;

/**
 * нтерфейс, содержащий методы для работы с отчетами пользователя
 */
public interface ReportServiceInterface {
    Report getReportById(Long id);
    boolean addReport (Report report);
    Report updateReport (Report report);
    public void deleteReport(Long id);
    Collection<Report> getAllReports ();
}
