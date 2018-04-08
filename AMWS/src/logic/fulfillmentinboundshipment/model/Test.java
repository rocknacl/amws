package logic.fulfillmentinboundshipment.model;

import java.util.ArrayList;

public class Test {

	public static void test0(){
		InboundShipment is = new InboundShipment();
		is.sellerCode = "AA";
		is.shipmentId = "AA00s001";
		is.inboundItemList = new ArrayList<InboundItem>();
		
		InboundItem ii = new InboundItem();
		ii.sellerSKU = "AA001";
		ii.fulfillmentNetworkSKU = "ASFASFASF";
		ii.quantityShipped = 100;
		ii.quantityReceived = 100;
		ii.quantityInCases = 0;
		is.inboundItemList.add(ii);
		
		ii = new InboundItem();
		ii.sellerSKU = "AA002";
		ii.fulfillmentNetworkSKU = "ASFASFASA";
		ii.quantityShipped = 120;
		ii.quantityReceived = 110;
		ii.quantityInCases = 0;
		is.inboundItemList.add(ii);
		
		is.inboundItemList.add(new InboundItem());
		
//		is.printColumnName(0);
		is.print(0);
	}
	
	public static void main(String[] a){
		test0();
	}
}
