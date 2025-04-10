package no.ntnu.idatt2105.marketplace.service;

import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO.CategoryDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO.SellerDTO;
import no.ntnu.idatt2105.marketplace.model.*;
import no.ntnu.idatt2105.marketplace.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private UserService userService;

  @Mock
  private ItemService itemService;

  @InjectMocks
  private ReservationService reservationService;

  @Test
  void testGetReservedItemsByUserId() {
    Long userId = 1L;
    User user = new User();
    user.setId(userId);
    
    // Create ItemResponseDTO instead of Item
    ItemResponseDTO itemDTO = new ItemResponseDTO();
    itemDTO.setId(1L);
    itemDTO.setTitle("Reserved Item");
    itemDTO.setDescription("Test Item");
    itemDTO.setPrice(100.0);
    itemDTO.setLocationLatitude(59.9);
    itemDTO.setLocationLongitude(10.7);
    itemDTO.setStatus("RESERVED");
    itemDTO.setCreatedAt(LocalDateTime.now());

    CategoryDTO category = new CategoryDTO();
    category.setId(1L);
    category.setName("Electronics");
    itemDTO.setCategories(Set.of(category));

    SellerDTO seller = new SellerDTO();
    seller.setId(2L);
    seller.setUsername("testSeller");
    seller.setEmail("seller@example.com");
    itemDTO.setSeller(seller);

    Reservation reservation = new Reservation(user, new Item());
    when(reservationRepository.findByUserId(userId)).thenReturn(List.of(reservation));
    when(itemService.convertToResponse(reservation.getItem())).thenReturn(itemDTO);

    List<ItemResponseDTO> reservedItems = reservationService.getReservedItemsByUserId(userId);

    assertThat(reservedItems).hasSize(1);
    assertThat(reservedItems.get(0).getTitle()).isEqualTo("Reserved Item");

    verify(reservationRepository, times(1)).findByUserId(userId);
  }

  @Test
  void testSaveReservation_Success() {
    Long userId = 1L;
    Long itemId = 1L;

    User user = new User();
    user.setId(userId);

    // Create ItemResponseDTO instead of Item
    ItemResponseDTO itemDTO = new ItemResponseDTO();
    itemDTO.setId(itemId);
    itemDTO.setTitle("Reserved Item");
    itemDTO.setDescription("Test");
    itemDTO.setPrice(100.0);
    itemDTO.setLocationLatitude(59.9);
    itemDTO.setLocationLongitude(10.7);
    itemDTO.setStatus("ACTIVE");
    itemDTO.setCreatedAt(LocalDateTime.now());

    CategoryDTO category = new CategoryDTO();
    category.setId(1L);
    category.setName("Electronics");
    itemDTO.setCategories(Set.of(category));

    SellerDTO seller = new SellerDTO();
    seller.setId(2L);
    seller.setUsername("testSeller");
    seller.setEmail("seller@example.com");
    itemDTO.setSeller(seller);

    Item item = new Item();
    item.setId(itemId);
    item.setStatus(ItemStatus.ACTIVE);

    when(userService.getUserById(userId)).thenReturn(Optional.of(user));
    when(itemService.getItemEntityById(itemId)).thenReturn(Optional.of(item));
    when(itemService.saveItemEntity(any(Item.class))).thenReturn(item);

    reservationService.saveReservation(userId, itemId);

    // Assert the status is changed to RESERVED
    assertThat(item.getStatus()).isEqualTo(ItemStatus.RESERVED);
    verify(userService).getUserById(userId);
    verify(itemService).getItemEntityById(itemId);
    verify(itemService).saveItemEntity(item);
    verify(reservationRepository).save(any(Reservation.class));
  }

  @Test
  void testSaveReservation_UserNotFound() {
    Long userId = 1L;
    Long itemId = 1L;

    when(userService.getUserById(userId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> reservationService.saveReservation(userId, itemId));

    assertThat(exception.getMessage()).isEqualTo("User not found with ID: " + userId);

    verify(userService).getUserById(userId);
    verify(itemService, never()).getItemEntityById(itemId);
    verify(reservationRepository, never()).save(any());
  }

  @Test
  void testSaveReservation_ItemNotFound() {
    Long userId = 1L;
    Long itemId = 1L;
    User user = new User();
    user.setId(userId);

    when(userService.getUserById(userId)).thenReturn(Optional.of(user));
    when(itemService.getItemEntityById(itemId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> reservationService.saveReservation(userId, itemId));

    assertThat(exception.getMessage()).isEqualTo("Item not found with ID: " + itemId);

    verify(userService).getUserById(userId);
    verify(itemService).getItemEntityById(itemId);
    verify(reservationRepository, never()).save(any());
  }

  @Test
  void testSaveReservation_ItemAlreadyReserved() {
    Long userId = 1L;
    Long itemId = 1L;

    User user = new User();
    user.setId(userId);

    Item item = new Item();
    item.setId(itemId);
    item.setStatus(ItemStatus.RESERVED);

    when(userService.getUserById(userId)).thenReturn(Optional.of(user));
    when(itemService.getItemEntityById(itemId)).thenReturn(Optional.of(item));

    IllegalStateException exception = assertThrows(IllegalStateException.class,
        () -> reservationService.saveReservation(userId, itemId));

    assertThat(exception.getMessage()).isEqualTo("Item is already reserved.");

    verify(userService).getUserById(userId);
    verify(itemService).getItemEntityById(itemId);
    verify(reservationRepository, never()).save(any());
  }

  @Test
  void testDeleteReservation_Success() {
    Long userId = 1L;
    Long itemId = 1L;

    Item item = new Item();
    item.setId(itemId);
    item.setStatus(ItemStatus.RESERVED);

    User user = new User();
    user.setId(userId);

    Reservation reservation = new Reservation(user, item);

    when(reservationRepository.findByItemId(itemId)).thenReturn(Optional.of(reservation));

    reservationService.deleteReservation(userId, itemId);

    assertThat(item.getStatus()).isEqualTo(ItemStatus.ACTIVE);
    verify(itemService).saveItemEntity(item);
    verify(reservationRepository).delete(reservation);
  }

  @Test
  void testDeleteReservation_ReservationNotFound() {
    Long userId = 1L;
    Long itemId = 1L;

    when(reservationRepository.findByItemId(itemId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> reservationService.deleteReservation(userId, itemId));

    assertThat(exception.getMessage()).isEqualTo("Reservation not found for item ID: " + itemId);
    verify(reservationRepository, never()).delete(any());
    verify(itemService, never()).saveItem(any());
  }
}
