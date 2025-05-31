package ru.edu.penzgtu.lab.service.mapper;

import org.springframework.stereotype.Service;
import ru.edu.penzgtu.lab.dto.MovieDto;
import ru.edu.penzgtu.lab.entity.Movie;
import ru.edu.penzgtu.lab.entity.Studio;
import java.util.stream.Collectors;


@Service
public class MovieMapper {

    private final StudioMapper studioMapper;
    private final GenreMapper genreMapper;
    private final AuthorMapper authorMapper;

    public MovieMapper(StudioMapper studioMapper, GenreMapper genreMapper, AuthorMapper authorMapper) {
        this.studioMapper = studioMapper;
        this.genreMapper = genreMapper;
        this.authorMapper = authorMapper;
    }

    public MovieDto toDto(Movie movie) {
        if (movie == null) {
            return null;
        }
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .releaseYear(movie.getReleaseYear())
                .durationMinutes(movie.getDurationMinutes())
                .studio(studioMapper.toDto(movie.getStudio()))
                .genres(movie.getGenres().stream().map(genreMapper::toDto).collect(Collectors.toSet()))
                .authors(movie.getAuthors().stream().map(authorMapper::toDto).collect(Collectors.toSet()))
                .build();
    }

    public Movie toEntity(MovieDto dto) {
        if (dto == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDurationMinutes(dto.getDurationMinutes());
        if (dto.getStudio() != null) {
            Studio studio = new Studio();
            studio.setId(dto.getStudio().getId());
            movie.setStudio(studio);
        }
        return movie;
    }
}