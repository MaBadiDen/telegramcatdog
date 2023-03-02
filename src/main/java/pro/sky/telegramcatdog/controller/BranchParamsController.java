package pro.sky.telegramcatdog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegramcatdog.model.BranchParams;
import pro.sky.telegramcatdog.service.BranchParamsService;

@RestController
@RequestMapping("pet-shelter/params")
public class BranchParamsController {
    private final BranchParamsService branchParamsService;

    public BranchParamsController(BranchParamsService branchParamsService) {
        this.branchParamsService = branchParamsService;
    }

    @Operation(
            summary = "Добавление нового бранча",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый бранч с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BranchParams.class)
                            )
                    )
            },
            tags = "Работа с бранчами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового бранча",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BranchParams.class)
                    )
            )
    )
    @PostMapping
    public BranchParams createBranch(@RequestBody BranchParams branchParams) {
        return branchParamsService.createBranch(branchParams);
    }

    @Operation(
            summary = "Редактирование бранча",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный бранч с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BranchParams.class)
                            )
                    )
            },
            tags = "Работа с бранчами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые параметры бранча",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BranchParams.class)
                    )
            )
    )
    @PutMapping
    public BranchParams editBranch(@RequestBody BranchParams branchParams) {
        return branchParamsService.editBranch(branchParams);
    }
}
