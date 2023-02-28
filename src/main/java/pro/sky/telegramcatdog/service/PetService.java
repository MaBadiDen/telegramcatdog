package pro.sky.telegramcatdog.service;

import org.springframework.stereotype.Service;
import pro.sky.telegramcatdog.exception.PetNotFoundException;
import pro.sky.telegramcatdog.model.Pet;
import pro.sky.telegramcatdog.repository.PetRepository;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet findPet(long id) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            throw new PetNotFoundException(id);
        }
        return pet;
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet editPet(Pet pet) {
        if (petRepository.findById(pet.getId()).orElse(null) == null) {
            return null;
        }
        return petRepository.save(pet);
    }
}
