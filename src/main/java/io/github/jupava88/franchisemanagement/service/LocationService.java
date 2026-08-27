package io.github.jupava88.franchisemanagement.service;

import io.github.jupava88.franchisemanagement.dto.CreateLocationRequest;
import io.github.jupava88.franchisemanagement.dto.LocationResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateNameRequest;
import io.github.jupava88.franchisemanagement.exception.ResourceNotFoundException;
import io.github.jupava88.franchisemanagement.model.Franchise;
import io.github.jupava88.franchisemanagement.model.Location;
import io.github.jupava88.franchisemanagement.repository.FranchiseRepository;
import io.github.jupava88.franchisemanagement.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

    private final FranchiseRepository franchiseRepository;
    private final LocationRepository locationRepository;

    public LocationService(
            FranchiseRepository franchiseRepository,
            LocationRepository locationRepository
    ) {
        this.franchiseRepository = franchiseRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public LocationResponse create(Long franchiseId, CreateLocationRequest request) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new ResourceNotFoundException("Franchise not found"));

        Location location = new Location(request.name());
        franchise.addLocation(location);

        Location savedLocation = locationRepository.save(location);

        return new LocationResponse(
                savedLocation.getId(),
                savedLocation.getName(),
                franchiseId
        );
    }

    @Transactional
    public LocationResponse updateName(Long locationId, UpdateNameRequest request) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        location.updateName(request.name());

        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getFranchise().getId()
        );
    }
}
