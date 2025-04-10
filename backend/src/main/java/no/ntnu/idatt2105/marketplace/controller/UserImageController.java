package no.ntnu.idatt2105.marketplace.controller;

import java.io.IOException;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.service.UserImageService;

/**
 * Controller for handling user profile image upload and retrieval.
 * Provides REST endpoints for managing images linked to users.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Images", description = "Operations for uploading and retrieving user profile images")
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
  @Operation(
      summary = "Upload a user profile image",
      description = "Uploads a profile image for a specific user and replaces any existing image if present."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Image uploaded successfully",
              content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error - Failed to upload image", content = @Content)
  })
  @PostMapping("/{userId}/image")
  public ResponseEntity<String> uploadImage(
    @Parameter(description = "The unique identifier of the user", required = true)
    @PathVariable Long userId,
    @Parameter(description = "The image file to be uploaded", required = true,
               content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
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
  @Operation(
      summary = "Retrieve a user's profile image",
      description = "Retrieves the profile image of a specific user. Returns the image as a byte stream " +
                    "with the correct content type and disposition, or a 404 if not found."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Image retrieved successfully",
              content = @Content(mediaType = "application/octet-stream", 
                      schema = @Schema(type = "string", format = "binary"))),
      @ApiResponse(responseCode = "404", description = "Image not found", content = @Content)
  })
  @GetMapping("/{userId}/image")
  public ResponseEntity<byte[]> getImage(
    @Parameter(description = "The unique identifier of the user", required = true)
    @PathVariable Long userId) {
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
