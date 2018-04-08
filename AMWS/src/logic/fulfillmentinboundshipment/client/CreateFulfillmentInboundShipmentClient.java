package logic.fulfillmentinboundshipment.client;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.Address;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.CreateInboundShipmentPlanResponse;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.CreateInboundShipmentResponse;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentHeader;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentItem;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentItemList;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentPlan;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentPlanItem;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.UpdateInboundShipmentResponse;

import amzint.fulfillmentinboundshipment.FulfillmentInboundShipmentClient;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import dao.entities.MerchantInboundAddress;
import logic.fulfillmentinboundshipment.model.InboundItem;
import logic.fulfillmentinboundshipment.model.InboundShipment;
import logic.fulfillmentinboundshipment.model.InboundUnit;

public class CreateFulfillmentInboundShipmentClient {

	public InboundUnit getInboundUnit(){
		InboundUnit iu = new InboundUnit();
		
		// TODO
		
		return iu;
	}
	
	public InboundUnit callAmazonCreatePlan(InboundUnit iu){
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(iu.sellerCode);
		FulfillmentInboundShipmentClient client = new FulfillmentInboundShipmentClient(merchant);
		
		CreateInboundShipmentPlanResponse response = client.createPlan(
				iu.shipFromAddress,
				iu.shipToCountryCode,
				iu.inboundShipmentPlanRequestItemList);
		iu.requestId = response.getResponseHeaderMetadata().getRequestId();
		iu.requestTimestamp = response.getResponseHeaderMetadata().getTimestamp();
		
		iu.inboundShipmentList = new ArrayList<InboundShipment>();
		for (InboundShipmentPlan isp : response.getCreateInboundShipmentPlanResult().getInboundShipmentPlans().getMember()) {
			InboundShipment is = new InboundShipment();
			is.sellerCode = iu.sellerCode;
			is.shipmentId = isp.getShipmentId();
			is.destinationFulfillmentCenterId = isp.getDestinationFulfillmentCenterId();
			is.shipToAddress = isp.getShipToAddress();
			is.labelPrepType = isp.getLabelPrepType();
			for (InboundShipmentPlanItem ispi : isp.getItems().getMember()) {
				InboundItem ii = new InboundItem();
				ii.sellerSKU = ispi.getSellerSKU();
				ii.fulfillmentNetworkSKU = ispi.getFulfillmentNetworkSKU();
				ii.quantityShipped = ispi.getQuantity();
				is.inboundItemList.add(ii);
			}
			iu.inboundShipmentList.add(is);
		}
		iu.isShipmentPlanCreated = true;
		return iu;
	}
	
	public InboundShipment callAmazonCreateShipment(InboundShipment is){
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(is.sellerCode);
		FulfillmentInboundShipmentClient client = new FulfillmentInboundShipmentClient(merchant);
		
		InboundShipmentHeader inboundShipmentHeader = new InboundShipmentHeader();
		inboundShipmentHeader.setDestinationFulfillmentCenterId( is.destinationFulfillmentCenterId );
		String shipmentName = merchant.getName() + "-" +  new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date());
		is.shipmentName = shipmentName;
		inboundShipmentHeader.setShipmentName( shipmentName );
		MerchantInboundAddress address = MerchantAccountDAO.getInBoundAddressByMerchantName(is.sellerCode);
		Address shipFromAddress = new Address()
				.withName(address.getName())
				.withAddressLine1(address.getAddressLine1())
				.withCity(address.getCity())
				.withCountryCode(address.getCountryCode());
		is.shipFromAddress = shipFromAddress;
		inboundShipmentHeader.setShipFromAddress( shipFromAddress );
		inboundShipmentHeader.setShipmentStatus("WORKING");
		is.shipmentStatus = "WORKING";
		inboundShipmentHeader.setLabelPrepPreference("SELLER_LABEL");
		is.labelPrepType = "SELLER_LABEL";
		
		InboundShipmentItemList inboundShipmentItems = new InboundShipmentItemList();
		List<InboundShipmentItem> member = new ArrayList<InboundShipmentItem>();
		for (InboundItem ii : is.inboundItemList) {
			InboundShipmentItem inboundShipmentItem = new InboundShipmentItem();
			inboundShipmentItem.setSellerSKU( ii.sellerSKU );
			inboundShipmentItem.setQuantityShipped( ii.quantityShipped );
			member.add(inboundShipmentItem);
		}
		inboundShipmentItems.setMember(member);
		
		CreateInboundShipmentResponse  response = client.createShipment(is.shipmentId, inboundShipmentHeader, inboundShipmentItems);
		is.requestId = response.getResponseHeaderMetadata().getRequestId();
		is.requestTimestamp = response.getResponseHeaderMetadata().getTimestamp();
		is.isShipmentCreated = true;
		return is;
	}
	
	public InboundUnit callAmazonCreateShipment(InboundUnit iu){
		for (InboundShipment is : iu.inboundShipmentList) {
			is = callAmazonCreateShipment(is);
		}
		return iu;
	}
	
	public UpdateInboundShipmentResponse callAmazonUpdateShipment(InboundShipment is){
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(is.sellerCode);
		FulfillmentInboundShipmentClient client = new FulfillmentInboundShipmentClient(merchant);
		
		InboundShipmentHeader inboundShipmentHeader = new InboundShipmentHeader();
		inboundShipmentHeader.setDestinationFulfillmentCenterId( is.destinationFulfillmentCenterId );
		inboundShipmentHeader.setShipmentName( is.shipmentName );
		MerchantInboundAddress address = MerchantAccountDAO.getInBoundAddressByMerchantName(is.sellerCode);
		Address shipFromAddress = new Address()
				.withName(address.getName())
				.withAddressLine1(address.getAddressLine1())
				.withCity(address.getCity())
				.withCountryCode(address.getCountryCode());
		inboundShipmentHeader.setShipFromAddress( shipFromAddress );
		inboundShipmentHeader.setShipmentStatus("WORKING");
		is.shipmentStatus = "WORKING";
		inboundShipmentHeader.setLabelPrepPreference(is.labelPrepType);
		
		InboundShipmentItemList inboundShipmentItems = new InboundShipmentItemList();
		List<InboundShipmentItem> member = new ArrayList<InboundShipmentItem>();
		for (InboundItem ii : is.inboundItemList) {
			InboundShipmentItem inboundShipmentItem = new InboundShipmentItem();
			inboundShipmentItem.setSellerSKU( ii.sellerSKU );
			inboundShipmentItem.setQuantityShipped( ii.quantityShipped );
			member.add(inboundShipmentItem);
		}
		inboundShipmentItems.setMember(member);
		
		UpdateInboundShipmentResponse response = client.updateShipment(is.shipmentId, inboundShipmentHeader, inboundShipmentItems);
		return response;
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
	
	public void CreateFulfillmentInboundShipment(String fileDir, String fileHistoryDir){
		InboundUnit iu = getInboundUnit();
		iu = callAmazonCreatePlan(iu);
		// TODO
		iu = callAmazonCreateShipment(iu);
		// TODO
		for (InboundShipment is : iu.inboundShipmentList) {
			callAmazonUpdateShipment(is);
		}
		generateFile(iu, fileDir);
		callOSS(fileDir, fileHistoryDir);
	}
}
