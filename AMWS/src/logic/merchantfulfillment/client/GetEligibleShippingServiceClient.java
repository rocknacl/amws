package logic.merchantfulfillment.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.GetEligibleShippingServicesResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ResponseHeaderMetadata;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ShippingService;

import amzint.merchantfulfillment.MerchantFulfillmentClient;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import logic.merchantfulfillment.model.EligibleShippingServices;
import logic.merchantfulfillment.model.MerchantShipmentPackages;
import logic.merchantfulfillment.model.ShipmentPackage;

public class GetEligibleShippingServiceClient {
	
	// get eligible shipping services
	public ShipmentPackage getEligibleShippingServices(ShipmentPackage sp) throws Exception{
		if (sp == null) return null;
		if (sp.sellerCode == null
				|| sp.sellerCode.isEmpty()
				|| sp.shipmentRequestDetails == null) {
			sp.isGetEligibleShippingServiceSuccess = false;
			return sp;
		}
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(sp.sellerCode);
		MerchantFulfillmentClient client = new MerchantFulfillmentClient(merchant);
		GetEligibleShippingServicesResponse response = client.getEligibleShippingServices(sp.shipmentRequestDetails);
		ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
		List<ShippingService> shippingServiceList = response.getGetEligibleShippingServicesResult().getShippingServiceList();
		
		sp.getEligibleShippingServiceRequestId = rhmd.getRequestId();
		sp.getEligibleShippingServiceRequestDate = rhmd.getTimestamp();
		sp.eligibleShippingServiceList = new ArrayList<EligibleShippingServices>();
		for (ShippingService shippingService : shippingServiceList) {
			EligibleShippingServices eligibleShippingServices = new EligibleShippingServices();
			eligibleShippingServices.carrierName = shippingService.getCarrierName();
			eligibleShippingServices.shippingServiceName = shippingService.getShippingServiceName();
			eligibleShippingServices.shippingServiceId = shippingService.getShippingServiceId();
			eligibleShippingServices.earliestEstimatedDeliveryDate = shippingService.getEarliestEstimatedDeliveryDate().toString();
			eligibleShippingServices.shipDate = shippingService.getShipDate().toString();
			eligibleShippingServices.rateAmount = shippingService.getRate().getAmount();
			eligibleShippingServices.rateCurrencyCode = shippingService.getRate().getCurrencyCode();
			eligibleShippingServices.shippingServiceOfferId = shippingService.getShippingServiceOfferId();
			sp.eligibleShippingServiceList.add(eligibleShippingServices);
		}
		sp.isGetEligibleShippingServiceSuccess = true;
		return sp;
	}
	
	public MerchantShipmentPackages getEligibleShippingServices(MerchantShipmentPackages msp){
		if (msp == null) return null;
		if (msp.sellerCode == null
				|| msp.sellerCode.isEmpty()
				|| msp.shipmentPackageList == null
				|| msp.shipmentPackageList.isEmpty()) return msp;
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(msp.sellerCode);
		MerchantFulfillmentClient client = new MerchantFulfillmentClient(merchant);
		for (ShipmentPackage sp : msp.shipmentPackageList){
			if (sp == null) continue;
			if (sp.sellerCode == null
					|| sp.sellerCode.isEmpty()
					|| sp.shipmentRequestDetails == null) {
				sp.isGetEligibleShippingServiceSuccess = false;
				continue;
			}
			try {
				GetEligibleShippingServicesResponse response = client.getEligibleShippingServices(sp.shipmentRequestDetails);
				ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
				List<ShippingService> shippingServiceList = response.getGetEligibleShippingServicesResult().getShippingServiceList();
				
				sp.getEligibleShippingServiceRequestId = rhmd.getRequestId();
				sp.getEligibleShippingServiceRequestDate = rhmd.getTimestamp();
				sp.eligibleShippingServiceList = new ArrayList<EligibleShippingServices>();
				for (ShippingService shippingService : shippingServiceList) {
					EligibleShippingServices eligibleShippingServices = new EligibleShippingServices();
					eligibleShippingServices.carrierName = shippingService.getCarrierName();
					eligibleShippingServices.shippingServiceName = shippingService.getShippingServiceName();
					eligibleShippingServices.shippingServiceId = shippingService.getShippingServiceId();
					eligibleShippingServices.earliestEstimatedDeliveryDate = shippingService.getEarliestEstimatedDeliveryDate().toString();
					eligibleShippingServices.shipDate = shippingService.getShipDate().toString();
					eligibleShippingServices.rateAmount = shippingService.getRate().getAmount();
					eligibleShippingServices.rateCurrencyCode = shippingService.getRate().getCurrencyCode();
					eligibleShippingServices.shippingServiceOfferId = shippingService.getShippingServiceOfferId();
					sp.eligibleShippingServiceList.add(eligibleShippingServices);
				}
			} catch (Exception e) {
				e.printStackTrace();
				sp.isGetEligibleShippingServiceSuccess = false;
			}
		}
		return msp;
	}
	
