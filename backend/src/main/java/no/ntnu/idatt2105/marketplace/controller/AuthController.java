package no.ntnu.idatt2105.marketplace.controller;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.security.JwtUtil;
import no.ntnu.idatt2105.marketplace.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    // Ensure required fields are provided
    if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null) {
      return ResponseEntity.badRequest().body("Username, email, password, and role are required");
    }
    // TODO: Should role be set to USER by default?

    // Hash the password before saving the user
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User newUser = userService.saveUser(user);

    // Generate a token for the newly registered user
    String token = jwtUtil.generateToken(newUser.getUsername(), newUser.getRole().name());

    // Return the saved user and the generated token
    return ResponseEntity.ok(Map.of("user", newUser, "token", token));
  }

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
