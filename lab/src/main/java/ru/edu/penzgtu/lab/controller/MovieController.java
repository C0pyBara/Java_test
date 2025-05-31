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
import ru.edu.penzgtu.lab.dto.MovieDto;
import ru.edu.penzgtu.lab.service.MovieService;
import ru.edu.penzgtu.lab.baseresponse.BaseResponseService;
import ru.edu.penzgtu.lab.baseresponse.ResponseWrapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Tag(name = "Фильмы", description = "CRUD операции над фильмами")
@Validated
public class MovieController {

    private final MovieService movieService;
    private final BaseResponseService baseResponseService;

    @GetMapping
    @Operation(summary = "Получить все фильмы", description = "Возвращает список всех фильмов из базы данных")
    public ResponseWrapper<List<MovieDto>> getAllMovies() {
        return baseResponseService.wrapSuccessResponse(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить фильм по ID", description = "Возвращает один фильм по указанному ID")
    @ApiResponse(responseCode = "200", description = "Фильм найден", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
    })
    @ApiResponse(responseCode = "404", description = "Фильм не найден", content = @Content)
    public ResponseWrapper<MovieDto> getMovieById(@PathVariable("id") UUID id) {
        return baseResponseService.wrapSuccessResponse(movieService.getMovieById(id));
    }

    @PostMapping
    @Operation(summary = "Создать новый фильм", description = "Добавляет новый фильм в систему")
    public ResponseWrapper<MovieDto> createMovie(@RequestBody @Valid MovieDto dto) {
        return baseResponseService.wrapSuccessResponse(movieService.createMovie(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить существующий фильм", description = "Обновляет данные фильма по указанному ID")
    public ResponseWrapper<MovieDto> updateMovie(@PathVariable("id") UUID id,
                                                 @RequestBody @Valid MovieDto dto) {
        return baseResponseService.wrapSuccessResponse(movieService.updateMovie(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить фильм по ID", description = "Удаляет фильм из системы по указанному ID")
    public void deleteMovie(@PathVariable("id") UUID id) {
        movieService.deleteMovie(id);
    }
}