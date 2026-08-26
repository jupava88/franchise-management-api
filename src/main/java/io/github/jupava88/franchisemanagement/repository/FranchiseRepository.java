package io.github.jupava88.franchisemanagement.repository;

import io.github.jupava88.franchisemanagement.model.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
}
