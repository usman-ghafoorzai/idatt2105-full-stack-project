package no.ntnu.idatt2105.marketplace.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data Transfer Object for creating a new item.
 * This class is used to transfer data from the client to the server when creating a new item.
 * It contains the necessary fields for item creation, such as title, description, price, location,
 * category ID, and seller ID.
 * The class includes getters and setters for each field to allow for easy access and modification of the data.
 */
@Schema(description = "Data Transfer Object for creating a new item. " +
       "Contains the necessary fields for item creation such as title, description, price, " +
       "location coordinates, category IDs, and seller ID.")
@Data
public class ItemCreateDTO {

  @Schema(description = "The title of the item", example = "Lamp", required = true)
  private String title;

  @Schema(description = "Detailed description of the item", example = "A beautiful lamp from the 1950s", required = true)
  private String description;

  @Schema(description = "The price of the item", example = "495.99", required = true)
  private Double price;

  @Schema(description = "The latitude of the item's location", example = "75.9139", required = false)
  private Double locationLatitude;

  @Schema(description = "The longitude of the item's location", example = "12.7522", required = false)
  private Double locationLongitude;

  @JsonProperty("category_ids")
  @Schema(description = "A set of category IDs that this item belongs to", example = "[1, 2]", required = false)
  private Set<Long> categoryIds;

  @JsonProperty("seller_id")
  @Schema(description = "The unique identifier of the seller", example = "42", required = true)
  private Long sellerId;
}
