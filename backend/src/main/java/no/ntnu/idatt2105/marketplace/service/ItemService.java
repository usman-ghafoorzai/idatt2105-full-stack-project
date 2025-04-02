package no.ntnu.idatt2105.marketplace.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.repository.ItemRepository;

@Service
@Transactional    // The transactional annotation is used to manage transactions in the service layer. It ensures that all database operations within the method are executed within a single transaction, providing consistency and rollback capabilities in case of errors.
@RequiredArgsConstructor
public class ItemService {
  private final ItemRepository itemRepository;

  public Optional<Item> getItemById(Long id) {
    return itemRepository.findById(id);
  }

  public Item saveItem(Item item) {
    return itemRepository.save(item);
  }

  public Optional<Item> updateItem(Long id, Item updatedItem) {
    return itemRepository.findById(id)
        .map(existingItem -> {
          existingItem.setTitle(updatedItem.getTitle());
          existingItem.setDescription(updatedItem.getDescription());
          existingItem.setPrice(updatedItem.getPrice());
          existingItem.setCategory(updatedItem.getCategory());
          return itemRepository.save(existingItem);
        });
  }

  public void deleteItem(Long id) {
    itemRepository.deleteById(id);
  }
}
