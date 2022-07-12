package sky.pro.pet_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.pet_bot.model.Picture;
import sky.pro.pet_bot.model.Report;
import sky.pro.pet_bot.service.PictureServiceInterface;
import sky.pro.pet_bot.service.ReportServiceInterface;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/report")
@Tag(name = "Контроллер отчетов", description = "добавление, обновление, удаление и другие операции с отчетами")
public class ReportController {

    private final ReportServiceInterface reportService;
    private final PictureServiceInterface pictureService;


    public ReportController(ReportServiceInterface reportService, PictureServiceInterface pictureService) {
        this.reportService = reportService;
        this.pictureService = pictureService;
    }

    @Operation(
            summary = "Поиск отчета",
            description = "Позволяет найти отчет по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный отчет"
                    )
            }
    )
    @GetMapping("/{id}")
    public Report getReportById (@PathVariable Long id){
        return reportService.getReportById(id);
    }

    @Operation(
            summary = "Добавление отчета",
            description = "Позволяет добавить отчет в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет добавлен"
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity <Boolean> addReport (@RequestBody Report report){
        return ResponseEntity.ok(reportService.addReport(report));
    }

    @Operation(
            summary = "Обновление отчета",
            description = "Позволяет обновить данные отчета в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет обновлен"
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity<Report> updateReport (@RequestBody Report report){
        Report findReport = reportService.getReportById(report.getId());
        if (findReport == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportService.updateReport(findReport));
    }

    @Operation(
            summary = "Удаление отчета",
            description = "Позволяет удалить отчет из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет удален"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Report> deleteReport (@PathVariable Long id){
        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить список всех отчетов",
            description = "Позволяет получить все отчеты находящиеся в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список отчетов получен"
                    )
            }
    )
    @GetMapping("/getAll")
    public Collection<Report> getAllReports (){
        return reportService.getAllReports();
    }
}
