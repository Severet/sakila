package com.example.sakila.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class FilmInput {
    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min = 1, max = 128)
    private String title;

    @NotNull(groups = ValidationGroup.Create.class)
    private Byte languageId;

    @NotNull(groups = ValidationGroup.Create.class)
    private Byte RentalDuration;

    @NotNull(groups = ValidationGroup.Create.class)
    private java.math.BigDecimal rentalRate;

    @NotNull(groups = ValidationGroup.Create.class)
    private java.math.BigDecimal replacementCost;

    @NotNull(groups = ValidationGroup.Create.class)
    private java.sql.Timestamp lastUpdate;

    private List<Short> actorIds;
}
