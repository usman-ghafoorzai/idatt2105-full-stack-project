package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.service.ItemService;

/**
 * Controller class for handling item-related HTTP requests.
 * Provides endpoints for CRUD operations on item entities.
 */
@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;

  /**
   * Retrieves an item by its unique identifier.
   *
   * @param id the unique identifier of the item
   * @return {@code ResponseEntity} containing the item if found, otherwise returns a 404 Not Found response
   */
  @GetMapping("/{id}")
  public ResponseEntity<Item> getItemById(@PathVariable Long id) {
    return itemService.getItemById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  } 
  
  /**
   * Saves a new item entity.
   *
   * @param item the item entity to be saved
   * @return {@code ResponseEntity} containing the saved item entity with a 200 OK status
   */
  @PostMapping
  public ResponseEntity<Item> createItem(@RequestBody Item item) {
    Item newItem = itemService.saveItem(item);
    return ResponseEntity.ok(newItem);
  }

  /**
   * Updates an existing item entity.
   *
   * @param id the unique identifier of the item to be updated
   * @param item the updated item entity
   * @return {@code ResponseEntity} containing the updated item if found, otherwise returns a 404 Not Found response
   */
  @PutMapping("/{id}")
  public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
    return itemService.updateItem(id, item)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Deletes an item by its unique identifier.
   *
   * @param id the unique identifier of the item to be deleted
   * @return {@code ResponseEntity} with a 204 No Content status if the item was successfully deleted, otherwise returns a 404 Not Found response
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    itemService.deleteItem(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public List<Item> getItems(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) Double minPrice,
      @RequestParam(required = false) Double maxPrice,
      @RequestParam(required = false) String status) {

    return itemService.getFilteredItems(title, category, minPrice, maxPrice, status);
  }
}
