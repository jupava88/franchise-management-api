package io.github.jupava88.franchisemanagement.controller;

import io.github.jupava88.franchisemanagement.dto.CreateLocationRequest;
import io.github.jupava88.franchisemanagement.dto.LocationResponse;
import io.github.jupava88.franchisemanagement.dto.UpdateNameRequest;
import io.github.jupava88.franchisemanagement.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LocationService locationService;

    @Test
    void createsALocation() throws Exception {
        when(locationService.create(eq(1L), any(CreateLocationRequest.class)))
                .thenReturn(new LocationResponse(10L, "Medellin", 1L));

        mockMvc.perform(post("/api/franchises/1/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Medellin\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.name").value("Medellin"));
    }

    @Test
    void updatesLocationName() throws Exception {
        when(locationService.updateName(eq(10L), any(UpdateNameRequest.class)))
                .thenReturn(new LocationResponse(10L, "New location name", 1L));

        mockMvc.perform(patch("/api/locations/10/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New location name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New location name"));
    }
}
