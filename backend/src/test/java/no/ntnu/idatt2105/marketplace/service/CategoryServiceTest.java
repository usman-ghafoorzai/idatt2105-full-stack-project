package no.ntnu.idatt2105.marketplace.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.ntnu.idatt2105.marketplace.dto.CategoryCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryUpdateDTO;
import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
  
  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  @Nested
  @DisplayName("Positive Tests")
  class PositiveTests {

    @Test
    void testFindByName() {
      Category category = new Category();
      category.setId(1L);
      category.setName("Test Category");
      category.setSubcategories(new ArrayList<>());
      
      when(categoryRepository.findByName("Test Category")).thenReturn(category);
      
      CategoryResponseDTO response = categoryService.findByName("Test Category");
      
      assertNotNull(response);
      assertEquals(1L, response.getId());
      assertEquals("Test Category", response.getName());
      assertEquals(0, response.getSubcategories().size());
      assertNull(response.getParentCategory());
    }

    @Test
    void testCreateCategoryWithoutParent() throws Exception {
      CategoryCreateDTO createDTO = new CategoryCreateDTO();
      createDTO.setName("New Category");
      createDTO.setParentCategoryName(null);
      
      when(categoryRepository.findByName("New Category")).thenReturn(null);
      
      Category savedCategory = new Category();
      savedCategory.setId(1L);
      savedCategory.setName("New Category");
      savedCategory.setSubcategories(new ArrayList<>());
      when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

      CategoryResponseDTO response = categoryService.createCategory(createDTO);
      
      assertNotNull(response);
      assertEquals(1L, response.getId());
      assertEquals("New Category", response.getName());
      assertEquals(0, response.getSubcategories().size());
      assertNull(response.getParentCategory());
    }

    @Test
    void testCreateCategoryWithParent() throws Exception {
      CategoryCreateDTO createDTO = new CategoryCreateDTO();
      createDTO.setName("Child Category");
      createDTO.setParentCategoryName("Parent Category");
      
      Category parentCategory = new Category();
      parentCategory.setId(2L);
      parentCategory.setName("Parent Category");
      parentCategory.setSubcategories(new ArrayList<>());
      
      when(categoryRepository.findByName("Child Category")).thenReturn(null);
      when(categoryRepository.findByName("Parent Category")).thenReturn(parentCategory);
      
      Category savedCategory = new Category();
      savedCategory.setId(3L);
      savedCategory.setName("Child Category");
      savedCategory.setParentCategory(parentCategory);
      savedCategory.setSubcategories(new ArrayList<>());
      
      when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

      CategoryResponseDTO response = categoryService.createCategory(createDTO);
      
      assertNotNull(response);
      assertEquals(3L, response.getId());
      assertEquals("Child Category", response.getName());
      assertEquals(0, response.getSubcategories().size());
      assertNotNull(response.getParentCategory());
      assertEquals(2L, response.getParentCategory().getId());
    }

    @Test
    void testGetAllCategories() {
      Category category1 = new Category();
      category1.setId(1L);
      category1.setName("Category 1");
      category1.setSubcategories(new ArrayList<>());
      
      Category category2 = new Category();
      category2.setId(2L);
      category2.setName("Category 2");
      category2.setSubcategories(new ArrayList<>());
      
      when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));
      
      List<CategoryResponseDTO> response = categoryService.getAllCategories();
      
      assertNotNull(response);
      assertEquals(2, response.size());
      assertEquals("Category 1", response.get(0).getName());
      assertEquals("Category 2", response.get(1).getName());
    }

    @Test
    void testUpdateCategory() throws Exception {
        Category category = new Category();
    category.setId(1L);
    category.setName("Old Name");
    category.setSubcategories(new ArrayList<>());

    CategoryUpdateDTO updateDTO = new CategoryUpdateDTO();
    updateDTO.setName("New Name");
    updateDTO.setParentCategoryId(2L);
    updateDTO.setSubCategoryIds(new ArrayList<>()); 

    Category newParent = new Category();
    newParent.setId(2L);
    newParent.setName("Parent Category");

    when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
    when(categoryRepository.findById(2L)).thenReturn(Optional.of(newParent));
    when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Optional<CategoryResponseDTO> responseOpt = categoryService.updateCategory(1L, updateDTO);

    assertTrue(responseOpt.isPresent());
    assertEquals("New Name", responseOpt.get().getName());
    assertNotNull(responseOpt.get().getParentCategory());
    assertEquals(2L, responseOpt.get().getParentCategory().getId());
    }

  @Test
  void testDeleteCategory() throws Exception {
    when(categoryRepository.existsById(1L)).thenReturn(true);
    doNothing().when(categoryRepository).deleteById(1L);

    categoryService.deleteCategory(1L);

    verify(categoryRepository, times(1)).deleteById(1L);
  }

  @Test
  void testAddSubCategory() throws Exception {
    // Arrange: Create a parent category with an empty subcategories list.
    Category parent = new Category();
    parent.setId(1L);
    parent.setName("Parent Category");
    parent.setSubcategories(new ArrayList<>());

    CategoryCreateDTO subDTO = new CategoryCreateDTO();
    subDTO.setName("Sub Category");

    when(categoryRepository.findByName("Parent Category")).thenReturn(parent);
    when(categoryRepository.findByName("Sub Category")).thenReturn(null);

    // Simulate saving a new sub-category.
    when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
      Category arg = invocation.getArgument(0);
      if (arg.getId() == null) {
        arg.setId(10L);
      }
      return arg;
    });
    when(categoryRepository.save(parent)).thenReturn(parent);

    CategoryResponseDTO response = categoryService.addSubCategory("Parent Category", subDTO);

    assertNotNull(response);
    assertEquals(10L, response.getId());
    assertEquals("Sub Category", response.getName());
    assertEquals(1, parent.getSubcategories().size());
    assertEquals("Sub Category", parent.getSubcategories().get(0).getName());
    assertEquals(10L, parent.getSubcategories().get(0).getId());
  }

  @Test
  void testRemoveSubCategory() throws Exception {
    Category parent = new Category();
    parent.setId(1L);
    parent.setName("Parent Category");
    parent.setSubcategories(new ArrayList<>());
    
    Category subCategory = new Category();
    subCategory.setId(2L);
    subCategory.setName("Sub Category");
    subCategory.setParentCategory(parent);
    
    parent.getSubcategories().add(subCategory);

    when(categoryRepository.findByName("Parent Category")).thenReturn(parent);
    when(categoryRepository.findByName("Sub Category")).thenReturn(subCategory);



    categoryService.removeSubCategory("Parent Category", "Sub Category");

    
    assertEquals(0, parent.getSubcategories().size());
    assertNull(subCategory.getParentCategory());
    verify(categoryRepository, times(1)).save(parent);
    verify(categoryRepository, times(1)).save(subCategory);
  }
  
  }

  @Nested
  @DisplayName("Negative Tests")
  class NegativeTests {

    @Test
    void testFindByName() {
      when(categoryRepository.findByName("Nonexistent Category")).thenReturn(null);
      
      assertEquals("Category not found: Nonexistent Category",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.findByName("Nonexistent Category");
          }).getMessage());
    }

    @Test
    void testCreateCategoryAlreadyExists() throws Exception {
      CategoryCreateDTO createDTO = new CategoryCreateDTO();
      createDTO.setName("Existing Category");
      createDTO.setParentCategoryName(null);

      Category existingCategory = new Category();
      existingCategory.setId(1L);
      existingCategory.setName("Existing Category");
      existingCategory.setSubcategories(new ArrayList<>());

      when(categoryRepository.findByName("Existing Category")).thenReturn(existingCategory);

      assertEquals("Category already exists: Existing Category",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.createCategory(createDTO);
          }).getMessage());
    }

    @Test
    void testCreateCategoryParentNotFound() throws Exception {
      CategoryCreateDTO createDTO = new CategoryCreateDTO();
      createDTO.setName("Child Category");
      createDTO.setParentCategoryName("Nonexistent Parent Category");

      when(categoryRepository.findByName("Child Category")).thenReturn(null);
      when(categoryRepository.findByName("Nonexistent Parent Category")).thenReturn(null);

      assertEquals("Parent category not found: Nonexistent Parent Category",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.createCategory(createDTO);
          }).getMessage());
    }

    @Test
    void testUpdateCategoryParentNotFound() throws Exception {
      CategoryUpdateDTO updateDTO = new CategoryUpdateDTO();
      updateDTO.setName("Updated Name");
      updateDTO.setParentCategoryId(999L); // Nonexistent parent ID

      when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category()));
      when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

      assertEquals("Parent category not found with id: 999",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.updateCategory(1L, updateDTO);
          }).getMessage());
    }

    @Test
    void testDeleteCategoryNotFound() {
      when(categoryRepository.existsById(1L)).thenReturn(false);
  
      assertEquals("Category not found with id: 1",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.deleteCategory(1L);
          }).getMessage());
    }

    @Test
    void testAddSubCategoryParentNotFound() {
      CategoryCreateDTO subDTO = new CategoryCreateDTO();
      subDTO.setName("Sub Category");

      when(categoryRepository.findByName("Parent Category")).thenReturn(null);

      assertEquals("Parent category not found: Parent Category",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.addSubCategory("Parent Category", subDTO);
          }).getMessage());
    }

    @Test
    void testAddSubCategoryAlreadyExists() {
      Category parent = new Category();
      parent.setId(1L);
      parent.setName("Parent Category");
      parent.setSubcategories(new ArrayList<>());
      
      CategoryCreateDTO subDTO = new CategoryCreateDTO();
      subDTO.setName("Sub Category");

      when(categoryRepository.findByName("Parent Category")).thenReturn(parent);
      when(categoryRepository.findByName("Sub Category")).thenReturn(new Category());

      assertEquals("Sub-category already exists: Sub Category",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.addSubCategory("Parent Category", subDTO);
          }).getMessage());
    }

    @Test
    void testRemoveSubCategoryParentNotFound() {
      when(categoryRepository.findByName("Parent Category")).thenReturn(null);

      assertEquals("Parent or sub-category not found",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.removeSubCategory("Parent Category", "Sub Category");
          }).getMessage());
    }

    @Test
    void testRemoveSubCategoryNotFound() {
      Category parent = new Category();
      parent.setId(1L);
      parent.setName("Parent Category");
      parent.setSubcategories(new ArrayList<>());
      
      when(categoryRepository.findByName("Parent Category")).thenReturn(parent);
      when(categoryRepository.findByName("Sub Category")).thenReturn(null);

      assertEquals("Parent or sub-category not found",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.removeSubCategory("Parent Category", "Sub Category");
          }).getMessage());
    }

    @Test
    void testRemoveSubCategoryNotLinked() {
      Category parent = new Category();
      parent.setId(1L);
      parent.setName("Parent Category");
      parent.setSubcategories(new ArrayList<>());
      
      Category subCategory = new Category();
      subCategory.setId(2L);
      subCategory.setName("Sub Category");
      subCategory.setParentCategory(null); // Not linked to parent
      
      when(categoryRepository.findByName("Parent Category")).thenReturn(parent);
      when(categoryRepository.findByName("Sub Category")).thenReturn(subCategory);

      assertEquals("The sub-category is not linked to the specified parent",
          assertThrows(IllegalArgumentException.class, () -> {
            categoryService.removeSubCategory("Parent Category", "Sub Category");
          }).getMessage());
    }
  }

}
