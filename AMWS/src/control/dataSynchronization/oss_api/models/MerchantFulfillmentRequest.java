package control.dataSynchronization.oss_api.models;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * request data for merchant fulfillment(request from oss).
 * @author D16
 *
 */
public class MerchantFulfillmentRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sellerCode;
	private String packageId;
	private String orderId;
	private String orderItemId;
	private Integer quantityToShip;
	private BigDecimal lengthValue;
	private BigDecimal widthValue;
	private BigDecimal HeigthValue;
	private String dimensionUnit;
	private BigDecimal weightValue;
	private String weightUnit;
	
	public MerchantFulfillmentRequest(){
		
	}
	public MerchantFulfillmentRequest(
			String sellerCode,
			String packageId,
			String orderId,
			String orderItemId,
			Integer quantityToShip,
			BigDecimal lengthValue,
			BigDecimal widthValue,
			BigDecimal HeigthValue,
			String dimensionUnit,
			BigDecimal weightValue,
			String weightUnit){
		this.sellerCode = sellerCode;
		this.packageId = packageId;
		this.orderId = orderId;
		this.orderItemId = orderItemId;
		this.quantityToShip = quantityToShip;
		this.lengthValue = lengthValue;
		this.widthValue = widthValue;
		this.HeigthValue = HeigthValue;
		this.dimensionUnit = dimensionUnit;
		this.weightValue = weightValue;
		this.weightUnit = weightUnit;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Integer getQuantityToShip() {
		return quantityToShip;
	}
	public void setQuantityToShip(Integer quantityToShip) {
		this.quantityToShip = quantityToShip;
	}
	public BigDecimal getLengthValue() {
		return lengthValue;
	}
	public void setLengthValue(BigDecimal lengthValue) {
		this.lengthValue = lengthValue;
	}
	public BigDecimal getWidthValue() {
		return widthValue;
	}
	public void setWidthValue(BigDecimal widthValue) {
		this.widthValue = widthValue;
	}
	public BigDecimal getHeigthValue() {
		return HeigthValue;
	}
	public void setHeigthValue(BigDecimal heigthValue) {
		HeigthValue = heigthValue;
	}
	public String getDimensionUnit() {
		return dimensionUnit;
	}
	public void setDimensionUnit(String dimensionUnit) {
		this.dimensionUnit = dimensionUnit;
	}
	public BigDecimal getWeightValue() {
		return weightValue;
	}
	public void setWeightValue(BigDecimal weightValue) {
		this.weightValue = weightValue;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	
}
