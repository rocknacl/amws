package dao.entities;
// Generated 2016-8-8 16:52:31 by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * GeneralResponseRecord generated by hbm2java
 */
public class GeneralResponseRecord implements java.io.Serializable {

	private String requestId;
	private String tstamp;
	private Date recordDate;
	private String className;
	private String firstContent;
	private String merchant;
	private Set productAsinCategoryDatas = new HashSet(0);
	private Set productLowestPricedOffersDatas = new HashSet(0);

	public GeneralResponseRecord() {
	}

	public GeneralResponseRecord(String requestId) {
		this.requestId = requestId;
	}

	public GeneralResponseRecord(String requestId, String tstamp, Date recordDate, String className,
			String firstContent, String merchant, Set productAsinCategoryDatas, Set productLowestPricedOffersDatas) {
		this.requestId = requestId;
		this.tstamp = tstamp;
		this.recordDate = recordDate;
		this.className = className;
		this.firstContent = firstContent;
		this.merchant = merchant;
		this.productAsinCategoryDatas = productAsinCategoryDatas;
		this.productLowestPricedOffersDatas = productLowestPricedOffersDatas;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTstamp() {
		return this.tstamp;
	}

	public void setTstamp(String tstamp) {
		this.tstamp = tstamp;
	}

	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFirstContent() {
		return this.firstContent;
	}

	public void setFirstContent(String firstContent) {
		this.firstContent = firstContent;
	}

	public String getMerchant() {
		return this.merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public Set getProductAsinCategoryDatas() {
		return this.productAsinCategoryDatas;
	}

	public void setProductAsinCategoryDatas(Set productAsinCategoryDatas) {
		this.productAsinCategoryDatas = productAsinCategoryDatas;
	}

	public Set getProductLowestPricedOffersDatas() {
		return this.productLowestPricedOffersDatas;
	}

	public void setProductLowestPricedOffersDatas(Set productLowestPricedOffersDatas) {
		this.productLowestPricedOffersDatas = productLowestPricedOffersDatas;
	}

}