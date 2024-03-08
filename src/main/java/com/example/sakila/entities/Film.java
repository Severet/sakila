package com.example.sakila.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film {
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

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private List<PartialActor> cast = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<PartialCategory> films = new ArrayList<>();

    public Film(short nId, String nTitle, byte nLanguageId, byte nRentalDuration, java.math.BigDecimal nRentalRate, java.math.BigDecimal nReplacementCost) {
        id = nId;
        title = nTitle;
        languageId = nLanguageId;
        rentalDuration = nRentalDuration;
        rentalRate = nRentalRate;
        replacementCost = nReplacementCost;
    }
}
