package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.dto.CategoryCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryUpdateDTO;
import no.ntnu.idatt2105.marketplace.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  /**
   * Finds a category by its name.
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
   * Creates a new category.
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
   * Retrieves all categories.
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
   * Returns 200 Ok if the category is updated successfully, or 404 Not Found if the category does not exist, 
   * or status 404 with the exception message in the header when parent/subcategory is not found.
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
   * Deletes a category.
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
   * Adds a new sub-category under the given parent.
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
   * Removes a sub-category from the specified parent.
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
