package ru.edu.penzgtu.lab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edu.penzgtu.lab.dto.GenreDto;
import ru.edu.penzgtu.lab.service.GenreService;
import ru.edu.penzgtu.lab.baseresponse.BaseResponseService;
import ru.edu.penzgtu.lab.baseresponse.ResponseWrapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@Tag(name = "Жанры", description = "CRUD операции над жанрами")
@Validated
public class GenreController {

    private final GenreService genreService;
    private final BaseResponseService baseResponseService;

    @GetMapping
    @Operation(summary = "Получить все жанры", description = "Возвращает список всех жанров из базы данных")
    public ResponseWrapper<List<GenreDto>> getAllGenres() {
        return baseResponseService.wrapSuccessResponse(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить жанр по ID", description = "Возвращает один жанр по указанному ID")
    @ApiResponse(responseCode = "200", description = "Жанр найден", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
    })
    @ApiResponse(responseCode = "404", description = "Жанр не найден", content = @Content)
    public ResponseWrapper<GenreDto> getGenreById(@PathVariable("id") UUID id) {
        return baseResponseService.wrapSuccessResponse(genreService.getGenreById(id));
    }

    @PostMapping
    @Operation(summary = "Создать новый жанр", description = "Добавляет новый жанр в систему")
    public ResponseWrapper<GenreDto> createGenre(@RequestBody @Valid GenreDto dto) {
        return baseResponseService.wrapSuccessResponse(genreService.createGenre(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить существующий жанр", description = "Обновляет данные жанра по указанному ID")
    public ResponseWrapper<GenreDto> updateGenre(@PathVariable("id") UUID id,
                                                 @RequestBody @Valid GenreDto dto) {
        return baseResponseService.wrapSuccessResponse(genreService.updateGenre(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить жанр по ID", description = "Удаляет жанр из системы по указанному ID")
    public void deleteGenre(@PathVariable("id") UUID id) {
        genreService.deleteGenre(id);
    }
}