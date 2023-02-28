package pro.sky.telegramcatdog.service;

import org.springframework.stereotype.Service;
import pro.sky.telegramcatdog.model.BranchParams;
import pro.sky.telegramcatdog.repository.BranchParamsRepository;

@Service
public class BranchParamsService {
    private final BranchParamsRepository shelterParamsRepository;

    public BranchParamsService(BranchParamsRepository shelterParamsRepository) {
        this.shelterParamsRepository = shelterParamsRepository;
    }

    public BranchParams createBranch(BranchParams branchParams) {
        return shelterParamsRepository.save(branchParams);
    }

    public BranchParams editBranch(BranchParams branchParams) {
        if (shelterParamsRepository.findById(branchParams.getId()).orElse(null) == null) {
            return null;
        }
        return shelterParamsRepository.save(branchParams);
    }
}
