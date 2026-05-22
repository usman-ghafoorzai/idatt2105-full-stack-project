package no.ntnu.idatt2105.marketplace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating a new category.
 * This class is used to transfer data from the client to the server when creating a new category.
 * It contains the name of the category and the name of the parent category.
 * The parent category name is optional and can be null if the category is a top-level category.
 */
@Schema(description = "DTO for creating a new category. Includes a name and an optional parent category name.")
public class CategoryCreateDTO {
  
  @NotBlank(message = "Category name is required.")
  @Size(max = 100, message = "Category name must be 100 characters or less.")
  @Schema(description = "The name of the category to create", example = "Electronics", required = true)
  private String name;

  @Schema(description = "Name of the parent category, if any", example = "Technology", required = false)
  private String parentCategoryName;

  // Getters and Setters

  /**
   * Gets the name of the category.
   * @return the name of the category
   */
  public String getName() {
    return name;
  }
  /**
   * Sets the name of the category.
   * @param name the name of the category
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * Gets the name of the parent category.
   * @return the name of the parent category
   */
  public String getParentCategoryName() {
    return parentCategoryName;
  }
  /**
   * Sets the name of the parent category.
   * @param parentCategoryName the name of the parent category
   */
  public void setParentCategoryName(String parentCategoryName) {
    this.parentCategoryName = parentCategoryName;
  }
}
