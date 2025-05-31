package ru.edu.penzgtu.lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о фильме")
public class MovieDto {

    @JsonProperty("id")
    @Schema(description = "ID фильма в БД", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @JsonProperty("title")
    @NotBlank(message = "Название фильма не может быть пустым")
    @Size(min = 2, max = 200, message = "Название должно содержать от 2 до 200 символов")
    @Schema(description = "Название фильма", example = "Война и мир")
    private String title;

    @JsonProperty("releaseYear")
    @NotNull(message = "Год выпуска обязателен")
    @Min(value = 1895, message = "Первый фильм появился в 1895 году")
    @Max(value = 2025, message = "Год выпуска не может быть больше текущего")
    @Schema(description = "Год выпуска", example = "1960")
    private Integer releaseYear;

    @JsonProperty("durationMinutes")
    @NotNull(message = "Продолжительность обязательна")
    @Min(value = 1, message = "Продолжительность должна быть больше 0 минут")
    @Schema(description = "Продолжительность фильма в минутах", example = "180")
    private Integer durationMinutes;

    @JsonProperty("studio")
    @Schema(description = "Студия, выпустившая фильм")
    private StudioDto studio;

    @JsonProperty("genres")
    @Schema(description = "Жанры фильма")
    private Set<GenreDto> genres;

    @JsonProperty("authors")
    @Schema(description = "Авторы фильма")
    private Set<AuthorDto> authors;
}