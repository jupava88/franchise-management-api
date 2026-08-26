package io.github.jupava88.franchisemanagement.service;

import io.github.jupava88.franchisemanagement.dto.CreateFranchiseRequest;
import io.github.jupava88.franchisemanagement.dto.FranchiseResponse;
import io.github.jupava88.franchisemanagement.model.Franchise;
import io.github.jupava88.franchisemanagement.repository.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseService franchiseService;

    @Test
    void createsAFranchise() {
        Franchise savedFranchise = mock(Franchise.class);
        when(savedFranchise.getId()).thenReturn(1L);
        when(savedFranchise.getName()).thenReturn("Nequi");
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(savedFranchise);

        FranchiseResponse response = franchiseService.create(new CreateFranchiseRequest("Nequi"));

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Nequi");

        ArgumentCaptor<Franchise> franchiseCaptor = ArgumentCaptor.forClass(Franchise.class);
        verify(franchiseRepository).save(franchiseCaptor.capture());
        assertThat(franchiseCaptor.getValue().getName()).isEqualTo("Nequi");
    }
}
