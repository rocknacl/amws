package logic.fulfillmentinboundshipment.client;

import java.io.File;
import java.util.ArrayList;

import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentItem;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentItemsResponse;

import amzint.fulfillmentinboundshipment.FulfillmentInboundShipmentClient;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import logic.fulfillmentinboundshipment.model.InboundItem;
import logic.fulfillmentinboundshipment.model.InboundShipment;
import logic.fulfillmentinboundshipment.model.InboundUnit;

public class ListInboundShipmentItemClient {

	public InboundShipment getInboundShipment(){
		InboundShipment is = new InboundShipment();
		
		// TODO
		
		return is;
	}
	
	public InboundUnit getInboundUnit(){
		InboundUnit iu = new InboundUnit();
		
		// TODO
		
		return iu;
	}
	
	public InboundShipment callAmazon(InboundShipment is){
		
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(is.sellerCode);
		FulfillmentInboundShipmentClient client = new FulfillmentInboundShipmentClient(merchant);
		
		ListInboundShipmentItemsResponse  response = client.listShipmentItems(is.shipmentId, null, null);
		is.inboundItemList = new ArrayList<InboundItem>();
		for (InboundShipmentItem isi : response.getListInboundShipmentItemsResult().getItemData().getMember()) {
			InboundItem ii = new InboundItem();
			ii.sellerSKU = isi.getSellerSKU();
			ii.fulfillmentNetworkSKU = isi.getFulfillmentNetworkSKU();
			ii.quantityShipped = isi.getQuantityShipped();
			ii.quantityReceived = isi.getQuantityReceived();
			ii.quantityInCases = isi.getQuantityInCase();
			is.inboundItemList.add(ii);
		}
		return is;
		
	}

	public InboundUnit callAmazon(InboundUnit iu){
		
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(iu.sellerCode);
		FulfillmentInboundShipmentClient client = new FulfillmentInboundShipmentClient(merchant);
		
		for ( InboundShipment is : iu.inboundShipmentList ) {
			ListInboundShipmentItemsResponse  response = client.listShipmentItems(is.shipmentId, null, null);
			is.inboundItemList = new ArrayList<InboundItem>();
			for (InboundShipmentItem isi : response.getListInboundShipmentItemsResult().getItemData().getMember()) {
				InboundItem ii = new InboundItem();
				ii.sellerSKU = isi.getSellerSKU();
				ii.fulfillmentNetworkSKU = isi.getFulfillmentNetworkSKU();
				ii.quantityShipped = isi.getQuantityShipped();
				ii.quantityReceived = isi.getQuantityReceived();
				ii.quantityInCases = isi.getQuantityInCase();
				is.inboundItemList.add(ii);
			}
		}
		return iu;
	}

	public void generateFile(InboundShipment is, String fileDir){
		// TODO
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
	
	public void ListInboundShipmentItem(String fileDir, String fileHistoryDir){
		InboundUnit iu = getInboundUnit();
		iu = callAmazon(iu);
		generateFile(iu, fileDir);
		callOSS(fileDir, fileHistoryDir);
	}
}
