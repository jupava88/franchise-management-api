package io.github.jupava88.franchisemanagement.service;

import io.github.jupava88.franchisemanagement.dto.CreateLocationRequest;
import io.github.jupava88.franchisemanagement.dto.LocationResponse;
import io.github.jupava88.franchisemanagement.exception.ResourceNotFoundException;
import io.github.jupava88.franchisemanagement.model.Franchise;
import io.github.jupava88.franchisemanagement.model.Location;
import io.github.jupava88.franchisemanagement.repository.FranchiseRepository;
import io.github.jupava88.franchisemanagement.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    void createsALocation() {
        Franchise franchise = new Franchise("Nequi");
        Location savedLocation = mock(Location.class);

        when(franchiseRepository.findById(1L)).thenReturn(Optional.of(franchise));
        when(savedLocation.getId()).thenReturn(10L);
        when(savedLocation.getName()).thenReturn("Medellin");
        when(locationRepository.save(any(Location.class))).thenReturn(savedLocation);

        LocationResponse response = locationService.create(
                1L,
                new CreateLocationRequest("Medellin")
        );

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.name()).isEqualTo("Medellin");
        assertThat(response.franchiseId()).isEqualTo(1L);

        ArgumentCaptor<Location> locationCaptor = ArgumentCaptor.forClass(Location.class);
        verify(locationRepository).save(locationCaptor.capture());
        assertThat(locationCaptor.getValue().getFranchise()).isSameAs(franchise);
    }

    @Test
    void rejectsAnUnknownFranchise() {
        when(franchiseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> locationService.create(
                99L,
                new CreateLocationRequest("Medellin")
        ))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Franchise not found");

        verifyNoInteractions(locationRepository);
    }
}
