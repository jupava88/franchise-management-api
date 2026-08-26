package io.github.jupava88.franchisemanagement.controller;

import io.github.jupava88.franchisemanagement.dto.CreateProductRequest;
import io.github.jupava88.franchisemanagement.dto.ProductResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateStockRequest;
import io.github.jupava88.franchisemanagement.exception.ResourceNotFoundException;
import io.github.jupava88.franchisemanagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void createsAProduct() throws Exception {
        when(productService.create(eq(1L), any(CreateProductRequest.class)))
                .thenReturn(new ProductResponse(10L, "Coffee", 25, 1L));

        mockMvc.perform(post("/api/locations/1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Coffee",
                                  "stock": 25
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.name").value("Coffee"))
                .andExpect(jsonPath("$.stock").value(25))
                .andExpect(jsonPath("$.locationId").value(1L));
    }

    @Test
    void updatesStock() throws Exception {
        when(productService.updateStock(eq(10L), any(UpdateStockRequest.class)))
                .thenReturn(new ProductResponse(10L, "Coffee", 40, 1L));

        mockMvc.perform(patch("/api/products/10/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "stock": 40
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.stock").value(40));
    }

    @Test
    void deletesAProduct() throws Exception {
        mockMvc.perform(delete("/api/products/10"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void rejectsNegativeStock() throws Exception {
        mockMvc.perform(patch("/api/products/10/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "stock": -1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.stock")
                        .value("must be greater than or equal to 0"));
    }

    @Test
    void returnsNotFoundForAnUnknownLocation() throws Exception {
        when(productService.create(eq(99L), any(CreateProductRequest.class)))
                .thenThrow(new ResourceNotFoundException("Location not found"));

        mockMvc.perform(post("/api/locations/99/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Coffee",
                                  "stock": 25
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Location not found"));
    }

    @Test
    void returnsNotFoundWhenDeletingAnUnknownProduct() throws Exception {
        doThrow(new ResourceNotFoundException("Product not found"))
                .when(productService).delete(99L);

        mockMvc.perform(delete("/api/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Product not found"));
    }
}
