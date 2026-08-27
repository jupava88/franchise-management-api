package io.github.jupava88.franchisemanagement.controller;

import io.github.jupava88.franchisemanagement.dto.CreateFranchiseRequest;
import io.github.jupava88.franchisemanagement.dto.FranchiseResponse;
import io.github.jupava88.franchisemanagement.dto.TopStockProductResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateNameRequest;
import io.github.jupava88.franchisemanagement.exception.ResourceNotFoundException;
import io.github.jupava88.franchisemanagement.service.FranchiseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FranchiseController.class)
class FranchiseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FranchiseService franchiseService;

    @Test
    void createsAFranchise() throws Exception {
        when(franchiseService.create(any(CreateFranchiseRequest.class)))
                .thenReturn(new FranchiseResponse(1L, "Nequi"));

        mockMvc.perform(post("/api/franchises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nequi\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Nequi"));
    }

    @Test
    void rejectsAnEmptyName() throws Exception {
        mockMvc.perform(post("/api/franchises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").exists());
    }

    @Test
    void returnsTopStockProducts() throws Exception {
        when(franchiseService.findTopStockProducts(1L)).thenReturn(List.of(
                new TopStockProductResponse(10L, "Medellin", 100L, "Coffee", 40),
                new TopStockProductResponse(10L, "Medellin", 101L, "Tea", 40)
        ));

        mockMvc.perform(get("/api/franchises/1/products/top-stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Coffee"))
                .andExpect(jsonPath("$[1].productName").value("Tea"));
    }

    @Test
    void returnsNotFoundForAnUnknownFranchise() throws Exception {
        when(franchiseService.findTopStockProducts(99L))
                .thenThrow(new ResourceNotFoundException("Franchise not found"));

        mockMvc.perform(get("/api/franchises/99/products/top-stock"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Franchise not found"));
    }

    @Test
    void updatesFranchiseName() throws Exception {
        when(franchiseService.updateName(eq(1L), any(UpdateNameRequest.class)))
                .thenReturn(new FranchiseResponse(1L, "New franchise name"));

        mockMvc.perform(patch("/api/franchises/1/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New franchise name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New franchise name"));
    }
}
