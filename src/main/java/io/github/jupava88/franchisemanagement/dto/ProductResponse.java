package io.github.jupava88.franchisemanagement.dto;

public record ProductResponse(
        Long id,
        String name,
        int stock,
        Long locationId
) {
}
