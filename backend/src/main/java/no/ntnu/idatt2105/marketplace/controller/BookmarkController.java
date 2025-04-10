package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
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
  public ResponseEntity<List<ItemResponseDTO>> getBookmarkedItems(@PathVariable Long userId) {
    List<ItemResponseDTO> items = bookmarkService.getBookmarkedItemsByUserId(userId);
    return ResponseEntity.ok(items);
  }

  /**
   * Creates a new bookmark for the specified user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @return {@code ResponseEntity} containing the result of the operation
   */
  @PostMapping("/{userId}/{itemId}")
  public ResponseEntity<?> bookmarkItem(@PathVariable Long userId, @PathVariable Long itemId) {
    try {
      bookmarkService.saveBookmark(userId, itemId);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(500).body("An error occurred while bookmarking the item.");
    }
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
