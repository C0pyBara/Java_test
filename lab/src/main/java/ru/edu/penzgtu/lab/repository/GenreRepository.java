package ru.edu.penzgtu.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.penzgtu.lab.entity.Genre;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
}