package ru.edu.penzgtu.lab.service.mapper;

import org.springframework.stereotype.Service;

import ru.edu.penzgtu.lab.dto.MovieDto;
import ru.edu.penzgtu.lab.entity.Movie;

@Service
public class MovieMapper {

    private final AuthorMapper authorMapper;

    public MovieMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public MovieDto toDto(Movie movie) {
        if (movie == null) {
            return null;
        }

        return MovieDto.builder()
                .id(movie.getId().longValue()) // предполагаем, что ID целочисленный
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .releaseYear(movie.getReleaseYear())
                .author(authorMapper.toDto(movie.getAuthor()))
                .build();
    }

    public Movie toEntity(MovieDto dto) {
        if (dto == null) {
            return null;
        }

        return Movie.builder()
                .id(dto.getId() != null ? dto.getId().intValue() : null)
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .releaseYear(dto.getReleaseYear())
                .author(authorMapper.toEntity(dto.getAuthor()))
                .build();
    }
}