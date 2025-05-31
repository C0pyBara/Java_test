package ru.edu.penzgtu.lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO для передачи данных о студии")
public class StudioDto {

    @JsonProperty("id")
    @Schema(description = "Уникальный идентификатор студии", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @JsonProperty("name")
    @NotBlank(message = "Название студии не может быть пустым")
    @Size(max = 100, message = "Название не должно превышать 100 символов")
    @Schema(description = "Название студии", example = "Warner Bros.")
    private String name;

    @JsonProperty("foundedYear")
    @NotNull(message = "Год основания обязателен")
    @Schema(description = "Год основания", example = "1923")
    private Integer foundedYear;

    @JsonProperty("country")
    @NotBlank(message = "Страна не может быть пустой")
    @Size(max = 100, message = "Название страны не должно превышать 100 символов")
    @Schema(description = "Страна студии", example = "США")
    private String country;

    @JsonProperty("website")
    @NotBlank(message = "Веб-сайт не может быть пустым")
    @Size(max = 200, message = "Веб-сайт не должен превышать 200 символов")
    @Schema(description = "Веб-сайт студии", example = "https://www.warnerbros.com")
    private String website;
}