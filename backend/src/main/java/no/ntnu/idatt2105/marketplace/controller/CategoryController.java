package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  /**
   * Finds a category by its name.
   * @param name the name of the category to find
   * @return the category with the specified name, or null if not found
   */
  @GetMapping("/findByName/{name}")
  public ResponseEntity<Category> findByName(@PathVariable String name) {
    Category category = categoryService.findByName(name);
    if (category != null) {
      return ResponseEntity.ok(category);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Creates a new category with the specified name and parent category name.
   * @param category the category to create
   * @param parentCategoryName the name of the parent category, or null if no parent
   * @return the created category, status 400 if the parent category does not exist, or status 500 if an unexpected error occurs
   */
  @PostMapping("/create")
  public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestParam (required = false) String parentCategoryName) {
    try {
      Category createdCategory = categoryService.createCategory(category, parentCategoryName);
      return ResponseEntity.ok(createdCategory);
    } catch (IllegalAccessException e) {
      return ResponseEntity.badRequest().header("Error-Message", e.getMessage()).body(null);
    } catch (Exception e) {
      return ResponseEntity.status(500).header("Error-Message", "An unexpected error occurred while creating the category.").body(null);
    }
  }
  
  /**
   * Retrieves all the categories
   * @return a list categories or status 204 if no categories are found
   */
  @GetMapping
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> categories = categoryService.getAllCategories();
    if (categories.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(categories);
  }

  /**
   * Updates an existing category with the specified ID.
   * @param id the ID of the category to update
   * @param category the updated category data
   * @return the updated category, or status 404 if the given category ID does not exist
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
    return categoryService.updateCategory(id, category)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{parentName}/subcategories/{subCategoryName}")
  public ResponseEntity<Void> removeSubCategory(@PathVariable String parentName, @PathVariable String subCategoryName) {
    Category parent = categoryService.findByName(parentName);
    Category subCategory = categoryService.findByName(subCategoryName);

    // Check if both categories exist and if the sub-category belongs to the parent
    if (subCategory.getParentCategory() == null || !subCategory.getParentCategory().getId().equals(parent.getId())) {
      return ResponseEntity.badRequest().build();
    }

    // Remove the sub-category from the parent category
    parent.getSubcategories().remove(subCategory);
    categoryService.updateCategory(parent.getId(), parent); // Update the parent category in the database
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{parentName}/subcategories")
  public ResponseEntity<Category> addSubCategory(@PathVariable String parentName, @RequestBody Category subCategory) {
    Category parent = categoryService.findByName(parentName);
    if (parent == null) {
      return ResponseEntity.notFound().build(); // Parent category not found
    }
    // Set parent for the new sub-category
    subCategory.setParentCategory(parent);

    try {
      // Save the new sub-category to the database
      Category savedSubCategory = categoryService.createCategory(subCategory, parentName);
      return ResponseEntity.ok(savedSubCategory);
    } catch (Exception e) {
      return ResponseEntity.status(500)
          .header("Error-Message", "An unexpected error occurred while creating the sub-category.").body(null);
    }
  }


  /**
   * Deletes a category by its ID.
   * @param id the ID of the category to delete
   * @return status 204 if the category is deleted successfully, or status 500 if an unexpected error occurs
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    try {
      categoryService.deleteCategory(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(500).header("Error-Message", "An unexpected error occurred while deleting the category.").body(null);
    }
  }
}
