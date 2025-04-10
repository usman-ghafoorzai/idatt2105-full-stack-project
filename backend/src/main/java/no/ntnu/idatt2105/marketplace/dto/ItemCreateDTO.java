package no.ntnu.idatt2105.marketplace.dto;

/**
 * Data Transfer Object for creating a new item.
 * This class is used to transfer data from the client to the server when creating a new item.
 * It contains the necessary fields for item creation, such as title, description, price, location,
 * category ID, and seller ID.
 * The class includes getters and setters for each field to allow for easy access and modification of the data.
 */
public class ItemCreateDTO {
  private String title;
  private String description;
  private Double price;
  private Double locationLatitude;
  private Double locationLongitude;
  private Long categoryId;
  private Long sellerId;

  // Getters and setters

  /**
   * Gets the title of the item.
   * @return the title of the item
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the item.
   * @param title the title of the item
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the description of the item.
   * @return the description of the item
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the item.
   * @param description the description of the item
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the price of the item.
   * @return the price of the item
   */
  public Double getPrice() {
    return price;
  }

  /**
   * Sets the price of the item.
   * @param price the price of the item
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Gets the latitude of the item's location.
   * @return the latitude of the item's location
   */
  public Double getLocationLatitude() {
    return locationLatitude;
  }

  /**
   * Sets the latitude of the item's location.
   * @param locationLatitude the latitude of the item's location
   */
  public void setLocationLatitude(Double locationLatitude) {
    this.locationLatitude = locationLatitude;
  }

  /**
   * Gets the longitude of the item's location.
   * @return the longitude of the item's location
   */
  public Double getLocationLongitude() {
    return locationLongitude;
  }

  /**
   * Sets the longitude of the item's location.
   * @param locationLongitude the longitude of the item's location
   */
  public void setLocationLongitude(Double locationLongitude) {
    this.locationLongitude = locationLongitude;
  }

  /**
   * Gets the category ID of the item.
   * @return the category ID of the item
   */
  public Long getCategoryId() {
    return categoryId;
  }

  /**
   * Sets the category ID of the item.
   * @param categoryId the category ID of the item
   */
  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * Gets the seller ID of the item.
   * @return the seller ID of the item
   */
  public Long getSellerId() {
    return sellerId;
  }

  /**
   * Sets the seller ID of the item.
   * @param sellerId the seller ID of the item
   */
  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }
}
