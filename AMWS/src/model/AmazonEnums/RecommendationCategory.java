package model.AmazonEnums;

import java.util.ArrayList;

public enum RecommendationCategory {
	Inventory("Inventory"), Selection("Selection"), Pricing("Pricing"), Fulfillment("Fulfillment"), ListingQuality(
			"ListingQuality"), GlobalSelling("GlobalSelling"), Advertising("Advertising");

	private final String value;

	RecommendationCategory(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public boolean equals(RecommendationCategory recomendationcategory) {
		return this.value.equals(recomendationcategory.getValue());
	}

	public boolean equals(String recomendationcategory) {
		if (recomendationcategory == null)
			return false;
		return this.value.equals(recomendationcategory);
	}
	
	public ArrayList<String> getAllString(){
		ArrayList<String> list = new ArrayList<String>();
		for(RecommendationCategory recommendationcategory:RecommendationCategory.values()){
			list.add(recommendationcategory.getValue());
		}
		return list;
	}
}
