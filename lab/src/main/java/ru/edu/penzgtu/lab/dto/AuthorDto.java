package ru.edu.penzgtu.lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO для передачи данных об авторе")
public class AuthorDto {

    @JsonProperty("id")
    @Schema(description = "Уникальный идентификатор автора", example = "1")
    private Integer id;

    @JsonProperty("name")
    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(max = 100, message = "Имя не должно превышать 100 символов")
    @Schema(description = "Имя автора", example = "Квентин Тарантино")
    private String name;

    @JsonProperty("country")
    @NotBlank(message = "Страна не может быть пустой")
    @Size(max = 50, message = "Название страны не должно превышать 50 символов")
    @Schema(description = "Страна автора", example = "США")
    private String country;

    @JsonProperty("birthYear")
    @NotNull(message = "Год рождения обязателен")
    @Min(value = 1800, message = "Год рождения не может быть меньше 1800")
    @Max(value = 2025, message = "Год рождения не может быть больше текущего")
    @Schema(description = "Год рождения", example = "1963")
    private Integer birthYear;
}