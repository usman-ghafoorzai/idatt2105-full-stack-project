package no.ntnu.idatt2105.marketplace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import no.ntnu.idatt2105.marketplace.dto.ItemCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemUpdateDTO;
import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.repository.CategoryRepository;
import no.ntnu.idatt2105.marketplace.repository.ItemRepository;
import no.ntnu.idatt2105.marketplace.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
  @Mock
  private ItemRepository itemRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private ItemService itemService;

  private Item item;
  private Category category;
  private User seller;

  @BeforeEach
  void setup() {
    category = new Category();
    category.setId(100L);
    category.setName("Electronics");

    seller = new User();
    seller.setId(200L);
    seller.setUsername("sellerUser");

    item = new Item();
    item.setId(300L);
    item.setTitle("Smartphone");
    item.setDescription("Latest model smartphone with advanced features.");
    item.setPrice(8999.95);
    item.setLocationLatitude(75.91);
    item.setLocationLongitude(10.75);
    item.setCategory(category);
    item.setSeller(seller);
    item.setCreatedAt(LocalDateTime.now());
  }

  @Nested
  @DisplayName("Positive test cases")
  class PositiveTests {

    @Test
    void testGetItemByID() {
      when(itemRepository.findById(300L)).thenReturn(Optional.of(item));

      Optional<ItemResponseDTO> result = itemService.getItemById(300L);

      assertThat(result).isPresent();
      ItemResponseDTO response = result.get();
      assertThat(response.getId()).isEqualTo(300L);
      assertThat(response.getTitle()).isEqualTo("Smartphone");
      assertThat(response.getDescription()).isEqualTo("Latest model smartphone with advanced features.");
      assertThat(response.getPrice()).isEqualTo(8999.95);
      assertThat(response.getLocationLatitude()).isEqualTo(75.91);
      assertThat(response.getLocationLongitude()).isEqualTo(10.75);
      assertThat(response.getCategory().getId()).isEqualTo(100L);
      assertThat(response.getCategory().getName()).isEqualTo("Electronics");
      assertThat(response.getSeller().getId()).isEqualTo(200L);
      assertThat(response.getSeller().getUsername()).isEqualTo("sellerUser");
    }

    @Test
    void testSaveItem() {
      ItemCreateDTO createDTO = new ItemCreateDTO();
      createDTO.setTitle("Laptop");
      createDTO.setDescription("Gaming laptop");
      createDTO.setPrice(12299.99);
      createDTO.setLocationLatitude(79.92);
      createDTO.setLocationLongitude(90.06);
      createDTO.setCategoryId(100L);
      createDTO.setSellerId(200L);

      // Stub repository calls for category and seller lookup.
      when(categoryRepository.findById(100L)).thenReturn(Optional.of(category));
      when(userRepository.findById(200L)).thenReturn(Optional.of(seller));

      // Simulate save call.
      Item savedItem = new Item();
      savedItem.setId(400L);
      savedItem.setTitle(createDTO.getTitle());
      savedItem.setDescription(createDTO.getDescription());
      savedItem.setPrice(createDTO.getPrice());
      savedItem.setLocationLatitude(createDTO.getLocationLatitude());
      savedItem.setLocationLongitude(createDTO.getLocationLongitude());
      savedItem.setCategory(category);
      savedItem.setSeller(seller);
      savedItem.setCreatedAt(LocalDateTime.now());

      when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

      
      ItemResponseDTO response = itemService.saveItem(createDTO);

      assertThat(response.getId()).isEqualTo(400L);
      assertThat(response.getTitle()).isEqualTo("Laptop");
      assertThat(response.getCategory().getName()).isEqualTo("Electronics");
      assertThat(response.getSeller().getUsername()).isEqualTo("sellerUser");
    }

    @Test
    void testUpdateItem() {
      ItemUpdateDTO updateDTO = new ItemUpdateDTO();
      updateDTO.setTitle("Smartphone Pro Max");
      updateDTO.setDescription("Updated model with even more advanced features.");
      updateDTO.setPrice(11999.99);
      updateDTO.setLocationLatitude(80.00);
      updateDTO.setLocationLongitude(90.00);
      updateDTO.setCategoryId(100L);

      when(itemRepository.findById(300L)).thenReturn(Optional.of(item));
      when(categoryRepository.findById(100L)).thenReturn(Optional.of(category));

      when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));

      Optional<ItemResponseDTO> resultOpt = itemService.updateItem(300L, updateDTO);

      assertThat(resultOpt).isPresent();
      ItemResponseDTO response = resultOpt.get();
      assertThat(response.getId()).isEqualTo(300L);
      assertThat(response.getTitle()).isEqualTo("Smartphone Pro Max");
      assertThat(response.getDescription()).isEqualTo("Updated model with even more advanced features.");
      assertThat(response.getPrice()).isEqualTo(11999.99);
    }

    @Test
    void testDeleteItem() {
      when(itemRepository.existsById(300L)).thenReturn(true);

      itemService.deleteItem(300L);

      verify(itemRepository, times(1)).deleteById(300L);
    }

    @Test
    void testGetAllItems() {
      when(itemRepository.findAll()).thenReturn(Arrays.asList(item, item));

      List<Item> result = itemService.getAllItems();

      assertThat(result).hasSize(2);
      assertThat(result.get(0).getId()).isEqualTo(300L);
      assertThat(result.get(1).getId()).isEqualTo(300L);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetFilteredItems() {
    List<Item> filteredItems = Collections.singletonList(item);
    
    when(itemRepository.findAll(any(Specification.class))).thenReturn(filteredItems);

    List<ItemResponseDTO> dtos = itemService.getFilteredItems("smart", "Electronics", 500.0, 1000.0, "AVAILABLE");

    assertThat(dtos).hasSize(1);
    ItemResponseDTO dto = dtos.get(0);
    assertThat(dto.getId()).isEqualTo(300L);
    assertThat(dto.getTitle()).isEqualTo("Smartphone");
    assertThat(dto.getDescription()).isEqualTo("Latest model smartphone with advanced features.");
    assertThat(dto.getPrice()).isEqualTo(8999.95);
    }

  }

  @Nested
  @DisplayName("Negative test cases")
  class NegativeTests {

    @Test
    void testGetItemById_NotFound() {

      when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

      Optional<ItemResponseDTO> resultOpt = itemService.getItemById(999L);

      assertThat(resultOpt).isNotPresent();
    }

    @Test
    void testSaveItemThrowsWhenCategoryNotFound() {
          // Arrange
    ItemCreateDTO createDTO = new ItemCreateDTO();
    createDTO.setTitle("Laptop");
    createDTO.setDescription("Gaming laptop");
    createDTO.setPrice(7299.05);
    createDTO.setCategoryId(999L); // non-existent category id
    createDTO.setSellerId(200L);

    when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> itemService.saveItem(createDTO))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Category not found");
    }

    @Test
    void testSaveItemThrowsWhenSellerNotFound() {
      // Arrange
      ItemCreateDTO createDTO = new ItemCreateDTO();
      createDTO.setTitle("Laptop");
      createDTO.setDescription("Gaming laptop");
      createDTO.setPrice(7299.05);
      createDTO.setCategoryId(100L);
      createDTO.setSellerId(999L); // non-existent seller id

      when(categoryRepository.findById(100L)).thenReturn(Optional.of(category));
      when(userRepository.findById(999L)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> itemService.saveItem(createDTO))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Seller not found");
    }

    @Test
    void testUpdateItemThrowsWhenItemNotFound() {
    ItemUpdateDTO updateDTO = new ItemUpdateDTO();
    updateDTO.setTitle("Nonexistent Item");
    
    when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
    
    Optional<ItemResponseDTO> responseOpt = itemService.updateItem(999L, updateDTO);
    
    assertThat(responseOpt).isNotPresent();
    }

    @Test
    void testUpdateItemThrowsWhenCategoryNotFound() {
      ItemUpdateDTO updateDTO = new ItemUpdateDTO();
      updateDTO.setTitle("Smartphone Pro Max");
      updateDTO.setDescription("Updated model with even more advanced features.");
      updateDTO.setPrice(11999.99);
      updateDTO.setLocationLatitude(80.00);
      updateDTO.setLocationLongitude(90.00);
      updateDTO.setCategoryId(999L); // non-existent category id

      when(itemRepository.findById(300L)).thenReturn(Optional.of(item));
      when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> itemService.updateItem(300L, updateDTO))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Category not found");
    }

    @Test
    void testDeleteItemThrowsWhenItemNotFound() {
      when(itemRepository.existsById(999L)).thenReturn(false);

      assertThatThrownBy(() -> itemService.deleteItem(999L))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Item not found");

      verify(itemRepository, times(0)).deleteById(anyLong());
    }
  }
}
