package model.AmazonEnums;

import java.util.ArrayList;

public enum ItemCondition {
	Any("Any"), New("New"), Used("Used"), Collectible("Collectible"), Refurbished("Refurbished"), Club("Club");

	private final String value;

	ItemCondition(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public boolean equals(ItemCondition recomendationcategory) {
		return this.value.equals(recomendationcategory.getValue());
	}

	public boolean equals(String recomendationcategory) {
		if (recomendationcategory == null)
			return false;
		return this.value.equals(recomendationcategory);
	}

	public ArrayList<String> getAllString() {
		ArrayList<String> list = new ArrayList<String>();
		for (ItemCondition recommendationcategory : ItemCondition.values()) {
			list.add(recommendationcategory.getValue());
		}
		return list;
	}
}
