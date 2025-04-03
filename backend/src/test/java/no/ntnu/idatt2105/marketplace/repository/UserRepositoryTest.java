package no.ntnu.idatt2105.marketplace.repository;

import no.ntnu.idatt2105.marketplace.model.Role;
import no.ntnu.idatt2105.marketplace.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Loads only repository and JPA-related components
public class UserRepositoryTest {
  @Autowired
  private UserRepository userRepository;

  @Test
  void testSaveAndFindByUsername() {
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
    user.setPassword("password123");
    user.setRole(Role.USER);
    userRepository.save(user);

    User foundUser = userRepository.findByUsername("testuser");

    assertThat(foundUser).isNotNull();
    assertThat(foundUser.getUsername()).isEqualTo("testuser");
  }

  @Test
  void testFindByUsername_NotFound() {
    User foundUser = userRepository.findByUsername("nonexistentuser");

    assertThat(foundUser).isNull();
  }

  @Test
  void testFindByEmail() {
    User user = new User();
    user.setUsername("anotheruser");
    user.setEmail("anotheruser@example.com");
    user.setPassword("password123");
    user.setRole(Role.USER);
    userRepository.save(user);

    User foundUser = userRepository.findByEmail("anotheruser@example.com");

    assertThat(foundUser).isNotNull();
    assertThat(foundUser.getEmail()).isEqualTo("anotheruser@example.com");
  }

  @Test
  void testFindByEmail_NotFound() {
    User foundUser = userRepository.findByEmail("nonexistent@example.com");

    assertThat(foundUser).isNull();
  }

  @Test
  void testFindById() {
    User user = new User();
    user.setUsername("userbyid");
    user.setEmail("userbyid@example.com");
    user.setPassword("password123");
    user.setRole(Role.USER);
    user = userRepository.save(user); // Save and retrieve with ID

    User foundUser = userRepository.findById(user.getId()).orElse(null);

    assertThat(foundUser).isNotNull();
    assertThat(foundUser.getUsername()).isEqualTo("userbyid");
  }

  @Test
  void testUpdateUser() {
    User user = new User();
    user.setUsername("oldusername");
    user.setEmail("oldemail@example.com");
    user.setPassword("password123");
    user.setRole(Role.USER);
    user = userRepository.save(user);

    user.setUsername("newusername");
    user = userRepository.save(user); // Update existing entry

    User updatedUser = userRepository.findById(user.getId()).orElse(null);
    assertThat(updatedUser).isNotNull();
    assertThat(updatedUser.getUsername()).isEqualTo("newusername");
  }

  @Test
  void testDeleteUser() {
    User user = new User();
    user.setUsername("tobedeleted");
    user.setEmail("tobedeleted@example.com");
    user.setPassword("password123");
    user.setRole(Role.USER);
    user = userRepository.save(user);
    Long userId = user.getId();

    userRepository.deleteById(userId);

    assertThat(userRepository.findById(userId)).isEmpty();
  }
}
