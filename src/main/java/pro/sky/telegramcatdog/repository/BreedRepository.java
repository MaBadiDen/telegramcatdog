package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegramcatdog.model.Breed;


@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

}