	public ArrayList<MerchantShipmentPackages> getEligibleShippingServices(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = getEligibleShippingServices(msp);
		return mspList;
	}
	
	// get future of get eligible shipping service response
	public MerchantShipmentPackages getFuture(MerchantShipmentPackages msp){
		if (msp == null) return null;
		if (msp.sellerCode == null
				|| msp.sellerCode.isEmpty()
				|| msp.shipmentPackageList == null
				|| msp.shipmentPackageList.isEmpty()) return msp;
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(msp.sellerCode);
		MerchantFulfillmentClient client = new MerchantFulfillmentClient(merchant);
		for (ShipmentPackage sp : msp.shipmentPackageList){
			try {
				Future<GetEligibleShippingServicesResponse> future = client.getEligibleShippingServicesAsync(sp.shipmentRequestDetails);
				sp.getEligibleShippingServicesResponseFuture = future;
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
				GetEligibleShippingServicesResponse response = sp.getEligibleShippingServicesResponseFuture.get();
				ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
				List<ShippingService> shippingServiceList = response.getGetEligibleShippingServicesResult().getShippingServiceList();
				
				sp.getEligibleShippingServiceRequestId = rhmd.getRequestId();
				sp.getEligibleShippingServiceRequestDate = rhmd.getTimestamp();
				sp.eligibleShippingServiceList = new ArrayList<EligibleShippingServices>();
				
				for ( ShippingService shippingService : shippingServiceList ) {
					EligibleShippingServices eligibleShippingServices = new EligibleShippingServices();
					eligibleShippingServices.carrierName = shippingService.getCarrierName();
					eligibleShippingServices.shippingServiceName = shippingService.getShippingServiceName();
					eligibleShippingServices.shippingServiceId = shippingService.getShippingServiceId();
					eligibleShippingServices.earliestEstimatedDeliveryDate = shippingService.getEarliestEstimatedDeliveryDate().toString();
					eligibleShippingServices.shipDate = shippingService.getShipDate().toString();
					eligibleShippingServices.rateAmount = shippingService.getRate().getAmount();
					eligibleShippingServices.rateCurrencyCode = shippingService.getRate().getCurrencyCode();
					eligibleShippingServices.shippingServiceOfferId = shippingService.getShippingServiceOfferId();
					sp.eligibleShippingServiceList.add(eligibleShippingServices);
				}
				sp.isGetEligibleShippingServiceSuccess = true;
			} catch ( Exception e ) {
				e.printStackTrace();
				sp.isGetEligibleShippingServiceSuccess = false;
			}
		}
		return msp;
	}
	
	public ArrayList<MerchantShipmentPackages> getFromFuture(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = getFromFuture(msp);
		return mspList;
	}
	
	// get eligible shipping services async
	public MerchantShipmentPackages getEligibleShippingServicesAsync(MerchantShipmentPackages msp){
		return getFromFuture(getFuture(msp));
	}
	
	public ArrayList<MerchantShipmentPackages> getEligibleShippingServicesAsync(ArrayList<MerchantShipmentPackages> mspList){
		return getFromFuture(getFuture(mspList));
	}
	
}
