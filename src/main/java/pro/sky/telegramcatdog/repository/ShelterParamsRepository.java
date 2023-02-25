package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegramcatdog.model.ShelterParams;

@Repository
public interface ShelterParamsRepository extends JpaRepository<ShelterParams, Integer> {

}