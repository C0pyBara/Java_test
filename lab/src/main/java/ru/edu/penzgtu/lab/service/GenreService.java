package ru.edu.penzgtu.lab.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.penzgtu.lab.dto.GenreDto;
import ru.edu.penzgtu.lab.entity.Genre;
import ru.edu.penzgtu.lab.repository.GenreRepository;
import ru.edu.penzgtu.lab.service.mapper.GenreMapper;
import ru.edu.penzgtu.lab.exception.PenzGtuException;
import ru.edu.penzgtu.lab.exception.ErrorType;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        logger.info("Получение всех жанров");
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GenreDto getGenreById(UUID id) {
        logger.info("Получение жанра с ID: {}", id);
        return genreMapper.toDto(genreRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Жанр не найден")));
    }

    @Transactional
    public GenreDto createGenre(GenreDto genreDto) {
        logger.info("Создание жанра: {}", genreDto.getName());
        Genre genre = genreMapper.toEntity(genreDto);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Transactional
    public GenreDto updateGenre(UUID id, GenreDto genreDto) {
        logger.info("Обновление жанра с ID: {}", id);
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Жанр не найден"));
        existingGenre.setName(genreDto.getName());
        existingGenre.setDescription(genreDto.getDescription());
        return genreMapper.toDto(genreRepository.save(existingGenre));
    }

    @Transactional
    public void deleteGenre(UUID id) {
        logger.info("Удаление жанра с ID: {}", id);
        if (!genreRepository.existsById(id)) {
            throw new PenzGtuException(ErrorType.NOT_FOUND, "Жанр не найден");
        }
        genreRepository.deleteById(id);
    }
}