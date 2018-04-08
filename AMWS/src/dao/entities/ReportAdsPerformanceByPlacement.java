package dao.entities;
// Generated 2016-8-8 16:52:31 by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * ReportAdsPerformanceByPlacement generated by hbm2java
 */
public class ReportAdsPerformanceByPlacement implements java.io.Serializable {

	private Integer id;
	private String store;
	private Date insertDate;
	private Date date;
	private String campaign;
	private String bid;
	private String placement;
	private Integer impressions;
	private Integer clicks;
	private String averageCpc;
	private BigDecimal totalSpend;
	private BigDecimal sales;
	private BigDecimal conversions;

	public ReportAdsPerformanceByPlacement() {
	}

	public ReportAdsPerformanceByPlacement(String store, Date insertDate, Date date, String campaign, String bid,
			String placement, Integer impressions, Integer clicks, String averageCpc, BigDecimal totalSpend,
			BigDecimal sales, BigDecimal conversions) {
		this.store = store;
		this.insertDate = insertDate;
		this.date = date;
		this.campaign = campaign;
		this.bid = bid;
		this.placement = placement;
		this.impressions = impressions;
		this.clicks = clicks;
		this.averageCpc = averageCpc;
		this.totalSpend = totalSpend;
		this.sales = sales;
		this.conversions = conversions;
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

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCampaign() {
		return this.campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getBid() {
		return this.bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getPlacement() {
		return this.placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public Integer getImpressions() {
		return this.impressions;
	}

	public void setImpressions(Integer impressions) {
		this.impressions = impressions;
	}

	public Integer getClicks() {
		return this.clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public String getAverageCpc() {
		return this.averageCpc;
	}

	public void setAverageCpc(String averageCpc) {
		this.averageCpc = averageCpc;
	}

	public BigDecimal getTotalSpend() {
		return this.totalSpend;
	}

	public void setTotalSpend(BigDecimal totalSpend) {
		this.totalSpend = totalSpend;
	}

	public BigDecimal getSales() {
		return this.sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	public BigDecimal getConversions() {
		return this.conversions;
	}

	public void setConversions(BigDecimal conversions) {
		this.conversions = conversions;
	}

}
