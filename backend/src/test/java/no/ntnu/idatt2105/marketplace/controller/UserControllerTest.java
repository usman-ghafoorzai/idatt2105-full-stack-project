package no.ntnu.idatt2105.marketplace.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import no.ntnu.idatt2105.marketplace.model.Role;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
  private MockMvc mockMvc;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON conversion

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for LocalDateTime serialization
  }

  @Test
  void testGetUserById_UserFound() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setUsername("test_user");
    user.setEmail("test@example.com");
    user.setPassword("password123");
    user.setRole(Role.USER);
    user.setCreatedAt(LocalDateTime.now());

    // Mock the service call
    when(userService.getUserById(1L)).thenReturn(Optional.of(user));

    // Perform the GET request and verify the response
    mockMvc.perform(get("/api/users/1"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.username").value("test_user"))
        .andExpect(jsonPath("$.email").value("test@example.com"))
        .andExpect(jsonPath("$.role").value("USER"));
    // TODO password?
  }

  @Test
  void testGetUserById_UserNotFound() throws Exception {
    when(userService.getUserById(2L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/users/2"))
        .andExpect(status().isNotFound());
  }

  @Test
  void testSaveUser_Success() throws Exception {
    User user = new User();
    user.setUsername("test_user");
    user.setEmail("test@example.com");
    user.setPassword("password123");
    user.setRole(Role.ADMIN);

    User savedUser = new User();
    savedUser.setId(1L);
    savedUser.setUsername("test_user");
    savedUser.setEmail("test@example.com");
    savedUser.setPassword("password123");
    savedUser.setRole(Role.ADMIN);
    savedUser.setCreatedAt(LocalDateTime.now());

    when(userService.saveUser(any(User.class))).thenReturn(savedUser);

    mockMvc.perform(post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.username").value("test_user"))
        .andExpect(jsonPath("$.email").value("test@example.com"))
        .andExpect(jsonPath("$.role").value("ADMIN"));
  }

  @Test
  void testUpdateUser_Success() throws Exception {
    Long userId = 1L;

    User updateRequest = new User();
    updateRequest.setUsername("updated_user");
    updateRequest.setEmail("updated@example.com");
    updateRequest.setFirstName("Updated");
    updateRequest.setLastName("User");
    updateRequest.setPassword("updatedPassword");
    updateRequest.setRole(Role.ADMIN);

    User updatedUser = new User();
    updatedUser.setId(userId);
    updatedUser.setUsername("updated_user");
    updatedUser.setEmail("updated@example.com");
    updatedUser.setFirstName("Updated");
    updatedUser.setLastName("User");
    updatedUser.setPassword("encodedPassword");
    updatedUser.setRole(Role.ADMIN);
    updatedUser.setCreatedAt(LocalDateTime.now());

    when(userService.updateUser(any(Long.class), any(User.class))).thenReturn(updatedUser);

    mockMvc.perform(put("/api/users/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userId))
        .andExpect(jsonPath("$.username").value("updated_user"))
        .andExpect(jsonPath("$.email").value("updated@example.com"))
        .andExpect(jsonPath("$.role").value("ADMIN"));
  }
}
