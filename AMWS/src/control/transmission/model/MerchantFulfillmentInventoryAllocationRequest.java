package control.transmission.model;

/**
 * request data for merchant fulfillment inventory allocation(request from oss).
 * @author D16
 *
 */
public class MerchantFulfillmentInventoryAllocationRequest {

	private String inventorySKU;
	private String sellerCode;
	private String sellerSKU;
	private int inventoryQuantity;
	private String sellerSKUStatus;
	
	public MerchantFulfillmentInventoryAllocationRequest(){
		
	}
	public MerchantFulfillmentInventoryAllocationRequest(
			String inventorySKU,
			String sellerCode,
			String sellerSKU,
			int inventoryQuantity,
			String sellerSKUStatus){
		this.inventorySKU = inventorySKU;
		this.sellerCode = sellerCode;
		this.sellerSKU = sellerSKU;
		this.inventoryQuantity = inventoryQuantity;
		this.sellerSKUStatus = sellerSKUStatus;
	}
	
	public String getInventorySKU() {
		return inventorySKU;
	}
	public void setInventorySKU(String inventorySKU) {
		this.inventorySKU = inventorySKU;
	}
	public MerchantFulfillmentInventoryAllocationRequest withInventorySKU(String inventorySKU) {
		this.inventorySKU = inventorySKU;
		return this;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public MerchantFulfillmentInventoryAllocationRequest withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public int getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(int inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public MerchantFulfillmentInventoryAllocationRequest withInventoryQuantity(int inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
		return this;
	}
	public String getSellerSKU() {
		return sellerSKU;
	}
	public void setSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
	}
	public MerchantFulfillmentInventoryAllocationRequest withSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
		return this;
	}
	public String getSellerSKUStatus() {
		return sellerSKUStatus;
	}
	public void setSellerSKUStatus(String sellerSKUStatus) {
		this.sellerSKUStatus = sellerSKUStatus;
	}
	public MerchantFulfillmentInventoryAllocationRequest withSellerSKUStatus(String sellerSKUStatus) {
		this.sellerSKUStatus = sellerSKUStatus;
		return this;
	}
	
}
