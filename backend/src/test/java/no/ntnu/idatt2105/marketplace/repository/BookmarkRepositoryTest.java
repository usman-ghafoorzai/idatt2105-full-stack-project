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
public class BookmarkRepositoryTest {

  @Autowired
  private BookmarkRepository bookmarkRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void testSaveAndFindByUserId() {
    Bookmark bookmark = createAndSaveBookmark();
    List<Bookmark> bookmarks = bookmarkRepository.findByUserId(bookmark.getUser().getId());

    assertThat(bookmarks).isNotEmpty();
    assertThat(bookmarks.get(0).getItem().getTitle()).isEqualTo("Test Item");
  }

  @Test
  void testDeleteByUserIdAndItemId() {
    Bookmark bookmark = createAndSaveBookmark();

    bookmarkRepository.deleteByUserIdAndItemId(
        bookmark.getUser().getId(), bookmark.getItem().getId());

    Optional<Bookmark> deleted = bookmarkRepository.findByUserIdAndItemId(
        bookmark.getUser().getId(), bookmark.getItem().getId());

    assertThat(deleted).isNotPresent();
  }

  @Test
  void testFindByUserIdAndItemId() {
    Bookmark bookmark = createAndSaveBookmark();

    Optional<Bookmark> found = bookmarkRepository.findByUserIdAndItemId(
        bookmark.getUser().getId(), bookmark.getItem().getId());

    assertThat(found).isPresent();
    assertThat(found.get().getItem().getTitle()).isEqualTo("Test Item");
  }

  @Test
  void testFindByUserIdAndItemIdNotFound() {
    Optional<Bookmark> found = bookmarkRepository.findByUserIdAndItemId(999L, 999L);

    assertThat(found).isNotPresent();
  }

  

  private Bookmark createAndSaveBookmark() {
    User user = new User();
    user.setUsername("bookmarkuser");
    user.setEmail("bookmark@example.com");
    user.setPassword("password");
    user.setFirstName("Book");
    user.setLastName("Marker");
    user.setRole(Role.USER);
    user = userRepository.save(user);

    Category category = new Category();
    category.setName("Tech");
    category = categoryRepository.save(category);

    Item item = new Item();
    item.setTitle("Test Item");
    item.setDescription("A cool item.");
    item.setPrice(123.0);
    item.setStatus(ItemStatus.ACTIVE);
    item.setSeller(user);
    item.setCategories(Set.of(category));
    item = itemRepository.save(item);

    Bookmark bookmark = new Bookmark();
    bookmark.setUser(user);
    bookmark.setItem(item);

    return bookmarkRepository.save(bookmark);
  }
}
