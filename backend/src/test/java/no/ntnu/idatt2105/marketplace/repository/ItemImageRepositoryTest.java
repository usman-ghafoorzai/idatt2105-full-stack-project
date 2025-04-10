package no.ntnu.idatt2105.marketplace.repository;

import no.ntnu.idatt2105.marketplace.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ItemImageRepositoryTest {

  @Autowired
  private ItemImageRepository itemImageRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void testSaveAndFindByItemId() {
    Item item = itemRepository.save(createTestItem("Camera", 599.0));

    ItemImage image = new ItemImage();
    image.setData("dummy-data-1".getBytes());
    image.setFileName("camera.jpg");
    image.setContentType("image/jpeg");
    image.setItem(item);
    itemImageRepository.save(image);

    List<ItemImage> images = itemImageRepository.findByItemId(item.getId());
    assertThat(images).hasSize(1);
    assertThat(images.get(0).getFileName()).isEqualTo("camera.jpg");
  }

  @Test
  void testFindByItemIdAndImageId() {
    Item item = itemRepository.save(createTestItem("Phone", 299.0));

    ItemImage image = new ItemImage();
    image.setData("dummy-data-2".getBytes());
    image.setFileName("phone.jpg");
    image.setContentType("image/jpeg");
    image.setItem(item);
    image = itemImageRepository.save(image);

    Optional<ItemImage> found = itemImageRepository.findByItemIdAndId(item.getId(), image.getId());
    assertThat(found).isPresent();
    assertThat(found.get().getFileName()).isEqualTo("phone.jpg");
  }

  @Test
  void testDeleteByItemIdAndId() {
    Item item = itemRepository.save(createTestItem("Monitor", 199.0));

    ItemImage image = new ItemImage();
    image.setData("dummy-data-3".getBytes());
    image.setFileName("monitor.jpg");
    image.setContentType("image/jpeg");
    image.setItem(item);
    image = itemImageRepository.save(image);

    itemImageRepository.deleteByItemIdAndId(item.getId(), image.getId());
    Optional<ItemImage> deleted = itemImageRepository.findById(image.getId());
    assertThat(deleted).isEmpty();
  }

  @Test
  void testDeleteByItemId() {
    Item item = itemRepository.save(createTestItem("Mouse", 49.0));

    ItemImage image1 = new ItemImage();
    image1.setData("image1".getBytes());
    image1.setFileName("mouse1.jpg");
    image1.setContentType("image/jpeg");
    image1.setItem(item);

    ItemImage image2 = new ItemImage();
    image2.setData("image2".getBytes());
    image2.setFileName("mouse2.jpg");
    image2.setContentType("image/jpeg");
    image2.setItem(item);

    itemImageRepository.saveAll(List.of(image1, image2));
    itemImageRepository.deleteByItemId(item.getId());

    List<ItemImage> remaining = itemImageRepository.findByItemId(item.getId());
    assertThat(remaining).isEmpty();
  }

  private Item createTestItem(String title, Double price) {
    Category category = new Category();
    category.setName("Electronics");
    category = categoryRepository.save(category);

    User seller = new User();
    seller.setUsername("seller_" + title);
    seller.setEmail(title.toLowerCase() + "@example.com");
    seller.setPassword("password123");
    seller.setFirstName("Seller");
    seller.setLastName("Name");
    seller.setRole(Role.USER);
    seller = userRepository.save(seller);

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
