package logic.fulfillmentinboundshipment.model;

import java.util.ArrayList;

import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.Address;

public class InboundShipment {

	public String sellerCode;
	
	public String shipmentId;
	public String shipmentName;
	public String destinationFulfillmentCenterId;
	public Address shipToAddress;
	public Address shipFromAddress;
	public String labelPrepType;
	public String shipmentStatus;
	public Boolean areCasesRequired;
	
	public ArrayList<InboundItem> inboundItemList;

	public Boolean isShipmentCreated;
	public String requestId;
	public String requestTimestamp;
	
	public String getColumnNameString(){
		return "SellerCode"
				+ "\t" + "ShipmentId"
				+ "\t" + "ShipmentName"
				+ "\t" + "DestinationFulfillmentCenterId"
				+ "\t" + "ShipToAddress"
				+ "\t" + "ShipFromAddress"
				+ "\t" + "LabelPrepType"
				+ "\t" + "ShipmentStatus"
				+ "\t" + "AreCasesRequired"
				+ "\t" + new InboundItem().getColumnNameString()
				+ "\t" + "IsShipmentCreated"
				+ "\t" + "RequestId"
				+ "\t" + "RequestTimestamp";
	}
	
	public void printColumnName(int tabNum){
		String tabIndentation = "";
		for ( int i = 0; i < tabNum; i++ ) tabIndentation = tabIndentation + "\t";
		System.out.println(tabIndentation
				+ getColumnNameString());
	}
	
	public ArrayList<String> getColumnValueStringList(){
		ArrayList<String> response = new ArrayList<>();
		if (inboundItemList == null || inboundItemList.isEmpty()) {
			response.add(
					sellerCode
					+ "\t" + shipmentId
					+ "\t" + shipmentName
					+ "\t" + destinationFulfillmentCenterId
					+ "\t" + (shipToAddress == null ? null :
						shipToAddress.getCountryCode() + " " + shipToAddress.getCity() + " " + shipToAddress.getAddressLine1())
					+ "\t" + (shipFromAddress == null ? null :
						shipFromAddress.getCountryCode() + " " + shipFromAddress.getCity() + " " + shipFromAddress.getAddressLine1())
					+ "\t" + labelPrepType
					+ "\t" + shipmentStatus
					+ "\t" + areCasesRequired
					+ "\t" + (inboundItemList == null || inboundItemList.isEmpty() ? null : 
						new InboundItem().getColumnValueString())
					+ "\t" + isShipmentCreated
					+ "\t" + requestId
					+ "\t" + requestTimestamp);
		} else {
			for (InboundItem ii : inboundItemList)
				response.add(
						sellerCode
						+ "\t" + shipmentId
						+ "\t" + shipmentName
						+ "\t" + destinationFulfillmentCenterId
						+ "\t" + (shipToAddress == null ? null :
							shipToAddress.getCountryCode() + " " + shipToAddress.getCity() + " " + shipToAddress.getAddressLine1())
						+ "\t" + (shipFromAddress == null ? null :
							shipFromAddress.getCountryCode() + " " + shipFromAddress.getCity() + " " + shipFromAddress.getAddressLine1())
						+ "\t" + labelPrepType
						+ "\t" + shipmentStatus
						+ "\t" + areCasesRequired
						+ "\t" + (inboundItemList == null || inboundItemList.isEmpty() ? null : 
							ii.getColumnValueString())
						+ "\t" + isShipmentCreated
						+ "\t" + requestId
						+ "\t" + requestTimestamp);
		}
		return response;
	}
	
	public void printColumnValue(int tabNum){
		String tabIndentation = "";
		for ( int i = 0; i < tabNum; i++ ) tabIndentation = tabIndentation + "\t";
		for ( String columnValueString : getColumnValueStringList() )
		System.out.println(tabIndentation
				+ columnValueString);
	}
	
	public void print(int tabNum){
		printColumnName(tabNum);
		printColumnValue(tabNum);
	}
	
}
