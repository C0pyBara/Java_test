package ru.edu.penzgtu.lab.service.mapper;

import org.springframework.stereotype.Service;
import ru.edu.penzgtu.lab.dto.GenreDto;
import ru.edu.penzgtu.lab.entity.Genre;

@Service
public class GenreMapper {

    public GenreDto toDto(Genre genre) {
        if (genre == null) {
            return null;
        }
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .description(genre.getDescription())
                .build();
    }

    public Genre toEntity(GenreDto dto) {
        if (dto == null) {
            return null;
        }
        return new Genre();
    }
}