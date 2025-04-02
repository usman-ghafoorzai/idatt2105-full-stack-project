package no.ntnu.idatt2105.marketplace.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.repository.UserRepository;
import no.ntnu.idatt2105.marketplace.model.User;
import java.util.Optional;

/**
 * Service class for handling user-related business logic.
 * Provides methods to retrieve and save user entities.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param id the unique identifier of the user
   * @return an {@code Optional} containing the user if found, otherwise empty
   */
  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  /**
   * Saves a user entity to the database.
   *
   * @param user the user entity to be saved
   * @return the saved user entity
   */
  public User saveUser(User user) {
    return userRepository.save(user);
  }

}
