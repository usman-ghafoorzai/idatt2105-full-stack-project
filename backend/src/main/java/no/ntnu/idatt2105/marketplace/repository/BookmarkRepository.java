package no.ntnu.idatt2105.marketplace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2105.marketplace.model.Bookmark;

/**
 * Repository interface for managing Bookmark entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    /**
     * Finds all bookmarks associated with a specific user by user ID.
     * @param userId the ID of the user whose bookmarks are to be found
     * @return a list of bookmarks associated with the specified user ID
     */
    List<Bookmark> findByUserId(Long userId);

    /**
     * Deletes a bookmark associated with a specific user and item by user ID and item ID.
     * @param userId the ID of the user whose bookmark is to be deleted
     * @param itemId the ID of the item whose bookmark is to be deleted
     */
    void deleteByUserIdAndItemId(Long userId, Long itemId);

    /**
     * Finds a bookmark associated with a specific user and item by user ID and item ID.
     * @param userId the ID of the user whose bookmark is to be found
     * @param itemId the ID of the item whose bookmark is to be found
     * @return an optional containing the found bookmark, or empty if not found
     */
    Optional<Bookmark> findByUserIdAndItemId(Long userId, Long itemId);
    
}
