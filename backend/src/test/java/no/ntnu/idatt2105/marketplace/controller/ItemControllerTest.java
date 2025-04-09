package no.ntnu.idatt2105.marketplace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import no.ntnu.idatt2105.marketplace.dto.ItemCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemUpdateDTO;
import no.ntnu.idatt2105.marketplace.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
  private MockMvc mockMvc;

  @Mock
  private ItemService itemService;

  @InjectMocks
  private ItemController itemController;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
  }

  @Nested
  @DisplayName("Positive Tests")
  class PositiveTests {

    @Test
    void testGetItemById() throws Exception {
      ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
      itemResponseDTO.setId(1L);
      itemResponseDTO.setTitle("Test Item");
      itemResponseDTO.setDescription("Test Description");
      itemResponseDTO.setPrice(100.0);

      when(itemService.getItemById(1L)).thenReturn(Optional.of(itemResponseDTO));

      mockMvc.perform(get("/api/items/{id}", 1L))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(1)))
          .andExpect(jsonPath("$.title", is("Test Item")))
          .andExpect(jsonPath("$.description", is("Test Description")))
          .andExpect(jsonPath("$.price", is(100.00)));
    }

    @Test
    void testCreateItem() throws Exception {
      ItemCreateDTO itemCreateDTO = new ItemCreateDTO();
      itemCreateDTO.setTitle("Test Item");
      itemCreateDTO.setDescription("Test Description");
      itemCreateDTO.setPrice(100.0);
      itemCreateDTO.setLocationLatitude(10.0);
      itemCreateDTO.setLocationLongitude(20.0);
      itemCreateDTO.setCategoryId(1L);
      itemCreateDTO.setSellerId(1L);

      ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
      itemResponseDTO.setId(1L);
      itemResponseDTO.setTitle("Test Item");
      itemResponseDTO.setDescription("Test Description");
      itemResponseDTO.setPrice(100.0);

      when(itemService.saveItem(any(ItemCreateDTO.class))).thenReturn(itemResponseDTO);

      mockMvc.perform(post("/api/items")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(itemCreateDTO)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(1)))
          .andExpect(jsonPath("$.title", is("Test Item")))
          .andExpect(jsonPath("$.description", is("Test Description")))
          .andExpect(jsonPath("$.price", is(100.00)));
    }

    @Test
    void testUpdateItem() throws Exception {
      ItemUpdateDTO itemUpdateDTO = new ItemUpdateDTO();
      itemUpdateDTO.setTitle("Updated Title");
      itemUpdateDTO.setDescription("Updated Description");
      itemUpdateDTO.setPrice(150.0);

      ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
      itemResponseDTO.setId(1L);
      itemResponseDTO.setTitle("Updated Title");
      itemResponseDTO.setDescription("Updated Description");
      itemResponseDTO.setPrice(150.0);

      when(itemService.updateItem(eq(1L), any(ItemUpdateDTO.class))).thenReturn(Optional.of(itemResponseDTO));

      mockMvc.perform(put("/api/items/{id}", 1L)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(itemUpdateDTO)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(1)))
          .andExpect(jsonPath("$.title", is("Updated Title")))
          .andExpect(jsonPath("$.description", is("Updated Description")))
          .andExpect(jsonPath("$.price", is(150.00)));
    }

    @Test
    void testDeleteItem() throws Exception {
      mockMvc.perform(delete("/api/items/{id}", 1L))
          .andExpect(status().isNoContent());
    }

    @Test
    void testGetItems() throws Exception {
      ItemResponseDTO item1 = new ItemResponseDTO();
      item1.setId(1L);
      item1.setTitle("Item 1");
      item1.setDescription("Description 1");
      item1.setPrice(100.0);

      ItemResponseDTO item2 = new ItemResponseDTO();
      item2.setId(2L);
      item2.setTitle("Item 2");
      item2.setDescription("Description 2");
      item2.setPrice(200.0);

      when(itemService.getFilteredItems(null, null, null, null, null)).thenReturn(Arrays.asList(item1, item2));

      mockMvc.perform(get("/api/items"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].id", is(1)))
          .andExpect(jsonPath("$[0].title", is("Item 1")))
          .andExpect(jsonPath("$[0].description", is("Description 1")))
          .andExpect(jsonPath("$[0].price", is(100.00)))
          .andExpect(jsonPath("$[1].id", is(2)))
          .andExpect(jsonPath("$[1].title", is("Item 2")))
          .andExpect(jsonPath("$[1].description", is("Description 2")))
          .andExpect(jsonPath("$[1].price", is(200.00)));
    }

    @Test
    void testGetItemsEmptyList() throws Exception {
      when(itemService.getFilteredItems(null, null, null, null, null)).thenReturn(Collections.emptyList());

      mockMvc.perform(get("/api/items"))
          .andExpect(status().isNoContent());
    }
  }

  @Nested
  @DisplayName("Negative Tests")
  class NegativeTests {
    @Test
    void testGetItemByIdNotFound() throws Exception {
      when(itemService.getItemById(1L)).thenReturn(Optional.empty());

      mockMvc.perform(get("/api/items/{id}", 1L))
          .andExpect(status().isNotFound());
    }

    @Test
    void testCreateItemBadRequest() throws Exception {
      ItemCreateDTO itemCreateDTO = new ItemCreateDTO();
      itemCreateDTO.setTitle("Test Item");
      itemCreateDTO.setDescription("Test Description");
      itemCreateDTO.setPrice(100.0);

      when(itemService.saveItem(any(ItemCreateDTO.class))).thenThrow(new IllegalArgumentException("Invalid data"));

      mockMvc.perform(post("/api/items")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(itemCreateDTO)))
          .andExpect(status().isBadRequest())
          .andExpect(header().string("Error-Message", "Invalid data"));
    }

    @Test
    void testUpdateItem_NotFound() throws Exception {

      ItemUpdateDTO updateDTO = new ItemUpdateDTO();
      updateDTO.setTitle("Nonexistent Update");

      when(itemService.updateItem(eq(99L), any(ItemUpdateDTO.class)))
          .thenReturn(Optional.empty());

      mockMvc.perform(put("/api/items/{id}", 99L)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(updateDTO)))
          .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateItem_BadRequest() throws Exception {
      ItemUpdateDTO updateDTO = new ItemUpdateDTO();
      updateDTO.setTitle("Update Error");

      when(itemService.updateItem(eq(1L), any(ItemUpdateDTO.class)))
          .thenThrow(new IllegalArgumentException("Category not found"));

      mockMvc.perform(put("/api/items/{id}", 1L)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(updateDTO)))
          .andExpect(status().isBadRequest())
          .andExpect(header().string("Error-Message", "Category not found"));
    }

    @Test
    void testDeleteItemNotFound() throws Exception {
      doThrow(new IllegalArgumentException("Item not found")).when(itemService).deleteItem(1L);

      mockMvc.perform(delete("/api/items/{id}", 1L))
          .andExpect(status().isBadRequest())
          .andExpect(header().string("Error-Message", "Item not found"));
    }
  }
}