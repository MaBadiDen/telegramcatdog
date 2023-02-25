package pro.sky.telegramcatdog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegramcatdog.model.ShelterParams;
import pro.sky.telegramcatdog.service.ShelterParamsService;

@RestController
@RequestMapping("shelter")
public class ShelterParamsController {
    private final ShelterParamsService shelterParamsService;

    public ShelterParamsController(ShelterParamsService shelterParamsService) {
        this.shelterParamsService = shelterParamsService;
    }

    @Operation(
            summary = "Добавление нового бранча",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый бранч с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterParams.class)
                            )
                    )
            },
            tags = "Shelter",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового бранча",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShelterParams.class)
                    )
            )
    )
    @PostMapping // POST http://localhost:8080/shelter
    public ShelterParams createNewBranch(@RequestBody ShelterParams shelterParams) {
        return shelterParamsService.createNewBranch(shelterParams);
    }

}
