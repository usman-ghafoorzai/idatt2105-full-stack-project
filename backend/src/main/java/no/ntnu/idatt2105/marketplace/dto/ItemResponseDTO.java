package no.ntnu.idatt2105.marketplace.dto;

import java.time.LocalDateTime;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for Item response.
 * This class is used to transfer item data between the server and client.
 * It contains fields for item details, including the associated category and seller.
 */
@Data
@Schema(description = "Data Transfer Object (DTO) for item responses. " +
        "Includes item details along with associated categories and seller information.")
public class ItemResponseDTO {

  @Schema(description = "Unique identifier of the item", example = "42")
  private Long id;

  @Schema(description = "Title of the item", example = "Vintage Wooden Table")
  private String title;

  @Schema(description = "Detailed description of the item", example = "A handmade wooden table in excellent condition.")
  private String description;

  @Schema(description = "Price of the item", example = "249.99")
  private Double price;

  @Schema(description = "Latitude of the item's location", example = "63.4305")
  private Double locationLatitude;

  @Schema(description = "Longitude of the item's location", example = "10.3951")
  private Double locationLongitude;

  @Schema(description = "Current status of the item (ACTIVE, SOLD, RESERVED)", example = "SOLD")
  private String status;

  @Schema(description = "Timestamp when the item was created", example = "2025-04-10T15:30:00")
  private LocalDateTime createdAt;

  @Schema(description = "Categories associated with the item as a set of CategoryDTO")
  private Set<CategoryDTO> categories;
  
  @Schema(description = "Seller information for the item as a SellerDTO")
  private SellerDTO seller;

  /**
   * Data Transfer Object (DTO) for Category.
   * This class is used to transfer category data between the server and client.
   */
  @Data
  @Schema(description = "Category details associated with an item")
  public static class CategoryDTO {
    
    @Schema(description = "Unique identifier of the category", example = "4")
    private Long id;
    
    @Schema(description = "Name of the category", example = "Furniture")
    private String name;
  }

  /**
   * Data Transfer Object (DTO) for Seller.
   * This class is used to transfer seller data between the server and client.
   */
  @Data
  @Schema(description = "Seller details associated with an item")
  public static class SellerDTO {

    @Schema(description = "Unique identifier of the seller", example = "7")
    private Long id;

    @Schema(description = "Username of the seller", example = "albert_henning")
    private String username;

    @Schema(description = "Email address of the seller", example = "alberth@example.com")
    private String email;
  }
}
