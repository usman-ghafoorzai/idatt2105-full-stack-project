package no.ntnu.idatt2105.marketplace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.ntnu.idatt2105.marketplace.model.Item;
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
}
