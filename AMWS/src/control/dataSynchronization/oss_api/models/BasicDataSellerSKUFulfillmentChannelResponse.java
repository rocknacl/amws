package control.dataSynchronization.oss_api.models;

public class BasicDataSellerSKUFulfillmentChannelResponse {

	private String sellerCode;
	private String sellerSKU;
	private String fulfillmentChannel;
	
	public BasicDataSellerSKUFulfillmentChannelResponse(){
		
	}
	public BasicDataSellerSKUFulfillmentChannelResponse(
			String sellerCode,
			String sellerSKU,
			String fulfillmentChannel){
		this.sellerCode = sellerCode;
		this.sellerSKU = sellerSKU;
		this.fulfillmentChannel = fulfillmentChannel;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public BasicDataSellerSKUFulfillmentChannelResponse withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public String getSellerSKU() {
		return sellerSKU;
	}
	public void setSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
	}
	public BasicDataSellerSKUFulfillmentChannelResponse withSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
		return this;
	}
	public String getFulfillmentChannel() {
		return fulfillmentChannel;
	}
	public void setFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
	}
	public BasicDataSellerSKUFulfillmentChannelResponse withFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
		return this;
	}
	
}
