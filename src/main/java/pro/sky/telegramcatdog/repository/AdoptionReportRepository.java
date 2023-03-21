package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegramcatdog.model.AdoptionReport;

import java.time.LocalDateTime;

@Repository
public interface AdoptionReportRepository extends JpaRepository<AdoptionReport, Long> {

    public AdoptionReport findByReportDate_Date(LocalDateTime day);

}
