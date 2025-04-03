package no.ntnu.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import no.ntnu.idatt2105.marketplace.controller.ItemController;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.service.ItemService;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
  private MockMvc mockMvc;

  @Mock
  private ItemService itemService;

  @InjectMocks
  private ItemController itemController;

  private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON conversion

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for LocalDateTime serialization
  }

  @Test
  void testGetItemById_ItemFound() throws Exception {
    Item item = new Item();
    item.setId(1L);
    item.setTitle("Test Item");
    item.setDescription("This is a test item.");
    item.setPrice(100.0);

    // Mock the service call
    when(itemService.getItemById(1L)).thenReturn(Optional.of(item));

    // Perform the GET request
    mockMvc.perform(get("/api/items/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.title").value("Test Item"))
        .andExpect(jsonPath("$.description").value("This is a test item."))
        .andExpect(jsonPath("$.price").value(100.0));
    // TODO: Add more assertions for seller and category?
  }

  @Test
  void testGetItemById_ItemNotFound() throws Exception {
    // Mock the service call to return an empty Optional
    when(itemService.getItemById(1L)).thenReturn(Optional.empty());

    // Perform the GET request and expect a 404 Not Found status
    mockMvc.perform(get("/api/items/1"))
        .andExpect(status().isNotFound());
  }
}
