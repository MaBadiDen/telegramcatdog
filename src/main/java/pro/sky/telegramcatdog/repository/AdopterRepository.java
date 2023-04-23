package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegramcatdog.constants.AdopterStatus;
import pro.sky.telegramcatdog.model.Adopter;

import java.util.List;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    public Adopter findByChatId(long chatId);

    List<Adopter> findAdoptersByStatus(AdopterStatus status);
}
