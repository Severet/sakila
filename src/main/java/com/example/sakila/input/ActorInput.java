package com.example.sakila.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ActorInput {
    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min = 1, max = 45)
    private String firstName;

    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min = 1, max = 45)
    private String lastName;

    private List<Short> filmIds;

}

