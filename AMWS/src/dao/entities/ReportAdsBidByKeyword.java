package dao.entities;
// Generated 2016-8-8 16:52:31 by Hibernate Tools 4.3.1.Final

import java.util.Date;

/**
 * ReportAdsBidByKeyword generated by hbm2java
 */
public class ReportAdsBidByKeyword implements java.io.Serializable {

	private Integer id;
	private String store;
	private Date insertDate;
	private Date reportDate;
	private String campaignName;
	private String adGroupName;
	private String keyword;
	private String currency;
	private String yourMaximunCpcBid;
	private String estPage1Bid;

	public ReportAdsBidByKeyword() {
	}

	public ReportAdsBidByKeyword(String store, Date insertDate, Date reportDate, String campaignName,
			String adGroupName, String keyword, String currency, String yourMaximunCpcBid, String estPage1Bid) {
		this.store = store;
		this.insertDate = insertDate;
		this.reportDate = reportDate;
		this.campaignName = campaignName;
		this.adGroupName = adGroupName;
		this.keyword = keyword;
		this.currency = currency;
		this.yourMaximunCpcBid = yourMaximunCpcBid;
		this.estPage1Bid = estPage1Bid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStore() {
		return this.store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getCampaignName() {
		return this.campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getAdGroupName() {
		return this.adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getYourMaximunCpcBid() {
		return this.yourMaximunCpcBid;
	}

	public void setYourMaximunCpcBid(String yourMaximunCpcBid) {
		this.yourMaximunCpcBid = yourMaximunCpcBid;
	}

	public String getEstPage1Bid() {
		return this.estPage1Bid;
	}

	public void setEstPage1Bid(String estPage1Bid) {
		this.estPage1Bid = estPage1Bid;
	}

}
