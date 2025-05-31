package ru.edu.penzgtu.lab.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.edu.penzgtu.lab.dto.AuthorDto;
import ru.edu.penzgtu.lab.dto.GenreDto;
import ru.edu.penzgtu.lab.dto.MovieDto;
import ru.edu.penzgtu.lab.entity.Movie;
import ru.edu.penzgtu.lab.entity.Studio;
import ru.edu.penzgtu.lab.entity.Genre;
import ru.edu.penzgtu.lab.entity.Author;
import ru.edu.penzgtu.lab.repository.MovieRepository;
import ru.edu.penzgtu.lab.repository.StudioRepository;
import ru.edu.penzgtu.lab.repository.GenreRepository;
import ru.edu.penzgtu.lab.repository.AuthorRepository;
import ru.edu.penzgtu.lab.service.mapper.MovieMapper;
import ru.edu.penzgtu.lab.exception.PenzGtuException;
import ru.edu.penzgtu.lab.exception.ErrorType;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;
    private final StudioRepository studioRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final MovieMapper movieMapper;

    @Transactional(readOnly = true)
    public List<MovieDto> getAllMovies() {
        logger.info("Получение всех фильмов");
        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MovieDto getMovieById(UUID id) {
        logger.info("Получение фильма с ID: {}", id);
        return movieMapper.toDto(movieRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Фильм не найден")));
    }

    @Transactional
    public MovieDto createMovie(MovieDto movieDto) {
        logger.info("Создание фильма: {}", movieDto.getTitle());
        Movie movie = movieMapper.toEntity(movieDto);

        // Проверка и установка студии
        if (movieDto.getStudio() == null || movieDto.getStudio().getId() == null) {
            throw new PenzGtuException(ErrorType.NOT_FOUND, "Студия обязательна");
        }
        Studio studio = studioRepository.findById(movieDto.getStudio().getId())
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Студия не найдена"));
        movie.setStudio(studio);

        // Добавление жанров
        if (movieDto.getGenres() != null) {
            Set<Genre> genres = new HashSet<>();
            for (GenreDto genreDto : movieDto.getGenres()) {
                Genre genre = genreRepository.findById(genreDto.getId())
                        .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Жанр не найден"));
                genres.add(genre);
            }
            movie.setGenres(genres);
        }

        // Сохранение фильма
        movie = movieRepository.save(movie);

        // Обработка авторов (Many-to-Many, где Author - владеющая сторона)
        if (movieDto.getAuthors() != null) {
            for (AuthorDto authorDto : movieDto.getAuthors()) {
                Author author = authorRepository.findById(authorDto.getId())
                        .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Автор не найден"));
                author.getMovies().add(movie);
                authorRepository.save(author);
            }
        }

        return movieMapper.toDto(movie);
    }

    @Transactional
    public MovieDto updateMovie(UUID id, MovieDto movieDto) {
        logger.info("Обновление фильма с ID: {}", id);
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Фильм не найден"));

        // Обновление основных полей
        existingMovie.setTitle(movieDto.getTitle());
        existingMovie.setReleaseYear(movieDto.getReleaseYear());
        existingMovie.setDurationMinutes(movieDto.getDurationMinutes());

        // Обновление студии
        if (movieDto.getStudio() == null || movieDto.getStudio().getId() == null) {
            throw new PenzGtuException(ErrorType.NOT_FOUND, "Студия обязательна");
        }
        Studio studio = studioRepository.findById(movieDto.getStudio().getId())
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Студия не найдена"));
        existingMovie.setStudio(studio);

        // Обновление жанров
        if (movieDto.getGenres() != null) {
            Set<Genre> genres = new HashSet<>();
            for (GenreDto genreDto : movieDto.getGenres()) {
                Genre genre = genreRepository.findById(genreDto.getId())
                        .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Жанр не найден"));
                genres.add(genre);
            }
            existingMovie.setGenres(genres);
        } else {
            existingMovie.getGenres().clear();
        }

        // Обновление авторов
        // Удаление фильма из авторов, которые больше не ассоциированы
        for (Author author : new HashSet<>(existingMovie.getAuthors())) {
            if (movieDto.getAuthors() == null || movieDto.getAuthors().stream()
                    .noneMatch(a -> a.getId().equals(author.getId()))) {
                author.getMovies().remove(existingMovie);
                authorRepository.save(author);
            }
        }
        // Добавление фильма к новым авторам
        if (movieDto.getAuthors() != null) {
            for (AuthorDto authorDto : movieDto.getAuthors()) {
                Author author = authorRepository.findById(authorDto.getId())
                        .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Автор не найден"));
                if (!author.getMovies().contains(existingMovie)) {
                    author.getMovies().add(existingMovie);
                    authorRepository.save(author);
                }
            }
        }

        return movieMapper.toDto(movieRepository.save(existingMovie));
    }

    @Transactional
    public void deleteMovie(UUID id) {
        logger.info("Удаление фильма с ID: {}", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Фильм не найден"));

        // Удаление фильма из коллекций авторов
        for (Author author : movie.getAuthors()) {
            author.getMovies().remove(movie);
            authorRepository.save(author);
        }

        movieRepository.delete(movie);
    }
}