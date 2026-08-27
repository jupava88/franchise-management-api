package io.github.jupava88.franchisemanagement.service;

import io.github.jupava88.franchisemanagement.dto.CreateFranchiseRequest;
import io.github.jupava88.franchisemanagement.dto.FranchiseResponse;
import io.github.jupava88.franchisemanagement.dto.TopStockProductResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateNameRequest;
import io.github.jupava88.franchisemanagement.exception.ResourceNotFoundException;
import io.github.jupava88.franchisemanagement.model.Franchise;
import io.github.jupava88.franchisemanagement.repository.FranchiseRepository;
import io.github.jupava88.franchisemanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final ProductRepository productRepository;

    public FranchiseService(
            FranchiseRepository franchiseRepository,
            ProductRepository productRepository
    ) {
        this.franchiseRepository = franchiseRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public FranchiseResponse create(CreateFranchiseRequest request) {
        Franchise franchise = new Franchise(request.name());
        Franchise savedFranchise = franchiseRepository.save(franchise);

        return new FranchiseResponse(savedFranchise.getId(), savedFranchise.getName());
    }

    @Transactional(readOnly = true)
    public List<TopStockProductResponse> findTopStockProducts(Long franchiseId) {
        if (!franchiseRepository.existsById(franchiseId)) {
            throw new ResourceNotFoundException("Franchise not found");
        }

        return productRepository.findTopStockByFranchiseId(franchiseId)
                .stream()
                .map(product -> new TopStockProductResponse(
                        product.getLocation().getId(),
                        product.getLocation().getName(),
                        product.getId(),
                        product.getName(),
                        product.getStock()
                ))
                .toList();
    }

    @Transactional
    public FranchiseResponse updateName(Long franchiseId, UpdateNameRequest request) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new ResourceNotFoundException("Franchise not found"));

        franchise.updateName(request.name());

        return new FranchiseResponse(franchise.getId(), franchise.getName());
    }
}
