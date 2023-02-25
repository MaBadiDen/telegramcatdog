package pro.sky.telegramcatdog.service;

import org.springframework.stereotype.Service;
import pro.sky.telegramcatdog.model.ShelterParams;
import pro.sky.telegramcatdog.repository.ShelterParamsRepository;

@Service
public class ShelterParamsService {
    private final ShelterParamsRepository shelterParamsRepository;

    public ShelterParamsService(ShelterParamsRepository shelterParamsRepository) {
        this.shelterParamsRepository = shelterParamsRepository;
    }

    public ShelterParams createNewBranch(ShelterParams shelterParams) {
        return shelterParamsRepository.save(shelterParams);
    }
}
