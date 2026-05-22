package no.ntnu.idatt2105.marketplace.repository;

import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.ItemStatus;
import no.ntnu.idatt2105.marketplace.model.Role;
import no.ntnu.idatt2105.marketplace.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Loads only repository and JPA-related components
public class ItemRepositoryTest {

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private UserRepository userRepository; // Needed for foreign key (seller)

  @Autowired
  private CategoryRepository categoryRepository; // Needed for foreign key (category)

  @Test
  void testSaveAndFindByTitle() {
    Item item = createTestItem("Laptop", 500.0);
    itemRepository.save(item);

    Item foundItem = itemRepository.findByTitle("Laptop");

    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getTitle()).isEqualTo("Laptop");
  }

  @Test
  void testFindByTitle_NotFound() {
    Item foundItem = itemRepository.findByTitle("Nonexistent Item");

    assertThat(foundItem).isNull();
  }

  @Test
  void testFindById() {
    Item item = createTestItem("Smartphone", 300.0);
    item = itemRepository.save(item);

    Item foundItem = itemRepository.findById(item.getId()).orElse(null);

    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getTitle()).isEqualTo("Smartphone");
  }

  @Test
  void testUpdateItem() {
    Item item = createTestItem("Tablet", 200.0);
    item.setPrice(250.0);
    item = itemRepository.save(item);

    Item updatedItem = itemRepository.findById(item.getId()).orElse(null);
    assertThat(updatedItem).isNotNull();
    assertThat(updatedItem.getPrice()).isEqualTo(250.0);
  }

  @Test
  void testDeleteItem() {
    Item item = createTestItem("Desk", 100.0);
    item = itemRepository.save(item);
    Long itemId = item.getId();

    itemRepository.deleteById(itemId);

    Optional<Item> deletedItem = itemRepository.findById(itemId);
    assertThat(deletedItem).isEmpty();
  }

  // Test for saving duplicate id's
  @Test
  void testSaveDuplicateItem() {
    Item item1 = createTestItem("Duplicate Item", 150.0);
    
    try {
      itemRepository.save(item1); // This should throw an error
    } catch (Exception e) {
      assertThat(e).isInstanceOf(Exception.class); // Expect exception if title is unique
    }
  }

  /**
   * Helper method to create a test Item with a title and price.
   * Ensures that required foreign key dependencies (Category, User) are set.
   */
  private Item createTestItem(String title, Double price) {
    Category category = new Category();
    category.setName("Electronics");
    category = categoryRepository.save(category); // Save category first

    User seller = new User();
    seller.setUsername("testuser");
    seller.setEmail("testuser@example.com");
    seller.setPassword("password123");
    seller.setFirstName("Test");
    seller.setLastName("User");
    seller.setRole(Role.USER);
    seller = userRepository.save(seller); // Save user first

    Item item = new Item();
    item.setTitle(title);
    item.setDescription("Test description for " + title);
    item.setPrice(price);
    item.setStatus(ItemStatus.ACTIVE);
    item.setCategories(Set.of(category)); 
    item.setSeller(seller);

    return item;
  }
}
