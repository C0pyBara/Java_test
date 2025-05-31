package ru.edu.penzgtu.lab.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о фильме")
public class MovieDto {

    @JsonProperty("id")
    @Schema(description = "ID фильма в БД", example = "456")
    private Long id;

    @JsonProperty("title")
    @NotBlank(message = "Название фильма не может быть пустым")
    @Size(min = 2, max = 100, message = "Название должно содержать от 2 до 100 символов")
    @Schema(description = "Название фильма", example = "Война и мир")
    private String title;

    @JsonProperty("genre")
    @NotBlank(message = "Жанр фильма не может быть пустым")
    @Size(min = 2, max = 50, message = "Жанр должен содержать от 2 до 50 символов")
    @Schema(description = "Жанр фильма", example = "Эпическая драма")
    private String genre;

    @JsonProperty("releaseYear")
    @NotNull(message = "Год выпуска обязателен")
    @Min(value = 1895, message = "Первый фильм появился в 1895 году")
    @Max(value = 2025, message = "Год выпуска не может быть больше текущего")
    @Schema(description = "Год выпуска", example = "1960")
    private Integer releaseYear;

    @JsonProperty("author")
    @Schema(description = "Автор фильма")
    private AuthorDto author;
}