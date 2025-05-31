package ru.edu.penzgtu.lab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Integer releaseYear;
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}