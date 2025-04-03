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
}
