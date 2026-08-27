package io.github.jupava88.franchisemanagement.controller;

import io.github.jupava88.franchisemanagement.dto.CreateProductRequest;
import io.github.jupava88.franchisemanagement.dto.ProductResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateNameRequest;
import io.github.jupava88.franchisemanagement.dto.UpdateStockRequest;
import io.github.jupava88.franchisemanagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
                        .content("{\"name\":\"Coffee\",\"stock\":25}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.stock").value(25));
    }

    @Test
    void updatesStock() throws Exception {
        when(productService.updateStock(eq(10L), any(UpdateStockRequest.class)))
                .thenReturn(new ProductResponse(10L, "Coffee", 40, 1L));

        mockMvc.perform(patch("/api/products/10/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stock\":40}"))
                .andExpect(status().isOk())
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
                        .content("{\"stock\":-1}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.stock").exists());
    }

    @Test
    void updatesProductName() throws Exception {
        when(productService.updateName(eq(10L), any(UpdateNameRequest.class)))
                .thenReturn(new ProductResponse(10L, "New product name", 25, 1L));

        mockMvc.perform(patch("/api/products/10/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New product name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New product name"));
    }
}
