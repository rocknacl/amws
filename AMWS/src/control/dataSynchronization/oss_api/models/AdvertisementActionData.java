package control.dataSynchronization.oss_api.models;

import java.io.Serializable;

public class AdvertisementActionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sellerCode;
	private String campaignName;
	private String adGroupName;
	private String keyword;
	private String matchType;
	private String startDate; // 2016-08-09
	private String endDate; // 2016-08-09
	private String proposedAction;
	private String realAction;
	private String result;
	
	public AdvertisementActionData(){
		
	}

	public AdvertisementActionData(BasicDataAdvertisementMegaBidResponse x){
		this(x.getSellerCode(),x.getCampaignName(),x.getAdGroupName(),x.getKeyword(),x.getMatchType(),
				x.getStartDate(),x.getEndDate(),null,null,null);
	}
	
	public AdvertisementActionData(String sellerCode, String campaignName, String adGroupName, String keyword,
			String matchType,String startDate, String endDate, String proposedAction, String realAction, String result) {
		this.sellerCode = sellerCode;
		this.campaignName = campaignName;
		this.adGroupName = adGroupName;
		this.keyword = keyword;
		this.setMatchType(matchType);
		this.startDate = startDate;
		this.endDate = endDate;
		this.proposedAction = proposedAction;
		this.realAction = realAction;
		this.result = result;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public String getProposedAction() {
		return proposedAction;
	}

	public void setProposedAction(String proposedAction) {
		this.proposedAction = proposedAction;
	}

	public String getRealAction() {
		return realAction;
	}

	public void setRealAction(String realAction) {
		this.realAction = realAction;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	
	
}
