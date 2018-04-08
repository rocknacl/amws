package control.transmission.model;

public class BasicDataSellerSKUAdvertisementResponse {
	
	public final static String dateFormat = "yyyyMMdd";
	private String sellerCode;
	private String date; // "20160706"
	private String sellerSKU;
	private Integer clicks;
	private Integer impressions;
	private Double totalSpend;

	public BasicDataSellerSKUAdvertisementResponse() {

	}

	public BasicDataSellerSKUAdvertisementResponse(String sellerCode, String date, String sellerSKU, Integer clicks,
			Integer impressions, Double totalSpend) {
		this.sellerCode = sellerCode;
		this.date = date;
		this.sellerSKU = sellerSKU;
		this.clicks = clicks;
		this.impressions = impressions;
		this.totalSpend = totalSpend;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public BasicDataSellerSKUAdvertisementResponse withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BasicDataSellerSKUAdvertisementResponse withDate(String date) {
		this.date = date;
		return this;
	}

	public String getSellerSKU() {
		return sellerSKU;
	}

	public void setSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
	}

	public BasicDataSellerSKUAdvertisementResponse withSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
		return this;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public BasicDataSellerSKUAdvertisementResponse withClicks(Integer clicks) {
		this.clicks = clicks;
		return this;
	}

	public Integer getImpressions() {
		return impressions;
	}

	public void setImpressions(Integer impressions) {
		this.impressions = impressions;
	}

	public BasicDataSellerSKUAdvertisementResponse withImpressions(Integer impressions) {
		this.impressions = impressions;
		return this;
	}

	public Double getTotalSpend() {
		return totalSpend;
	}

	public void setTotalSpend(Double totalSpend) {
		this.totalSpend = totalSpend;
	}

	public BasicDataSellerSKUAdvertisementResponse withTotalSpend(Double totalSpend) {
		this.totalSpend = totalSpend;
		return this;
	}

	public void printColumnName(int tabNum, boolean isLineBreak) {
		String tabIndentation = "";
		for (int i = 0; i < tabNum; i++)
			tabIndentation = tabIndentation + "\t";
		System.out.print(tabIndentation + "SellerCode" + "\t" + "Date" + "\t" + "SellerSKU" + "\t" + "Clicks" + "\t"
				+ "Impressions" + "\t" + "TotalSpend");
		if (isLineBreak)
			System.out.println();
	}

	public void printColumnName(int tabNum) {
		printColumnName(tabNum, true);
	}

	public void printColumnValue(int tabNum, boolean isLineBreak) {
		String tabIndentation = "";
		for (int i = 0; i < tabNum; i++)
			tabIndentation = tabIndentation + "\t";
		System.out.print(tabIndentation + sellerCode + "\t" + date + "\t" + sellerSKU + "\t" + clicks + "\t"
				+ impressions + "\t" + totalSpend);
		if (isLineBreak)
			System.out.println();
	}

	public void printColumnValue(int tabNum) {
		printColumnValue(tabNum, true);
	}

	public void print(int tabNum) {
		printColumnName(tabNum);
		printColumnValue(tabNum);
	}

}
