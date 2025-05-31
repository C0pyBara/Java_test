package ru.edu.penzgtu.lab.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.penzgtu.lab.dto.AuthorDto;
import ru.edu.penzgtu.lab.entity.Author;
import ru.edu.penzgtu.lab.repository.AuthorRepository;
import ru.edu.penzgtu.lab.service.mapper.AuthorMapper;
import ru.edu.penzgtu.lab.exception.PenzGtuException;
import ru.edu.penzgtu.lab.exception.ErrorType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors() {
        logger.info("Получение всех авторов");
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(Integer id) {
        logger.info("Получение автора с ID: {}", id);
        return authorMapper.toDto(authorRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Автор не найден")));
    }

    @Transactional
    public AuthorDto createAuthor(AuthorDto authorDto) {
        logger.info("Создание автора: {}", authorDto.getName());
        Author author = authorMapper.toEntity(authorDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Transactional
    public AuthorDto updateAuthor(Integer id, AuthorDto authorDto) {
        logger.info("Обновление автора с ID: {}", id);
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Автор не найден"));
        existingAuthor.setName(authorDto.getName());
        existingAuthor.setCountry(authorDto.getCountry());
        existingAuthor.setBirthYear(authorDto.getBirthYear());
        return authorMapper.toDto(authorRepository.save(existingAuthor));
    }

    @Transactional
    public void deleteAuthor(Integer id) {
        logger.info("Удаление автора с ID: {}", id);
        if (!authorRepository.existsById(id)) {
            throw new PenzGtuException(ErrorType.NOT_FOUND, "Автор не найден");
        }
        authorRepository.deleteById(id);
    }
}