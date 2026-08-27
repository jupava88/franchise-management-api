package io.github.jupava88.franchisemanagement.service;

import io.github.jupava88.franchisemanagement.dto.CreateProductRequest;
import io.github.jupava88.franchisemanagement.dto.ProductResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateNameRequest;
import io.github.jupava88.franchisemanagement.dto.UpdateStockRequest;
import io.github.jupava88.franchisemanagement.exception.ResourceNotFoundException;
import io.github.jupava88.franchisemanagement.model.Location;
import io.github.jupava88.franchisemanagement.model.Product;
import io.github.jupava88.franchisemanagement.repository.LocationRepository;
import io.github.jupava88.franchisemanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final LocationRepository locationRepository;
    private final ProductRepository productRepository;

    public ProductService(
            LocationRepository locationRepository,
            ProductRepository productRepository
    ) {
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(Long locationId, CreateProductRequest request) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        Product product = new Product(request.name(), request.stock());
        location.addProduct(product);

        Product savedProduct = productRepository.save(product);

        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getStock(),
                locationId
        );
    }

    @Transactional
    public ProductResponse updateStock(Long productId, UpdateStockRequest request) {
        Product product = findProduct(productId);
        product.updateStock(request.stock());

        return toResponse(product);
    }

    @Transactional
    public ProductResponse updateName(Long productId, UpdateNameRequest request) {
        Product product = findProduct(productId);
        product.updateName(request.name());

        return toResponse(product);
    }

    @Transactional
    public void delete(Long productId) {
        Product product = findProduct(productId);
        productRepository.delete(product);
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getLocation().getId()
        );
    }
}
