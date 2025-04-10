package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.dto.ItemCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemUpdateDTO;
import no.ntnu.idatt2105.marketplace.service.CategoryService;
import no.ntnu.idatt2105.marketplace.service.ItemService;

/**
 * Controller class for handling item-related HTTP requests.
 * Provides endpoints for CRUD operations on item entities.
 */
// TODO: Update javadoc to inclide status codes
@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;
  private final CategoryService categoryService;

  /**
   * Retrieves an item by its unique identifier.
   *
   * @param id the unique identifier of the item
   * @return {@code ResponseEntity} containing the item if found, otherwise returns a 404 Not Found response
   */
  @GetMapping("/{id}")
  public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Long id) {
    Optional<ItemResponseDTO> itemDTO = itemService.getItemById(id);
    return itemDTO.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
  
  /**
   * Saves a new item entity.
   *
   * @param item the item entity to be saved
   * @return {@code ResponseEntity} containing the saved item entity with a 200 OK
   *         status
   */
  @PostMapping
  public ResponseEntity<ItemResponseDTO> createItem(@RequestBody ItemCreateDTO createDTO) {
    try {
      ItemResponseDTO newItem = itemService.saveItem(createDTO);
      return ResponseEntity.ok(newItem);
    } catch (Exception e) {
      return ResponseEntity.badRequest().header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Updates an existing item entity.
   *
   * @param id   the unique identifier of the item to be updated
   * @param item the updated item entity
   * @return {@code ResponseEntity} containing the updated item if found,
   *         otherwise returns a 404 Not Found response
   */
  @PutMapping("/{id}")
  public ResponseEntity<ItemResponseDTO> updateItem(@PathVariable Long id, @RequestBody ItemUpdateDTO updateDTO) {
    try {
      Optional<ItemResponseDTO> updatedItem = itemService.updateItem(id, updateDTO);
      return updatedItem.map(ResponseEntity::ok)
          .orElse(ResponseEntity.notFound().build());
    } catch (Exception e) {
      return ResponseEntity.badRequest().header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Deletes an item by its unique identifier.
   *
   * @param id the unique identifier of the item to be deleted
   * @return {@code ResponseEntity} with a 204 No Content status if the item was successfully deleted
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    try {
      itemService.deleteItem(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Retrieves items based on various filters.
   * Filters can be a combination of title, category, minimum price, maximum price, and status.
   * @param title the title of the item. It can be a partial match and is case-insensitive.
   * @param category the category of the item. Must match exactly.
   * @param minPrice the minimum price of the item
   * @param maxPrice the maximum price of the item
   * @param status the status of the item (e.g., ACTIVE, SOLD, etc.)
   * @return {@code ResponseEntity} containing a list of filtered items. If no items are found, returns a 204 No Content response.
   */
  @GetMapping
  public ResponseEntity<List<ItemResponseDTO>> getItems(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) Double minPrice,
      @RequestParam(required = false) Double maxPrice,
      @RequestParam(required = false) String status) {

        List<ItemResponseDTO> items = itemService.getFilteredItems(title, category, minPrice, maxPrice, status);
    if (items.isEmpty()) {
      return ResponseEntity.noContent().build(); 
    }
    return ResponseEntity.ok(items);
  }

  /**
   * Retrieves all items in the database based on the parent category name.
   * This method first retrieves all descendant category IDs of the specified parent category,
   * then queries items with category IDs in the set.
   * @param parentName the name of the parent category
   * @return {@code ResponseEntity} containing a set of items. If no items are found, returns a 204 No Content response.
   */
  @GetMapping("/by-parent-category/{parentName}")
  public ResponseEntity<Set<ItemResponseDTO>> getItemsByParentCategory(
          @PathVariable String parentName) {
      try {
          // Get all descendant category ids
          var descendantIds = categoryService.getDescendantCategoryIds(parentName);
          // Query items with category id in the set
          Set<ItemResponseDTO> items = itemService.getItemsByCategoryIds(descendantIds);
          if (items.isEmpty()) {
              return ResponseEntity.noContent().build();
          }
          return ResponseEntity.ok(items);
      } catch (Exception e) {
          return ResponseEntity.badRequest().header("Error-Message", e.getMessage()).build();
      }
  }
}
