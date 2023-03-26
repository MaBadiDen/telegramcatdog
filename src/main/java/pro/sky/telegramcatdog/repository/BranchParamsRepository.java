package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegramcatdog.constants.PetType;
import pro.sky.telegramcatdog.model.BranchParams;

import java.util.Optional;

@Repository
public interface BranchParamsRepository extends JpaRepository<BranchParams, Integer> {

     Optional<BranchParams> findByPetType(PetType petType);
}
