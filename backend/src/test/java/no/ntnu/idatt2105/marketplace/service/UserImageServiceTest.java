package no.ntnu.idatt2105.marketplace.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.model.UserImage;
import no.ntnu.idatt2105.marketplace.repository.UserImageRepository;
import no.ntnu.idatt2105.marketplace.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserImageServiceTest {

  @Mock
  private UserImageRepository userImageRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private MultipartFile file;

  @InjectMocks
  private UserImageService userImageService;

  private User user;
  private UserImage image;

  @BeforeEach
  void setup() throws IOException {
    user = new User();
    user.setId(1L);

    image = new UserImage();
    image.setId(100L);
    image.setUser(user);
    image.setFileName("avatar.png");
    image.setContentType("image/png");
    image.setData("imgdata".getBytes());
  }

  @Nested
  @DisplayName("Positive cases")
  class Positive {

    @Test
    void testSaveImage() throws IOException {
      when(userRepository.findById(1L)).thenReturn(Optional.of(user));

      userImageService.saveImage(1L, file);

      verify(userImageRepository).deleteByUserId(1L);
      verify(userImageRepository).flush();
      verify(userImageRepository).save(any(UserImage.class));
    }

    @Test
    void testGetImageByUserId() {
      when(userImageRepository.findByUserId(1L)).thenReturn(Optional.of(image));

      Optional<UserImage> result = userImageService.getImageByUserId(1L);

      assertThat(result).isPresent();
      assertThat(result.get().getFileName()).isEqualTo("avatar.png");
    }
  }

  @Nested
  @DisplayName("Negative cases")
  class Negative {

    @Test
    void testSaveImageThrowsWhenUserNotFound() {
      when(userRepository.findById(1L)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> userImageService.saveImage(1L, file))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("User not found");
    }

    @Test
    void testGetImageByUserIdReturnsEmptyIfNotFound() {
      when(userImageRepository.findByUserId(1L)).thenReturn(Optional.empty());

      Optional<UserImage> result = userImageService.getImageByUserId(1L);

      assertThat(result).isEmpty();
    }
  }
}
