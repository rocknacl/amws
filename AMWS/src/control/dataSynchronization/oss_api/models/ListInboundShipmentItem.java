package control.dataSynchronization.oss_api.models;

import java.io.Serializable;

/**
 * details for list in bound shipment item.
 * @author D16
 *
 */
public class ListInboundShipmentItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sellerCode;
	private String shipmentId;
	private String sellerSKU;
	private String fulfillmentNetworkSKU;
	private Integer quantityShipped;
	private Integer quantityreveiced;
	private Integer quantityInCase;
	private String requestId;
	private String requestTimestamp;
	
	public ListInboundShipmentItem(){
		
	}
	public ListInboundShipmentItem(
			String sellerCode,
			String shipmentId,
			String sellerSKU,
			String fulfillmentNetworkSKU,
			Integer quantityShipped,
			Integer quantityreveiced,
			Integer quantityInCase,
			String requestId,
			String requestTimestamp){
		this.sellerCode = sellerCode;
		this.shipmentId = shipmentId;
		this.sellerSKU = sellerSKU;
		this.fulfillmentNetworkSKU = fulfillmentNetworkSKU;
		this.quantityShipped = quantityShipped;
		this.quantityreveiced = quantityreveiced;
		this.quantityInCase = quantityInCase;
		this.requestId = requestId;
		this.requestTimestamp = requestTimestamp;
	}

	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public ListInboundShipmentItem withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public ListInboundShipmentItem withShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
		return this;
	}
	public String getSellerSKU() {
		return sellerSKU;
	}
	public void setSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
	}
	public ListInboundShipmentItem withSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
		return this;
	}
	public String getFulfillmentNetworkSKU() {
		return fulfillmentNetworkSKU;
	}
	public void setFulfillmentNetworkSKU(String fulfillmentNetworkSKU) {
		this.fulfillmentNetworkSKU = fulfillmentNetworkSKU;
	}
	public ListInboundShipmentItem withFulfillmentNetworkSKU(String fulfillmentNetworkSKU) {
		this.fulfillmentNetworkSKU = fulfillmentNetworkSKU;
		return this;
	}
	public Integer getQuantityShipped() {
		return quantityShipped;
	}
	public void setQuantityShipped(Integer quantityShipped) {
		this.quantityShipped = quantityShipped;
	}
	public ListInboundShipmentItem withQuantityShipped(Integer quantityShipped) {
		this.quantityShipped = quantityShipped;
		return this;
	}
	public Integer getQuantityreveiced() {
		return quantityreveiced;
	}
	public void setQuantityreveiced(Integer quantityreveiced) {
		this.quantityreveiced = quantityreveiced;
	}
	public ListInboundShipmentItem withQuantityreveiced(Integer quantityreveiced) {
		this.quantityreveiced = quantityreveiced;
		return this;
	}
	public Integer getQuantityInCase() {
		return quantityInCase;
	}
	public void setQuantityInCase(Integer quantityInCase) {
		this.quantityInCase = quantityInCase;
	}
	public ListInboundShipmentItem withQuantityInCase(Integer quantityInCase) {
		this.quantityInCase = quantityInCase;
		return this;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public ListInboundShipmentItem withRequestId(String requestId) {
		this.requestId = requestId;
		return this;
	}
	public String getRequestTimestamp() {
		return requestTimestamp;
	}
	public void setRequestTimestamp(String requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}
	public ListInboundShipmentItem withRequestTimestamp(String requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
		return this;
	}
	
}
