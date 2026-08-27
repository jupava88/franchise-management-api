package io.github.jupava88.franchisemanagement.repository;

import io.github.jupava88.franchisemanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p
            FROM Product p
            JOIN FETCH p.location l
            WHERE l.franchise.id = :franchiseId
              AND p.stock = (
                  SELECT MAX(p2.stock)
                  FROM Product p2
                  WHERE p2.location = l
              )
            ORDER BY l.id, p.id
            """)
    List<Product> findTopStockByFranchiseId(@Param("franchiseId") Long franchiseId);
}
