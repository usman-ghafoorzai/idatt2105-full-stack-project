package no.ntnu.idatt2105.marketplace.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.Reservation;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.model.ItemStatus;
import no.ntnu.idatt2105.marketplace.repository.ReservationRepository;

/**
 * Service class for handling reservation-related business logic.
 * Provides methods to manage reservations for users and items.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final UserService userService;
  private final ItemService itemService;

  /**
   * Retrieves a list of items reserved by the specified user.
   *
   * @param userId the unique identifier of the user
   * @return a list of items reserved by the user
   * @throws IllegalArgumentException if the user is not found
   */
  public List<Item> getReservedItemsByUserId(Long userId) {
    List<Reservation> reservation = reservationRepository.findByUserId(userId);
    return reservation.stream()
        .map(Reservation::getItem)
        .toList();
  }

  /**
   * Saves a new reservation for a specific user and item.
   * It also sets the item status to RESERVED when a reservation is made.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @return the saved reservation entity
   * @throws IllegalArgumentException if the user or item is not found
   */
  public Reservation saveReservation(Long userId, Long itemId) {
    // Fetch user and item
    User user = userService.getUserById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

    Item item = itemService.getItemEntityById(itemId)
        .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));

    // Check if the item is already reserved
    if (item.getStatus() == ItemStatus.RESERVED) {
      throw new IllegalStateException("Item is already reserved.");
    }

    // Set the item's status to RESERVED
    item.setStatus(ItemStatus.RESERVED);
    itemService.saveItemEntity(item); // Save the item status update

    // Create and save the reservation
    Reservation reservation = new Reservation(user, item);
    return reservationRepository.save(reservation);
  }

  /**
   * Deletes a reservation for a specific user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @throws IllegalStateException if the item is not reserved
   */
  public void deleteReservation(Long userId, Long itemId) {
    reservationRepository.deleteByUserIdAndItemId(userId, itemId);
    Reservation reservation = reservationRepository.findByItemId(itemId)
        .orElseThrow(() -> new IllegalArgumentException("Reservation not found for item ID: " + itemId));

    if (!reservation.getItem().getId().equals(itemId)) {
      throw new IllegalArgumentException("Reservation not found for the specified item.");
    }

    // Set the item's status back to ACTIVE when reservation is deleted
    Item item = reservation.getItem();
    item.setStatus(ItemStatus.ACTIVE);
    itemService.saveItemEntity(item); // Save the item status update

    // Delete the reservation
    reservationRepository.delete(reservation);
  }
}
