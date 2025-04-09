package no.ntnu.idatt2105.marketplace.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.repository.UserRepository;

/**
 * Service class for handling user-related business logic.
 * Provides methods to retrieve and save user entities.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

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

  /**
   * Retrieves a user by their username.
   *
   * @param username the username of the user
   * @return the user entity if found, otherwise null
   */
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  /**
   * Deletes all users from the database.
   * This method is intended for testing purposes only.
   */
  public void deleteAllUsers() {
    userRepository.deleteAll();
  }

  /**
   * Updates an existing user.
   *
   * @param id          the ID of the user to update
   * @param updatedUser the user object with updated fields
   * @return the updated user entity
   * @throws IllegalArgumentException if user with given ID doesn't exist
   */
  public User updateUser(Long id, User updatedUser) {
    return userRepository.findById(id).map(existingUser -> {
      // Update fields based on the values in the updatedUser object
      if (updatedUser.getUsername() != null) {
        existingUser.setUsername(updatedUser.getUsername());
      }
      if (updatedUser.getEmail() != null) {
        existingUser.setEmail(updatedUser.getEmail());
      }
      if (updatedUser.getFirstName() != null) {
        existingUser.setFirstName(updatedUser.getFirstName());
      }
      if (updatedUser.getLastName() != null) {
        existingUser.setLastName(updatedUser.getLastName());
      }
      if (updatedUser.getPassword() != null) {
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Hash the password before saving
      }
      if (updatedUser.getRole() != null) {
        existingUser.setRole(updatedUser.getRole());
      }

      // Save the updated user entity
      return userRepository.save(existingUser);
    }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
  }

}
