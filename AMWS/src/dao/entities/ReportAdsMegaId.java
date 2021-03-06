package dao.entities;
// Generated 2016-8-5 10:25:53 by Hibernate Tools 4.3.1.Final

import java.util.Date;

/**
 * ReportAdsMegaId generated by hbm2java
 */
public class ReportAdsMegaId implements java.io.Serializable {

	private String store;
	private String campaignName;
	private String adGroupName;
	private String advertisedSku;
	private String keyword;
	private String matchType;
	private Date startDate;
	private Date endDate;

	public ReportAdsMegaId() {
	}

	public ReportAdsMegaId(String store, String campaignName, String adGroupName, String advertisedSku, String keyword,
			String matchType, Date startDate, Date endDate) {
		this.store = store;
		this.campaignName = campaignName;
		this.adGroupName = adGroupName;
		this.advertisedSku = advertisedSku;
		this.keyword = keyword;
		this.matchType = matchType;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getStore() {
		return this.store;
	}

	public void setStore(String store) {
		this.store = store;
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

	public String getAdvertisedSku() {
		return this.advertisedSku;
	}

	public void setAdvertisedSku(String advertisedSku) {
		this.advertisedSku = advertisedSku;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMatchType() {
		return this.matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ReportAdsMegaId))
			return false;
		ReportAdsMegaId castOther = (ReportAdsMegaId) other;

		return ((this.getStore() == castOther.getStore()) || (this.getStore() != null && castOther.getStore() != null
				&& this.getStore().equals(castOther.getStore())))
				&& ((this.getCampaignName() == castOther.getCampaignName())
						|| (this.getCampaignName() != null && castOther.getCampaignName() != null
								&& this.getCampaignName().equals(castOther.getCampaignName())))
				&& ((this.getAdGroupName() == castOther.getAdGroupName())
						|| (this.getAdGroupName() != null && castOther.getAdGroupName() != null
								&& this.getAdGroupName().equals(castOther.getAdGroupName())))
				&& ((this.getAdvertisedSku() == castOther.getAdvertisedSku())
						|| (this.getAdvertisedSku() != null && castOther.getAdvertisedSku() != null
								&& this.getAdvertisedSku().equals(castOther.getAdvertisedSku())))
				&& ((this.getKeyword() == castOther.getKeyword()) || (this.getKeyword() != null
						&& castOther.getKeyword() != null && this.getKeyword().equals(castOther.getKeyword())))
				&& ((this.getMatchType() == castOther.getMatchType()) || (this.getMatchType() != null
						&& castOther.getMatchType() != null && this.getMatchType().equals(castOther.getMatchType())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this.getStartDate() != null
						&& castOther.getStartDate() != null && this.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate().equals(castOther.getEndDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getStore() == null ? 0 : this.getStore().hashCode());
		result = 37 * result + (getCampaignName() == null ? 0 : this.getCampaignName().hashCode());
		result = 37 * result + (getAdGroupName() == null ? 0 : this.getAdGroupName().hashCode());
		result = 37 * result + (getAdvertisedSku() == null ? 0 : this.getAdvertisedSku().hashCode());
		result = 37 * result + (getKeyword() == null ? 0 : this.getKeyword().hashCode());
		result = 37 * result + (getMatchType() == null ? 0 : this.getMatchType().hashCode());
		result = 37 * result + (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37 * result + (getEndDate() == null ? 0 : this.getEndDate().hashCode());
		return result;
	}

}
