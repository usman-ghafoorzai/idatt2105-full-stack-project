package no.ntnu.idatt2105.marketplace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
import no.ntnu.idatt2105.marketplace.model.Bookmark;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.repository.BookmarkRepository;

@ExtendWith(MockitoExtension.class)
public class BookmarkServiceTest {

  @Mock
  private BookmarkRepository bookmarkRepository;

  @Mock
  private UserService userService;

  @Mock
  private ItemService itemService;

  @InjectMocks
  private BookmarkService bookmarkService;

  @Test
  void testGetBookmarkedItemsByUserId_Found() {

    Long userId = 1L;
    User user = new User();
    user.setId(userId);
    Item item = new Item();
    item.setId(1L);
    item.setTitle("Sample Item");

    // Create ItemResponseDTO to match the service update
    ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
    itemResponseDTO.setId(item.getId());
    itemResponseDTO.setTitle(item.getTitle());
    
    Bookmark bookmark = new Bookmark();
    bookmark.setUser(user);
    bookmark.setItem(item);

    when(bookmarkRepository.findByUserId(userId)).thenReturn(Arrays.asList(bookmark));
    when(itemService.convertToResponse(item)).thenReturn(itemResponseDTO);

    List<ItemResponseDTO> result = bookmarkService.getBookmarkedItemsByUserId(userId);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getTitle()).isEqualTo("Sample Item");

    verify(bookmarkRepository, times(1)).findByUserId(userId);
    verify(itemService, times(1)).convertToResponse(item);
  }

  @Test
  void testSaveBookmark() {

    Long userId = 1L;
    Long itemId = 1L;
    User user = new User();
    user.setId(userId);

    Item item = new Item();
    item.setId(itemId);
    item.setTitle("New Item");

    Bookmark bookmark = new Bookmark();
    bookmark.setUser(user);
    bookmark.setItem(item);

    when(userService.getUserById(userId)).thenReturn(Optional.of(user));
    when(itemService.getItemEntityById(itemId)).thenReturn(Optional.of(item));
    when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);

    bookmarkService.saveBookmark(userId, itemId);

    verify(userService, times(1)).getUserById(userId);
    verify(itemService, times(1)).getItemEntityById(itemId);
    verify(bookmarkRepository, times(1)).save(any(Bookmark.class));
  }

  @Test
  void testSaveBookmark_UserNotFound() {

    Long userId = 1L;
    Long itemId = 1L;

    when(userService.getUserById(userId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
        () -> {
          bookmarkService.saveBookmark(userId, itemId);
        });

    assertThat(exception.getMessage()).isEqualTo("User not found with ID: " + userId);
    verify(userService, times(1)).getUserById(userId);
    verify(itemService, times(0)).getItemEntityById(itemId);
    verify(bookmarkRepository, times(0)).save(any(Bookmark.class));
  }

  @Test
  void testSaveBookmark_ItemNotFound() {

    Long userId = 1L;
    Long itemId = 1L;
    User user = new User();
    user.setId(userId);

    when(userService.getUserById(userId)).thenReturn(Optional.of(user));
    when(itemService.getItemEntityById(itemId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
        () -> {
          bookmarkService.saveBookmark(userId, itemId);
        });

    assertThat(exception.getMessage()).isEqualTo("Item not found with ID: " + itemId);
    verify(userService, times(1)).getUserById(userId);
    verify(itemService, times(1)).getItemEntityById(itemId);
    verify(bookmarkRepository, times(0)).save(any(Bookmark.class));
  }

  @Test
  void testDeleteBookmark() {

    Long userId = 1L;
    Long itemId = 1L;

    bookmarkService.deleteBookmark(userId, itemId);

    verify(bookmarkRepository, times(1)).deleteByUserIdAndItemId(userId, itemId);
  }
}
