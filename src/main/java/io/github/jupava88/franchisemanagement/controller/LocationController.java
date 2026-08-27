package io.github.jupava88.franchisemanagement.controller;

import io.github.jupava88.franchisemanagement.dto.CreateLocationRequest;
import io.github.jupava88.franchisemanagement.dto.LocationResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateNameRequest;
import io.github.jupava88.franchisemanagement.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/franchises/{franchiseId}/locations")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationResponse create(
            @PathVariable Long franchiseId,
            @Valid @RequestBody CreateLocationRequest request
    ) {
        return locationService.create(franchiseId, request);
    }

    @PatchMapping("/locations/{locationId}/name")
    public LocationResponse updateName(
            @PathVariable Long locationId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return locationService.updateName(locationId, request);
    }
}
