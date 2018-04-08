package logic.merchantfulfillment.client;

import java.util.ArrayList;
import java.util.concurrent.Future;

import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CreateShipmentResponse;

import amzint.merchantfulfillment.MerchantFulfillmentClient;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import logic.merchantfulfillment.model.MerchantShipmentPackages;
import logic.merchantfulfillment.model.ShipmentPackage;

public class CreateShipmentClient {

	// crate shipment
	public ShipmentPackage createShipment(ShipmentPackage sp){
		if (sp == null) return sp;
		if (sp.sellerCode == null
				|| sp.sellerCode.isEmpty()
				|| sp.shipmentRequestDetails == null
				|| sp.eligibleShippingServiceList == null
				|| sp.eligibleShippingServiceList.isEmpty()){
			sp.isCreateShipmentSuccess = false;
			return sp;
		}
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(sp.sellerCode);
		MerchantFulfillmentClient client = new MerchantFulfillmentClient(merchant);
		
		Integer index = sp.getShippingServiceIndex(null, "USPS First Class");
		if ( index == null ) index = sp.getShippingServiceIndex(null, "USPS Priority Mail");
		if ( index == null ) index = sp.getLowestPriceShippingServiceIndex();
		
		CreateShipmentResponse response = client.createShipment(
				sp.shipmentRequestDetails,
				sp.eligibleShippingServiceList.get(index).shippingServiceId,
				null);
		
		sp.shipment = response.getCreateShipmentResult().getShipment();
		sp.createShipmentRequestId = response.getResponseMetadata().getRequestId();
		sp.createShipmentRequestDate = response.getResponseHeaderMetadata().getTimestamp();
		sp.isCreateShipmentSuccess = true;
		return sp;
	}
	
	public MerchantShipmentPackages createShipment(MerchantShipmentPackages msp){
		if(msp == null) return null;
		if(msp.sellerCode == null
				|| msp.sellerCode.isEmpty()
				|| msp.shipmentPackageList == null
				|| msp.shipmentPackageList.isEmpty()) return msp;
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(msp.sellerCode);
		MerchantFulfillmentClient client = new MerchantFulfillmentClient(merchant);
		for (ShipmentPackage sp : msp.shipmentPackageList){
			if (sp == null) continue;
			if (sp.sellerCode == null
					|| sp.sellerCode.isEmpty()
					|| sp.shipmentRequestDetails == null
					|| sp.eligibleShippingServiceList == null
					|| sp.eligibleShippingServiceList.isEmpty()){
				sp.isCreateShipmentSuccess = false;
				continue;
			}
			try {
				Integer index = sp.getShippingServiceIndex(null, "USPS First Class");
				if ( index == null ) index = sp.getShippingServiceIndex(null, "USPS Priority Mail");
				if ( index == null ) index = sp.getLowestPriceShippingServiceIndex();
				
				CreateShipmentResponse response = client.createShipment(
						sp.shipmentRequestDetails,
						sp.eligibleShippingServiceList.get(index).shippingServiceId,
						null);
				sp.shipment = response.getCreateShipmentResult().getShipment();
				sp.createShipmentRequestId = response.getResponseMetadata().getRequestId();
				sp.createShipmentRequestDate = response.getResponseHeaderMetadata().getTimestamp();
				sp.isCreateShipmentSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msp;
	}
	
	public ArrayList<MerchantShipmentPackages> createShipment(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = createShipment(msp);
		return mspList;
	}

	// get future of create shipment response
	public MerchantShipmentPackages getFuture(MerchantShipmentPackages msp){
		if(msp == null) return null;
		if(msp.sellerCode == null
				|| msp.sellerCode.isEmpty()
				|| msp.shipmentPackageList == null
				|| msp.shipmentPackageList.isEmpty()) return msp;
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(msp.sellerCode);
		MerchantFulfillmentClient client = new MerchantFulfillmentClient(merchant);
		for (ShipmentPackage sp : msp.shipmentPackageList){
			try {
				Integer index = sp.getShippingServiceIndex(null, "USPS First Class");
				if ( index == null ) index = sp.getShippingServiceIndex(null, "USPS Priority Mail");
				if ( index == null ) index = sp.getLowestPriceShippingServiceIndex();
				
				Future<CreateShipmentResponse> future
					= client.createShipmentAsync(
							sp.shipmentRequestDetails,
							sp.eligibleShippingServiceList.get(index).shippingServiceId,
							null);
				sp.createShipmentResponseFuture = future;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return msp;
	}
	
	public ArrayList<MerchantShipmentPackages> getFuture(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = getFuture(msp);
		return mspList;
	}
	
	// get response from future
	public MerchantShipmentPackages getFromFuture(MerchantShipmentPackages msp){
		if (msp == null) return null;
		if (msp.shipmentPackageList == null
				|| msp.shipmentPackageList.isEmpty()) return msp;
		for (ShipmentPackage sp : msp.shipmentPackageList){
			try {
				CreateShipmentResponse response = sp.createShipmentResponseFuture.get();
				sp.shipment = response.getCreateShipmentResult().getShipment();
				sp.createShipmentRequestId = response.getResponseMetadata().getRequestId();
				sp.createShipmentRequestDate = response.getResponseHeaderMetadata().getTimestamp();
				sp.isCreateShipmentSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
				sp.isCreateShipmentSuccess = false;
			}
		}
		return msp;
	}
	
	public ArrayList<MerchantShipmentPackages> getFromFuture(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = getFromFuture(msp);
		return mspList;
	}
	
	// create shipment async
	public MerchantShipmentPackages createShipmentAsync(MerchantShipmentPackages msp){
		return getFromFuture(getFuture(msp));
	}
	
	public ArrayList<MerchantShipmentPackages> createShipmentAsync(ArrayList<MerchantShipmentPackages> mspList){
		return getFromFuture(getFuture(mspList));
	}
	
}
