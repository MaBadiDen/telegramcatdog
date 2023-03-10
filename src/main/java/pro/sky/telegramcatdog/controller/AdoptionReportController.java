package pro.sky.telegramcatdog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegramcatdog.model.AdoptionReport;
import pro.sky.telegramcatdog.model.Volunteer;
import pro.sky.telegramcatdog.service.AdoptionReportService;

@RestController
public class AdoptionReportController {

    private final AdoptionReportService adoptionReportService;

    public AdoptionReportController(AdoptionReportService adoptionReportService) {
        this.adoptionReportService = adoptionReportService;
    }

    @Operation(
            summary = "Добавление нового ежедневного отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый ежедневный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Работа с ежедневными отчетами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые  ежедневный отчет",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            )
    )
    @PostMapping("/createAdoptionReport")
    public AdoptionReport createAdoptionReport(@RequestBody AdoptionReport adoptionReport) {
        return adoptionReportService.createAdoptionReport(adoptionReport);
    }



    @Operation(
            summary = "Редактирование ежедневного отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный ежедневный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Работа с ежедневными отчетами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые параметры ежедневного отчета",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            )
    )
    @PutMapping("{adoptionReportId}")
    public AdoptionReport updateAdoptionReport(@PathVariable long adoptionReportId,
                            @RequestBody AdoptionReport adoptionReport) {
        return adoptionReportService.updateAdoptionReport(adoptionReportId, adoptionReport);
    }


    @Operation(
            summary = "Поиск ежедневного отчета по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден ежедневный отчет ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Работа с ежедневными отчетами"
    )
    @GetMapping("{adoptionReportId}")
    public ResponseEntity<AdoptionReport> findAdoptionReport(@PathVariable long adoptionReportId) {
        AdoptionReport findAdoptionReport = adoptionReportService.readAdoptionReport(adoptionReportId);
        if (findAdoptionReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findAdoptionReport);
    }


    @Operation(
            summary = "Удаление ежедневного отчета по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ежедневный отчет удален",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Работа с ежедневными отчетами"
    )
    @DeleteMapping("{adoptionReportId}")
    public void deleteAdoptionReport(@PathVariable long adoptionReportId) {
        adoptionReportService.deleteAdoptionReport(adoptionReportId);
    }
}
