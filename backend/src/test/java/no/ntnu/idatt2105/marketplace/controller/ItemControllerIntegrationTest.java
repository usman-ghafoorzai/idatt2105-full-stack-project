package no.ntnu.idatt2105.marketplace.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2105.marketplace.model.Role;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private String jwtToken;

  @BeforeEach
  void setup() throws Exception {
    // Clean and prepare test user
    userService.deleteAllUsers(); // Optional: only if you want a clean slate
    User user = new User();
    user.setUsername("itemuser");
    user.setPassword(passwordEncoder.encode("password123"));
    user.setRole(Role.USER);
    user.setEmail("itemuser@example.com");
    userService.saveUser(user);

    // Login and retrieve JWT token
    String loginJson = """
      {
        "username": "itemuser",
        "password": "password123"
      }
      """;

    MvcResult result = mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginJson))
        .andExpect(status().isOk())
        .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    jwtToken = new ObjectMapper().readTree(responseBody).get("token").asText();
  }

  @Test
  public void testCreateItemWithAuth() throws Exception {
    String itemJson = """
      {
        "title": "Test Item",
        "description": "This is a test item",
        "price": 100.0
      }
      """;

    mockMvc.perform(post("/api/items")
        .header("Authorization", "Bearer " + jwtToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(itemJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Test Item"));
  }

  @Test
  public void testAccessWithoutToken() throws Exception {
    mockMvc.perform(post("/api/items"))
        .andExpect(status().isForbidden());
  }

  @Test
  public void testAccessWithInvalidToken() throws Exception {
    mockMvc.perform(post("/api/items")
        .header("Authorization", "Bearer invalid.token.here"))
        .andExpect(status().isUnauthorized());
  }
}
