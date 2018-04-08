package control.transmission.model;

import java.util.ArrayList;

/**
 * request data for list in bound shipment(request from oss).
 * @author D16
 *
 */
public class ListInboundShipmentRequestMerchant {

	private String sellerCode;
	private ArrayList<String> shipmentIdList;
	
	public ListInboundShipmentRequestMerchant(){
		
	}
	public ListInboundShipmentRequestMerchant(String sellerCode, ArrayList<String> shipmentIdList){
		this.sellerCode = sellerCode;
		this.shipmentIdList = shipmentIdList;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public ListInboundShipmentRequestMerchant withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public ArrayList<String> getShipmentIdList() {
		return shipmentIdList;
	}
	public void setShipmentIdList(ArrayList<String> shipmentIdList) {
		this.shipmentIdList = shipmentIdList;
	}
	public ListInboundShipmentRequestMerchant withShipmentIdList(ArrayList<String> shipmentIdList) {
		this.shipmentIdList = shipmentIdList;
		return this;
	}
	
}
