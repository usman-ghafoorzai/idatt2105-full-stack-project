package no.ntnu.idatt2105.marketplace.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Bookmark;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.repository.BookmarkRepository;
/**
 * Service class for handling bookmark-related business logic.
 * Provides methods to retrieve bookmarked items for a specific user.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

  private final BookmarkRepository bookmarkRepository;
  private final UserService userService;
  private final ItemService itemService;

  /**
   * Retrieves a list of items bookmarked by the specified user.
   *
   * @param userId the unique identifier of the user
   * @return a list of items bookmarked by the user
   * @throws IllegalArgumentException if the user is not found
   */
  public List<Item> getBookmarkedItemsByUserId(Long userId) {
    List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);
    return bookmarks.stream()
        .map(Bookmark::getItem)
        .toList();
  }

  /**
   * Saves a new bookmark for a specific user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @return the saved bookmark entity
   */
  public Bookmark saveBookmark(Long userId, Long itemId) {
    User user = userService.getUserById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    
    Item item = itemService.getItemEntityById(itemId)
        .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));
    
    Bookmark bookmark = new Bookmark(user, item);
    return bookmarkRepository.save(bookmark);
  }

  /**
   * Deletes a bookmark for a specific user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   */
  public void deleteBookmark(Long userId, Long itemId) {
    bookmarkRepository.deleteByUserIdAndItemId(userId, itemId);
  }

  
}
