package sky.pro.pet_bot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sky.pro.pet_bot.dao.ReportRepository;
import sky.pro.pet_bot.exception.NotFoundReportException;
import sky.pro.pet_bot.model.Report;
import sky.pro.pet_bot.model.User;
import sky.pro.pet_bot.model.Volunteer;
import sky.pro.pet_bot.service.ReportServiceInterface;
import sky.pro.pet_bot.service.UserServiceInterface;
import sky.pro.pet_bot.service.VolunteerServiceInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Service
public class ReportServiceInterfaceImpl implements ReportServiceInterface {
    //проверять наличие отчета в базе - отправлять запрос в базу отчетов раз в сутки
    //если отчета нет напомнить пользователю
    //если есть ... необходимо проеврить
    //если в отчете только фото, то запросить у пользователя текст
    //если в отчете только текст, то запросить фото
    //если количество символов в тексте отчета меньше 500, отправить предупреждение
    //когда истекает 30ти дневный триал период, он может быть продлен волонтером на 14 или 30 дней
    //если не продлен, триал период закончен и поздравляем пользователя
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceInterfaceImpl.class);

    private final Properties messagesProperties;
    private final ReportRepository reportRepository;
    private final UserServiceInterface userService;
    private final VolunteerServiceInterface volunteerService;
    private final TelegramBot telegramBot;

    private final LocalDateTime currentTime = LocalDateTime.now();
    private LocalDateTime penaltyTime;
    private Report lastReport;

    public ReportServiceInterfaceImpl(ReportRepository reportRepository, UserServiceInterface userService, VolunteerServiceInterface volunteerService, TelegramBot telegramBot) throws IOException {
        this.reportRepository = reportRepository;
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.telegramBot = telegramBot;
        this.messagesProperties = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("/messages.properties"),
                StandardCharsets.UTF_8));
    }

    @Scheduled(cron = "${interval-daily-at-21pm-cron}")
    public void checkingDailyReportFromUser(){
        sendNoticesUserAboutReport();
        sendMessageVolunteerAboutReportOverdue();
        sendMessageVolunteerAboutEndTrialPeriod();
    }

    @Scheduled(cron = "${interval-daily-at-21pm-cron}")
    public void checkingTrialPeriod(){

    }

    public Report saveReport(User user){
        Report newReport = new Report();
        newReport.setUser(user);
        newReport.setTimeSendingReport(currentTime);
        reportRepository.save(newReport);
        return newReport;
    }

    @Override
    public Long getIdLastReportIdByUserId(Long userId) {
        return reportRepository.getIdLastReportByUserId(userId);
    }

    @Override
    public LocalDateTime getTimeLastReportByUserId(Long userId) {
        return reportRepository.getTimeLastReportByUserId(userId);
    }

    @Override
    public String getValueLastReportTextColumn(Long reportId) {
        return reportRepository.getValueLastReportTextColumn(reportId);
    }

    @Override
    public Report getLastReportByUserId(Long userId) {
        return reportRepository.getLastReportByUserId(userId);
    }


    public boolean isExistReportByUserId(Long userId){
        logger.info("Run method isExistReportByUserId: {}", userId);
        boolean isExistReport = reportRepository.existsReportByUserId(userId);
        logger.info("isExistReport result: {}", isExistReport);
        return isExistReport;
    }

    public Report getReportById(Long id) {
        logger.info("Method getPetById is started");
        return reportRepository.findById(id).get();
    }

    public boolean addReport (Report report){
        return reportRepository.existsById(report);
    }

    @Override
    @Transactional
    public void updateReport(Long id, String description){
        reportRepository.updateReport(id, description);
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

    private void sendNoticesUserAboutReport(){
        List<User> usersWithPet = (List<User>) userService.getUsersWithPet();
        penaltyTime = currentTime.minusDays(1);
        if (!usersWithPet.isEmpty()){
            for(User user : usersWithPet){
                lastReport = reportRepository.getLastReportByUserId(user.getId());
                if (lastReport == null && !user.getStartTrialPeriod().atStartOfDay().equals(currentTime) ||
                        lastReport != null && lastReport.getTimeSendingReport().isBefore(penaltyTime)){
                    SendMessage message = new SendMessage(user.getChatId(),
                            messagesProperties.getProperty("REPORT_TIME_OVERDUE"));
                    telegramBot.execute(message);
                }
            }
        }
    }

    private void sendMessageVolunteerAboutReportOverdue(){
        List<User> usersWithPet = (List<User>) userService.getUsersWithPet();
        penaltyTime = currentTime.minusDays(2);
        if (!usersWithPet.isEmpty()) {
            for(User user : usersWithPet){
                lastReport = reportRepository.getLastReportByUserId(user.getId());
                if ((lastReport == null && !user.getStartTrialPeriod().atStartOfDay().equals(penaltyTime)) ||
                        (lastReport != null && lastReport.getTimeSendingReport().isBefore(penaltyTime))){
                    List<Volunteer> volunteers = (List<Volunteer>) volunteerService.findVolunteersByUserId(user.getId());
                    for(Volunteer volunteer : volunteers){
                        SendMessage message = new SendMessage(volunteer.getChatId(), "Усыновитель "
                                + user.getName() + " более двух дней не отправлял отчет о питомце\nСрочно свяжитесь с ним!");
                        telegramBot.execute(message);
                    }
                }
            }
        }
    }

    //если прошло 30 дней, юзеру ставится статус ЗАВЕРШЕН
    //волонтеру отправляется меню принять, продлить на 14, продлить на 30 или отклонить
    //если продливается юзеру ставится статус СТАРТ
    //если отклонен юзеру ставится статус ОТКАЗ
    private void sendMessageVolunteerAboutEndTrialPeriod(){
        List<User> usersWithPet = (List<User>) userService.getUsersWithPet();
        penaltyTime = currentTime.minusDays(30);
        if (!usersWithPet.isEmpty()) {
            for(User user : usersWithPet){
                lastReport = reportRepository.getLastReportByUserId(user.getId());
                if (lastReport != null && user.getEndTrialPeriod().atStartOfDay().equals(penaltyTime)){
                    List<Volunteer> volunteers = (List<Volunteer>) volunteerService.findVolunteersByUserId(user.getId());
                    for(Volunteer volunteer : volunteers){
                        InlineKeyboardMarkup menuForVolunteer = new InlineKeyboardMarkup(
                                new InlineKeyboardButton[]{});
                        SendMessage message = new SendMessage(volunteer.getChatId(), "У усыновителя "
                                + user.getName() + " закончился испытательный период\nВыбирите действие из меню ниже");
                        menuForVolunteer.addRow(new InlineKeyboardButton("ПРИНЯТЬ").callbackData(user.getChatId().toString()),
                                new InlineKeyboardButton("ПРОДЛИТЬ НА 14 ДНЕЙ").callbackData(user.getChatId().toString()),
                                new InlineKeyboardButton("ПРОДЛИТЬ НА 30 ДНЕЙ").callbackData(user.getChatId().toString()),
                                new InlineKeyboardButton("ОТКЛОНИТЬ").callbackData(user.getChatId().toString()));
                        message.replyMarkup(menuForVolunteer);
                        telegramBot.execute(message);
                    }
                }
            }
        }
    }
}
