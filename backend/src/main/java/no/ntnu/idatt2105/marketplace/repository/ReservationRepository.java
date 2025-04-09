package no.ntnu.idatt2105.marketplace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2105.marketplace.model.Reservation;

/**
 * Repository interface for managing Reservation entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Finds a reservation associated with a specific user by user ID.
     * @param userId the ID of the user whose reservation is to be found
     * @return a list of reservations associated with the specified user ID
     */
    List<Reservation> findByUserId(Long userId);

    /**
     * Deletes a reservation associated with a specific user and item by user ID and item ID.
     * @param userId the ID of the user whose reservation is to be deleted
     * @param itemId the ID of the item whose reservation is to be deleted
     */
    void deleteByUserIdAndItemId(Long userId, Long itemId);

    /**
     * Finds a reservation associated with a specific item by item ID.
     * @param itemId the ID of the item whose reservation is to be found
     * @return an optional containing the found reservation, or empty if not found
     */
    Optional<Reservation> findByItemId(Long itemId);
}
