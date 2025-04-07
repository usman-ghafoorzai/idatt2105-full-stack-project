package no.ntnu.idatt2105.marketplace.dto;

import java.util.List;

public class CategoryUpdateDTO {
  private String name;
  private Long parentCategoryId; // optional; can be null to remove the parent
  private List<Long> subCategoryIds; // optional; list of subcategory IDs to remain

  // Getters and setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getParentCategoryId() {
    return parentCategoryId;
  }

  public void setParentCategoryId(Long parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public List<Long> getSubCategoryIds() {
    return subCategoryIds;
  }

  public void setSubCategoryIds(List<Long> subCategoryIds) {
    this.subCategoryIds = subCategoryIds;
  }
}
