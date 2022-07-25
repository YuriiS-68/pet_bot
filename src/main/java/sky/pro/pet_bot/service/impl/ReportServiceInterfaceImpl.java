package sky.pro.pet_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.pet_bot.dao.ReportRepository;
import sky.pro.pet_bot.exception.NotFoundReportException;
import sky.pro.pet_bot.model.Report;
import sky.pro.pet_bot.service.ReportServiceInterface;

import java.util.Collection;

@Service
public class ReportServiceInterfaceImpl implements ReportServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceInterfaceImpl.class);
    private final ReportRepository reportRepository;

    public ReportServiceInterfaceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report getReportById(Long id) {
        logger.info("Method getPetById is started");
        return reportRepository.findById(id).get();
    }

    public boolean addReport (Report report){
        return reportRepository.existsById(report);
    }

    public Report updateReport (Report report){
        reportRepository.save(report);
        return report;
    }

    public void deleteReport(Long id){
        long numOfReportDelete = reportRepository.deleteReportById(id);
        if (numOfReportDelete != 1){
            String errorMessage = "No such id " + id + " found in the DB";
            logger.error(errorMessage);
            throw new NotFoundReportException(errorMessage);
        }
    }

    public Collection<Report> getAllReports (){
        return reportRepository.findAll();
    }
}
