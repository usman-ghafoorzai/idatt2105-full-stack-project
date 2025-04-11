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
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
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
@Tag(name = "Reservations", description = "Operations for managing item reservations")
public class ReservationController {

  private final ReservationService reservationService;

  /**
   * Retrieves the list of items reserved by a specific user.
   *
   * @param userId the unique identifier of the user
   * @return {@code ResponseEntity} containing the list of reserved items
   */
  @Operation(
      summary = "Get reserved items",
      description = "Retrieves the list of items reserved by a specific user."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reserved items retrieved successfully",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "User not found or no reservations found", content = @Content)
  })
  @GetMapping("/{userId}")
  public ResponseEntity<List<ItemResponseDTO>> getReservedItems(@PathVariable Long userId) {
    List<ItemResponseDTO> items = reservationService.getReservedItemsByUserId(userId);
    return ResponseEntity.ok(items);
  }

  /**
   * Creates a new reservation for the specified user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @return {@code ResponseEntity} containing the result of the operation
   */
  @Operation(
      summary = "Reserve an item",
      description = "Creates a new reservation for the specified user and item."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reservation created successfully",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request - error creating reservation", content = @Content)
  })
  @PostMapping("/{userId}/{itemId}")
  public ResponseEntity<?> reserveItem(@PathVariable Long userId, @PathVariable Long itemId) {
    try {
      reservationService.saveReservation(userId, itemId);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(500).body("An error occurred while reserving the item.");
    }
  }

  /**
   * Deletes a reservation for the specified user and item.
   *
   * @param userId the unique identifier of the user
   * @param itemId the unique identifier of the item
   * @return {@code ResponseEntity} indicating the result of the deletion
   */
  @Operation(
      summary = "Delete a reservation",
      description = "Deletes a reservation for the specified user and item."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Reservation deleted successfully", content = @Content),
      @ApiResponse(responseCode = "400", description = "Bad Request - error deleting reservation", content = @Content)
  })
  @DeleteMapping("/{userId}/{itemId}")
  public ResponseEntity<Void> deleteReservation(
    @Parameter(description = "The unique identifier of the user", required = true)
    @PathVariable Long userId,
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long itemId) {
    reservationService.deleteReservation(userId, itemId);
    return ResponseEntity.noContent().build();
  }
}
