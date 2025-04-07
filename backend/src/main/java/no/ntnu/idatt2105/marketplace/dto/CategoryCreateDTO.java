package no.ntnu.idatt2105.marketplace.dto;

public class CategoryCreateDTO {
  private String name;
  private String parentCategoryName;

  // Getters and Setters
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getParentCategoryName() {
    return parentCategoryName;
  }
  public void setParentCategoryName(String parentCategoryName) {
    this.parentCategoryName = parentCategoryName;
  }
}
