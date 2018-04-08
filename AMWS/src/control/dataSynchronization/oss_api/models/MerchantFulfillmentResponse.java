package control.dataSynchronization.oss_api.models;

import java.io.Serializable;
import java.math.BigDecimal;

import model.basic.FileData;

/**
 * response data for merchant fulfillment(response to oss).
 * @author D16
 *
 */
public class MerchantFulfillmentResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sellerCode;
	private String packageId;
	private String shipmentId;
	private String orderId;
	private BigDecimal rateAmount;
	private String rateCurrencyCode;
	private String status;
	private String trackingId;
	private String shippingServiceId;
	private String shippingServiceName;
	private FileData labelFile;
	
	public MerchantFulfillmentResponse(){
		
	}
	public MerchantFulfillmentResponse(
			String sellerCode,
			String packageId,
			String shipmentId,
			String orderId,
			BigDecimal rateAmount,
			String rateCurrencyCode,
			String status,
			String trackingId,
			String shippingServiceId,
			String shippingServiceName,
			FileData labelFile){
		this.sellerCode = sellerCode;
		this.packageId = packageId;
		this.shipmentId = shipmentId;
		this.orderId = orderId;
		this.rateAmount = rateAmount;
		this.rateCurrencyCode = rateCurrencyCode;
		this.status = status;
		this.trackingId = trackingId;
		this.shippingServiceId = shippingServiceId;
		this.shippingServiceName = shippingServiceName;
		this.labelFile = labelFile;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getRateAmount() {
		return rateAmount;
	}
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getShippingServiceId() {
		return shippingServiceId;
	}
	public void setShippingServiceId(String shippingServiceId) {
		this.shippingServiceId = shippingServiceId;
	}
	public String getShippingServiceName() {
		return shippingServiceName;
	}
	public void setShippingServiceName(String shippingServiceName) {
		this.shippingServiceName = shippingServiceName;
	}
	public FileData getLabelFile() {
		return labelFile;
	}
	public void setLabelFile(FileData labelFile) {
		this.labelFile = labelFile;
	}
	public String getRateCurrencyCode() {
		return rateCurrencyCode;
	}
	public void setRateCurrencyCode(String rateCurrencyCode) {
		this.rateCurrencyCode = rateCurrencyCode;
	}
	
}
