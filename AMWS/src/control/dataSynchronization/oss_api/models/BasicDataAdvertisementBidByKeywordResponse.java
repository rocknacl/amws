package control.dataSynchronization.oss_api.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class BasicDataAdvertisementBidByKeywordResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sellerCode;
	private String reportDate;
	private String campaignName;
	private String adGroupName;
	private String keyword;
	private String currency;
	private BigDecimal maxCPCBid;
	private BigDecimal estPage1Bid;
	
	public BasicDataAdvertisementBidByKeywordResponse(){
		
	}

	public BasicDataAdvertisementBidByKeywordResponse(String sellerCode, String reportDate, String campaignName,
			String adGroupName, String keyword, String currency, BigDecimal maxCPCBid, BigDecimal estPage1Bid) {
		this.sellerCode = sellerCode;
		this.reportDate = reportDate;
		this.campaignName = campaignName;
		this.adGroupName = adGroupName;
		this.keyword = keyword;
		this.currency = currency;
		this.maxCPCBid = maxCPCBid;
		this.estPage1Bid = estPage1Bid;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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