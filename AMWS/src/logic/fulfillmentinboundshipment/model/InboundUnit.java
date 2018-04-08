package logic.fulfillmentinboundshipment.model;

import java.util.ArrayList;

import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.Address;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentPlanRequestItemList;

public class InboundUnit {

	public String sellerCode;
	public Address shipFromAddress;
	public String shipToCountryCode;
	public InboundShipmentPlanRequestItemList inboundShipmentPlanRequestItemList;
	
	public Boolean isShipmentPlanCreated;
	public String requestId;
	public String requestTimestamp;
	public ArrayList<InboundShipment> inboundShipmentList;
	
}
