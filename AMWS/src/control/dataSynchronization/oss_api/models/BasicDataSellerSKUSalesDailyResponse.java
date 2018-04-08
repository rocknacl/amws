package control.dataSynchronization.oss_api.models;

import java.io.Serializable;

public class BasicDataSellerSKUSalesDailyResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String dateFormat = "yyyyMMddHHmmss";
	private String sellerCode;
	private String sellerSKU;
	private String fulfillmentChannel;
	private String date;
	private Integer salesVolumn;
	private Double salesAmount;
	
	public BasicDataSellerSKUSalesDailyResponse(){
		
	}
	public BasicDataSellerSKUSalesDailyResponse(
			String sellerCode,
			String sellerSKU,
			String fulfillmentChannel,
			String date,
			Integer salesVolumn,
			Double salesAmount){
		this.sellerCode = sellerCode;
		this.sellerSKU = sellerSKU;
		this.fulfillmentChannel = fulfillmentChannel;
		this.date = date;
		this.salesVolumn = salesVolumn;
		this.salesAmount = salesAmount;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public BasicDataSellerSKUSalesDailyResponse withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public String getSellerSKU() {
		return sellerSKU;
	}
	public void setSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
	}
	public BasicDataSellerSKUSalesDailyResponse withSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
		return this;
	}
	public String getFulfillmentChannel() {
		return fulfillmentChannel;
	}
	public void setFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
	}
	public BasicDataSellerSKUSalesDailyResponse withFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
		return this;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public BasicDataSellerSKUSalesDailyResponse withDate(String date) {
		this.date = date;
		return this;
	}
	public Integer getSalesVolumn() {
		return salesVolumn;
	}
	public void setSalesVolumn(Integer salesVolumn) {
		this.salesVolumn = salesVolumn;
	}
	public BasicDataSellerSKUSalesDailyResponse withSalesVolumn(Integer salesVolumn) {
		this.salesVolumn = salesVolumn;
		return this;
	}
	public Double getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(Double salesAmount) {
		this.salesAmount = salesAmount;
	}
	public BasicDataSellerSKUSalesDailyResponse withSalesAmount(Double salesAmount) {
		this.salesAmount = salesAmount;
		return this;
	}
	
}
