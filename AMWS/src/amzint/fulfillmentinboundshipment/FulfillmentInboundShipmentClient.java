package amzint.fulfillmentinboundshipment;

import java.util.concurrent.Future;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.Address;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.CreateInboundShipmentPlanRequest;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.CreateInboundShipmentPlanResponse;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.CreateInboundShipmentRequest;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.CreateInboundShipmentResponse;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentHeader;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentItemList;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentPlanRequestItemList;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentItemsRequest;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentItemsResponse;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentsRequest;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ListInboundShipmentsResponse;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ShipmentIdList;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.ShipmentStatusList;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.UpdateInboundShipmentRequest;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.UpdateInboundShipmentResponse;

import amzint.AbstractFBAInboundServiceMWSAsyncClient;
import dao.entities.MerchantAccount;

/**
 * @Description Fulfillment in bound shipment client
 * @author D16
 * @CreatedDate JUN 22, 2016
 * @LastUpdated JUN 22, 2016
 */
public class FulfillmentInboundShipmentClient extends AbstractFBAInboundServiceMWSAsyncClient{

	public FulfillmentInboundShipmentClient(MerchantAccount merchant) {
		super(merchant);
	}

	// create plan
	public CreateInboundShipmentPlanResponse createPlan(
			Address shipFromAddress,
			String shipToCountryCode,
			InboundShipmentPlanRequestItemList inboundShipmentPlanRequestItems){
		CreateInboundShipmentPlanRequest request = new CreateInboundShipmentPlanRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipFromAddress( shipFromAddress );
		request.setShipToCountryCode( shipToCountryCode );
		request.setInboundShipmentPlanRequestItems( inboundShipmentPlanRequestItems );
		return client.createInboundShipmentPlan(request);
	}
	
	public Future<CreateInboundShipmentPlanResponse> createPlanAsync(
			Address shipFromAddress,
			String shipToCountryCode,
			InboundShipmentPlanRequestItemList inboundShipmentPlanRequestItems){
		CreateInboundShipmentPlanRequest request = new CreateInboundShipmentPlanRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipFromAddress( shipFromAddress );
		request.setShipToCountryCode( shipToCountryCode );
		request.setInboundShipmentPlanRequestItems( inboundShipmentPlanRequestItems );
		return client.createInboundShipmentPlanAsync(request);
	}
	
	// create shipment
	public CreateInboundShipmentResponse createShipment(
			String shipmentId,
			InboundShipmentHeader inboundShipmentHeader,
			InboundShipmentItemList inboundShipmentItems){
		CreateInboundShipmentRequest request = new CreateInboundShipmentRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipmentId(shipmentId);
		request.setInboundShipmentHeader(inboundShipmentHeader);
		request.setInboundShipmentItems(inboundShipmentItems);
		return client.createInboundShipment(request);
	}
	
	public Future<CreateInboundShipmentResponse> createShipmentAsync(
			String shipmentId,
			InboundShipmentHeader inboundShipmentHeader,
			InboundShipmentItemList inboundShipmentItems){
		CreateInboundShipmentRequest request = new CreateInboundShipmentRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipmentId(shipmentId);
		request.setInboundShipmentHeader(inboundShipmentHeader);
		request.setInboundShipmentItems(inboundShipmentItems);
		return client.createInboundShipmentAsync(request);
	}
	
	// update shipment
	public UpdateInboundShipmentResponse updateShipment(
			String shipmentId,
			InboundShipmentHeader inboundShipmentHeader,
			InboundShipmentItemList inboundShipmentItems){
		UpdateInboundShipmentRequest request = new UpdateInboundShipmentRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipmentId(shipmentId);
		request.setInboundShipmentHeader(inboundShipmentHeader);
		request.setInboundShipmentItems(inboundShipmentItems);
		return client.updateInboundShipment(request);
	}
	
	public Future<UpdateInboundShipmentResponse> updateShipmentAsync(
			String shipmentId,
			InboundShipmentHeader inboundShipmentHeader,
			InboundShipmentItemList inboundShipmentItems){
		UpdateInboundShipmentRequest request = new UpdateInboundShipmentRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipmentId(shipmentId);
		request.setInboundShipmentHeader(inboundShipmentHeader);
		request.setInboundShipmentItems(inboundShipmentItems);
		return client.updateInboundShipmentAsync(request);
	}
	
	// list shipment
	/**
	 * @param shipmentStatusList
	 * 			Required if shipmentIdList is null, If both ShipmentStatusList and ShipmentIdList are specified,
	 * 			only shipments that match both parameters are returned.
	 * @param shipmentIdList
	 * 			Required if shipmentStatusList is null, If both ShipmentStatusList and ShipmentIdList are specified,
	 * 			only shipments that match both parameters are returned.
	 * @param lastUpdatedBefore
	 * 			Works only if lastUpdatedAfter is specified.
	 * @param lastUpdatedAfter
	 * 			Works only if lastUpdatedBefore is specified.
	 */
	public ListInboundShipmentsResponse listShipment(
			ShipmentStatusList shipmentStatusList,
			ShipmentIdList shipmentIdList,
			XMLGregorianCalendar lastUpdatedBefore,
			XMLGregorianCalendar lastUpdatedAfter){
		ListInboundShipmentsRequest request = new ListInboundShipmentsRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		if ( shipmentStatusList != null && shipmentStatusList.isSetMember() ) {
			request.setShipmentStatusList(shipmentStatusList);
		} else if ( shipmentIdList != null && shipmentIdList.isSetMember() ) {
			request.setShipmentIdList(shipmentIdList);
		}
        if ( lastUpdatedBefore != null && lastUpdatedAfter!= null ) {
        	request.setLastUpdatedBefore(lastUpdatedBefore);
        	request.setLastUpdatedAfter(lastUpdatedAfter);
        }
		return client.listInboundShipments(request);
	}
	
	// list shipment items
	/**
	 * @param shipmentId
	 * 			Required if lastUpdatedBefore and lastUpdatedAfter are not specified.
	 * @param lastUpdatedBefore
	 * 			Works only if lastUpdatedAfter is specified.
	 * @param lastUpdatedAfter
	 * 			Works only if lastUpdatedBefore is specified.
	 */
	public ListInboundShipmentItemsResponse listShipmentItems(
			String shipmentId,
			XMLGregorianCalendar lastUpdatedBefore,
			XMLGregorianCalendar lastUpdatedAfter){
		ListInboundShipmentItemsRequest request = new ListInboundShipmentItemsRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		if ( shipmentId != null && !shipmentId.isEmpty() ) request.setShipmentId(shipmentId);
        if ( lastUpdatedBefore != null && lastUpdatedAfter != null ) {
            request.setLastUpdatedBefore(lastUpdatedBefore);
            request.setLastUpdatedAfter(lastUpdatedAfter);
        }
        return client.listInboundShipmentItems(request);
	}

}
