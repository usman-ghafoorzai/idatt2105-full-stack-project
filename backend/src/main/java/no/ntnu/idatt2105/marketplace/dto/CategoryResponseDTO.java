package no.ntnu.idatt2105.marketplace.dto;

import java.util.List;

/**
 * DTO (Data Transfer Object) for representing a category response.
 * This class is used to transfer category data from the server to the client.
 * It includes the category ID, name, parent category information,
 * and a list of subcategories.
 */
public class CategoryResponseDTO {
  private Long id;
  private String name;
  private ParentCategoryDTO parentCategory;
  private List<SubCategoryDTO> subcategories;

  /**
   * DTO for representing a parent category.
   * This class is used to transfer parent category data from the server to the client.
   * It includes the parent category ID and name.
   */
  public static class ParentCategoryDTO {
    private Long id;
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
  public static class SubCategoryDTO {
    private Long id;
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
