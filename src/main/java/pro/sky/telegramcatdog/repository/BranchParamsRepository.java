package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegramcatdog.model.BranchParams;

@Repository
public interface BranchParamsRepository extends JpaRepository<BranchParams, Integer> {

}
