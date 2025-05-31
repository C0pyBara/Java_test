package ru.edu.penzgtu.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.penzgtu.lab.dto.MovieDto;
import ru.edu.penzgtu.lab.entity.Movie;
import ru.edu.penzgtu.lab.repository.MovieRepository;
import ru.edu.penzgtu.lab.service.mapper.AuthorMapper;
import ru.edu.penzgtu.lab.service.mapper.MovieMapper;
import ru.edu.penzgtu.lab.exception.PenzGtuException; // ← импортируем наше исключение
import ru.edu.penzgtu.lab.exception.ErrorType; // ← импортируем типы ошибок

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final AuthorMapper authorMapper;

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    public MovieDto getMovieById(Integer id) {
        return movieMapper.toDto(movieRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Фильм не найден")));
    }

    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = movieMapper.toEntity(movieDto);
        return movieMapper.toDto(movieRepository.save(movie));
    }

    public MovieDto updateMovie(Integer id, MovieDto movieDto) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Фильм не найден"));

        // Обновляем поля фильма
        existingMovie.setTitle(movieDto.getTitle());
        existingMovie.setGenre(movieDto.getGenre());
        existingMovie.setReleaseYear(movieDto.getReleaseYear());

        // Преобразуем автора через AuthorMapper, а не MovieMapper
        existingMovie.setAuthor(authorMapper.toEntity(movieDto.getAuthor()));

        return movieMapper.toDto(movieRepository.save(existingMovie));
    }

    public void deleteMovie(Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new PenzGtuException(ErrorType.NOT_FOUND, "Фильм не найден");
        }
        movieRepository.deleteById(id);
    }
}