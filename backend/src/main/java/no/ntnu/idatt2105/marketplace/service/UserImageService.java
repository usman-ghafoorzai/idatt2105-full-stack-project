package no.ntnu.idatt2105.marketplace.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.model.UserImage;
import no.ntnu.idatt2105.marketplace.repository.UserImageRepository;
import no.ntnu.idatt2105.marketplace.repository.UserRepository;

/**
 * Service class for handling user image-related business logic.
 * Provides methods to save and retrieve user profile images.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserImageService {
  private final UserImageRepository userImageRepository;
  private final UserRepository userRepository;

  /**
   * Saves or replaces a user’s profile image.
   *
   * @param userId the ID of the user
   * @param file the uploaded image file
   * @throws IOException if reading the file fails
   */
  public void saveImage(Long userId, MultipartFile file) throws IOException {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

    userImageRepository.deleteByUserId(userId); // replace existing image if any

    UserImage image = new UserImage();
    image.setUser(user);
    image.setData(file.getBytes());
    image.setFileName(file.getOriginalFilename());
    image.setContentType(file.getContentType());

    userImageRepository.save(image);
  }

  /**
   * Retrieves a user image by the user ID.
   *
   * @param userId the ID of the user
   * @return an Optional containing the UserImage, or empty if not found
   */
  public Optional<UserImage> getImageByUserId(Long userId) {
    return userImageRepository.findByUserId(userId);
  }
}