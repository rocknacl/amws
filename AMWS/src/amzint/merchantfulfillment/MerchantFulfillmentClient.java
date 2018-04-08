package amzint.merchantfulfillment;

import java.util.concurrent.Future;

import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CancelShipmentRequest;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CancelShipmentResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CreateShipmentRequest;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CreateShipmentResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.GetEligibleShippingServicesRequest;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.GetEligibleShippingServicesResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ShipmentRequestDetails;

import amzint.AbstractMWSMerchantFulfillmentServiceAsyncClient;
import dao.entities.MerchantAccount;

public class MerchantFulfillmentClient extends AbstractMWSMerchantFulfillmentServiceAsyncClient {

	public MerchantFulfillmentClient(MerchantAccount merchant) {
		super(merchant);
	}

	// get eligible shipping services
	public GetEligibleShippingServicesResponse getEligibleShippingServices(ShipmentRequestDetails shipmentRequestDetails){
		GetEligibleShippingServicesRequest request = new GetEligibleShippingServicesRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipmentRequestDetails(shipmentRequestDetails);
		return client.getEligibleShippingServices(request);
	}
	
	// get eligible shipping services async
	public Future<GetEligibleShippingServicesResponse> getEligibleShippingServicesAsync(ShipmentRequestDetails shipmentRequestDetails){
		GetEligibleShippingServicesRequest request = new GetEligibleShippingServicesRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipmentRequestDetails(shipmentRequestDetails);
		return client.getEligibleShippingServicesAsync(request);
	}
	
	// create shipment
	public CreateShipmentResponse createShipment(
			ShipmentRequestDetails shipmentRequestDetails,
			String shippingServiceId,
			String shippingServiceOfferId){
		CreateShipmentRequest request = new CreateShipmentRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipmentRequestDetails(shipmentRequestDetails);
		request.setShippingServiceId(shippingServiceId);
		if ( shippingServiceOfferId != null ) request.setShippingServiceOfferId(shippingServiceOfferId);
		return client.createShipment(request);
	}
	
	// create shipment async
	public Future<CreateShipmentResponse> createShipmentAsync(
			ShipmentRequestDetails shipmentRequestDetails,
			String shippingServiceId,
			String shippingServiceOfferId){
		CreateShipmentRequest request = new CreateShipmentRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipmentRequestDetails(shipmentRequestDetails);
		request.setShippingServiceId(shippingServiceId);
		if ( shippingServiceOfferId != null ) request.setShippingServiceOfferId(shippingServiceOfferId);
		return client.createShipmentAsync(request);
	}
	
	// cancel shipment
	public CancelShipmentResponse cancelShipment(String shipmentId){
		CancelShipmentRequest request = new CancelShipmentRequest();
		request.setSellerId(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setShipmentId(shipmentId);
		return client.cancelShipment(request);
	}
}
