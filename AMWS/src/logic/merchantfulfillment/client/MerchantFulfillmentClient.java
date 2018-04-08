package logic.merchantfulfillment.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceAsyncClient;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceConfig;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CreateShipmentRequest;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CreateShipmentResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.GetEligibleShippingServicesRequest;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.GetEligibleShippingServicesResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ResponseHeaderMetadata;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ShippingService;

import amzint.MarketplaceWebServiceFactory;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import logic.merchantfulfillment.model.EligibleShippingServices;
import logic.merchantfulfillment.model.MerchantShipmentPackages;
import logic.merchantfulfillment.model.ShipmentPackage;

public class MerchantFulfillmentClient {
	
	// get array list of MerchantShipmentPackages
	public ArrayList<MerchantShipmentPackages> getMerchantShipmentPackagesList(){
		ArrayList<MerchantShipmentPackages> mspList = new ArrayList<MerchantShipmentPackages>();
		// TODO
		return mspList;
	}
	// --------------------------------------------------------------------------
	
	// get eligible shipping services
	public ShipmentPackage callAmazonGetEligibleShippingServices(ShipmentPackage sp) throws Exception{
		if (sp == null) return null;
		if (sp.sellerCode == null
				|| sp.sellerCode.isEmpty()
				|| sp.shipmentRequestDetails == null) {
			sp.isGetEligibleShippingServiceSuccess = false;
			return sp;
		}
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(sp.sellerCode);
		GetEligibleShippingServicesResponse response
			= new MWSMerchantFulfillmentServiceAsyncClient(
					merchant.getAccessKey(),
					merchant.getSecretKey(),
					new MarketplaceWebServiceFactory().getAppName(),
					new MarketplaceWebServiceFactory().getAppVersion(),
					new MWSMerchantFulfillmentServiceConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl()),
					null).getEligibleShippingServices(
							new GetEligibleShippingServicesRequest()
								.withSellerId(merchant.getId())
								.withMWSAuthToken(merchant.getAuthToken())
								.withShipmentRequestDetails(sp.shipmentRequestDetails));
		
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
	
