package control.dataSynchronization.oss_api.models;

public class BasicDataSellerSKUASINResponse {

	private String sellerCode;
	private String sellerSKU;
	private String asin;
	
	public BasicDataSellerSKUASINResponse(){
		
	}
	public BasicDataSellerSKUASINResponse(
			String sellerCode,
			String sellerSKU,
			String asin){
		this.sellerCode = sellerCode;
		this.sellerSKU = sellerSKU;
		this.asin = asin;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public BasicDataSellerSKUASINResponse withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public String getSellerSKU() {
		return sellerSKU;
	}
	public void setSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
	}
	public BasicDataSellerSKUASINResponse withSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
		return this;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public BasicDataSellerSKUASINResponse withAsin(String asin) {
		this.asin = asin;
		return this;
	}
	
}