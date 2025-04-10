package no.ntnu.idatt2105.marketplace.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating a category.
 * This class is used to transfer data between the client and server for category updates.
 * It contains fields for the category name, parent category ID, and a list of subcategory IDs.
 * The parent category ID can be null to remove the parent category association.
 * The subcategory IDs can be an empty list to remove all subcategories.
 */
public class CategoryUpdateDTO {

  @NotBlank(message = "Category name is required.")
  @Size(max = 100, message = "Category name must be 100 characters or less.")
  private String name;
  
  private Long parentCategoryId; // optional; can be null to remove the parent
  private List<Long> subCategoryIds; // optional; list of subcategory IDs to remain

  // Getters and setters

  /**
   * Gets the name of the category.
   * @return the name of the category
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the category.
   * @param name the new name of the category
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the ID of the parent category.
   * @return the ID of the parent category, or null if there is no parent
   */
  public Long getParentCategoryId() {
    return parentCategoryId;
  }

  /**
   * Sets the ID of the parent category.
   * @param parentCategoryId the new ID of the parent category, or null to remove the parent
   */
  public void setParentCategoryId(Long parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  /**
   * Gets the list of subcategory IDs.
   * @return the list of subcategory IDs, or an empty list if there are no subcategories
   */
  public List<Long> getSubCategoryIds() {
    return subCategoryIds;
  }

  /**
   * Sets the list of subcategory IDs.
   * @param subCategoryIds the new list of subcategory IDs, or an empty list to remove all subcategories
   */
  public void setSubCategoryIds(List<Long> subCategoryIds) {
    this.subCategoryIds = subCategoryIds;
  }
}
