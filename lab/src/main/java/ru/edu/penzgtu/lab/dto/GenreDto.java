package ru.edu.penzgtu.lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO для передачи данных о жанре")
public class GenreDto {

    @JsonProperty("id")
    @Schema(description = "Уникальный идентификатор жанра", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @JsonProperty("name")
    @NotBlank(message = "Название жанра не может быть пустым")
    @Size(max = 100, message = "Название не должно превышать 100 символов")
    @Schema(description = "Название жанра", example = "Драма")
    private String name;

    @JsonProperty("description")
    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 500, message = "Описание не должно превышать 500 символов")
    @Schema(description = "Описание жанра", example = "Жанр, сосредоточенный на человеческих эмоциях и отношениях")
    private String description;
}