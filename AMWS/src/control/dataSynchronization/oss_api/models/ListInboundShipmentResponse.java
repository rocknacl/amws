package control.dataSynchronization.oss_api.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * response data for list in bound shipment(response to oss).
 * @author D16
 *
 */
public class ListInboundShipmentResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sellerCode;
	private String shipmentId;
	private String shipmentName;
	private String shipmentStatus;
	private String destinationFulfillmentCenterId;
	private String labelPrepType;
	private Boolean areCaseRequired;
	private String requestId;
	private String requestTimestamp;
	private ArrayList<ListInboundShipmentItem> lisiList;
	
	public ListInboundShipmentResponse(){
		
	}
	public ListInboundShipmentResponse(
			String sellerCode,
			String shipmentId,
			String shipmentName,
			String shipmentStatus,
			String destinationFulfillmentCenterId,
			String labelPrepType,
			Boolean areCaseRequired,
			String requestId,
			String requestTimestamp,
			ArrayList<ListInboundShipmentItem> lisiList){
		this.sellerCode = sellerCode;
		this.shipmentId = shipmentId;
		this.shipmentName = shipmentName;
		this.shipmentStatus = shipmentStatus;
		this.destinationFulfillmentCenterId = destinationFulfillmentCenterId;
		this.labelPrepType = labelPrepType;
		this.areCaseRequired = areCaseRequired;
		this.requestId = requestId;
		this.requestTimestamp = requestTimestamp;
		this.lisiList = lisiList;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public ListInboundShipmentResponse withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public ListInboundShipmentResponse withShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
		return this;
	}
	public String getShipmentName() {
		return shipmentName;
	}
	public void setShipmentName(String shipmentName) {
		this.shipmentName = shipmentName;
	}
	public ListInboundShipmentResponse withShipmentName(String shipmentName) {
		this.shipmentName = shipmentName;
		return this;
	}
	public String getShipmentStatus() {
		return shipmentStatus;
	}
	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	public ListInboundShipmentResponse withShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
		return this;
	}
	public String getDestinationFulfillmentCenterId() {
		return destinationFulfillmentCenterId;
	}
	public void setDestinationFulfillmentCenterId(String destinationFulfillmentCenterId) {
		this.destinationFulfillmentCenterId = destinationFulfillmentCenterId;
	}
	public ListInboundShipmentResponse withDestinationFulfillmentCenterId(String destinationFulfillmentCenterId) {
		this.destinationFulfillmentCenterId = destinationFulfillmentCenterId;
		return this;
	}
	public String getLabelPrepType() {
		return labelPrepType;
	}
	public void setLabelPrepType(String labelPrepType) {
		this.labelPrepType = labelPrepType;
	}
	public ListInboundShipmentResponse withLabelPrepType(String labelPrepType) {
		this.labelPrepType = labelPrepType;
		return this;
	}
	public Boolean getAreCaseRequired() {
		return areCaseRequired;
	}
	public void setAreCaseRequired(Boolean areCaseRequired) {
		this.areCaseRequired = areCaseRequired;
	}
	public ListInboundShipmentResponse withAreCaseRequired(Boolean areCaseRequired) {
		this.areCaseRequired = areCaseRequired;
		return this;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public ListInboundShipmentResponse withRequestId(String requestId) {
		this.requestId = requestId;
		return this;
	}
	public String getRequestTimestamp() {
		return requestTimestamp;
	}
	public void setRequestTimestamp(String requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}
	public ListInboundShipmentResponse withRequestTimestamp(String requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
		return this;
	}
	public ArrayList<ListInboundShipmentItem> getLisiList() {
		return lisiList;
	}
	public void setLisiList(ArrayList<ListInboundShipmentItem> lisiList) {
		this.lisiList = lisiList;
	}
	public ListInboundShipmentResponse withLisiList(ArrayList<ListInboundShipmentItem> lisiList) {
		this.lisiList = lisiList;
		return this;
	}
	public void addLisiList(ListInboundShipmentItem lisi) {
		if (lisiList == null) lisiList = new ArrayList<ListInboundShipmentItem>();
		this.lisiList.add(lisi);
	}
	
}
