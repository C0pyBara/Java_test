package ru.edu.penzgtu.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.penzgtu.lab.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}