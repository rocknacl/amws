package logic.fulfillmentinboundshipment.client;

import java.io.File;
import java.util.ArrayList;

import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentInfo;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentsResponse;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ShipmentIdList;

import amzint.fulfillmentinboundshipment.FulfillmentInboundShipmentClient;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import logic.fulfillmentinboundshipment.model.InboundShipment;
import logic.fulfillmentinboundshipment.model.InboundUnit;

public class ListInboundShipmentClient {

	public InboundUnit getInboundUnit(){
		InboundUnit iu = new InboundUnit();
		
		// TODO
		
		return iu;
	}
	
	public InboundUnit callAmazon(InboundUnit iu){
		
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(iu.sellerCode);
		FulfillmentInboundShipmentClient client = new FulfillmentInboundShipmentClient(merchant);
		
		ShipmentIdList shipmentIdList = new ShipmentIdList();
		ArrayList<String> member = new ArrayList<String>();
		for (InboundShipment is : iu.inboundShipmentList) member.add(is.shipmentId);
		shipmentIdList.setMember(member);
		
		ListInboundShipmentsResponse response = client.listShipment(null, shipmentIdList, null, null);
		for (InboundShipmentInfo isi : response.getListInboundShipmentsResult().getShipmentData().getMember()) {
			for ( InboundShipment is : iu.inboundShipmentList ) {
				if ( is.shipmentId.equals(isi.getShipmentId()) ) {
					is.shipmentName = isi.getShipmentName();
					is.shipFromAddress = isi.getShipFromAddress();
					is.destinationFulfillmentCenterId = isi.getDestinationFulfillmentCenterId();
					is.shipmentStatus = isi.getShipmentStatus();
					is.labelPrepType = isi.getLabelPrepType();
					is.areCasesRequired = isi.getAreCasesRequired();
				}
			}
		}
		return iu;
	}
	
	public void generateFile(InboundUnit iu, String fileDir){
		// TODO
	}
	
	public void callOSS(String fileDir, String fileHistoryDir){
		File[] feedFileList = new File( fileDir ).listFiles();
		for (File file : feedFileList) {
			try {
				
				// upload to oss
				// TODO
				
				// move feed file --> feed file history
				file.renameTo(new File(fileHistoryDir + "/" + file.getName()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ListInboundShipment(String fileDir, String fileHistoryDir){
		InboundUnit iu = getInboundUnit();
		iu = callAmazon(iu);
		generateFile(iu, fileDir);
		callOSS(fileDir, fileHistoryDir);
	}
	
}
