package no.ntnu.idatt2105.marketplace.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO (Data Transfer Object) for representing a category response.
 * This class is used to transfer category data from the server to the client.
 * It includes the category ID, name, parent category information,
 * and a list of subcategories.
 */
@Schema(description = "DTO representing a category response. Includes parent category and subcategories.")
public class CategoryResponseDTO {

  @Schema(description = "The ID of the category.", example = "1")
  private Long id;

  @Schema(description = "The name of the category.", example = "Electronics")
  private String name;

  @Schema(description = "The parent category information.", implementation = ParentCategoryDTO.class)
  private ParentCategoryDTO parentCategory;

  @Schema(description = "The list of subcategories.", implementation = SubCategoryDTO.class)
  private List<SubCategoryDTO> subcategories;

  /**
   * DTO for representing a parent category.
   * This class is used to transfer parent category data from the server to the client.
   * It includes the parent category ID and name.
   */
  @Schema(description = "DTO representing a parent category.")
  public static class ParentCategoryDTO {

    @Schema(description = "The ID of the parent category.", example = "2")
    private Long id;

    @Schema(description = "The name of the parent category.", example = "Home")
    private String name;

    // Getters and setters

    /**
     * Get the ID of the parent category.
     * @return the ID of the parent category
     */
    public Long getId() {
      return id;
    }

    /**
     * Set the ID of the parent category.
     * @param id the ID of the parent category
     */
    public void setId(Long id) {
      this.id = id;
    }

    /**
     * Get the name of the parent category.
     * @return the name of the parent category
     */
    public String getName() {
      return name;
    }

    /**
     * Set the name of the parent category.
     * @param name the name of the parent category
     */
    public void setName(String name) {
      this.name = name;
    }
  }

  /**
   * DTO for representing a subcategory.
   * This class is used to transfer subcategory data from the server to the client.
   * It includes the subcategory ID and name.
   */
  @Schema(description = "DTO representing a subcategory.")
  public static class SubCategoryDTO {

    @Schema(description = "The ID of the subcategory.", example = "3")
    private Long id;

    @Schema(description = "The name of the subcategory.", example = "Mobile Phones")
    private String name;

    // Getters and setters

    /**
     * Get the ID of the subcategory.
     * @return the ID of the subcategory
     */
    public Long getId() {
      return id;
    }

    /**
     * Set the ID of the subcategory.
     * @param id the ID of the subcategory
     */
    public void setId(Long id) {
      this.id = id;
    }

    /**
     * Get the name of the subcategory.
     * @return the name of the subcategory
     */
    public String getName() {
      return name;
    }

    /**
     * Set the name of the subcategory.
     * @param name the name of the subcategory
     */
    public void setName(String name) {
      this.name = name;
    }
  }

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

  /**
   * Get the parent category information.
   * @return the parent category information
   */
  public ParentCategoryDTO getParentCategory() {
    return parentCategory;
  }

  /**
   * Set the parent category information.
   * @param parentCategory the parent category information
   */
  public void setParentCategory(ParentCategoryDTO parentCategory) {
    this.parentCategory = parentCategory;
  }

  /**
   * Get the list of subcategories.
   * @return the list of subcategories
   */
  public List<SubCategoryDTO> getSubcategories() {
    return subcategories;
  }

  /**
   * Set the list of subcategories.
   * @param subcategories the list of subcategories
   */
  public void setSubcategories(List<SubCategoryDTO> subcategories) {
    this.subcategories = subcategories;
  }
}
