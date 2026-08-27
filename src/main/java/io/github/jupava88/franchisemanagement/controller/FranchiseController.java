package io.github.jupava88.franchisemanagement.controller;

import io.github.jupava88.franchisemanagement.dto.CreateFranchiseRequest;
import io.github.jupava88.franchisemanagement.dto.FranchiseResponse;
import io.github.jupava88.franchisemanagement.dto.TopStockProductResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateNameRequest;
import io.github.jupava88.franchisemanagement.service.FranchiseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final FranchiseService franchiseService;

    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FranchiseResponse create(@Valid @RequestBody CreateFranchiseRequest request) {
        return franchiseService.create(request);
    }

    @GetMapping("/{franchiseId}/products/top-stock")
    public List<TopStockProductResponse> findTopStockProducts(
            @PathVariable Long franchiseId
    ) {
        return franchiseService.findTopStockProducts(franchiseId);
    }

    @PatchMapping("/{franchiseId}/name")
    public FranchiseResponse updateName(
            @PathVariable Long franchiseId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return franchiseService.updateName(franchiseId, request);
    }
}
