// Copyright (c) 2022 Promineo Tech

package recipes.entity;

/**
 * This class holds data from a row in the category table. It has getters and
 * setters for each field, and a {@link #toString()} method.
 * 
 * @author Promineo
 *
 */
public class Category {
  private Integer categoryId;
  private String categoryName;

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  @Override
  public String toString() {
    return "ID=" + categoryId + ", categoryName=" + categoryName;
  }
}
