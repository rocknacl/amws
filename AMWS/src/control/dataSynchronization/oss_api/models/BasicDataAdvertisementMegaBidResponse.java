package control.dataSynchronization.oss_api.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class BasicDataAdvertisementMegaBidResponse  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sellerCode;
	private String campaignName;
	private String adGroupName;
	private String adSKU;
	private String keyword;
	private String matchType;
	private String startDate;
	private String endDate;
	private Integer clicks;
	private Integer impressions;
	private BigDecimal totalSpend;
	private String currency;
	private Integer orderQuantity;
	private BigDecimal sales;
	private BigDecimal maxCPCBid;
	private BigDecimal estPage1Bid;
	
	public BasicDataAdvertisementMegaBidResponse(){
		
	}
	
	public BasicDataAdvertisementMegaBidResponse(BasicDataAdvertisementMegaResponse r){
		this.sellerCode = r.getSellerCode();
		this.campaignName = r.getCampaignName();
		this.adGroupName = r.getAdGroupName();
		this.adSKU = r.getAdSKU();
		this.keyword = r.getKeyword();
		this.matchType = r.getMatchType();
		this.startDate = r.getStartDate();
		this.endDate = r.getEndDate();
		this.clicks = r.getClicks();
		this.impressions = r.getImpressions();
		this.totalSpend = r.getTotalSpend();
		this.currency = r.getCurrency();
		this.orderQuantity = r.getOrderQuantity();
		this.sales = r.getSales();
	}

	public BasicDataAdvertisementMegaBidResponse(String sellerCode, String campaignName, String adGroupName,
			String adSKU, String keyword, String matchType, String startDate, String endDate, Integer clicks,
			Integer impressions, BigDecimal totalSpend, String currency, Integer orderQuantity, BigDecimal sales,
			BigDecimal maxCPCBid, BigDecimal estPage1Bid) {
		this.sellerCode = sellerCode;
		this.campaignName = campaignName;
		this.adGroupName = adGroupName;
		this.adSKU = adSKU;
		this.keyword = keyword;
		this.matchType = matchType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.clicks = clicks;
		this.impressions = impressions;
		this.totalSpend = totalSpend;
		this.currency = currency;
		this.orderQuantity = orderQuantity;
		this.sales = sales;
		this.maxCPCBid = maxCPCBid;
		this.estPage1Bid = estPage1Bid;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public String getAdSKU() {
		return adSKU;
	}

	public void setAdSKU(String adSKU) {
		this.adSKU = adSKU;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public Integer getImpressions() {
		return impressions;
	}

	public void setImpressions(Integer impressions) {
		this.impressions = impressions;
	}

	public BigDecimal getTotalSpend() {
		return totalSpend;
	}

	public void setTotalSpend(BigDecimal totalSpend) {
		this.totalSpend = totalSpend;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	public BigDecimal getMaxCPCBid() {
		return maxCPCBid;
	}

	public void setMaxCPCBid(BigDecimal maxCPCBid) {
		this.maxCPCBid = maxCPCBid;
	}

	public BigDecimal getEstPage1Bid() {
		return estPage1Bid;
	}

	public void setEstPage1Bid(BigDecimal estPage1Bid) {
		this.estPage1Bid = estPage1Bid;
	}
}
