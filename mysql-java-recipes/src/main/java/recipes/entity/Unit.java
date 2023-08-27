package recipes.entity;

public class Unit {
	private Integer unitId;
	private String unitNameSingular;
	private String unitNamePlurarl;

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

	public String getUnitNamePlurarl() {
		return unitNamePlurarl;
	}

	public void setUnitNamePlurarl(String unitNamePlurarl) {
		this.unitNamePlurarl = unitNamePlurarl;
	}

	@Override
	public String toString() {
		return "Unit [unitId=" + unitId + ", unitNameSingular=" + unitNameSingular + ", unitNamePlurarl="
				+ unitNamePlurarl + "]";
	}

}
