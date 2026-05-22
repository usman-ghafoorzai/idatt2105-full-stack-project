package no.ntnu.idatt2105.marketplace.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.dto.CategoryCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryUpdateDTO;
import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.repository.CategoryRepository;

/**
 * Service class for managing categories in the marketplace.
 * This class provides methods to create, update, delete, and retrieve categories.
 * Only the administrator can perform these operations, except for the retrieval of categories,
 * which is available to all users.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  /**
   * Private method to map a Category entity to a CategoryResponseDTO for API response purposes.
   * It also sets the parent category and subcategories if they exist.
   * @param category the Category entity to convert
   * @return the converted CategoryResponseDTO
   */
    private CategoryResponseDTO convertToResponse(Category category) {
    CategoryResponseDTO response = new CategoryResponseDTO();
    response.setId(category.getId());
    response.setName(category.getName());

    // Set parent info if exists
    if (category.getParentCategory() != null) {
      CategoryResponseDTO.ParentCategoryDTO parentDTO = new CategoryResponseDTO.ParentCategoryDTO();
      parentDTO.setId(category.getParentCategory().getId());
      parentDTO.setName(category.getParentCategory().getName());
      response.setParentCategory(parentDTO);
    }

    // Map subcategories (only id and name for brevity)
    if (category.getSubcategories() != null) {
      List<CategoryResponseDTO.SubCategoryDTO> subs = category.getSubcategories().stream()
          .map(sub -> {
            CategoryResponseDTO.SubCategoryDTO subDTO = new CategoryResponseDTO.SubCategoryDTO();
            subDTO.setId(sub.getId());
            subDTO.setName(sub.getName());
            return subDTO;
          }).collect(Collectors.toList());
      response.setSubcategories(subs);
    }
    return response;
  }

  /**
   * Finds a category by its name and converts it to a response DTO.
   * @param name the name of the category to find
   * @return the CategoryResponseDTO containing the category details
   * @throws IllegalArgumentException if the category is not found
   */
  public CategoryResponseDTO findByName(String name) throws IllegalArgumentException {
    Category category = categoryRepository.findByName(name);
    if (category == null) {
      throw new IllegalArgumentException("Category not found: " + name);
    }
    return convertToResponse(category);
  }

  /**
   * Creates a new category based on the provided CategoryCreateDTO.
   * The new category is saved to the database.
   * @param createDTO the DTO containing the details of the category to create
   * @return the created CategoryResponseDTO
   * @throws Exception if the category already exists or if the parent category is not found
   */
  public CategoryResponseDTO createCategory(CategoryCreateDTO createDTO) throws Exception {
    // Check if category already exists (based on unique name)
    Category existingCategory = categoryRepository.findByName(createDTO.getName());
    if (existingCategory != null) {
      throw new IllegalArgumentException("Category already exists: " + createDTO.getName());
    }

    // Create new category
    Category category = new Category();
    category.setName(createDTO.getName());

    // If a parent category name is provided, look it up and set it
    if (createDTO.getParentCategoryName() != null) {
      Category parent = categoryRepository.findByName(createDTO.getParentCategoryName());
      if (parent == null) {
        throw new IllegalArgumentException("Parent category not found: " + createDTO.getParentCategoryName());
      }
      category.setParentCategory(parent);
    }
    Category savedCategory = categoryRepository.save(category);
    return convertToResponse(savedCategory);
  }

  /**
   * Retrieves all categories from the database and converts them to a list of CategoryResponseDTOs.
   * @return a list of CategoryResponseDTOs containing all categories
   */
  public List<CategoryResponseDTO> getAllCategories() {
    return categoryRepository.findAll()
        .stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  /**
   * Updates an existing category based on the provided CategoryUpdateDTO.
   * The update includes changing the name, parent category, and subcategories.
   * The updated category is saved to the database.
   * @param id the ID of the category to update
   * @param updateDTO the {link CategoryUpdateDTO} containing the updated details of the category. 
   * The updateDTO contains the new name, the ID of the new parent category (if any),
   * and a list of subcategory IDs to remain associated with the category.
   * @return the updated {@link CategoryResponseDTO} containing the updated category details
   * @throws Exception if any provided category is not found or if there are issues with the update
   */
  @Transactional
  public Optional<CategoryResponseDTO> updateCategory(Long id, CategoryUpdateDTO updateDTO) throws Exception {
    return categoryRepository.findById(id).map(existingCategory -> {
      // Update name
      existingCategory.setName(updateDTO.getName());

      // Update parent category if provided (or remove if null)
      if (updateDTO.getParentCategoryId() != null) {
        Category newParent = categoryRepository.findById(updateDTO.getParentCategoryId())
            .orElseThrow(() -> new IllegalArgumentException(
                "Parent category not found with id: " + updateDTO.getParentCategoryId()));
        existingCategory.setParentCategory(newParent);
      } else {
        existingCategory.setParentCategory(null);
      }

      // Update subcategories if provided
      if (updateDTO.getSubCategoryIds() != null) {
        // Remove subcategories that are no longer associated
        existingCategory.getSubcategories().removeIf(sub -> !updateDTO.getSubCategoryIds().contains(sub.getId()));

        // For each subcategory ID, ensure that the subcategory's parent is set to the current category
        for (Long subId : updateDTO.getSubCategoryIds()) {
          Category subCategory = categoryRepository.findById(subId)
              .orElseThrow(() -> new IllegalArgumentException("Sub-category not found with id: " + subId));
          if (subCategory.getParentCategory() == null
              || !subCategory.getParentCategory().getId().equals(existingCategory.getId())) {
            subCategory.setParentCategory(existingCategory);
            if (!existingCategory.getSubcategories().contains(subCategory)) {
              existingCategory.getSubcategories().add(subCategory);
            }
          }
        }
      }
      Category updated = categoryRepository.save(existingCategory);
      return convertToResponse(updated);
    });
  }

  /**
   * Deletes a category by its ID.
   * @param id the ID of the category to delete
   * @throws Exception if the category is not found or if there are issues with deletion
   */
  public void deleteCategory(Long id) throws Exception {
    if (!categoryRepository.existsById(id)) {
      throw new IllegalArgumentException("Category not found with id: " + id);
    }
    categoryRepository.deleteById(id);
  }

   /**
    * Retrieves all descendant category IDs of a given parent category.
    * This includes the IDs of the parent category itself and all its subcategories.
    * @param parentName the name of the parent category
    * @return a set of IDs of all descendant categories
    * @throws IllegalArgumentException if the parent category is not found
    */
    public Set<Long> getDescendantCategoryIds(String parentName) {
        Category parent = categoryRepository.findByName(parentName);
        if (parent == null) {
            throw new IllegalArgumentException("Category not found: " + parentName);
        }
        Set<Long> ids = new HashSet<>();
        gatherDescendantIds(parent, ids);
        return ids;
    }

    /**
     * Helper method to recursively gather all descendant category IDs.
     * @param category the current category to process
     * @param ids the set of IDs to populate
     */
    private void gatherDescendantIds(Category category, Set<Long> ids) {
        ids.add(category.getId());
        if (category.getSubcategories() != null) {
            for (Category sub : category.getSubcategories()) {
                gatherDescendantIds(sub, ids);
            }
        }
    }

  // Additional Methods for Subcategory Operations
  
  /**
   * Adds a subcategory to a parent category.
   * @param parentName the name of the parent category
   * @param subDTO the DTO containing the details of the subcategory to add
   * @return the created CategoryResponseDTO for the subcategory
   * @throws Exception if the parent category is not found or if the subcategory already exists
   */
  public CategoryResponseDTO addSubCategory(String parentName, CategoryCreateDTO subDTO) throws Exception {
    Category parent = categoryRepository.findByName(parentName);
    if (parent == null) {
      throw new IllegalArgumentException("Parent category not found: " + parentName);
    }
    // Check if subcategory already exists
    Category existingSub = categoryRepository.findByName(subDTO.getName());
    if (existingSub != null) {
      throw new IllegalArgumentException("Sub-category already exists: " + subDTO.getName());
    }
    Category subCategory = new Category();
    subCategory.setName(subDTO.getName());
    subCategory.setParentCategory(parent);
    Category savedSub = categoryRepository.save(subCategory);
    // Update parent's subcategories list
    parent.getSubcategories().add(savedSub);
    categoryRepository.save(parent);
    return convertToResponse(savedSub);
  }

  /**
   * Removes a subcategory from a parent category.
   * @param parentName the name of the parent category
   * @param subCategoryName the name of the subcategory to remove
   * @throws Exception if the parent or subcategory is not found or if they are not linked correctly
   */
  public void removeSubCategory(String parentName, String subCategoryName) throws Exception {
    Category parent = categoryRepository.findByName(parentName);
    Category subCategory = categoryRepository.findByName(subCategoryName);
    if (parent == null || subCategory == null) {
      throw new IllegalArgumentException("Parent or sub-category not found");
    }
    if (subCategory.getParentCategory() == null || !subCategory.getParentCategory().getId().equals(parent.getId())) {
      throw new IllegalArgumentException("The sub-category is not linked to the specified parent");
    }
    parent.getSubcategories().remove(subCategory);
    subCategory.setParentCategory(null);
    categoryRepository.save(parent);
    categoryRepository.save(subCategory);
  }

  /**
   * Deletes all categories from the database.
   * This method is intended for testing purposes only.
   */
  public void deleteAllCategories() {
    categoryRepository.deleteAll();
  }
}
