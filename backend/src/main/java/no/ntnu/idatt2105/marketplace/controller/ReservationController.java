package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.Reservation;
import no.ntnu.idatt2105.marketplace.service.ReservationService;

/**
 * Controller class for handling reservation-related HTTP requests.
 * Provides endpoints to manage item reservations by users.
 */
@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  /**
   * Retrieves the list of items reserved by a specific user.
   *
   * @param userId the unique identifier of the user
   * @return {@code ResponseEntity} containing the list of reserved items
   */
  @GetMapping("/{userId}")
  public ResponseEntity<List<Item>> getReservedItems(@PathVariable Long userId) {
    List<Item> items = reservationService.getReservedItemsByUserId(userId);
    return ResponseEntity.ok(items);
  }

  /**
   * Creates a new reservation for the specified user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @return {@code ResponseEntity} containing the created reservation
   */
  @PostMapping("/{userId}/{itemId}")
  public ResponseEntity<Reservation> reserveItem(@PathVariable Long userId, @PathVariable Long itemId) {
    Reservation reservation = reservationService.saveReservation(userId, itemId);
    return ResponseEntity.ok(reservation);
  }

  /**
   * Deletes a reservation for the specified user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @return {@code ResponseEntity} indicating the result of the deletion
   */
  @DeleteMapping("/{userId}/{itemId}")
  public ResponseEntity<Void> deleteReservation(@PathVariable Long userId, @PathVariable Long itemId) {
    reservationService.deleteReservation(userId, itemId);
    return ResponseEntity.noContent().build();
  }
}
