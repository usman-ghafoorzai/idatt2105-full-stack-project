package no.ntnu.idatt2105.marketplace.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.ntnu.idatt2105.marketplace.dto.CategoryResponseDTO;
import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
  
  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

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
}
