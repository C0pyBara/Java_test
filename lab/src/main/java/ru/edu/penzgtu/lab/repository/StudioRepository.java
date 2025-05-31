package ru.edu.penzgtu.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.penzgtu.lab.entity.Studio;
import java.util.UUID;

public interface StudioRepository extends JpaRepository<Studio, UUID> {
}