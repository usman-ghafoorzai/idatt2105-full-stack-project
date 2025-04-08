package no.ntnu.idatt2105.marketplace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.Reservation;
import no.ntnu.idatt2105.marketplace.model.User;
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

import java.util.Arrays;
import java.util.List;

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

        Item item = new Item();
        item.setId(1L);
        item.setTitle("Reserved Item");

        List<Item> items = Arrays.asList(item);

        when(reservationService.getReservedItemsByUserId(userId)).thenReturn(items);

        mockMvc.perform(get("/api/reservations/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Reserved Item"));
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
    void testReserveItem_ReturnsCreatedReservation() throws Exception {
        Long userId = 1L;
        Long itemId = 2L;

        User user = new User();
        user.setId(userId);

        Item item = new Item();
        item.setId(itemId);
        item.setTitle("New Reserved Item");

        Reservation reservation = new Reservation(user, item);

        when(reservationService.saveReservation(userId, itemId)).thenReturn(reservation);

        mockMvc.perform(post("/api/reservations/{userId}/{itemId}", userId, itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(userId))
                .andExpect(jsonPath("$.item.id").value(itemId))
                .andExpect(jsonPath("$.item.title").value("New Reserved Item"));
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
