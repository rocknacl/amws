package control.process;

import java.util.ArrayList;

import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.FBAInboundServiceMWSAsyncClient;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.FBAInboundServiceMWSConfig;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentInfo;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentItem;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentItemsRequest;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentItemsResponse;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentsRequest;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentsResponse;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ShipmentIdList;

import amzint.MarketplaceWebServiceFactory;
import control.dataSynchronization.oss_api.models.ListInboundShipmentItem;
import control.dataSynchronization.oss_api.models.ListInboundShipmentRequestMerchant;
import control.dataSynchronization.oss_api.models.ListInboundShipmentResponse;
import control.dataSynchronization.oss_api.models.ListInboundShipmentResponseMerchant;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;

public class ListInboudnShipment {

	private String readMessageToOSSClassName = "control.dataSynchronization.DataSynchronizationOperationsForReflection";// TODO
	private String readMessageToOSSMethodName = "updateShipmentStatus";// TODO
	
	private ArrayList<ListInboundShipmentResponseMerchant> readOSSData(ArrayList<ListInboundShipmentRequestMerchant> lisrdList){
		ArrayList<ListInboundShipmentResponseMerchant> lisrmdList = new ArrayList<ListInboundShipmentResponseMerchant>();
		for (ListInboundShipmentRequestMerchant lisrd : lisrdList) {
			lisrmdList.add(new ListInboundShipmentResponseMerchant(
								lisrd.getSellerCode(),
								lisrd.getShipmentIdList(),
								new ArrayList<ListInboundShipmentResponse>()));
		}
		return lisrmdList;
	}
	
	// -----------------------------------------------------------------------
	
	// list in bound shipment
	private ListInboundShipmentResponseMerchant callAmazonListInboundShipment(ListInboundShipmentResponseMerchant lisrmd){
		if (lisrmd.getLisrdList() == null) lisrmd.setLisrdList(new ArrayList<ListInboundShipmentResponse>());
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(lisrmd.getSellerCode());
		ListInboundShipmentsResponse response
			= new FBAInboundServiceMWSAsyncClient(
					merchant.getAccessKey(), 
					merchant.getSecretKey(),
					new MarketplaceWebServiceFactory().getAppName(), 
					new MarketplaceWebServiceFactory().getAppVersion(), 
					new FBAInboundServiceMWSConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl()), 
					null).listInboundShipments(new ListInboundShipmentsRequest()
													.withSellerId(merchant.getId())
													.withMWSAuthToken(merchant.getAuthToken())
													.withShipmentIdList(new ShipmentIdList(lisrmd.getShipmentIdList())));
		for (InboundShipmentInfo isi : response.getListInboundShipmentsResult().getShipmentData().getMember())
			lisrmd.addLisrdList(new ListInboundShipmentResponse(
										lisrmd.getSellerCode(),
										isi.getShipmentId(),
										isi.getShipmentName(),
										isi.getShipmentStatus(),
										isi.getDestinationFulfillmentCenterId(),
										isi.getLabelPrepType(),
										isi.getAreCasesRequired(),
										response.getResponseHeaderMetadata().getRequestId(),
										response.getResponseHeaderMetadata().getTimestamp(),
										null));
		return lisrmd;
	}
	
	private ArrayList<ListInboundShipmentResponseMerchant> callAmazonListInboundShipment(
			ArrayList<ListInboundShipmentResponseMerchant> lisrmdList){
		for (ListInboundShipmentResponseMerchant lisrmd : lisrmdList)
			try {lisrmd = callAmazonListInboundShipment(lisrmd);} catch (Exception e) {e.printStackTrace();}
		return lisrmdList;
	}

	// list in bound shipment item
	private ListInboundShipmentResponse callAmazonListInboundShipmentItem(ListInboundShipmentResponse lisrd){
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(lisrd.getSellerCode());
		ListInboundShipmentItemsResponse response
			= new FBAInboundServiceMWSAsyncClient(
					merchant.getAccessKey(), 
					merchant.getSecretKey(),
					new MarketplaceWebServiceFactory().getAppName(), 
					new MarketplaceWebServiceFactory().getAppVersion(), 
					new FBAInboundServiceMWSConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl()), 
					null).listInboundShipmentItems(new ListInboundShipmentItemsRequest()
														.withSellerId(merchant.getId())
														.withMWSAuthToken(merchant.getAuthToken())
														.withShipmentId(lisrd.getShipmentId()));
		for (InboundShipmentItem isi : response.getListInboundShipmentItemsResult().getItemData().getMember())
			lisrd.addLisiList(new ListInboundShipmentItem(
									lisrd.getSellerCode(),
									isi.getShipmentId(),
									isi.getSellerSKU(),
									isi.getFulfillmentNetworkSKU(),
									isi.getQuantityShipped(),
									isi.getQuantityReceived(),
									isi.getQuantityInCase(),
									response.getResponseHeaderMetadata().getRequestId(),
									response.getResponseHeaderMetadata().getTimestamp()));
		return lisrd;
	}
	
	private ListInboundShipmentResponseMerchant callAmazonListInboundShipmentItem(ListInboundShipmentResponseMerchant lisrmd){
		for (ListInboundShipmentResponse lisrd : lisrmd.getLisrdList())
			try {lisrd = callAmazonListInboundShipmentItem(lisrd);} catch (Exception e) {e.printStackTrace();}
		return lisrmd;
	}
	
	private ArrayList<ListInboundShipmentResponseMerchant> callAmazonListInboundShipmentItem(
			ArrayList<ListInboundShipmentResponseMerchant> lisrmdList){
		for (ListInboundShipmentResponseMerchant lisrmd : lisrmdList)
			try {lisrmd = callAmazonListInboundShipmentItem(lisrmd);} catch (Exception e) {e.printStackTrace();}
		return lisrmdList;
	}
	
	public Message process(Message message) throws Exception{
		ArrayList<ListInboundShipmentResponseMerchant> lisrmdList
			= callAmazonListInboundShipmentItem(
					callAmazonListInboundShipment(
							readOSSData(
									(ArrayList<ListInboundShipmentRequestMerchant>) message.getData())));
		return new Message(readMessageToOSSClassName,readMessageToOSSMethodName,lisrmdList);
	}
	
	public Message process(Message message, String readMessageToOSSClassName, String readMessageToOSSMethodName) throws Exception{
		ArrayList<ListInboundShipmentResponseMerchant> lisrmdList
			= callAmazonListInboundShipmentItem(
					callAmazonListInboundShipment(
							readOSSData(
									(ArrayList<ListInboundShipmentRequestMerchant>) message.getData())));
		return new Message(readMessageToOSSClassName,readMessageToOSSMethodName,lisrmdList);
	}
	
}
