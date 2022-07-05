package sky.pro.pet_bot.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.pet_bot.model.Picture;
import sky.pro.pet_bot.model.Report;
import sky.pro.pet_bot.service.impl.PictureServiceInterfaceImpl;
import sky.pro.pet_bot.service.impl.ReportServiceInterfaceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportServiceInterfaceImpl reportService;
    private final PictureServiceInterfaceImpl pictureServiceInterfaceImpl;


    public ReportController(ReportServiceInterfaceImpl reportService, PictureServiceInterfaceImpl pictureServiceInterfaceImpl) {
        this.reportService = reportService;
        this.pictureServiceInterfaceImpl = pictureServiceInterfaceImpl;
    }

    @GetMapping("/{id}")
    public Report getReportById (@PathVariable Long id){
        return reportService.getReportById(id);
    }

    @PostMapping("/add")
    public ResponseEntity <Boolean> addReport (@RequestBody Report report){
        return ResponseEntity.ok(reportService.addReport(report));
    }

    @PutMapping("/update")
    public ResponseEntity<Report> updateReport (@RequestBody Report report){
        Report findReport = reportService.getReportById(report.getId());
        if (findReport == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportService.updateReport(findReport));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Report> deleteReport (@PathVariable Long id){
        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllReports")
    public Collection<Report> getAllReports (){
        return reportService.getAllReports();
    }

    @PostMapping(value = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPic (@PathVariable Long id, @RequestParam MultipartFile picture) throws IOException {
        //pictureServiceInterfaceImpl.uploadAnswerPic(id, picture);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/picture-from-file")
    public void downloadPic(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Picture picture = pictureServiceInterfaceImpl.findPictureByReportId(id);
        Path path = Path.of(picture.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(picture.getMediaType());
            response.setContentLength((int) picture.getFileSize());
            is.transferTo(os);
        }}

}
