package no.ntnu.idatt2105.marketplace.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Data Transfer Object for creating a new item.
 * This class is used to transfer data from the client to the server when creating a new item.
 * It contains the necessary fields for item creation, such as title, description, price, location,
 * category ID, and seller ID.
 * The class includes getters and setters for each field to allow for easy access and modification of the data.
 */
@Data
public class ItemCreateDTO {

  private String title;

  private String description;

  private Double price;

  private Double locationLatitude;

  private Double locationLongitude;

  @JsonProperty("category_ids")
  private Set<Long> categoryIds;

  @JsonProperty("seller_id")
  private Long sellerId;
}
