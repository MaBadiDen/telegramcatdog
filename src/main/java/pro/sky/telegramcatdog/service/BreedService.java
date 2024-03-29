package pro.sky.telegramcatdog.service;

import org.springframework.stereotype.Service;
import pro.sky.telegramcatdog.exception.BreedNotFoundException;
import pro.sky.telegramcatdog.model.Breed;
import pro.sky.telegramcatdog.repository.BreedRepository;

@Service
public class BreedService {

    private final BreedRepository breedRepository;


    public BreedService(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    public Breed readBreed(long id) {
        Breed breed = breedRepository.findById(id).orElse(null);
        if (breed == null) {
            throw new BreedNotFoundException(id);
        }
        return breed;
    }

    public Breed createBreed(Breed breed) {
        return breedRepository.save(breed);
    }

    public Breed editBreed(Breed breed) {
        if (breedRepository.findById(breed.getId()).orElse(null) == null) {
            return null;
        }
        return breedRepository.save(breed);
    }


}
