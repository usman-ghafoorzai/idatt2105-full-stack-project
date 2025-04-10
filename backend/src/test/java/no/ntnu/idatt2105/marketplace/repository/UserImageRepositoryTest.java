package no.ntnu.idatt2105.marketplace.repository;

import no.ntnu.idatt2105.marketplace.model.Role;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.model.UserImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserImageRepositoryTest {

  @Autowired
  private UserImageRepository userImageRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  void testSaveAndFindByUserId() {
    User user = userRepository.save(createTestUser("finduser"));

    UserImage image = new UserImage();
    image.setData("profile-data".getBytes());
    image.setFileName("profile.jpg");
    image.setContentType("image/jpeg");
    image.setUser(user);
    userImageRepository.save(image);

    Optional<UserImage> found = userImageRepository.findByUserId(user.getId());
    assertThat(found).isPresent();
    assertThat(found.get().getFileName()).isEqualTo("profile.jpg");
  }

  @Test
  void testDeleteByUserId() {
    User user = userRepository.save(createTestUser("deluser"));

    UserImage image = new UserImage();
    image.setData("to-delete".getBytes());
    image.setFileName("delete.jpg");
    image.setContentType("image/jpeg");
    image.setUser(user);
    userImageRepository.save(image);

    userImageRepository.deleteByUserId(user.getId());
    Optional<UserImage> deleted = userImageRepository.findByUserId(user.getId());
    assertThat(deleted).isEmpty();
  }

  private User createTestUser(String username) {
    User user = new User();
    user.setUsername(username);
    user.setEmail(username + "@example.com");
    user.setPassword("secure123");
    user.setFirstName("Test");
    user.setLastName("User");
    user.setRole(Role.USER);
    return user;
  }
}
