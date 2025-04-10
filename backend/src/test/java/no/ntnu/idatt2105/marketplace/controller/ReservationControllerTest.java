package no.ntnu.idatt2105.marketplace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO.CategoryDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO.SellerDTO;
import no.ntnu.idatt2105.marketplace.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    void testGetReservedItems_ReturnsItems() throws Exception {
        Long userId = 1L;

        ItemResponseDTO itemDTO = new ItemResponseDTO();
        itemDTO.setId(1L);
        itemDTO.setTitle("Reserved Item");
        itemDTO.setDescription("Test");
        itemDTO.setPrice(100.0);
        itemDTO.setLocationLatitude(59.9);
        itemDTO.setLocationLongitude(10.7);
        itemDTO.setStatus("ACTIVE");
        itemDTO.setCreatedAt(LocalDateTime.now());

        CategoryDTO category = new CategoryDTO();
        category.setId(1L);
        category.setName("Electronics");
        itemDTO.setCategories(Set.of(category));

        SellerDTO seller = new SellerDTO();
        seller.setId(2L);
        seller.setUsername("testSeller");
        seller.setEmail("seller@example.com");
        itemDTO.setSeller(seller);

        List<ItemResponseDTO> items = Arrays.asList(itemDTO);

        when(reservationService.getReservedItemsByUserId(userId)).thenReturn(items);

        mockMvc.perform(get("/api/reservations/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Reserved Item"))
                .andExpect(jsonPath("$[0].category.name").value("Electronics"))
                .andExpect(jsonPath("$[0].seller.username").value("testSeller"));
    }

    @Test
    void testGetReservedItems_ReturnsEmptyList() throws Exception {
        Long userId = 1L;

        when(reservationService.getReservedItemsByUserId(userId)).thenReturn(List.of());

        mockMvc.perform(get("/api/reservations/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testReserveItem_Success() throws Exception {
        Long userId = 1L;
        Long itemId = 2L;

        // reservationService.saveReservation returns void now
        doNothing().when(reservationService).saveReservation(userId, itemId);

        mockMvc.perform(post("/api/reservations/{userId}/{itemId}", userId, itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteReservation_Success() throws Exception {
        Long userId = 1L;
        Long itemId = 2L;

        doNothing().when(reservationService).deleteReservation(userId, itemId);

        mockMvc.perform(delete("/api/reservations/{userId}/{itemId}", userId, itemId))
                .andExpect(status().isNoContent());
    }
}
