package no.ntnu.idatt2105.marketplace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.service.UserService;

/**
 * Controller class for handling user-related HTTP requests.
 * Provides endpoints to retrieve and save user entities.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param id the unique identifier of the user
   * @return {@code ResponseEntity} containing the user if found, otherwise
   *         returns a 404 Not Found response
   */
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return userService.getUserById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Saves a new user entity.
   *
   * @param user the user entity to be saved
   * @return {@code ResponseEntity} containing the saved user entity
   */
  @PostMapping
  public ResponseEntity<User> saveUser(@RequestBody User user) {
    User newUser = userService.saveUser(user);
    return ResponseEntity.ok(newUser);
  }


  /**
   * Retrieves the currently logged-in user.
   *
   * @param authentication the authentication object containing user details
   * @return {@code ResponseEntity} containing the logged-in user if found,
   *         otherwise returns a 401 Unauthorized response
   */
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/me")
  public ResponseEntity<User> getLoggedInUser(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized
    }

    String username = authentication.getName();
    User user = userService.getUserByUsername(username);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
    }
    return ResponseEntity.ok(user); // OK
  }

}
