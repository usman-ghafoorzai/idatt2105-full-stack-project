package no.ntnu.idatt2105.marketplace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  /**
   * Finds a Category by its name.
   * @param name the name of the Category to find
   * @return the Category with the specified name, or null if not found
   */
  public Category findByName(String name) {
    return categoryRepository.findByName(name);
  }

  /**
   * Creates a new Category and saves it to the database.
   * @param category the Category to create
   * @param parentCategoryName the name of the parent category (can be null if no parent)
   * @return the created Category
   * @throws Exception if the parent category does not exist
   */
  public Category createCategory(Category category, String parentCategoryName) throws Exception {
      // Check if the category already exists
      Category existingCategory = categoryRepository.findByName(category.getName());
      if (existingCategory != null) {
          return existingCategory;      // TODO; Maybe throw an exception instead
      }
      // Check if the parent category exists
      if (parentCategoryName != null) {
          Category parent = categoryRepository.findByName(parentCategoryName);
          if (parent == null) {
              throw new IllegalArgumentException("Parent category not found: " + parentCategoryName);
          }
          category.setParentCategory(parent);
      }
      // Save the new category to the database
      return categoryRepository.save(category);
  }

  /**
   * Get all the stored categories in the database.
   * @return a list of all categories
   */
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }
  
}
