package io.github.jupava88.franchisemanagement.repository;

import io.github.jupava88.franchisemanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
