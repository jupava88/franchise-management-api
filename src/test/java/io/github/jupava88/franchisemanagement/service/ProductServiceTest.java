package io.github.jupava88.franchisemanagement.service;

import io.github.jupava88.franchisemanagement.dto.CreateProductRequest;
import io.github.jupava88.franchisemanagement.dto.ProductResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateStockRequest;
import io.github.jupava88.franchisemanagement.model.Location;
import io.github.jupava88.franchisemanagement.model.Product;
import io.github.jupava88.franchisemanagement.repository.LocationRepository;
import io.github.jupava88.franchisemanagement.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createsAProduct() {
        Location location = new Location("Medellin");
        Product savedProduct = mock(Product.class);

        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(savedProduct.getId()).thenReturn(10L);
        when(savedProduct.getName()).thenReturn("Coffee");
        when(savedProduct.getStock()).thenReturn(25);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.create(
                1L,
                new CreateProductRequest("Coffee", 25)
        );

        assertThat(response.name()).isEqualTo("Coffee");
        assertThat(response.stock()).isEqualTo(25);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        assertThat(productCaptor.getValue().getLocation()).isSameAs(location);
    }

    @Test
    void updatesStock() {
        Product product = mock(Product.class);
        Location location = mock(Location.class);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(product.getId()).thenReturn(10L);
        when(product.getName()).thenReturn("Coffee");
        when(product.getStock()).thenReturn(40);
        when(product.getLocation()).thenReturn(location);
        when(location.getId()).thenReturn(1L);

        ProductResponse response = productService.updateStock(
                10L,
                new UpdateStockRequest(40)
        );

        verify(product).updateStock(40);
        assertThat(response.stock()).isEqualTo(40);
    }
}
