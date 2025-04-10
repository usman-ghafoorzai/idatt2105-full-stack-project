package no.ntnu.idatt2105.marketplace.controller;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.security.JwtUtil;
import no.ntnu.idatt2105.marketplace.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

/**
 * Controller for handling user authentication and registration.
 * Provides REST endpoints for user login and registration.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  /**
   * Registers a new user account.
   * @param user The user object containing the registration details.
   * @return ResponseEntity with the registered user and authentication token.
   */
  @Operation(
      summary = "User registration",
      description = "Registers a new user account. " +
                    "The request must contain a username, email, password, and role. " +
                    "On success, returns the registered user along with an authentication token."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User registered successfully",
              content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request - missing required fields", content = @Content)
  })
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    // Ensure required fields are provided
    if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null) {
      return ResponseEntity.badRequest().body("Username, email, password, and role are required");
    }

    // Hash the password before saving the user
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User newUser = userService.saveUser(user);

    // Generate a token for the newly registered user
    String token = jwtUtil.generateToken(newUser.getUsername(), newUser.getRole().name());

    // Return the saved user and the generated token
    return ResponseEntity.ok(Map.of("user", newUser, "token", token));
  }

  /**
   * Logs in a user by validating the provided credentials.
   * If the credentials are valid, an authentication token is returned.
   * @param loginRequest A map containing the username and password.
   * @return ResponseEntity with the authentication token if successful, or an error message
   *         if the credentials are invalid.
   */
  @Operation(
      summary = "User login",
      description = "Authenticates a user by validating the provided credentials. " +
                    "Returns an authentication token if the credentials are correct."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User logged in successfully",
              content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = Map.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials", content = @Content)
  })
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
    String username = loginRequest.get("username");
    String password = loginRequest.get("password");

    User user = userService.getUserByUsername(username);
    if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }

    String token = jwtUtil.generateToken(username, user.getRole().toString());
    return ResponseEntity.ok(Map.of("token", token));
  }
}
