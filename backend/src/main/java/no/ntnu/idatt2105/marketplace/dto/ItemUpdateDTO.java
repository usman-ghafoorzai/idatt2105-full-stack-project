package no.ntnu.idatt2105.marketplace.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Data Transfer Object for updating an item.
 * This class is used to transfer data from the client to the server when updating an item.
 * It contains fields for the item's title, description, price, location (latitude and longitude),
 * and category ID.
 */
@Data
public class ItemUpdateDTO {

  private String title;

  private String description;

  private Double price;

  private Double locationLatitude;

  private Double locationLongitude;

  @JsonProperty("category_ids")
  private Set<Long> categoryIds;
}
