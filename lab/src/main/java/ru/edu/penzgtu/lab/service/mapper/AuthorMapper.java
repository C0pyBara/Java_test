package ru.edu.penzgtu.lab.service.mapper;

import org.springframework.stereotype.Service;

import ru.edu.penzgtu.lab.dto.AuthorDto;
import ru.edu.penzgtu.lab.entity.Author;

@Service
public class AuthorMapper {

    public AuthorDto toDto(Author author) {
        if (author == null) {
            return null;
        }

        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .country(author.getCountry())
                .birthYear(author.getBirthYear())
                .build();
    }

    public Author toEntity(AuthorDto dto) {
        if (dto == null) {
            return null;
        }

        return Author.builder()
                .id(dto.getId())
                .name(dto.getName())
                .country(dto.getCountry())
                .birthYear(dto.getBirthYear())
                .build();
    }
}