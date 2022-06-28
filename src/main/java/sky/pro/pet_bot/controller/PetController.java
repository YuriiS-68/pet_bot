package sky.pro.pet_bot.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.pet_bot.model.Pet;
import sky.pro.pet_bot.model.Picture;
import sky.pro.pet_bot.service.impl.PetServiceInterfaceImpl;
import sky.pro.pet_bot.service.impl.PictureServiceInterfaceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/pets")
public class PetController {

private final PetServiceInterfaceImpl petServiceInterfaceImpl;
private final PictureServiceInterfaceImpl pictureServiceInterfaceImpl;

    public PetController(PetServiceInterfaceImpl petServiceInterfaceImpl, PictureServiceInterfaceImpl pictureServiceInterfaceImpl) {
        this.petServiceInterfaceImpl = petServiceInterfaceImpl;
        this.pictureServiceInterfaceImpl = pictureServiceInterfaceImpl;
    }

    @GetMapping ("/{id}")
    public Pet getPetById (@PathVariable Long id){
        return petServiceInterfaceImpl.getPetById(id);
    }

    @PostMapping
    public ResponseEntity <Pet> addPet (@RequestBody Pet pet){
        return ResponseEntity.ok(petServiceInterfaceImpl.addUserPet(pet));
    }

    @PostMapping(value = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPic (@PathVariable Long id, @RequestParam MultipartFile picture) throws IOException{
        pictureServiceInterfaceImpl.uploadPetPic(id, picture);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/picture-from-file")
    public void downloadPic(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Picture picture = pictureServiceInterfaceImpl.findPictureByPetId(id);
        Path path = Path.of(picture.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(picture.getMediaType());
            response.setContentLength((int) picture.getFileSize());
            is.transferTo(os);
        }}

    @PutMapping
    public ResponseEntity <Pet> updatePet (@RequestBody Pet pet){
        return ResponseEntity.ok(petServiceInterfaceImpl.updatePet(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Pet> deletePet (@PathVariable Long id){
    petServiceInterfaceImpl.deletePetById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllPets")
    public Collection<Pet> getAllPets (){
        return petServiceInterfaceImpl.getAllPets();
    }

}
