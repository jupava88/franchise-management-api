package io.github.jupava88.franchisemanagement.dto;

public record TopStockProductResponse(
        Long locationId,
        String locationName,
        Long productId,
        String productName,
        int stock
) {
}
