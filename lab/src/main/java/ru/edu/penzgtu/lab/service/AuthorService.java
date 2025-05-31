package ru.edu.penzgtu.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.penzgtu.lab.dto.AuthorDto;
import ru.edu.penzgtu.lab.entity.Author;
import ru.edu.penzgtu.lab.repository.AuthorRepository;
import ru.edu.penzgtu.lab.service.mapper.AuthorMapper;
import ru.edu.penzgtu.lab.exception.PenzGtuException; // ← импортируем
import ru.edu.penzgtu.lab.exception.ErrorType; // ← импортируем

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    public AuthorDto getAuthorById(Integer id) {
        return authorMapper.toDto(authorRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Автор не найден")));
    }

    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    public AuthorDto updateAuthor(Integer id, AuthorDto updatedDto) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Автор не найден"));

        existingAuthor.setName(updatedDto.getName());
        existingAuthor.setCountry(updatedDto.getCountry());
        existingAuthor.setBirthYear(updatedDto.getBirthYear());

        return authorMapper.toDto(authorRepository.save(existingAuthor));
    }

    public void deleteAuthor(Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new PenzGtuException(ErrorType.NOT_FOUND, "Автор не найден");
        }
        authorRepository.deleteById(id);
    }
}