package com.example.sakila.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "film")
@Getter
@Setter
public class PartialFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private short id;

    @Column(name = "title")
    private String title;

    @Column(name = "language_id")
    private Byte languageId;

    @Column(name = "rental_duration")
    private Byte rentalDuration;

    @Column(name = "rental_rate")
    private java.math.BigDecimal rentalRate;

    @Column(name = "replacement_cost")
    private java.math.BigDecimal replacementCost;

    public PartialFilm(Film f) {
        this.title = f.getTitle();
        this.languageId = f.getLanguageId();
        this.rentalDuration = f.getRentalDuration();
        this.rentalRate = f.getRentalRate();
        this.replacementCost = f.getReplacementCost();
    }
    public PartialFilm() {

    }
}
