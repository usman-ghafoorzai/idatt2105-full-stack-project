package no.ntnu.idatt2105.marketplace.controller;

import java.io.IOException;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.service.UserImageService;

/**
 * Controller for handling user profile image upload and retrieval.
 * Provides REST endpoints for managing images linked to users.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserImageController {
  private final UserImageService userImageService;

  /**
   * Uploads a profile image for a specific user.
   * Replaces any existing image for that user if present.
   *
   * @param userId the ID of the user
   * @param image  the uploaded image file
   * @return HTTP 200 OK if successful, otherwise an error response
   */
  @PostMapping("/{userId}/image")
  public ResponseEntity<String> uploadImage(
      @PathVariable Long userId,
      @RequestParam("image") MultipartFile image) {
    System.out.println("Received image for user " + userId);
    System.out.println("Image name: " + image.getOriginalFilename()); // TODO: Remove 
    try {
      userImageService.saveImage(userId, image);
      return ResponseEntity.ok("Image uploaded successfully");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
    }
  }

  /**
   * Retrieves the profile image of a specific user.
   *
   * @param userId the ID of the user
   * @return the image as a byte stream with appropriate headers, or 404 if not
   *         found
   */
  @GetMapping("/{userId}/image")
  public ResponseEntity<byte[]> getImage(@PathVariable Long userId) {
    return userImageService.getImageByUserId(userId)
        .map(image -> {
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.parseMediaType(image.getContentType()));
          headers.setContentDisposition(ContentDisposition.inline().filename(image.getFileName()).build());
          return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
        })
        .orElse(ResponseEntity.notFound().build());
  }
}
