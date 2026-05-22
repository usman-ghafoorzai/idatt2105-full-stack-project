package no.ntnu.idatt2105.marketplace.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data Transfer Object for updating an item.
 * This class is used to transfer data from the client to the server when updating an item.
 * It contains fields for the item's title, description, price, location (latitude and longitude),
 * and category ID.
 */
@Data
@Schema(description = "Data Transfer Object for updating an item. " +
       "Contains fields for updating the item's title, description, price, location (latitude and longitude), " +
       "and associated category IDs.")
public class ItemUpdateDTO {

  @Schema(description = "The updated title of the item", example = "Antique Chair", required = true)
  private String title;

  @Schema(description = "The updated description of the item", example = "A charming antique chair with unique carvings", required = true)
  private String description;

  @Schema(description = "The updated price of the item", example = "129.99", required = true)
  private Double price;

  @Schema(description = "The updated latitude of the item's location", example = "59.9139", required = false)
  private Double locationLatitude;

  @Schema(description = "The updated longitude of the item's location", example = "10.7522", required = false)
  private Double locationLongitude;

  @JsonProperty("category_ids")
  @Schema(description = "A set of updated category IDs associated with the item", example = "[3, 4]", required = false)
  private Set<Long> categoryIds;
}
