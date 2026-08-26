package io.github.jupava88.franchisemanagement.service;

import io.github.jupava88.franchisemanagement.dto.CreateFranchiseRequest;
import io.github.jupava88.franchisemanagement.dto.FranchiseResponse;
import io.github.jupava88.franchisemanagement.model.Franchise;
import io.github.jupava88.franchisemanagement.repository.FranchiseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;

    public FranchiseService(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    @Transactional
    public FranchiseResponse create(CreateFranchiseRequest request) {
        Franchise franchise = new Franchise(request.name());
        Franchise savedFranchise = franchiseRepository.save(franchise);

        return new FranchiseResponse(savedFranchise.getId(), savedFranchise.getName());
    }
}
