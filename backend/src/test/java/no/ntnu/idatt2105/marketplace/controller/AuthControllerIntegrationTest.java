package no.ntnu.idatt2105.marketplace.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;

import no.ntnu.idatt2105.marketplace.model.Role;
import no.ntnu.idatt2105.marketplace.model.User;

import no.ntnu.idatt2105.marketplace.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setup() {
    // Clean up the database before each test
    userService.deleteAllUsers();
  }

  @Test
  void testRegister() throws Exception {
    String json = "{\n" +
        "  \"username\": \"testuser\",\n" +
        "  \"email\": \"testuser@example.com\",\n" +
        "  \"password\": \"password123\",\n" +
        "  \"role\": \"USER\"\n" +
        "}";

    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.username").value("testuser"))
        .andExpect(jsonPath("$.user.email").value("testuser@example.com"))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testLogin() throws Exception {
    // First, register a user
    User newUser = new User();
    newUser.setUsername("testuser");
    newUser.setPassword(passwordEncoder.encode("password123"));
    newUser.setRole(Role.USER);
    newUser.setEmail("test@example.com");
    userService.saveUser(newUser);

    // Prepare login request with correct credentials
    String loginJson = "{\n" +
        "  \"username\": \"testuser\",\n" +
        "  \"password\": \"password123\"\n" +
        "}";

    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists()); // Check that a JWT token is returned
  }

  @Test
  public void testLoginInvalidCredentials() throws Exception {
    // Try to log in with invalid credentials
    String invalidLoginJson = "{\n" +
        "  \"username\": \"invaliduser\",\n" +
        "  \"password\": \"wrongpassword\"\n" +
        "}";

    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidLoginJson))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("Invalid credentials"));
  }

  @Test
  public void testRegisterMissingFields() throws Exception {
    // Try to register with missing fields (e.g., missing password)
    String incompleteJson = "{\n" +
        "  \"username\": \"testuser\",\n" +
        "  \"email\": \"testuser@example.com\"\n" +
        "}";

    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(incompleteJson))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Username, email, password, and role are required"));
  }
}
