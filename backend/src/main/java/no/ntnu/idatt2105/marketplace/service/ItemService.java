package no.ntnu.idatt2105.marketplace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.repository.ItemRepository;

/**
 * Service class for handling item-related business logic.
 * Provides methods to retrieve, save, update, and delete item entities.
 */
@Service
@Transactional // The transactional annotation is used to manage transactions in the service
               // layer. It ensures that all database operations within the method are executed
               // within a single transaction, providing consistency and rollback capabilities
               // in case of errors.
@RequiredArgsConstructor
public class ItemService {
  private final ItemRepository itemRepository;

  /**
   * Retrieves an item by its unique identifier.
   *
   * @param id the unique identifier of the item
   * @return an {@code Optional} containing the item if found, otherwise empty
   */
  public Optional<Item> getItemById(Long id) {
    return itemRepository.findById(id);
  }

  /**
   * Saves a new item entity to the database.
   *
   * @param item the item entity to be saved
   * @return the saved item entity
   */
  public Item saveItem(Item item) {
    return itemRepository.save(item);
  }

  /**
   * Updates an existing item entity.
   *
   * @param id          the unique identifier of the item to be updated
   * @param updatedItem the updated item entity
   * @return an {@code Optional} containing the updated item if found, otherwise empty
   */
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

  /**
   * Deletes an item entity by its unique identifier.
   *
   * @param id the unique identifier of the item to be deleted
   */
  public void deleteItem(Long id) {
    itemRepository.deleteById(id);
  }

  /**
   * Retrieves all items in the database.
   *
   * @return a list of all items
   */
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  public List<Item> getFilteredItems(String categoryName, Double minPrice, Double maxPrice, String status) {
    Specification<Item> spec = Specification.where(null);

    if (categoryName != null) {
        spec = spec.and((root, query, cb) -> cb.equal(root.join("category").get("name"), categoryName));
    }
    if (minPrice != null) {
        spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
    }
    if (maxPrice != null) {
        spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
    }
    if (status != null) {
        spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
    }

    return itemRepository.findAll(spec);
  }

}
