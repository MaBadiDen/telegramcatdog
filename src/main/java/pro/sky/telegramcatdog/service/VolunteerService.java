package pro.sky.telegramcatdog.service;

import org.springframework.stereotype.Service;
import pro.sky.telegramcatdog.exception.VolunteerNotFoundException;
import pro.sky.telegramcatdog.model.Volunteer;
import pro.sky.telegramcatdog.repository.VolunteerRepository;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer findVolunteer(int id) {
        Volunteer volunteer = volunteerRepository.findById(id).orElse(null);
        if (volunteer == null) {
            throw new VolunteerNotFoundException(id);
        }
        return volunteer;
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer editVolunteer(Volunteer volunteer) {
        if (volunteerRepository.findById(volunteer.getId()).orElse(null) == null) {
            return null;
        }
        return volunteerRepository.save(volunteer);
    }
}
