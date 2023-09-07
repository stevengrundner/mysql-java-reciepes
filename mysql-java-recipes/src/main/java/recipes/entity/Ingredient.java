// Copyright (c) 2022 Promineo Tech

package recipes.entity;

import java.math.BigDecimal;
import java.util.Objects;
import provided.entity.EntityBase;

/**
 * This class holds data for a row in the ingredient table. It contains getters
 * and setters for all instance variables. It also has a {@link #toString()}
 * method to print the ingredient along with the unit.
 * 
 * The class extends {@link EntityBase}, which provides a method used in the
 * {@link #toString()} method to convert a decimal value (1.5) to a fraction (1
 * 1/2).
 * 
 * @author Promineo
 *
 */
public class Ingredient extends EntityBase {
  private Integer ingredientId;
  private Integer recipeId;
  private Unit unit;
  private String ingredientName;
  private String instruction;
  private Integer ingredientOrder;
  private BigDecimal amount;

  /**
   * Returns a line like: ID=5: 1/4 cup carrots, thinly sliced. Note that the
   * {@link #toFraction(BigDecimal)} method in the base class is called to
   * convert from a decimal to a fraction.
   */
  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();

    b.append("ID=").append(ingredientId).append(": ");
    b.append(toFraction(amount));

    if (Objects.nonNull(unit) && Objects.nonNull(unit.getUnitId())) {
      String singular = unit.getUnitNameSingular();
      String plural = unit.getUnitNamePlural();
      String word = amount.compareTo(BigDecimal.ONE) > 0 ? plural : singular;

      b.append(word).append(" ");
    }

    b.append(ingredientName);

    if (Objects.nonNull(instruction)) {
      b.append(", ").append(instruction);
    }

    return b.toString();
  }

  public Integer getIngredientId() {
    return ingredientId;
  }

  public void setIngredientId(Integer ingredientId) {
    this.ingredientId = ingredientId;
  }

  public Integer getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(Integer recipeId) {
    this.recipeId = recipeId;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public String getIngredientName() {
    return ingredientName;
  }

  public void setIngredientName(String ingredientName) {
    this.ingredientName = ingredientName;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public Integer getIngredientOrder() {
    return ingredientOrder;
  }

  public void setIngredientOrder(Integer ingredientOrder) {
    this.ingredientOrder = ingredientOrder;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
