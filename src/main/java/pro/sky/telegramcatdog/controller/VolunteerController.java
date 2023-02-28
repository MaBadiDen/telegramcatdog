package pro.sky.telegramcatdog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegramcatdog.model.Volunteer;
import pro.sky.telegramcatdog.service.VolunteerService;

@RestController
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping("/createVolunteer")
    public Volunteer createVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }
    @PutMapping("{volunteerId}")
    public Volunteer updateVolunteer(@PathVariable long volunteerId,
                            @RequestBody Volunteer volunteer) {
        return volunteerService.updateVolunteer(volunteerId, volunteer);
    }
    @GetMapping("{volunteerId}")
    public ResponseEntity<Volunteer> findVolunteer(@PathVariable long volunteerId) {
        Volunteer findVolunteer = volunteerService.readVolunteer(volunteerId);
        if (findVolunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findVolunteer);
    }
    @DeleteMapping("{volunteerId}")
    public void deleteVolunteer(@PathVariable long volunteerId) {
        volunteerService.deleteVolunteer(volunteerId);
    }


}
