package no.ntnu.idatt2105.marketplace.dto;

import java.util.List;

public class CategoryResponseDTO {
  private Long id;
  private String name;
  private ParentCategoryDTO parentCategory;
  private List<SubCategoryDTO> subcategories;

  public static class ParentCategoryDTO {
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

  public static class SubCategoryDTO {
    private Long id;
    private String name;

    // Optionally include minimal parent info if needed
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

  public ParentCategoryDTO getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(ParentCategoryDTO parentCategory) {
    this.parentCategory = parentCategory;
  }

  public List<SubCategoryDTO> getSubcategories() {
    return subcategories;
  }

  public void setSubcategories(List<SubCategoryDTO> subcategories) {
    this.subcategories = subcategories;
  }
}
