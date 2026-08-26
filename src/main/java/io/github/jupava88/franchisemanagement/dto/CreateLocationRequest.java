package io.github.jupava88.franchisemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateLocationRequest(
        @NotBlank
        @Size(max = 120)
        String name
) {
}
