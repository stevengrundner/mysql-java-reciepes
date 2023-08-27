package recipes.entity;

import java.math.BigDecimal;
import java.util.Objects;
import provided.entity.EntityBase;

public class Ingredient extends EntityBase {
	private static final Unit unit = null;
	private Integer ingredientId;
	private Integer recipeId;
	private Unit unitId;
	private String ingredientName;
	private String instruction;
	private Integer ingredientOrder;
	private BigDecimal amount;

	public Integer getIngredientId() {
		return ingredientId;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();

		b.append("ID=").append(ingredientId).append(": ");
		b.append(toFraction(amount));

		if(Objects.nonNull(unit) && Objects.nonNull(unit.getUnitId())) {
			String singular = unit.getUnitNameSingular();
			String plural = unit.getUnitNamePlurarl();
			String word = amount.compareTo(BigDecimal.ONE) > 0 ? plural : singular;
			b.append(word).append(" ");
		}

			b.append(ingredientName);
			
			if(Objects.nonNull(instruction)) {
				b.append(", ").append(instruction);
			}
		return b.toString();
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

	public Unit getUnitId() {
		return unitId;
	}

	public void setUnitId(Unit unitId) {
		this.unitId = unitId;
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
