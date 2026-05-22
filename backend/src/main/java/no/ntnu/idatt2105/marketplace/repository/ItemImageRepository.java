package no.ntnu.idatt2105.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.idatt2105.marketplace.model.ItemImage;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing ItemImage entities.
 * Provides methods to perform CRUD operations on item images.
 */
@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

  /**
   * Finds all ItemImages associated with a specific item ID.
   *
   * @param itemId the ID of the item
   * @return a list of ItemImages associated with the item
   */
  List<ItemImage> findByItemId(Long itemId);

  /**
   * Finds a specific ItemImage by its ID and item ID.
   *
   * @param itemId  the ID of the item
   * @param imageId the ID of the image
   * @return an Optional containing the ItemImage if found, otherwise empty
   */
  Optional<ItemImage> findByItemIdAndId(Long itemId, Long imageId);

  /**
   * Deletes all ItemImages by the item ID.
   *
   * @param itemId the ID of the item
   */
  void deleteByItemId(Long itemId);

  /**
   * Deletes a specific ItemImage by its ID and item ID.
   *
   * @param itemId  the ID of the item
   * @param imageId the ID of the image
   */
  void deleteByItemIdAndId(Long itemId, Long imageId);
}
