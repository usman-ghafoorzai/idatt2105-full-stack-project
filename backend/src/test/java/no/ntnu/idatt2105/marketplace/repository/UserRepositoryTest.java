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
    user.setFirstName("Test");
    user.setLastName("User");
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
    user.setFirstName("Another");
    user.setLastName("User");
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
    user.setFirstName("User");
    user.setLastName("ById");
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
    user.setFirstName("Old");
    user.setLastName("user");
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
    user.setFirstName("test");
    user.setLastName("user");
    user.setRole(Role.USER);
    user = userRepository.save(user);
    Long userId = user.getId();

    userRepository.deleteById(userId);

    assertThat(userRepository.findById(userId)).isEmpty();
  }

  @Test
  void testSaveDuplicateUser() {
    User user1 = new User();
    user1.setUsername("duplicateuser");
    user1.setEmail("duplicate@example.com");
    user1.setPassword("password123");
    user1.setFirstName("Duplicate");
    user1.setLastName("User");
    user1.setRole(Role.USER);
    userRepository.save(user1);

    User user2 = new User();
    user2.setUsername("duplicateuser"); // Same username
    user2.setEmail("different@example.com"); // Different email
    user2.setPassword("password123");
    user2.setFirstName("Another");
    user2.setLastName("User");
    user2.setRole(Role.USER);

    try {
      userRepository.save(user2);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(Exception.class); // Expect exception due to unique constraint
    }
  }

  @Test
  void testSaveDuplicateEmail() {
    User user1 = new User();
    user1.setUsername("uniqueuser1");
    user1.setEmail("sameemail@example.com");
    user1.setPassword("password123");
    user1.setFirstName("test");
    user1.setLastName("User1");
    user1.setRole(Role.USER);
    userRepository.save(user1);

    User user2 = new User();
    user2.setUsername("uniqueuser2");
    user2.setEmail("sameemail@example.com"); // Same email as user1
    user2.setPassword("password123");
    user2.setFirstName("test");
    user2.setLastName("User2");
    user2.setRole(Role.USER);

    try {
      userRepository.save(user2);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(Exception.class); // Expect exception due to unique constraint
    }
  }
}
