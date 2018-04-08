package control.transmission.model;

import java.util.ArrayList;

public class ListInboundShipmentResponseMerchant {

	private String sellerCode;
	private ArrayList<String> shipmentIdList;
	private ArrayList<ListInboundShipmentResponse> lisrdList;
	
	public ListInboundShipmentResponseMerchant(){
		
	}
	public ListInboundShipmentResponseMerchant(
			String sellerCode,
			ArrayList<String> shipmentIdList,
			ArrayList<ListInboundShipmentResponse> lisrdList){
		this.sellerCode = sellerCode;
		this.shipmentIdList = shipmentIdList;
		this.lisrdList = lisrdList;
	}

	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public ListInboundShipmentResponseMerchant withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public ArrayList<String> getShipmentIdList() {
		return shipmentIdList;
	}
	public void setShipmentIdList(ArrayList<String> shipmentIdList) {
		this.shipmentIdList = shipmentIdList;
	}
	public ListInboundShipmentResponseMerchant withShipmentIdList(ArrayList<String> shipmentIdList) {
		this.shipmentIdList = shipmentIdList;
		return this;
	}
	public ArrayList<ListInboundShipmentResponse> getLisrdList() {
		return lisrdList;
	}
	public void setLisrdList(ArrayList<ListInboundShipmentResponse> lisrdList) {
		this.lisrdList = lisrdList;
	}
	public ListInboundShipmentResponseMerchant withLisrdList(ArrayList<ListInboundShipmentResponse> lisrdList) {
		this.lisrdList = lisrdList;
		return this;
	}
	public void addLisrdList(ListInboundShipmentResponse lisrd) {
		this.lisrdList.add(lisrd);
	}
	
}
