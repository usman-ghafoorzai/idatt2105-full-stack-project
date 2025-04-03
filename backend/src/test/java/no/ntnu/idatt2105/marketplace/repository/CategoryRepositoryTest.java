package no.ntnu.idatt2105.marketplace.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import no.ntnu.idatt2105.marketplace.model.Category;

@DataJpaTest // Loads only repository and JPA-related components
public class CategoryRepositoryTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void testSaveAndFindByName() {
    Category category = new Category();
    category.setName("Electronics");
    categoryRepository.save(category);

    Category foundCategory = categoryRepository.findByName("Electronics");

    assertThat(foundCategory).isNotNull();
    assertThat(foundCategory.getName()).isEqualTo("Electronics");
  }

  @Test
  void testFindByName_NotFound() {
    Category foundCategory = categoryRepository.findByName("NonExisting");

    assertThat(foundCategory).isNull();
  }

  @Test
  void testFindById() {
    Category category = new Category();
    category.setName("Books");
    category = categoryRepository.save(category); // Save and retrieve with ID

    Category foundCategory = categoryRepository.findById(category.getId()).orElse(null);

    assertThat(foundCategory).isNotNull();
    assertThat(foundCategory.getName()).isEqualTo("Books");
  }

  @Test
  void testUpdateCategory() {
    Category category = new Category();
    category.setName("Clothing");
    category = categoryRepository.save(category);
  
    category.setName("Fashion");
    category = categoryRepository.save(category); // Update existing entry

    Category updatedCategory = categoryRepository.findById(category.getId()).orElse(null);
    assertThat(updatedCategory).isNotNull();
    assertThat(updatedCategory.getName()).isEqualTo("Fashion");
  }
}
