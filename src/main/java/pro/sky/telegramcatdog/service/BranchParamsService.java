package pro.sky.telegramcatdog.service;

import org.springframework.stereotype.Service;
import pro.sky.telegramcatdog.exception.BranchNotFoundException;
import pro.sky.telegramcatdog.model.BranchParams;
import pro.sky.telegramcatdog.repository.BranchParamsRepository;

@Service
public class BranchParamsService {
    private final BranchParamsRepository branchParamsRepository;

    public BranchParamsService(BranchParamsRepository shelterParamsRepository) {
        this.branchParamsRepository = shelterParamsRepository;
    }

    public BranchParams getBranchById(int id) {
        BranchParams branchParams = branchParamsRepository.findById(id).orElse(null);
        if (branchParams == null) {
            throw new BranchNotFoundException(id);
        }
        return branchParams;
    }

    public BranchParams createBranch(BranchParams branchParams) {
        return branchParamsRepository.save(branchParams);
    }

    public BranchParams editBranch(BranchParams branchParams) {
        if (branchParamsRepository.findById(branchParams.getId()).orElse(null) == null) {
            return null;
        }
        return branchParamsRepository.save(branchParams);
    }
}
