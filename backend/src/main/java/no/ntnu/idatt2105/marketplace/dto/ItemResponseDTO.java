package no.ntnu.idatt2105.marketplace.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Item response.
 * This class is used to transfer item data between the server and client.
 * It contains fields for item details, including the associated category and seller.
 */
public class ItemResponseDTO {
  private Long id;
  private String title;
  private String description;
  private Double price;
  private Double locationLatitude;
  private Double locationLongitude;
  private String status;
  private LocalDateTime createdAt;

  private CategoryDTO category;
  private SellerDTO seller;

  // Getters and setters

  /**
   * Get the ID of the item.
   * @return the ID of the item
   */
  public Long getId() {
    return id;
  }

  /**
   * Set the ID of the item.
   * @param id the ID of the item
   */
  public void setId(Long id) {
    this.id = id;
  }

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
   * Get the status of the item.
   * @return the status of the item
   */
  public String getStatus() {
    return status;
  }

  /**
   * Set the status of the item.
   * @param status the status of the item
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Get the creation date and time of the item.
   * @return the creation date and time of the item
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the creation date and time of the item.
   * @param createdAt the creation date and time of the item
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Get the category of the item.
   * @return the category of the item
   */
  public CategoryDTO getCategory() {
    return category;
  }

  /**
   * Set the category of the item.
   * @param category the category of the item
   */
  public void setCategory(CategoryDTO category) {
    this.category = category;
  }

  /**
   * Get the seller of the item.
   * @return the seller of the item
   */
  public SellerDTO getSeller() {
    return seller;
  }

  /**
   * Set the seller of the item.
   * @param seller the seller of the item
   */
  public void setSeller(SellerDTO seller) {
    this.seller = seller;
  }

  /**
   * Data Transfer Object (DTO) for Category.
   * This class is used to transfer category data between the server and client.
   */
  public static class CategoryDTO {
    private Long id;
    private String name;
    
    // Getters and setters

    /**
     * Get the ID of the category.
     * @return the ID of the category
     */
    public Long getId() {
      return id;
    }

    /**
     * Set the ID of the category.
     * @param id the ID of the category
     */
    public void setId(Long id) {
      this.id = id;
    }

    /**
     * Get the name of the category.
     * @return the name of the category
     */
    public String getName() {
      return name;
    }

    /**
     * Set the name of the category.
     * @param name the name of the category
     */
    public void setName(String name) {
      this.name = name;
    }
  }

  /**
   * Data Transfer Object (DTO) for Seller.
   * This class is used to transfer seller data between the server and client.
   */
  public static class SellerDTO {
    private Long id;
    private String username;
    private String email;
    
    // Getters and setters

    /**
     * Get the ID of the seller.
     * @return the ID of the seller
     */
    public Long getId() {
      return id;
    }

    /**
     * Set the ID of the seller.
     * @param id the ID of the seller
     */
    public void setId(Long id) {
      this.id = id;
    }

    /**
     * Get the username of the seller.
     * @return the username of the seller
     */
    public String getUsername() {
      return username;
    }

    /**
     * Set the username of the seller.
     * @param username the username of the seller
     */
    public void setUsername(String username) {
      this.username = username;
    }

    /**
     * Get the email of the seller.
     * @return the email of the seller
     */
    public String getEmail() {
      return email;
    }

    /**
     * Set the email of the seller.
     * @param email the email of the seller
     */
    public void setEmail(String email) {
      this.email = email;
    }
  }
}
