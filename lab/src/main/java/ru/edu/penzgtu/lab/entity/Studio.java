package ru.edu.penzgtu.lab.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "studios")
@Data
public class Studio {

    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Column(name = "founded_year", nullable = false)
    @NotNull
    private Integer foundedYear;

    @Column(name = "country", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String country;

    @Column(name = "website", nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    private String website;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "studio", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Movie> movies = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}