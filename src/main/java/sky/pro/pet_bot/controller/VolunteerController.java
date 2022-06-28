package sky.pro.pet_bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.pet_bot.model.Volunteer;
import sky.pro.pet_bot.service.impl.VolunteerServiceInterfaceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerServiceInterfaceImpl volunteerServiceInterfaceImpl;

    public VolunteerController(VolunteerServiceInterfaceImpl volunteerServiceInterface) {
        this.volunteerServiceInterfaceImpl = volunteerServiceInterface;
    }

    @GetMapping("/{id}")
    public Collection<Volunteer> getVolunteerById(@PathVariable Long id){
        return volunteerServiceInterfaceImpl.getVolunteerById(id);
    }

    @PostMapping
    public ResponseEntity<Volunteer> addVolunteer (@RequestBody Volunteer volunteer){
        return ResponseEntity.ok(volunteerServiceInterfaceImpl.addVolunteer(volunteer));
    }

    @PutMapping
    public ResponseEntity<Volunteer> updateVolunteer (@RequestBody Volunteer volunteer){
        return ResponseEntity.ok(volunteerServiceInterfaceImpl.update(volunteer));}

    @DeleteMapping("/{id}")
    public ResponseEntity<Volunteer> deleteVolunteer (@PathVariable Long id){
        volunteerServiceInterfaceImpl.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Collection<Volunteer> getAllVolunteers(){
        return volunteerServiceInterfaceImpl.getAllVolunteers();
    }
}
