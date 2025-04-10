package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Bookmarks", description = "Operations related to managing bookmarks")
public class BookmarkController {

  private final BookmarkService bookmarkService;

  /**
   * Retrieves the list of items bookmarked by a specific user.
   *
   * @param userId the unique identifier of the user
   * @return {@code ResponseEntity} containing the list of bookmarked items
   */
  @Operation(
      summary = "Get bookmarked items",
      description = "Retrieves the list of items bookmarked by a specific user."
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Bookmarked items retrieved successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))),
    @ApiResponse(responseCode = "404", description = "User not found or no bookmarks found", content = @Content)
  })
  @GetMapping("/{userId}")
  public ResponseEntity<List<Item>> getBookmarkedItems(
    @Parameter(description = "The unique identifier of the user", required = true)
    @PathVariable Long userId) {
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
  @Operation(
      summary = "Bookmark an item",
      description = "Creates a new bookmark for the specified user and item."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item bookmarked successfully", content = @Content),
      @ApiResponse(responseCode = "400", description = "Error bookmarking the item", content = @Content)
  })
  @PostMapping("/{userId}/{itemId}")
  public ResponseEntity<Void> bookmarkItem(
    @Parameter(description = "The unique identifier of the user", required = true)
    @PathVariable Long userId,
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long itemId) {
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
  @Operation(
      summary = "Delete a bookmark",
      description = "Deletes a bookmark for the specified user and item."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Bookmark deleted successfully", content = @Content),
      @ApiResponse(responseCode = "400", description = "Error deleting the bookmark", content = @Content)
  })
  @DeleteMapping("/{userId}/{itemId}")
  public ResponseEntity<Void> deleteBookmark(
    @Parameter(description = "The unique identifier of the user", required = true)
    @PathVariable Long userId,
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long itemId) {
    bookmarkService.deleteBookmark(userId, itemId);
    return ResponseEntity.noContent().build();
  }
}
