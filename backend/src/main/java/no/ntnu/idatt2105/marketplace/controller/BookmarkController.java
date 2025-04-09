package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.service.BookmarkService;

/**
 * Controller class for handling bookmark-related HTTP requests.
 * Provides endpoints to retrieve items bookmarked by a user.
 */
@RestController
@RequestMapping("/api/bookmarks")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BookmarkController {

  private final BookmarkService bookmarkService;

  /**
   * Retrieves the list of items bookmarked by a specific user.
   *
   * @param userId the unique identifier of the user
   * @return {@code ResponseEntity} containing the list of bookmarked items
   */
  @GetMapping("/{userId}")
  public ResponseEntity<List<Item>> getBookmarkedItems(@PathVariable Long userId) {
    List<Item> items = bookmarkService.getBookmarkedItemsByUserId(userId);
    return ResponseEntity.ok(items);
  }

  /**
   * Creates a new bookmark for the specified user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @return {@code ResponseEntity} containing the created bookmark
   */
  @PostMapping("/{userId}/{itemId}")
  public ResponseEntity<Void> bookmarkItem(@PathVariable Long userId, @PathVariable Long itemId) {
    bookmarkService.saveBookmark(userId, itemId);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes a bookmark for the specified user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @return {@code ResponseEntity} indicating the result of the deletion
   */
  @DeleteMapping("/{userId}/{itemId}")
  public ResponseEntity<Void> deleteBookmark(@PathVariable Long userId, @PathVariable Long itemId) {
    bookmarkService.deleteBookmark(userId, itemId);
    return ResponseEntity.noContent().build();
  }
}
