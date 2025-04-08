package no.ntnu.idatt2105.marketplace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.idatt2105.marketplace.model.UserImage;

/**
 * Repository interface for managing UserImage entities.
 * Provides methods to perform CRUD operations on user images.
 */
@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {
  /**
   * Finds a UserImage by the user ID.
   *
   * @param userId the ID of the user
   * @return an Optional containing the UserImage if found, otherwise empty
   */
  Optional<UserImage> findByUserId(Long userId);
  
  /**
   * Deletes a UserImage by the user ID.
   *
   * @param userId the ID of the user
   */
  void deleteByUserId(Long userId);
}
