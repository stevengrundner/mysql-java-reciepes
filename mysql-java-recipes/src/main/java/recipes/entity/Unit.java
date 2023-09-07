// Copyright (c) 2022 Promineo Tech

package recipes.entity;

/**
 * This class contains data that represents a row in the unit table. A unit has
 * a singular name (i.e., 'teaspoon') and a plural name (i.e., 'teaspoons').
 * 
 * This class just contains getter and setter methods, and a {@link #toString()}
 * method. It uses the default zero-argument constructor created by the
 * compiler.
 * 
 * @author Promineo
 *
 */
public class Unit {
  private Integer unitId;
  private String unitNameSingular;
  private String unitNamePlural;

  public Integer getUnitId() {
    return unitId;
  }

  public void setUnitId(Integer unitId) {
    this.unitId = unitId;
  }

  public String getUnitNameSingular() {
    return unitNameSingular;
  }

  public void setUnitNameSingular(String unitNameSingular) {
    this.unitNameSingular = unitNameSingular;
  }

  public String getUnitNamePlural() {
    return unitNamePlural;
  }

  public void setUnitNamePlural(String unitNamePlural) {
    this.unitNamePlural = unitNamePlural;
  }

  @Override
  public String toString() {
    return "Unit [unitId=" + unitId + ", singular=" + unitNameSingular
        + ", plural=" + unitNamePlural + "]";
  }
}
