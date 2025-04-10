package no.ntnu.idatt2105.marketplace.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO.CategoryDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO.SellerDTO;
import no.ntnu.idatt2105.marketplace.service.BookmarkService;

@ExtendWith(MockitoExtension.class)
public class BookmarkControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookmarkService bookmarkService;

    @InjectMocks
    private BookmarkController bookmarkController;

    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON conversion

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookmarkController).build();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for LocalDateTime serialization
    }

    @Test
    void testGetBookmarkedItems_UserFound() throws Exception {
        Long userId = 1L;
        
        ItemResponseDTO itemDTO = new ItemResponseDTO();
        itemDTO.setId(1L);
        itemDTO.setTitle("Bookmarked Item");
        itemDTO.setDescription("A test item");
        itemDTO.setPrice(10.0);
        itemDTO.setLocationLatitude(59.91);
        itemDTO.setLocationLongitude(10.75);
        itemDTO.setStatus("ACTIVE");
        itemDTO.setCreatedAt(LocalDateTime.now());

        CategoryDTO category = new CategoryDTO();
        category.setId(1L);
        category.setName("Books");
        itemDTO.setCategories(Set.of(category));

        SellerDTO seller = new SellerDTO();
        seller.setId(2L);
        seller.setUsername("sellerUser");
        seller.setEmail("seller@example.com");
        itemDTO.setSeller(seller);

        List<ItemResponseDTO> itemDTOs = Arrays.asList(itemDTO);

        when(bookmarkService.getBookmarkedItemsByUserId(userId)).thenReturn(itemDTOs);

        // Perform the GET request
        mockMvc.perform(get("/api/bookmarks/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Bookmarked Item"));
    }

    @Test
    void testGetBookmarkedItems_UserNotFound() throws Exception {
        // Given
        Long userId = 1L;

        // Mock the service call to return an empty list (no bookmarks)
        when(bookmarkService.getBookmarkedItemsByUserId(userId)).thenReturn(Arrays.asList());

        // Perform the GET request and expect an empty list
        mockMvc.perform(get("/api/bookmarks/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
