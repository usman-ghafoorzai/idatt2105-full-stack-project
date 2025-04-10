package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Items", description = "Operations related to item management")
public class ItemController {
  private final ItemService itemService;
  private final CategoryService categoryService;

  /**
   * Retrieves an item by its unique identifier.
   *
   * @param id the unique identifier of the item
   * @return a {@code ResponseEntity} containing the item response DTO if found, otherwise a 404 Not Found response
   */
  @Operation(
      summary = "Get an item by ID",
      description = "Retrieves an item based on its unique identifier."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<ItemResponseDTO> getItemById(
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long id) {
    Optional<ItemResponseDTO> itemDTO = itemService.getItemById(id);
    return itemDTO.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
  
  /**
   * Creates a new item entity.
   *
   * @param createDTO the item creation payload as a {@link ItemCreateDTO} object
   * @return {@code ResponseEntity} containing the created item response DTO {@link ItemResponseDTO}
   */
  @Operation(
      summary = "Create a new item",
      description = "Saves a new item entity based on the provided payload. " +
                    "In case of any error (for example, an invalid category or seller), a 400 Bad Request is returned.",
      security = { @SecurityRequirement(name = "bearer-key") }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item created successfully",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request - error during item creation", content = @Content)
  })
  @PostMapping
  public ResponseEntity<ItemResponseDTO> createItem(
    @Parameter(description = "Item creation payload", required = true)
    @RequestBody ItemCreateDTO createDTO) {
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
   * @param id the unique identifier of the item to be updated
   * @param updateDTO the item update payload as a {@link ItemUpdateDTO} object
   * @return {@code ResponseEntity} containing the updated item response DTO {@link ItemResponseDTO} if found, 
   *  otherwise a 404 Not Found response or a 400 Bad Request response if any error occurs during update
   */
  @Operation(
      summary = "Update an existing item",
      description = "Updates an existing item based on the provided payload. " +
                    "Returns a 404 if the item is not found, otherwise updates the item. " +
                    "A 400 Bad Request is returned if any error occurs during update.",
      security = { @SecurityRequirement(name = "bearer-key") } 
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item updated successfully",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
      @ApiResponse(responseCode = "400", description = "Bad Request - error during update", content = @Content)
  })
  @PutMapping("/{id}")
  public ResponseEntity<ItemResponseDTO> updateItem(
    @Parameter(description = "The unique identifier of the item to update", required = true)
    @PathVariable Long id,
    @Parameter(description = "Item update payload", required = true)
    @RequestBody ItemUpdateDTO updateDTO) {
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
   * @return {@code ResponseEntity} with a 204 No Content status if deletion is successful,
   *  or a 400 Bad Request response if any error occurs during deletion
   */
  @Operation(
      summary = "Delete an item",
      description = "Deletes an item using its unique identifier. " +
                    "Returns a 204 No Content status if deletion is successful, " +
                    "or a 400 Bad Request if any error occurs.",
      security = { @SecurityRequirement(name = "bearer-key") }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Item deleted successfully", content = @Content),
      @ApiResponse(responseCode = "400", description = "Bad Request - error during deletion", content = @Content)
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(
    @Parameter(description = "The unique identifier of the item to delete", required = true)
    @PathVariable Long id) {
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
  @Operation(
      summary = "Get filtered items",
      description = "Retrieves a list of items based on the provided search filters. " +
                    "Filters include title (partial match, case-insensitive), exact category name, " +
                    "minimum price, maximum price, and item status. If no items match the filters, " +
                    "a 204 No Content response is returned."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Filtered items retrieved successfully",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDTO.class))),
      @ApiResponse(responseCode = "204", description = "No items found", content = @Content)
  })
  @GetMapping
  public ResponseEntity<List<ItemResponseDTO>> getItems(
    @Parameter(description = "Title of the item (partial match, case-insensitive)", required = false)
    @RequestParam(required = false) String title,
    @Parameter(description = "Category name of the item (exact match)", required = false)
    @RequestParam(required = false) String category,
    @Parameter(description = "Minimum price of the item", required = false)
    @RequestParam(required = false) Double minPrice,
    @Parameter(description = "Maximum price of the item", required = false)
    @RequestParam(required = false) Double maxPrice,
    @Parameter(description = "Status of the item (ACTIVE, SOLD, RESERVED)", required = false)
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
