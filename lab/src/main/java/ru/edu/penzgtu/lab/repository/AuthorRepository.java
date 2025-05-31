package ru.edu.penzgtu.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.penzgtu.lab.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}