package com.example.sakila.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CategoryInput {
    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min = 1, max = 25)
    private String categoryName;

    private List<Short> filmIds;
}
