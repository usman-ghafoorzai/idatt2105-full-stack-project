package no.ntnu.idatt2105.marketplace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.ItemStatus;
import no.ntnu.idatt2105.marketplace.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
  @Mock
  private ItemRepository itemRepository;

  @InjectMocks
  private ItemService itemService;

  @Test
  void testGetItemById_Found() {
    Item item = new Item();
    item.setId(1L);
    item.setTitle("Phone");

    when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

    Optional<Item> result = itemService.getItemById(1L);

    assertThat(result).isPresent();
    assertThat(result.get().getTitle()).isEqualTo("Phone");
  }

  @Test
  void testGetItemById_NotFound() {
    when(itemRepository.findById(2L)).thenReturn(Optional.empty());

    Optional<Item> result = itemService.getItemById(2L);

    assertThat(result).isNotPresent();
  }

  @Test
  void testSaveItem() {
    Item item = new Item();
    item.setTitle("Laptop");

    when(itemRepository.save(item)).thenReturn(item);

    Item saved = itemService.saveItem(item);

    assertThat(saved).isNotNull();
    assertThat(saved.getTitle()).isEqualTo("Laptop");
  }

  @Test
  void testUpdateItem_Found() {
    Item existing = new Item();
    existing.setId(1L);
    existing.setTitle("Old Title");

    Item updated = new Item();
    updated.setTitle("New Title");
    updated.setDescription("Updated description");
    updated.setPrice(199.99);
    updated.setCategory(new Category());

    when(itemRepository.findById(1L)).thenReturn(Optional.of(existing));
    when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

    Optional<Item> result = itemService.updateItem(1L, updated);

    assertThat(result).isPresent();
    assertThat(result.get().getTitle()).isEqualTo("New Title");
    assertThat(result.get().getDescription()).isEqualTo("Updated description");
  }

  @Test
  void testUpdateItem_NotFound() {
    Item updated = new Item();
    updated.setTitle("Doesn't matter");

    when(itemRepository.findById(999L)).thenReturn(Optional.empty());

    Optional<Item> result = itemService.updateItem(999L, updated);

    assertThat(result).isNotPresent();
  }

  @Test
  void testDeleteItem() {
    Long id = 1L;

    itemService.deleteItem(id);

    // Verify that the deleteById method was called with the correct ID
    verify(itemRepository, times(1)).deleteById(id);
  }

  @Test
  void testGetAllItems() {
    List<Item> mockItems = Arrays.asList(new Item(), new Item());
    when(itemRepository.findAll()).thenReturn(mockItems);

    List<Item> result = itemService.getAllItems();

    assertThat(result).hasSize(2);
  }

  @SuppressWarnings("unchecked") // Suppress warning for unchecked cast
  @Test
  void testGetFilteredItems() {
    List<Item> filteredItems = List.of(new Item(), new Item());

    when(itemRepository.findAll((Specification<Item>) any(Specification.class))).thenReturn(filteredItems);

    List<Item> result = itemService.getFilteredItems("Phone1", "Electronics", 100.0, 500.0, ItemStatus.ACTIVE.name());

    assertThat(result).hasSize(2);
    verify(itemRepository, times(1)).findAll(any(Specification.class));
  }
}
