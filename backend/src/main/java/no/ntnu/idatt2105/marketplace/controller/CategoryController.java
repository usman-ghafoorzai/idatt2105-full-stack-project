package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.dto.CategoryCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryUpdateDTO;
import no.ntnu.idatt2105.marketplace.service.CategoryService;

/**
 * Controller for managing categories in the marketplace.
 * This controller provides endpoints for creating, updating, deleting, and
 * retrieving categories.
 * Only the administrator can perform these operations, except for the retrieval
 * of categories,
 * which is available to all users.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
@Tag(name = "Categories", description = "Operations related to category management")
public class CategoryController {
  private final CategoryService categoryService;

  /**
   * Finds a category by its name.
   * 
   * @param name the name of the category to find
   * @return a {@code ResponseEntity} with:
   *         - status 200 OK and the {@link CategoryResponseDTO} containing the
   *         category details if found,
   *         - status 404 Not Found if the category name does not exist in the
   *         database
   */
  @Operation(summary = "Find a category by its name", description = "Returns the category details if found. Otherwise, responds with 404.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
  })
  @GetMapping("/findByName/{name}")
  public ResponseEntity<CategoryResponseDTO> findByName(
      @Parameter(description = "The name of the category to retrieve", required = true) @PathVariable String name) {
    try {
      CategoryResponseDTO category = categoryService.findByName(name);
      return ResponseEntity.ok(category);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Creates a new category based on the provided {@link CategoryCreateDTO}.
   * The new category is saved to the database.
   * 
   * @param createDTO the DTO {@link CategoryCreateDTO} containing the details of
   *                  the category to create
   * @return a {@code ResponseEntity} with:
   *         - status 200 OK and the {@link CategoryResponseDTO} containing the
   *         created category details,
   *         - status 400 Bad Request with error message in the header if the
   *         category already exists or if the parent category is not found
   */
  @Operation(summary = "Create a new category", description = "Creates a new category based on the provided details. Returns an error if the category already exists or parent category is not found.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request: Category already exists or invalid parent", content = @Content)
  })
  @PostMapping("/create")
  public ResponseEntity<CategoryResponseDTO> createCategory(
      @Parameter(description = "Category creation payload", required = true) @RequestBody CategoryCreateDTO createDTO) {
    try {
      CategoryResponseDTO createdCategory = categoryService.createCategory(createDTO);
      return ResponseEntity.ok(createdCategory);
    } catch (Exception e) {
      return ResponseEntity.badRequest().header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Retrieves all categories from the database.
   * 
   * @return a {@code ResponseEntity} with:
   *         - status 200 OK and a list of {@link CategoryResponseDTO} containing
   *         all categories,
   *         - status 204 No Content if no categories are found in the database
   */
  @Operation(summary = "Get all categories", description = "Retrieves all categories from the system. Returns 204 No Content if none are found.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Categories retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))),
      @ApiResponse(responseCode = "204", description = "No categories found", content = @Content)
  })
  @GetMapping
  public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
    List<CategoryResponseDTO> categories = categoryService.getAllCategories();
    if (categories.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(categories);
  }

  /**
   * Updates an existing category based on the provided {@link CategoryUpdateDTO}.
   * The update includes changing the name, parent category, and subcategories.
   * The updated category is saved to the database.
   * 
   * @param id        the ID of the category to update
   * @param updateDTO the DTO {@link CategoryUpdateDTO} containing the updated
   *                  details of the category.
   * @return a {@code ResponseEntity} with:
   *         - status 200 OK if the category is updated successfully and the
   *         {@link CategoryResponseDTO} containing the updated category details,
   *         - status 404 Not Found if the category does not exist or if the
   *         parent/subcategory is not found
   */
  @Operation(summary = "Update an existing category", description = "Updates the category by its ID, including its name, parent category, and subcategories. Responds with 404 if not found.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Category or related subcategory not found", content = @Content)
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/update/{id}")
  public ResponseEntity<CategoryResponseDTO> updateCategory(
      @Parameter(description = "The ID of the category to update", required = true) @PathVariable Long id,
      @Parameter(description = "The category update payload", required = true) @RequestBody CategoryUpdateDTO updateDTO) {
    try {
      return categoryService.updateCategory(id, updateDTO)
          .map(ResponseEntity::ok)
          .orElse(ResponseEntity.notFound().build());
    } catch (Exception e) {
      return ResponseEntity.status(404).header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Deletes a category by its ID.
   * 
   * @param id the ID of the category to delete
   * @return a {@code ResponseEntity} with:
   *         - status 204 No Content if the category is deleted successfully,
   *         - status 404 Not Found with error message in the header if the
   *         category does not exist
   */
  @Operation(summary = "Delete a category", description = "Deletes a category by its ID. Responds with 404 if the category is not found.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Category deleted successfully", content = @Content),
      @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteCategory(
      @Parameter(description = "The ID of the category to delete", required = true) @PathVariable Long id) {
    try {
      categoryService.deleteCategory(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(404).header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Adds a sub-category to the specified parent category.
   * 
   * @param parentName the name of the parent category
   * @param createDTO  the DTO {@link CategoryCreateDTO} containing the details of
   *                   the sub-category to add
   * @return a {@code ResponseEntity} with:
   *         - status 200 OK and the {@link CategoryResponseDTO} containing the
   *         created sub-category details,
   *         - status 500 if something goes wrong (e.g., parent category not
   *         found, sub-category already exists)
   */
  @Operation(summary = "Add a sub-category",
             description = "Adds a new sub-category to the specified parent category. Returns an error if the parent is not found or the sub-category already exists.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sub-category added successfully",
              content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CategoryResponseDTO.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{parentName}/subcategories")
  public ResponseEntity<CategoryResponseDTO> addSubCategory(
      @Parameter(description = "The name of the parent category", required = true) @PathVariable String parentName,
      @Parameter(description = "Payload to create a new sub-category", required = true) @RequestBody CategoryCreateDTO createDTO) {
    try {
      CategoryResponseDTO subCategory = categoryService.addSubCategory(parentName, createDTO);
      return ResponseEntity.ok(subCategory);
    } catch (Exception e) {
      return ResponseEntity.status(500).header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Removes a sub-category from the specified parent category.
   * 
   * @param parentName      the name of the parent category
   * @param subCategoryName the name of the sub-category to remove
   * @return a {@code ResponseEntity} with:
   *         - status 204 No Content if the sub-category is removed and deleted
   *         successfully,
   *         - status 400 Bad Request with error message in the header if the
   *         parent or sub-category is not found or if parent/sub-category are not
   *         linked correctly
   */
  @Operation(summary = "Remove a sub-category",
             description = "Removes a sub-category from the specified parent category. Returns 400 if the relationship does not match.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Sub-category removed successfully", content = @Content),
      @ApiResponse(responseCode = "400", description = "Bad Request: Invalid parent/sub-category relationship", content = @Content)
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{parentName}/subcategories/{subCategoryName}")
  public ResponseEntity<Void> removeSubCategory(
    @Parameter(description = "The name of the parent category", required = true)
    @PathVariable String parentName,
    @Parameter(description = "The name of the sub-category to remove", required = true)
    @PathVariable String subCategoryName) {
    try {
      categoryService.removeSubCategory(parentName, subCategoryName);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(400).header("Error-Message", e.getMessage()).build();
    }
  }
}
