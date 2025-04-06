package no.ntnu.idatt2105.marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
   * @return the created category
   */
  @GetMapping("/create")
  public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestParam (required = false) String parentCategoryName) {
    Category createdCategory = categoryService.createCategory(category, parentCategoryName);
    return ResponseEntity.ok(createdCategory);
  }
}
