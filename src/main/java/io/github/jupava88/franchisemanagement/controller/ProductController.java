package io.github.jupava88.franchisemanagement.controller;

import io.github.jupava88.franchisemanagement.dto.CreateProductRequest;
import io.github.jupava88.franchisemanagement.dto.ProductResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateNameRequest;
import io.github.jupava88.franchisemanagement.dto.UpdateStockRequest;
import io.github.jupava88.franchisemanagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/locations/{locationId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @PathVariable Long locationId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        return productService.create(locationId, request);
    }

    @PatchMapping("/products/{productId}/stock")
    public ProductResponse updateStock(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateStockRequest request
    ) {
        return productService.updateStock(productId, request);
    }

    @PatchMapping("/products/{productId}/name")
    public ProductResponse updateName(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return productService.updateName(productId, request);
    }

    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId) {
        productService.delete(productId);
    }
}
