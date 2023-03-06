package pro.sky.telegramcatdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegramcatdog.model.AdoptionReport;

@Repository
public interface AdoptionReportRepository extends JpaRepository<AdoptionReport, Long> {
}
