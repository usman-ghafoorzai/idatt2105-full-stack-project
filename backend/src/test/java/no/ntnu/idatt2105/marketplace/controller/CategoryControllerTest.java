package no.ntnu.idatt2105.marketplace.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import no.ntnu.idatt2105.marketplace.dto.CategoryCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.CategoryResponseDTO;
import no.ntnu.idatt2105.marketplace.service.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
  private MockMvc mockMvc;

  @Mock
  private CategoryService categoryService;

  @InjectMocks
  private CategoryController categoryController;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Nested
  @DisplayName("Positive test cases")
  class PositiveTests {

    @Test
    void testFindByName() throws Exception {
      CategoryResponseDTO responseDTO = new CategoryResponseDTO();
      responseDTO.setId(1L);
      responseDTO.setName("Electronics");

      when(categoryService.findByName("Electronics")).thenReturn(responseDTO);

      mockMvc.perform(get("/api/categories/findByName/Electronics"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(1L))
          .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test
    void testCreateCategory() throws Exception {
      CategoryCreateDTO createDTO = new CategoryCreateDTO();
      createDTO.setName("Electronics");
      createDTO.setParentCategoryName(null);

      CategoryResponseDTO responseDTO = new CategoryResponseDTO();
      responseDTO.setId(1L);
      responseDTO.setName("Electronics");

      when(categoryService.createCategory(any(CategoryCreateDTO.class))).thenReturn(responseDTO);

      mockMvc.perform(post("/api/categories/create")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createDTO)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(1)))
          .andExpect(jsonPath("$.name", is("Electronics")));
    }

    @Test
    void testGetAllCategories() throws Exception {
      CategoryResponseDTO category1 = new CategoryResponseDTO();
      category1.setId(1L);
      category1.setName("Electronics");

      CategoryResponseDTO category2 = new CategoryResponseDTO();
      category2.setId(2L);
      category2.setName("Books");

      when(categoryService.getAllCategories()).thenReturn(List.of(category1, category2));

      mockMvc.perform(get("/api/categories"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].id").value(1L))
          .andExpect(jsonPath("$[0].name").value("Electronics"))
          .andExpect(jsonPath("$[1].id").value(2L))
          .andExpect(jsonPath("$[1].name").value("Books"));
    }

    @Test
    void testGetAllCategories_NoContent() throws Exception {
      when(categoryService.getAllCategories()).thenReturn(List.of());

      mockMvc.perform(get("/api/categories"))
          .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateCategory_Ok() throws Exception {
      CategoryResponseDTO responseDTO = new CategoryResponseDTO();
      responseDTO.setId(1L);
      responseDTO.setName("Updated Electronics");

      when(categoryService.updateCategory(eq(1L), any())).thenReturn(java.util.Optional.of(responseDTO));

      mockMvc.perform(put("/api/categories/update/1")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(new CategoryCreateDTO())))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(1)))
          .andExpect(jsonPath("$.name", is("Updated Electronics")));
    }

    @Test
    void testDeleteCategory() throws Exception {
      mockMvc.perform(delete("/api/categories/delete/1"))
          .andExpect(status().isNoContent());
    }

    @Test
    void testAddSubCategory() throws Exception {
      // Arrange
      String parentName = "Electronics";
      CategoryCreateDTO createDTO = new CategoryCreateDTO();
      createDTO.setName("Phones");

      CategoryResponseDTO responseDTO = new CategoryResponseDTO();
      responseDTO.setId(7L);
      responseDTO.setName("Phones");

      when(categoryService.addSubCategory(eq(parentName), any(CategoryCreateDTO.class)))
          .thenReturn(responseDTO);

      // Act & Assert
      mockMvc.perform(post("/api/categories/{parentName}/subcategories", parentName)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createDTO)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(7)))
          .andExpect(jsonPath("$.name", is("Phones")));
    }

    @Test
    void testRemoveSubCategory() throws Exception {
      String parentName = "Electronics";
      String subCategoryName = "Phones";

      mockMvc
          .perform(delete("/api/categories/{parentName}/subcategories/{subCategoryName}", parentName, subCategoryName))
          .andExpect(status().isNoContent());
    }

  }

  @Nested
  @DisplayName("Negative test cases")
  class NegativeTests {
    @Test
    void testFindByNameNotFound() throws Exception {
      when(categoryService.findByName("NonExistentCategory"))
          .thenThrow(new IllegalArgumentException("Category not found"));

      mockMvc.perform(get("/api/categories/findByName/NonExistentCategory")).andExpect(status().isNotFound());
    }

    @Test
    void testCreateCategoryInvalidParent() throws Exception {
      CategoryCreateDTO createDTO = new CategoryCreateDTO();
      createDTO.setName("Electronics");
      createDTO.setParentCategoryName("NonExistentParent");

      when(categoryService.createCategory(any(CategoryCreateDTO.class)))
          .thenThrow(new IllegalArgumentException("Parent category not found"));

      mockMvc.perform(post("/api/categories/create")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createDTO)))
          .andExpect(status().isBadRequest())
          .andExpect(header().string("Error-Message", "Parent category not found"));
    }

    @Test
    void testCreateCategoryAlreadyExists() throws Exception {
      CategoryCreateDTO createDTO = new CategoryCreateDTO();
      createDTO.setName("Electronics");
      createDTO.setParentCategoryName(null);

      when(categoryService.createCategory(any(CategoryCreateDTO.class)))
          .thenThrow(new IllegalArgumentException("Category already exists"));

      mockMvc.perform(post("/api/categories/create")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createDTO)))
          .andExpect(status().isBadRequest())
          .andExpect(header().string("Error-Message", "Category already exists"));
    }

    @Test
    void testUpdateCategoryNotFound() throws Exception {
      when(categoryService.updateCategory(eq(1L), any())).thenThrow(new IllegalArgumentException("Category not found"));

      mockMvc.perform(put("/api/categories/update/1")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(new CategoryCreateDTO())))
          .andExpect(status().isNotFound())
          .andExpect(header().string("Error-Message", "Category not found"));
    }

    @Test
    void testDeleteCategoryNotFound() throws Exception {
      doThrow(new IllegalArgumentException("Category not found with id: 4")).when(categoryService).deleteCategory(4L);

      mockMvc.perform(delete("/api/categories/delete/4"))
          .andExpect(status().isNotFound())
          .andExpect(header().string("Error-Message", "Category not found with id: 4"));
    }

    @Test
    void testAddSubCategoryParentNotFound() throws Exception {
      String parentName = "NonExistentParent";
      CategoryCreateDTO createDTO = new CategoryCreateDTO();
      createDTO.setName("Phones");

      when(categoryService.addSubCategory(eq(parentName), any(CategoryCreateDTO.class)))
          .thenThrow(new IllegalArgumentException("Parent category not found"));

      mockMvc.perform(post("/api/categories/{parentName}/subcategories", parentName)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createDTO)))
          .andExpect(status().isInternalServerError())
          .andExpect(header().string("Error-Message", "Parent category not found"));
    }

    @Test
    void testRemoveSubCategory_BadRequest() throws Exception {
      // Arrange: simulate exception thrown when removal fails
      String parentName = "ParentCat";
      String subCategoryName = "WrongSubCat";

      doThrow(new IllegalArgumentException("The sub-category is not linked to the specified parent"))
          .when(categoryService).removeSubCategory(parentName, subCategoryName);

      // Act & Assert
      mockMvc.perform(delete("/api/categories/{parentName}/subcategories/{subCategoryName}",
          parentName, subCategoryName))
          .andExpect(status().isBadRequest())
          .andExpect(header().string("Error-Message", "The sub-category is not linked to the specified parent"));
    }
  }
}
