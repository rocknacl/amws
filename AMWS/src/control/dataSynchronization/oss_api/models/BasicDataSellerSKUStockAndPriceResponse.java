package control.dataSynchronization.oss_api.models;

import java.io.Serializable;

public class BasicDataSellerSKUStockAndPriceResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static  String dateFormat = "yyyyMMddHHmmss";
	private String sellerCode;
	private String sellerSKU;
	private String asin;
	private String inventoryDate;
	private String openListingDate;
	private String openDate;
	private Double price;
	private Integer quantityMFN;
	private Integer quantityAFNSellable;
	private Integer quantityAFNUnsellable;
	private String lastestOrderFulfillmentChannel;
	
	public BasicDataSellerSKUStockAndPriceResponse(){
		
	}
	public BasicDataSellerSKUStockAndPriceResponse(
			String sellerCode,
			String sellerSKU,
			String asin,
			String inventoryDate,
			String openListingDate,
			String openDate,
			Double price,
			Integer quantityMFN,
			Integer quantityAFNSellable,
			Integer quantityAFNUnsellable,
			String lastestOrderFulfillmentChannel){
		this.sellerCode = sellerCode;
		this.sellerSKU = sellerSKU;
		this.asin = asin;
		this.inventoryDate = inventoryDate;
		this.openListingDate = openListingDate;
		this.setOpenDate(openDate);
		this.price = price;
		this.quantityMFN = quantityMFN;
		this.quantityAFNSellable = quantityAFNSellable;
		this.quantityAFNUnsellable = quantityAFNUnsellable;
		this.lastestOrderFulfillmentChannel = lastestOrderFulfillmentChannel;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public BasicDataSellerSKUStockAndPriceResponse withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public String getSellerSKU() {
		return sellerSKU;
	}
	public void setSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
	}
	public BasicDataSellerSKUStockAndPriceResponse withSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
		return this;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public BasicDataSellerSKUStockAndPriceResponse withAsin(String asin) {
		this.asin = asin;
		return this;
	}
	public String getInventoryDate() {
		return inventoryDate;
	}
	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
	}
	public BasicDataSellerSKUStockAndPriceResponse withInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
		return this;
	}
	public String getOpenListingDate() {
		return openListingDate;
	}
	public void setOpenListingDate(String openListingDate) {
		this.openListingDate = openListingDate;
	}
	public BasicDataSellerSKUStockAndPriceResponse withOpenListingDate(String openListingDate) {
		this.openListingDate = openListingDate;
		return this;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public BasicDataSellerSKUStockAndPriceResponse withPrice(Double price) {
		this.price = price;
		return this;
	}
	public Integer getQuantityMFN() {
		return quantityMFN;
	}
	public void setQuantityMFN(Integer quantityMFN) {
		this.quantityMFN = quantityMFN;
	}
	public BasicDataSellerSKUStockAndPriceResponse withQuantityMFN(Integer quantityMFN) {
		this.quantityMFN = quantityMFN;
		return this;
	}
	public Integer getQuantityAFNSellable() {
		return quantityAFNSellable;
	}
	public void setQuantityAFNSellable(Integer quantityAFNSellable) {
		this.quantityAFNSellable = quantityAFNSellable;
	}
	public BasicDataSellerSKUStockAndPriceResponse withQuantityAFNSellable(Integer quantityAFNSellable) {
		this.quantityAFNSellable = quantityAFNSellable;
		return this;
	}
	public Integer getQuantityAFNUnsellable() {
		return quantityAFNUnsellable;
	}
	public void setQuantityAFNUnsellable(Integer quantityAFNUnsellable) {
		this.quantityAFNUnsellable = quantityAFNUnsellable;
	}
	public BasicDataSellerSKUStockAndPriceResponse withQuantityAFNUnsellable(Integer quantityAFNUnsellable) {
		this.quantityAFNUnsellable = quantityAFNUnsellable;
		return this;
	}
	public String getLastestOrderFulfillmentChannel() {
//		return lastestOrderFulfillmentChannel != null ?
//				lastestOrderFulfillmentChannel :
//				quantityAFNSellable != null ? "FBA" : "CBM";
		return lastestOrderFulfillmentChannel;
	}
	public void setLastestOrderFulfillmentChannel(String lastestOrderFulfillmentChannel) {
		this.lastestOrderFulfillmentChannel = lastestOrderFulfillmentChannel;
	}
	public BasicDataSellerSKUStockAndPriceResponse withLastestOrderFulfillmentChannel(String lastestOrderFulfillmentChannel) {
		this.lastestOrderFulfillmentChannel = lastestOrderFulfillmentChannel;
		return this;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	
}
