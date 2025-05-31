package ru.edu.penzgtu.lab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.penzgtu.lab.dto.StudioDto;
import ru.edu.penzgtu.lab.service.StudioService;
import ru.edu.penzgtu.lab.baseresponse.BaseResponseService;
import ru.edu.penzgtu.lab.baseresponse.ResponseWrapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/studios")
@RequiredArgsConstructor
@Tag(name = "Студии", description = "CRUD операции над студиями")
@Validated
public class StudioController {

    private final StudioService studioService;
    private final BaseResponseService baseResponseService;

    @GetMapping
    @Operation(summary = "Получить все студии", description = "Возвращает список всех студий из базы данных")
    public ResponseWrapper<List<StudioDto>> getAllStudios() {
        return baseResponseService.wrapSuccessResponse(studioService.getAllStudios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить студию по ID", description = "Возвращает одну студию по указанному ID")
    @ApiResponse(responseCode = "200", description = "Студия найдена", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
    })
    @ApiResponse(responseCode = "404", description = "Студия не найдена", content = @Content)
    public ResponseWrapper<StudioDto> getStudioById(@PathVariable("id") UUID id) {
        return baseResponseService.wrapSuccessResponse(studioService.getStudioById(id));
    }

    @PostMapping
    @Operation(summary = "Создать новую студию", description = "Добавляет новую студию в систему")
    public ResponseWrapper<StudioDto> createStudio(@RequestBody @Valid StudioDto dto) {
        return baseResponseService.wrapSuccessResponse(studioService.createStudio(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить существующую студию", description = "Обновляет данные студии по указанному ID")
    public ResponseWrapper<StudioDto> updateStudio(@PathVariable("id") UUID id,
                                                  @RequestBody @Valid StudioDto dto) {
        return baseResponseService.wrapSuccessResponse(studioService.updateStudio(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить студию по ID", description = "Удаляет студию из системы по указанному ID")
    public void deleteStudio(@PathVariable("id") UUID id) {
        studioService.deleteStudio(id);
    }
}