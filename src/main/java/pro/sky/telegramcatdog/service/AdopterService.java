package pro.sky.telegramcatdog.service;

import org.springframework.stereotype.Service;
import pro.sky.telegramcatdog.exception.AdopterNotFoundException;
import pro.sky.telegramcatdog.model.Adopter;
import pro.sky.telegramcatdog.repository.AdopterRepository;

@Service
public class AdopterService {
    private final AdopterRepository adopterRepository;

    public AdopterService(AdopterRepository adopterRepository) {
        this.adopterRepository = adopterRepository;
    }

    public Adopter createAdopter(Adopter adopter) {
        return adopterRepository.save(adopter);
    }

    public Adopter readAdopter(long id) {
        Adopter adopter = adopterRepository.findById(id).orElse(null);
        if (adopter == null) {
            throw new AdopterNotFoundException(id);
        }
        return adopter;
    }

    public Adopter updateAdopter(Adopter adopter) {
        if (adopterRepository.findById(adopter.getId()).orElse(null) == null) {
            return null;
        }
        return adopterRepository.save(adopter);
    }
}
