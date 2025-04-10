package no.ntnu.idatt2105.marketplace.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for Item response.
 * This class is used to transfer item data between the server and client.
 * It contains fields for item details, including the associated category and seller.
 */
@Data
public class ItemResponseDTO {

  private Long id;

  private String title;

  private String description;

  private Double price;

  private Double locationLatitude;

  private Double locationLongitude;

  private String status;

  private LocalDateTime createdAt;

  private Set<CategoryDTO> categories;
  
  private SellerDTO seller;

  /**
   * Data Transfer Object (DTO) for Category.
   * This class is used to transfer category data between the server and client.
   */
  @Data
  public static class CategoryDTO {
    
    private Long id;
    
    private String name;
  }

  /**
   * Data Transfer Object (DTO) for Seller.
   * This class is used to transfer seller data between the server and client.
   */
  @Data
  public static class SellerDTO {

    private Long id;

    private String username;

    private String email;
  }
}
