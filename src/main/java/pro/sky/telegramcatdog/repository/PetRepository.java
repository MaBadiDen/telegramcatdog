package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegramcatdog.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
