package no.ntnu.idatt2105.marketplace.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.ItemImage;
import no.ntnu.idatt2105.marketplace.service.ItemImageService;

/**
 * Controller for handling item image upload, retrieval, and deletion.
 * Provides REST endpoints for managing images linked to items.
 */
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemImageController {

  private final ItemImageService itemImageService;

  /**
   * Uploads images for a specific item. Does not replace existing images.
   * 
   * @param itemId the ID of the item
   * @param images the uploaded image files
   * @return HTTP 200 OK if successful, otherwise an error response
   */
  @PostMapping("/{itemId}/images")
  public ResponseEntity<String> uploadImages(
      @PathVariable Long itemId,
      @RequestParam("images") MultipartFile[] images) {
    try {
      itemImageService.uploadItemImages(itemId, images);
      return ResponseEntity.status(HttpStatus.CREATED).body("Images uploaded successfully");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images");
    }
  }

  /**
   * Retrieves all images for a specific item.
   * 
   * @param itemId the ID of the item
   * @return a list of ItemImage objects with image metadata
   */
  @GetMapping("/{itemId}/images")
  public ResponseEntity<List<ItemImage>> getItemImages(@PathVariable Long itemId) {
    List<ItemImage> images = itemImageService.getItemImages(itemId);
    if (images.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(images);
  }

  /**
   * Retrieves a specific image for an item.
   * 
   * @param itemId  the ID of the item
   * @param imageId the ID of the image
   * @return the image as a byte stream with appropriate headers, or 404 if not
   *         found
   */
  @GetMapping("/{itemId}/images/{imageId}")
  public ResponseEntity<byte[]> getItemImage(
      @PathVariable Long itemId,
      @PathVariable Long imageId) {
    return itemImageService.getItemImageById(itemId, imageId)
        .map(image -> {
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.parseMediaType(image.getContentType()));
          headers.setContentDisposition(ContentDisposition.inline().filename(image.getFileName()).build());
          return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Deletes a specific image for a specific item.
   * 
   * @param itemId  the ID of the item
   * @param imageId the ID of the image
   * @return HTTP 204 NO CONTENT if the image was deleted successfully
   */
  @DeleteMapping("/{itemId}/images/{imageId}")
  public ResponseEntity<Void> deleteImage(
      @PathVariable Long itemId,
      @PathVariable Long imageId) {
    boolean isDeleted = itemImageService.deleteImage(itemId, imageId);
    return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  /**
   * Deletes all images associated with a specific item.
   * 
   * @param itemId the ID of the item
   * @return HTTP 204 NO CONTENT if all images were deleted successfully
   */
  @DeleteMapping("/{itemId}/images")
  public ResponseEntity<Void> deleteAllImages(@PathVariable Long itemId) {
    itemImageService.deleteAllImages(itemId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Retrieves the first image for a specific item.
   * 
   * @param itemId the ID of the item
   * @return the first image as a byte stream with appropriate headers, or 404 if
   *         not found
   */
  @GetMapping("/{itemId}/images/first")
  public ResponseEntity<byte[]> getFirstImage(@PathVariable Long itemId) {
    Optional<ItemImage> firstImage = itemImageService.getFirstImage(itemId);
    return firstImage
        .map(image -> {
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.parseMediaType(image.getContentType()));
          headers.setContentDisposition(ContentDisposition.inline().filename(image.getFileName()).build());
          return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
        })
        .orElse(ResponseEntity.notFound().build());
  }
}
