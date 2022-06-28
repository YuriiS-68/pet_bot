package sky.pro.pet_bot.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.pet_bot.model.Answer;
import sky.pro.pet_bot.model.Picture;
import sky.pro.pet_bot.service.impl.AnswerServiceInterfaceImpl;
import sky.pro.pet_bot.service.impl.PictureServiceInterfaceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerServiceInterfaceImpl answerServiceInterfaceImpl;
    private final PictureServiceInterfaceImpl pictureServiceInterfaceImpl;


    public AnswerController(AnswerServiceInterfaceImpl answerServiceInterfaceImpl, PictureServiceInterfaceImpl pictureServiceInterfaceImpl) {
        this.answerServiceInterfaceImpl = answerServiceInterfaceImpl;
        this.pictureServiceInterfaceImpl = pictureServiceInterfaceImpl;
    }

    @GetMapping("/{id}")
    public Answer getAnswerById (@PathVariable Long id){
        return answerServiceInterfaceImpl.getAnswerById(id);
    }

    @PostMapping
    public ResponseEntity <Answer> addAnswer (@RequestBody Answer answer){
        return ResponseEntity.ok(answerServiceInterfaceImpl.addAnswer(answer));
    }

    @PostMapping(value = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPic (@PathVariable Long id, @RequestParam MultipartFile picture) throws IOException {
        pictureServiceInterfaceImpl.uploadAnswerPic(id, picture);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/picture-from-file")
    public void downloadPic(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Picture picture = pictureServiceInterfaceImpl.findPictureByAnswerId(id);
        Path path = Path.of(picture.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(picture.getMediaType());
            response.setContentLength((int) picture.getFileSize());
            is.transferTo(os);
        }}


    @PutMapping
    public ResponseEntity<Answer> updateAnswer (@RequestBody Answer answer){
        return ResponseEntity.ok(answerServiceInterfaceImpl.update(answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Answer> deleteAnswer (@PathVariable Long id){
         answerServiceInterfaceImpl.deleteAnswer(id);
         return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllAnswers")
    public Collection<Answer> getAllAnswers (){
        return answerServiceInterfaceImpl.getAllAnswers();
    }
}
