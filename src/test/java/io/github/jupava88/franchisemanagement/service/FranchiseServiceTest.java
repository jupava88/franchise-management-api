package io.github.jupava88.franchisemanagement.service;

import io.github.jupava88.franchisemanagement.dto.TopStockProductResponse;
import io.github.jupava88.franchisemanagement.model.Location;
import io.github.jupava88.franchisemanagement.model.Product;
import io.github.jupava88.franchisemanagement.repository.FranchiseRepository;
import io.github.jupava88.franchisemanagement.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FranchiseService franchiseService;

    @Test
    void returnsTopStockProducts() {
        Location location = new Location("Medellin");
        Product firstProduct = new Product("Coffee", 40);
        Product secondProduct = new Product("Tea", 40);
        location.addProduct(firstProduct);
        location.addProduct(secondProduct);

        when(franchiseRepository.existsById(1L)).thenReturn(true);
        when(productRepository.findTopStockByFranchiseId(1L))
                .thenReturn(List.of(firstProduct, secondProduct));

        List<TopStockProductResponse> response = franchiseService.findTopStockProducts(1L);

        assertThat(response).hasSize(2);
        assertThat(response.get(0).productName()).isEqualTo("Coffee");
        assertThat(response.get(1).productName()).isEqualTo("Tea");
    }
}
