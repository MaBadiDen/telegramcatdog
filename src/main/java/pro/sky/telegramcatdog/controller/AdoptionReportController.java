package pro.sky.telegramcatdog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegramcatdog.model.AdoptionReport;
import pro.sky.telegramcatdog.service.AdoptionReportService;

@RestController
public class AdoptionReportController {

    private final AdoptionReportService adoptionReportService;

    public AdoptionReportController(AdoptionReportService adoptionReportService) {
        this.adoptionReportService = adoptionReportService;
    }

    @PostMapping("/createAdoptionReport")
    public AdoptionReport createAdoptionReport(@RequestBody AdoptionReport adoptionReport) {
        return adoptionReportService.createAdoptionReport(adoptionReport);
    }

    @PutMapping("{adoptionReportId}")
    public AdoptionReport updateAdoptionReport(@PathVariable long adoptionReportId,
                            @RequestBody AdoptionReport adoptionReport) {
        return adoptionReportService.updateAdoptionReport(adoptionReportId, adoptionReport);
    }

    @GetMapping("{adoptionReportId}")
    public ResponseEntity<AdoptionReport> findAdoptionReport(@PathVariable long adoptionReportId) {
        AdoptionReport findAdoptionReport = adoptionReportService.readAdoptionReport(adoptionReportId);
        if (findAdoptionReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findAdoptionReport);
    }
    @DeleteMapping("{adoptionReportId}")
    public void deleteAdoptionReport(@PathVariable long adoptionReportId) {
        adoptionReportService.deleteAdoptionReport(adoptionReportId);
    }
}
