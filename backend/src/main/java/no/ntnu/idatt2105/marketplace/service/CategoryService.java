package no.ntnu.idatt2105.marketplace.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.dto.CategoryCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryUpdateDTO;
import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

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

  public CategoryResponseDTO findByName(String name) {
    Category category = categoryRepository.findByName(name);
    if (category == null) {
      throw new IllegalArgumentException("Category not found: " + name);
    }
    return convertToResponse(category);
  }

  public CategoryResponseDTO createCategory(CategoryCreateDTO createDTO) throws Exception {
    // Check if category already exists (based on unique name)
    Category existingCategory = categoryRepository.findByName(createDTO.getName());
    if (existingCategory != null) {
      throw new IllegalArgumentException("Category already exists: " + createDTO.getName());
    }
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

  public List<CategoryResponseDTO> getAllCategories() {
    return categoryRepository.findAll()
        .stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

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

  public void deleteCategory(Long id) throws Exception {
    if (!categoryRepository.existsById(id)) {
      throw new IllegalArgumentException("Category not found with id: " + id);
    }
    categoryRepository.deleteById(id);
  }

  // Additional Methods for Subcategory Operations
  
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
}
