package ru.edu.penzgtu.lab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edu.penzgtu.lab.dto.AuthorDto;
import ru.edu.penzgtu.lab.service.AuthorService;
import ru.edu.penzgtu.lab.baseresponse.BaseResponseService; // Добавлен импорт
import ru.edu.penzgtu.lab.baseresponse.ResponseWrapper;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Tag(name = "Авторы", description = "CRUD операции над авторами")
@Validated
public class AuthorController {

    private final AuthorService authorService;
    private final BaseResponseService baseResponseService; // Добавлено поле

    @GetMapping
    @Operation(summary = "Получить всех авторов", description = "Возвращает список всех авторов из базы данных")
    public ResponseWrapper<List<AuthorDto>> getAllAuthors() {
        return baseResponseService.wrapSuccessResponse(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить автора по ID", description = "Возвращает одного автора по указанному ID")
    @ApiResponse(responseCode = "200", description = "Автор найден", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
    })
    @ApiResponse(responseCode = "404", description = "Автор не найден", content = @Content)
    public ResponseWrapper<AuthorDto> getAuthorById(@PathVariable("id") @Min(0) Integer id) {
        return baseResponseService.wrapSuccessResponse(authorService.getAuthorById(id));
    }

    @PostMapping
    @Operation(summary = "Создать нового автора", description = "Добавляет нового автора в систему")
    public ResponseWrapper<AuthorDto> createAuthor(@RequestBody @Valid AuthorDto dto) {
        return baseResponseService.wrapSuccessResponse(authorService.createAuthor(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить существующего автора", description = "Обновляет данные автора по указанному ID")
    public ResponseWrapper<AuthorDto> updateAuthor(@PathVariable("id") @Min(0) Integer id,
                                                  @RequestBody @Valid AuthorDto dto) {
        return baseResponseService.wrapSuccessResponse(authorService.updateAuthor(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить автора по ID", description = "Удаляет автора из системы по указанному ID")
    public void deleteAuthor(@PathVariable("id") @Min(0) Integer id) {
        authorService.deleteAuthor(id);
    }
}