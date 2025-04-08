package no.ntnu.idatt2105.marketplace.dto;

/**
 * Data Transfer Object for updating an item.
 * This class is used to transfer data from the client to the server when updating an item.
 * It contains fields for the item's title, description, price, location (latitude and longitude),
 * and category ID.
 */
public class ItemUpdateDTO {
  private String title;
  private String description;
  private Double price;
  private Double locationLatitude;
  private Double locationLongitude;
  private Long categoryId; // Allowing update of the category

  // Getters and setters

  /**
   * Get the title of the item.
   * @return the title of the item
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set the title of the item.
   * @param title the title of the item
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Get the description of the item.
   * @return the description of the item
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of the item.
   * @param description the description of the item
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get the price of the item.
   * @return the price of the item
   */
  public Double getPrice() {
    return price;
  }

  /**
   * Set the price of the item.
   * @param price the price of the item
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Get the latitude of the item's location.
   * @return the latitude of the item's location
   */
  public Double getLocationLatitude() {
    return locationLatitude;
  }

  /**
   * Set the latitude of the item's location.
   * @param locationLatitude the latitude of the item's location
   */
  public void setLocationLatitude(Double locationLatitude) {
    this.locationLatitude = locationLatitude;
  }

  /**
   * Get the longitude of the item's location.
   * @return the longitude of the item's location
   */
  public Double getLocationLongitude() {
    return locationLongitude;
  }

  /**
   * Set the longitude of the item's location.
   * @param locationLongitude the longitude of the item's location
   */
  public void setLocationLongitude(Double locationLongitude) {
    this.locationLongitude = locationLongitude;
  }

  /**
   * Get the category ID of the item.
   * @return the category ID of the item
   */
  public Long getCategoryId() {
    return categoryId;
  }

  /**
   * Set the category ID of the item.
   * @param categoryId the category ID of the item
   */
  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
}
