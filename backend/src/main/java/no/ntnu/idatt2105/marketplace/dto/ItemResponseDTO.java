package no.ntnu.idatt2105.marketplace.dto;

import java.time.LocalDateTime;

public class ItemResponseDTO {
  private Long id;
  private String title;
  private String description;
  private Double price;
  private Double locationLatitude;
  private Double locationLongitude;
  private String status;
  private LocalDateTime createdAt;

  // Minimal representation for category
  private CategoryDTO category;

  // Minimal representation for seller
  private SellerDTO seller;

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getLocationLatitude() {
    return locationLatitude;
  }

  public void setLocationLatitude(Double locationLatitude) {
    this.locationLatitude = locationLatitude;
  }

  public Double getLocationLongitude() {
    return locationLongitude;
  }

  public void setLocationLongitude(Double locationLongitude) {
    this.locationLongitude = locationLongitude;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public CategoryDTO getCategory() {
    return category;
  }

  public void setCategory(CategoryDTO category) {
    this.category = category;
  }

  public SellerDTO getSeller() {
    return seller;
  }

  public void setSeller(SellerDTO seller) {
    this.seller = seller;
  }

  // Nested DTOs for associated objects
  public static class CategoryDTO {
    private Long id;
    private String name;
    // Getters and setters

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class SellerDTO {
    private Long id;
    private String username;
    private String email;
    // Getters and setters

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }
}
