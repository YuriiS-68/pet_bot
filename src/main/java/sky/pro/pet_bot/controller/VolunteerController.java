package sky.pro.pet_bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.pet_bot.model.Volunteer;
import sky.pro.pet_bot.service.impl.VolunteerServiceInterfaceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerServiceInterfaceImpl volunteerService;

    public VolunteerController(VolunteerServiceInterfaceImpl volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id){
        return ResponseEntity.ok(volunteerService.getVolunteerById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addVolunteer (@RequestBody Volunteer volunteer){
        return ResponseEntity.ok(volunteerService.addVolunteer(volunteer.getName(), volunteer.getPhoneNumber()));
    }

    @PutMapping("/update")
    public ResponseEntity<Volunteer> updateVolunteer (@RequestBody Volunteer volunteer){
        Volunteer findVolunteer = volunteerService.getVolunteerById(volunteer.getId());
        if (findVolunteer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteerService.updateVolunteer(findVolunteer));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Volunteer> deleteVolunteer (@PathVariable Long id){
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public Collection<Volunteer> getAllVolunteers(){
        return volunteerService.getAllVolunteers();
    }
}
