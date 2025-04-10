package no.ntnu.idatt2105.marketplace.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import no.ntnu.idatt2105.marketplace.model.ItemImage;
import no.ntnu.idatt2105.marketplace.service.ItemImageService;

/**
 * Controller for handling item image upload, retrieval, and deletion.
 * Provides REST endpoints for managing images linked to items.
 */
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Item Images", description = "Operations for uploading, retrieving, and deleting images of items")
public class ItemImageController {

  private final ItemImageService itemImageService;

  /**
   * Uploads images for a specific item. Does not replace existing images.
   * 
   * @param itemId the ID of the item
   * @param images the uploaded image files
   * @return HTTP 200 OK if successful, otherwise an error response
   */
  @Operation(
      summary = "Upload images for an item",
      description = "Uploads one or more images for a specific item. " +
                    "This endpoint does not replace existing images, but adds new ones."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Images uploaded successfully",
          content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error - Failed to upload images", content = @Content)
  })
  @PostMapping("/{itemId}/images")
  public ResponseEntity<String> uploadImages(
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long itemId,
    @Parameter(description = "Array of image files to upload", required = true,
               content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    @RequestParam("images") MultipartFile[] images) {
    try {
      itemImageService.uploadItemImages(itemId, images);
      return ResponseEntity.status(HttpStatus.CREATED).body("Images uploaded successfully");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images");
    }
  }

  /**
   * Retrieves all image IDs associated with a specific item.
   * 
   * @param itemId the ID of the item
   * @return a list of image IDs, or 404 if no images are found
   */
  @Operation(
      summary = "Get image IDs for an item",
      description = "Retrieves all image IDs associated with the specified item. " +
                    "Returns a 404 status if no images are found."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Image IDs retrieved successfully",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
      @ApiResponse(responseCode = "404", description = "No images found for the specified item", content = @Content)
  })
  @GetMapping("/{itemId}/images")
  public ResponseEntity<List<Long>> getItemImageIds(
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long itemId) {

    List<Long> imageIds = itemImageService.getItemImageIds(itemId);
    if (imageIds.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(imageIds);
  }

  /**
   * Retrieves a specific image for an item.
   * 
   * @param itemId  the ID of the item
   * @param imageId the ID of the image
   * @return the image as a byte stream with appropriate headers, or 404 if not
   *         found
   */
  @Operation(
    summary = "Get a specific image for an item",
    description = "Retrieves a specific image for the provided item and image IDs. " +
                  "Returns the image as a byte stream with proper content type and disposition."
)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Image retrieved successfully",
        content = @Content(mediaType = "application/octet-stream", 
                   schema = @Schema(type = "string", format = "binary"))),
    @ApiResponse(responseCode = "404", description = "Image not found", content = @Content)
})
  @GetMapping("/{itemId}/images/{imageId}")
  public ResponseEntity<byte[]> getItemImage(
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long itemId,
    @Parameter(description = "The unique identifier of the image", required = true)
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
  @Operation(
      summary = "Delete a specific image for an item",
      description = "Deletes a specific image for the specified item. " +
                    "Returns a 204 No Content status if the deletion is successful."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Image deleted successfully", content = @Content),
      @ApiResponse(responseCode = "404", description = "Image not found", content = @Content)
  })
  @DeleteMapping("/{itemId}/images/{imageId}")
  public ResponseEntity<Void> deleteImage(
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long itemId,
    @Parameter(description = "The unique identifier of the image", required = true)
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
  @Operation(
      summary = "Delete all images for an item",
      description = "Deletes all images associated with the specified item. " +
                    "Returns a 204 No Content status if the deletion is successful."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "All images deleted successfully", content = @Content)
  })
  @DeleteMapping("/{itemId}/images")
  public ResponseEntity<Void> deleteAllImages(
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long itemId) {
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
  @Operation(
      summary = "Get the first image for an item",
      description = "Retrieves the first image associated with the specified item. " +
                    "Returns the image as a byte stream with appropriate headers, or 404 if not found."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "First image retrieved successfully",
          content = @Content(mediaType = "application/octet-stream", 
                     schema = @Schema(type = "string", format = "binary"))),
      @ApiResponse(responseCode = "404", description = "Image not found", content = @Content)
  })
  @GetMapping("/{itemId}/images/first")
  public ResponseEntity<byte[]> getFirstImage(
    @Parameter(description = "The unique identifier of the item", required = true)
    @PathVariable Long itemId) {
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
