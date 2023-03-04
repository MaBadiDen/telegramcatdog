package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegramcatdog.model.Adopter;

public interface AdoptersRepository extends JpaRepository<Adopter, Long> {
}
