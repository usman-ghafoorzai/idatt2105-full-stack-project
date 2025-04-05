package no.ntnu.idatt2105.marketplace.service;

import no.ntnu.idatt2105.marketplace.model.Role;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  void testGetUserById_Found() {
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");
    user.setPassword("password123");
    user.setRole(Role.USER);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    Optional<User> result = userService.getUserById(1L);

    assertThat(result).isPresent();
    assertThat(result.get().getUsername()).isEqualTo("testuser");
  }

  @Test
  void testGetUserById_NotFound() {
    when(userRepository.findById(2L)).thenReturn(Optional.empty());

    Optional<User> result = userService.getUserById(2L);

    assertThat(result).isNotPresent();
  }

  @Test
  void testSaveUser() {
    User user = new User();
    user.setUsername("newuser");
    user.setPassword("password123");
    user.setRole(Role.USER);

    when(userRepository.save(user)).thenReturn(user);

    User savedUser = userService.saveUser(user);

    assertThat(savedUser).isNotNull();
    assertThat(savedUser.getUsername()).isEqualTo("newuser");
  }

  @Test
  void testGetUserByUsername() {
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");
    user.setPassword("password123");
    user.setRole(Role.USER);

    when(userRepository.findByUsername("testuser")).thenReturn(user);

    User result = userService.getUserByUsername("testuser");

    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo("testuser");
  }

  @Test
  void testGetUserByUsername_NotFound() {
    when(userRepository.findByUsername("nonexistent")).thenReturn(null);

    User result = userService.getUserByUsername("nonexistent");

    assertThat(result).isNull();
  }
}
