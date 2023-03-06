package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegramcatdog.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
