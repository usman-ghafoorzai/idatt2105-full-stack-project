package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.dto.CategoryCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryUpdateDTO;
import no.ntnu.idatt2105.marketplace.service.CategoryService;

/**
 * Controller for managing categories in the marketplace.
 * This controller provides endpoints for creating, updating, deleting, and retrieving categories.
 * Only the administrator can perform these operations, except for the retrieval of categories,
 * which is available to all users.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  /**
   * Finds a category by its name.
   * @param name the name of the category to find
   * @return a {@code ResponseEntity} with:
   *         - status 200 OK and the {@link CategoryResponseDTO} containing the category details if found,
   *         - status 404 Not Found if the category name does not exist in the database
   */
  @GetMapping("/findByName/{name}")
  public ResponseEntity<CategoryResponseDTO> findByName(@PathVariable String name) {
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
   * @param createDTO the DTO {@link CategoryCreateDTO} containing the details of the category to create
   * @return a {@code ResponseEntity} with:
   *        - status 200 OK and the {@link CategoryResponseDTO} containing the created category details,
   *        - status 400 Bad Request with error message in the header if the category already exists or if the parent category is not found
   */
  @PostMapping("/create")
  public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryCreateDTO createDTO) {
    try {
      CategoryResponseDTO createdCategory = categoryService.createCategory(createDTO);
      return ResponseEntity.ok(createdCategory);
    } catch (Exception e) {
      return ResponseEntity.badRequest().header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Retrieves all categories from the database.
   * @return a {@code ResponseEntity} with:
   *         - status 200 OK and a list of {@link CategoryResponseDTO} containing all categories,
   *         - status 204 No Content if no categories are found in the database
   */
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
   * @param id the ID of the category to update
   * @param updateDTO the DTO {@link CategoryUpdateDTO} containing the updated details of the category.
   * @return a {@code ResponseEntity} with:
   *        - status 200 OK if the category is updated successfully and the {@link CategoryResponseDTO} containing the updated category details,
   *        - status 404 Not Found if the category does not exist or if the parent/subcategory is not found
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateDTO updateDTO) {
    try {
      return categoryService.updateCategory(id, updateDTO)
          .map(ResponseEntity::ok)
          .orElse(ResponseEntity.notFound().build());
    } catch (Exception e) {
      // Return 404 Not Found with the exception message in the header
      return ResponseEntity.status(404).header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Deletes a category by its ID.
   * @param id the ID of the category to delete
   * @return a {@code ResponseEntity} with:
   *        - status 204 No Content if the category is deleted successfully,
   *        - status 404 Not Found with error message in the header if the category does not exist
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    try {
      categoryService.deleteCategory(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(404).header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Adds a sub-category to the specified parent category.
   * @param parentName the name of the parent category
   * @param createDTO the DTO {@link CategoryCreateDTO} containing the details of the sub-category to add
   * @return a {@code ResponseEntity} with:
   *         - status 200 OK and the {@link CategoryResponseDTO} containing the created sub-category details,
   *         - status 500 if something goes wrong (e.g., parent category not found, sub-category already exists)
   */
  @PostMapping("/{parentName}/subcategories")
  public ResponseEntity<CategoryResponseDTO> addSubCategory(@PathVariable String parentName, @RequestBody CategoryCreateDTO createDTO) {
    try {
      CategoryResponseDTO subCategory = categoryService.addSubCategory(parentName, createDTO);
      return ResponseEntity.ok(subCategory);
    } catch (Exception e) {
      return ResponseEntity.status(500).header("Error-Message", e.getMessage()).build();
    }
  }

  /**
   * Removes a sub-category from the specified parent category.
   * @param parentName the name of the parent category
   * @param subCategoryName the name of the sub-category to remove
   * @return a {@code ResponseEntity} with:
   *        - status 204 No Content if the sub-category is removed and deleted successfully,
   *        - status 400 Bad Request with error message in the header if the parent or sub-category is not found or if parent/sub-category are not linked correctly
   */
  @DeleteMapping("/{parentName}/subcategories/{subCategoryName}")
  public ResponseEntity<Void> removeSubCategory(@PathVariable String parentName, @PathVariable String subCategoryName) {
    try {
      categoryService.removeSubCategory(parentName, subCategoryName);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(400).header("Error-Message", e.getMessage()).build();
    }
  }
}