	public MerchantShipmentPackages callAmazonGetEligibleShippingServices(MerchantShipmentPackages msp){
		if (msp == null) return null;
		if (msp.sellerCode == null
				|| msp.sellerCode.isEmpty()
				|| msp.shipmentPackageList == null
				|| msp.shipmentPackageList.isEmpty()) return msp;
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(msp.sellerCode);
		for (ShipmentPackage sp : msp.shipmentPackageList){
			if (sp == null) continue;
			if (sp.sellerCode == null
					|| sp.sellerCode.isEmpty()
					|| sp.shipmentRequestDetails == null) {
				sp.isGetEligibleShippingServiceSuccess = false;
				continue;
			}
			try {
				GetEligibleShippingServicesResponse response
					= new MWSMerchantFulfillmentServiceAsyncClient(
							merchant.getAccessKey(),
							merchant.getSecretKey(),
							new MarketplaceWebServiceFactory().getAppName(),
							new MarketplaceWebServiceFactory().getAppVersion(),
							new MWSMerchantFulfillmentServiceConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl()),
							null).getEligibleShippingServices(
									new GetEligibleShippingServicesRequest()
										.withSellerId(merchant.getId())
										.withMWSAuthToken(merchant.getAuthToken())
										.withShipmentRequestDetails(sp.shipmentRequestDetails));
				
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
	
	public ArrayList<MerchantShipmentPackages> callAmazonGetEligibleShippingServices(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = callAmazonGetEligibleShippingServices(msp);
		return mspList;
	}
	
	// get future of get eligible shipping service response
	public MerchantShipmentPackages callAmazonGetEligibleShippingServicesFuture(MerchantShipmentPackages msp){
		if (msp == null) return null;
		if (msp.sellerCode == null
				|| msp.sellerCode.isEmpty()
				|| msp.shipmentPackageList == null
				|| msp.shipmentPackageList.isEmpty()) return msp;
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(msp.sellerCode);
		for (ShipmentPackage sp : msp.shipmentPackageList){
			try {
				Future<GetEligibleShippingServicesResponse> future
					= new MWSMerchantFulfillmentServiceAsyncClient(
							merchant.getAccessKey(),
							merchant.getSecretKey(),
							new MarketplaceWebServiceFactory().getAppName(),
							new MarketplaceWebServiceFactory().getAppVersion(),
							new MWSMerchantFulfillmentServiceConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl()),
							null).getEligibleShippingServicesAsync(
									new GetEligibleShippingServicesRequest()
										.withSellerId(merchant.getId())
										.withMWSAuthToken(merchant.getAuthToken())
										.withShipmentRequestDetails(sp.shipmentRequestDetails));
				
				sp.getEligibleShippingServicesResponseFuture = future;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return msp;
	}
	
	public ArrayList<MerchantShipmentPackages> callAmazonGetEligibleShippingServicesFuture(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = callAmazonGetEligibleShippingServicesFuture(msp);
		return mspList;
	}
	
	// get response from future 
	public MerchantShipmentPackages getFromGetEligibleShippingServicesFuture(MerchantShipmentPackages msp){
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
	
	public ArrayList<MerchantShipmentPackages> getFromGetEligibleShippingServicesFuture(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = getFromGetEligibleShippingServicesFuture(msp);
		return mspList;
	}
	
	// ----------------------------------------------------------------------
	
	// crate shipment
	public ShipmentPackage callAmazonCreateShipment(ShipmentPackage sp){
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
		
		Integer index = sp.getShippingServiceIndex(null, "USPS First Class");
		if ( index == null ) index = sp.getShippingServiceIndex(null, "USPS Priority Mail");
		if ( index == null ) index = sp.getLowestPriceShippingServiceIndex();
		
		CreateShipmentResponse response
			= new MWSMerchantFulfillmentServiceAsyncClient(
					merchant.getAccessKey(),
					merchant.getSecretKey(),
					new MarketplaceWebServiceFactory().getAppName(),
					new MarketplaceWebServiceFactory().getAppVersion(),
					new MWSMerchantFulfillmentServiceConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl()),
					null).createShipment(
							new CreateShipmentRequest()
								.withSellerId(merchant.getId())
								.withMWSAuthToken(merchant.getAuthToken())
								.withShipmentRequestDetails(sp.shipmentRequestDetails)
								.withShippingServiceId(sp.eligibleShippingServiceList.get(index).shippingServiceId));
		
		sp.shipment = response.getCreateShipmentResult().getShipment();
		sp.createShipmentRequestId = response.getResponseMetadata().getRequestId();
		sp.createShipmentRequestDate = response.getResponseHeaderMetadata().getTimestamp();
		sp.isCreateShipmentSuccess = true;
		return sp;
	}
	
	public MerchantShipmentPackages callAmazonCreateShipment(MerchantShipmentPackages msp){
		if(msp == null) return null;
		if(msp.sellerCode == null
				|| msp.sellerCode.isEmpty()
				|| msp.shipmentPackageList == null
				|| msp.shipmentPackageList.isEmpty()) return msp;
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(msp.sellerCode);
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
				
				CreateShipmentResponse response
					= new MWSMerchantFulfillmentServiceAsyncClient(
							merchant.getAccessKey(),
							merchant.getSecretKey(),
							new MarketplaceWebServiceFactory().getAppName(),
							new MarketplaceWebServiceFactory().getAppVersion(),
							new MWSMerchantFulfillmentServiceConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl()),
							null).createShipment(
									new CreateShipmentRequest()
										.withSellerId(merchant.getId())
										.withMWSAuthToken(merchant.getAuthToken())
										.withShipmentRequestDetails(sp.shipmentRequestDetails)
										.withShippingServiceId(sp.eligibleShippingServiceList.get(index).shippingServiceId));
				
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
	
	public ArrayList<MerchantShipmentPackages> callAmazonCreateShipment(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = callAmazonCreateShipment(msp);
		return mspList;
	}

	// get future of create shipment response
	public MerchantShipmentPackages callAmazonCreateShipmentFuture(MerchantShipmentPackages msp){
		if(msp == null) return null;
		if(msp.sellerCode == null
				|| msp.sellerCode.isEmpty()
				|| msp.shipmentPackageList == null
				|| msp.shipmentPackageList.isEmpty()) return msp;
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(msp.sellerCode);
		for (ShipmentPackage sp : msp.shipmentPackageList){
			try {
				Integer index = sp.getShippingServiceIndex(null, "USPS First Class");
				if ( index == null ) index = sp.getShippingServiceIndex(null, "USPS Priority Mail");
				if ( index == null ) index = sp.getLowestPriceShippingServiceIndex();
				
				Future<CreateShipmentResponse> future
					= new MWSMerchantFulfillmentServiceAsyncClient(
							merchant.getAccessKey(),
							merchant.getSecretKey(),
							new MarketplaceWebServiceFactory().getAppName(),
							new MarketplaceWebServiceFactory().getAppVersion(),
							new MWSMerchantFulfillmentServiceConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl()),
							null).createShipmentAsync(
									new CreateShipmentRequest()
									.withSellerId(merchant.getId())
									.withMWSAuthToken(merchant.getAuthToken())
									.withShipmentRequestDetails(sp.shipmentRequestDetails)
									.withShippingServiceId(sp.eligibleShippingServiceList.get(index).shippingServiceId));
				
				sp.createShipmentResponseFuture = future;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return msp;
	}
	
	public ArrayList<MerchantShipmentPackages> callAmazonCreateShipmentFuture(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = callAmazonCreateShipmentFuture(msp);
		return mspList;
	}
	
	// get response from future
	public MerchantShipmentPackages getFromCreateShipmentFuture(MerchantShipmentPackages msp){
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
	
	public ArrayList<MerchantShipmentPackages> getFromCreateShipmentFuture(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = getFromCreateShipmentFuture(msp);
		return mspList;
	}
	
	// ------------------------------------------------------------------------------------------------

	public void generateFile(ArrayList<MerchantShipmentPackages> mspList, String fileDir){
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

	public void merchantFulfillment(String fileDir, String fileHistoryDir){
		ArrayList<MerchantShipmentPackages> mspList = getMerchantShipmentPackagesList();
		mspList = callAmazonGetEligibleShippingServices(mspList);
		mspList = callAmazonCreateShipment(mspList);
		generateFile(mspList, fileDir);
		callOSS(fileDir, fileHistoryDir);
	}
	
	public void merchantFulfillmentAsync(String fileDir, String fileHistoryDir){
		ArrayList<MerchantShipmentPackages> mspList = getMerchantShipmentPackagesList();
		mspList = getFromGetEligibleShippingServicesFuture(
					callAmazonGetEligibleShippingServicesFuture(mspList));
		mspList = getFromCreateShipmentFuture(
					callAmazonCreateShipmentFuture(mspList));
		generateFile(mspList, fileDir);
		callOSS(fileDir, fileHistoryDir);
	}
	
}
