package control.process;

import java.io.BufferedWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceAsyncClient;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceConfig;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.Address;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CreateShipmentRequest;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CreateShipmentResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.GetEligibleShippingServicesRequest;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.GetEligibleShippingServicesResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.Item;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.PackageDimensions;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ResponseHeaderMetadata;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.Shipment;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ShipmentRequestDetails;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ShippingService;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ShippingServiceOptions;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.Weight;

import amzint.MarketplaceWebServiceFactory;
import control.dataSynchronization.oss_api.models.MerchantFulfillmentRequest;
import control.dataSynchronization.oss_api.models.MerchantFulfillmentResponse;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import dao.entities.MerchantMfnAddress;
import model.basic.FileData;


/**
 * merchant fulfillment: this process receive a message(data to create merchant fulfillment shipments) object from oss,
 * and return a message(details of shipment created in this process) to oss.
 * 
 * @author D16
 * @note labelFileDir: save shipment label created in this process.
 * @note readMessageToOSSClassName/readMessageToOSSMethodName: this process will return a message to oss, 
 * 		which need to deal by readMessageToOSSClassName.readMessageToOSSMethodName.
 */
public class MerchantFulfillment {

	private String labelFileDir = "file/MerchantFulfillmentLabel";
	private String requestFileDir = "file/MerchantFulfillmentFile/Request";
	private String responseFileDir = "file/MerchantFulfillmentFile/Response";
	
	private String readMessageToOSSClassName = "control.dataSynchronization.DataSynchronizationOperationsForReflection";
	private String readMessageToOSSMethodName = "updateOrdersPrinted";
	
	private class EligibleShippingServices{
		public String carrierName;
		public String shippingServiceName;
		public String shippingServiceId;
		public String earliestEstimatedDeliveryDate;
		public String shipDate;
		public BigDecimal rateAmount;
		public String rateCurrencyCode;
		public String shippingServiceOfferId;
		
		public void printColumnValue(int tabNum, boolean isLineBreak) {
			String tabIndentation = "";
			for ( int i = 0; i < tabNum; i++ ) tabIndentation = tabIndentation + "\t";
			
			System.out.print(tabIndentation
							+ carrierName
							+ "\t" + earliestEstimatedDeliveryDate
							+ "\t" + shipDate
							+ "\t" + shippingServiceOfferId
							+ "\t" + rateAmount
							+ "\t" + rateCurrencyCode
							+ "\t" + shippingServiceId
							+ "\t" + shippingServiceName);
			
			if (isLineBreak) System.out.println();
		}
		
		public void printColumnValue(int tabNum) {
			printColumnValue(tabNum, true);
		}
		
		public void printColumnName(int tabNum, boolean isLineBreak) {
			String tabIndentation = "";
			for ( int i = 0; i < tabNum; i++ ) tabIndentation = tabIndentation + "\t";
			
			System.out.print(tabIndentation
					+ "CarrierName"
					+ "\tEarliestEstimatedDeliveryDate"
					+ "\tShipDate"
					+ "\tShippingServiceOfferId"
					+ "\tRateAmount"
					+ "\tRateCurrencyCode"
					+ "\tShippingServiceId"
					+ "\tShippingServiceName");
			
			if (isLineBreak) System.out.println();
		}
		
		public void printColumnName(int tabNum){
			printColumnName(tabNum, true);
		}
		
		public void print(int tabNum) {
			String tabIndentation = "";
			for ( int i = 0; i < tabNum; i++ ) tabIndentation = tabIndentation + "\t";
			System.out.println(tabIndentation + "Eligible Shipping Service:");
			printColumnName(tabNum+1);
			printColumnValue(tabNum+1);
		}
	}

	private class ShipmentPackage{
		public String sellerCode;
		public String amazonOrderId;
		public String packageId;
		public ShipmentRequestDetails shipmentRequestDetails;
		
		// get eligible shipping services
		public Future<GetEligibleShippingServicesResponse> getEligibleShippingServicesResponseFuture;
		
		public Boolean isGetEligibleShippingServiceSuccess;
		
		public String getEligibleShippingServiceRequestId;
		public String getEligibleShippingServiceRequestDate;
		public ArrayList<EligibleShippingServices> eligibleShippingServiceList;
		
		// create shipment
		public BigDecimal rateAmount;
		public String rateCurrencyCode;
		public String shippingServiceName;
		public String shippingServiceId;
		public Future<CreateShipmentResponse> createShipmentResponseFuture;
		
		public Boolean isCreateShipmentSuccess;
		
		public String createShipmentRequestId;
		public String createShipmentRequestDate;
		public Shipment shipment;
		
		// ---------------------------------------------------------------------------------------------------
		
		public Integer getLowestPriceShippingServiceIndex(){
			if ( eligibleShippingServiceList == null || eligibleShippingServiceList.isEmpty() ) return null;
			Integer index = 0;
			BigDecimal lowestRateAmount = eligibleShippingServiceList.get(0).rateAmount;
			for (int i=1; i<eligibleShippingServiceList.size(); i++) {
				if ( eligibleShippingServiceList.get(i).rateAmount.compareTo(lowestRateAmount) == -1 ) index = i;
			}
			return index;
		}
		
		/**
		 * @param carrierName
		 * 			null is allowed which means carrierName is not a filter criteria.
		 * @param shippingServiceName
		 * 			null is allowed which means shippingServiceName is not a filter criteria.
		 * @return the first matched index
		 */
		public Integer getShippingServiceIndex(String carrierName, String shippingServiceName){
			if ( eligibleShippingServiceList == null ) return null;
			for ( Integer i=1; i<eligibleShippingServiceList.size(); i++ ) {
				if ( (carrierName == null || eligibleShippingServiceList.get(i).carrierName.equals(carrierName))
						&& (shippingServiceName == null || eligibleShippingServiceList.get(i).shippingServiceName.equals(shippingServiceName)) )
					return i;
			}
			return null;
		}
		
		/**
		 * @param carrierName
		 * 			null is allowed which means carrierName is not a filter criteria.
		 * @param shippingServiceName
		 * 			null is allowed which means shippingServiceName is not a filter criteria.
		 * @return the array list of matched index
		 */
		public ArrayList<Integer> getShippingServiceIndexList(String carrierName, String shippingServiceName){
			ArrayList<Integer> indexList = new ArrayList<Integer>();
			if ( eligibleShippingServiceList == null ) return null;
			for ( Integer i=1; i<eligibleShippingServiceList.size(); i++ ) {
				if ( (carrierName == null || eligibleShippingServiceList.get(i).carrierName.equals(carrierName))
						&& (shippingServiceName == null || eligibleShippingServiceList.get(i).shippingServiceName.equals(shippingServiceName)) )
					indexList.add(i);
			}
			return indexList;
		}
	}

	private class MerchantShipmentPackages{
		public String sellerCode;
		public ArrayList<ShipmentPackage> shipmentPackageList;
	}
	
	// --------------------------------------------------------------------------------
	
	// get merchant shipment packages
	private ArrayList<MerchantShipmentPackages> readOSSData(ArrayList<MerchantFulfillmentRequest> mfrdList) {
		System.out.println("readOSSData");
		ArrayList<MerchantShipmentPackages> mspList = new ArrayList<MerchantShipmentPackages>();
		
		if (mfrdList == null ){
			System.out.println("message(order to print) is null");
			return mspList;
		} else if(mfrdList.isEmpty()) {
			System.out.println("message(order to print) is empty");
			return mspList;
		}
		
		try {
			writeRequest(mfrdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (MerchantFulfillmentRequest mfrd : mfrdList) {
			System.out.println(mfrd.getSellerCode()
					+ "\t" + mfrd.getPackageId()
					+ "\t" + mfrd.getOrderId()
					+ "\t" + mfrd.getOrderItemId()
					+ "\t" + mfrd.getQuantityToShip()
					+ "\t" + mfrd.getLengthValue()
					+ "\t" + mfrd.getWidthValue()
					+ "\t" + mfrd.getHeigthValue()
					+ "\t" + mfrd.getDimensionUnit()
					+ "\t" + mfrd.getWeightValue()
					+ "\t" + mfrd.getWeightUnit());
			try {
				Item item = new Item();
				item.setOrderItemId(mfrd.getOrderItemId());
				item.setQuantity(mfrd.getQuantityToShip());
				ArrayList<Item> ial = new ArrayList<Item>();
				ial.add(item);
				ShipmentPackage sp = new ShipmentPackage();
				sp.sellerCode = mfrd.getSellerCode();
				sp.amazonOrderId = mfrd.getOrderId();
				sp.packageId = mfrd.getPackageId();
//				MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(sp.sellerCode);
				MerchantMfnAddress mma = MerchantAccountDAO.getMfnAddressByMerchantName(sp.sellerCode);
				sp.shipmentRequestDetails = new ShipmentRequestDetails()
												.withAmazonOrderId(mfrd.getOrderId())
												.withShippingServiceOptions(new ShippingServiceOptions()
																				.withCarrierWillPickUp(false)
																				.withDeliveryExperience("DeliveryConfirmationWithoutSignature"))
												.withWeight(new Weight()
																.withUnit(mfrd.getWeightUnit())
																.withValue(mfrd.getWeightValue()))
												.withPackageDimensions(new PackageDimensions()
																			.withLength(mfrd.getLengthValue())
																			.withWidth(mfrd.getWidthValue())
																			.withHeight(mfrd.getHeigthValue())
																			.withUnit(mfrd.getDimensionUnit()))
												.withShipFromAddress(new Address()
																		.withName(mma.getName())
																		.withAddressLine1(mma.getAddressLine1())
																		.withAddressLine2(mma.getAddressLine2())
																		.withEmail(mma.getEmail())
																		.withCity(mma.getCity())
																		.withCountryCode(mma.getCountryCode())
																		.withPostalCode(mma.getPostalCode())
																		.withPhone(mma.getPhone())
																		.withStateOrProvinceCode(mma.getStateProvinceCode()));
				sp.shipmentRequestDetails.setItemList(ial);
				boolean isMerchantMatch = false;
				for ( MerchantShipmentPackages msp : mspList ) {
					if ( msp.sellerCode.equals(mfrd.getSellerCode()) ) {
						boolean isPackageMatch = false;
						for ( ShipmentPackage spg : msp.shipmentPackageList ){
							if (spg.packageId.equals(mfrd.getPackageId())) {
								spg.shipmentRequestDetails.getItemList().add(item);
								isPackageMatch = true;
								break;
							}
						}
						if (!isPackageMatch){
							msp.shipmentPackageList.add(sp);
						}
						isMerchantMatch = true;
						break;
					}
				}
				if (!isMerchantMatch) {
					MerchantShipmentPackages mp = new MerchantShipmentPackages();
					mp.sellerCode = mfrd.getSellerCode();
					mp.shipmentPackageList = new ArrayList<ShipmentPackage>();
					mp.shipmentPackageList.add(sp);
					mspList.add(mp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mspList;
	}
	
	private void writeRequest(ArrayList<MerchantFulfillmentRequest> mfrdList) throws Exception{
		if (mfrdList == null || mfrdList.isEmpty()) return;
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				requestFileDir + "/" 
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt")));
		pw.println("SellerCode"
				+ "\t" + "PackageId"
				+ "\t" + "OrderId"
				+ "\t" + "OrderItemId"
				+ "\t" + "QuantityToShip"
				+ "\t" + "Length"
				+ "\t" + "Width"
				+ "\t" + "Heigth"
				+ "\t" + "DimensionUnit"
				+ "\t" + "Weight"
				+ "\t" + "WeightUnit");
		for (MerchantFulfillmentRequest mfrd : mfrdList) {
			pw.println(mfrd.getSellerCode()
					+ "\t" + mfrd.getPackageId()
					+ "\t" + mfrd.getOrderId()
					+ "\t" + mfrd.getOrderItemId()
					+ "\t" + mfrd.getQuantityToShip()
					+ "\t" + mfrd.getLengthValue()
					+ "\t" + mfrd.getWidthValue()
					+ "\t" + mfrd.getHeigthValue()
					+ "\t" + mfrd.getDimensionUnit()
					+ "\t" + mfrd.getWeightValue()
					+ "\t" + mfrd.getWeightUnit());
		}
		pw.close();
	}
	
	// ----------------------------------------------------------------------------------
	
	// get eligible shipping services
	private ShipmentPackage callAmazonGetEligibleShippingServices(ShipmentPackage sp) throws Exception{
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
	
	private MerchantShipmentPackages callAmazonGetEligibleShippingServices(MerchantShipmentPackages msp){
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
							null
							).getEligibleShippingServices(
								new GetEligibleShippingServicesRequest()
									.withSellerId(merchant.getId())
									.withMWSAuthToken(merchant.getAuthToken())
									.withShipmentRequestDetails(sp.shipmentRequestDetails)
									);
				
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
	
	private ArrayList<MerchantShipmentPackages> callAmazonGetEligibleShippingServices(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = callAmazonGetEligibleShippingServices(msp);
		return mspList;
	}
	
	// get future of get eligible shipping service response
	private MerchantShipmentPackages callAmazonGetEligibleShippingServicesFuture(MerchantShipmentPackages msp){
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
	
	private ArrayList<MerchantShipmentPackages> callAmazonGetEligibleShippingServicesFuture(ArrayList<MerchantShipmentPackages> mspList){
		System.out.println("callAmazonGetEligibleShippingServicesFuture");
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = callAmazonGetEligibleShippingServicesFuture(msp);
		return mspList;
	}
	
	// get response from future 
	private MerchantShipmentPackages getFromGetEligibleShippingServicesFuture(MerchantShipmentPackages msp){
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
	
	private ArrayList<MerchantShipmentPackages> getFromGetEligibleShippingServicesFuture(ArrayList<MerchantShipmentPackages> mspList){
		System.out.println("getFromGetEligibleShippingServicesFuture");
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = getFromGetEligibleShippingServicesFuture(msp);
		return mspList;
	}
	
	// ----------------------------------------------------------------------
	
	// crate shipment
	private ShipmentPackage callAmazonCreateShipment(ShipmentPackage sp){
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
		sp.rateAmount = sp.eligibleShippingServiceList.get(index).rateAmount;
		sp.rateCurrencyCode = sp.eligibleShippingServiceList.get(index).rateCurrencyCode;
		sp.shippingServiceName = sp.eligibleShippingServiceList.get(index).shippingServiceName;
		sp.shippingServiceId = sp.eligibleShippingServiceList.get(index).shippingServiceId;
		sp.shipment = response.getCreateShipmentResult().getShipment();
		sp.createShipmentRequestId = response.getResponseMetadata().getRequestId();
		sp.createShipmentRequestDate = response.getResponseHeaderMetadata().getTimestamp();
		sp.isCreateShipmentSuccess = true;
		return sp;
	}
	
	private MerchantShipmentPackages callAmazonCreateShipment(MerchantShipmentPackages msp){
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
				sp.rateAmount = sp.eligibleShippingServiceList.get(index).rateAmount;
				sp.rateCurrencyCode = sp.eligibleShippingServiceList.get(index).rateCurrencyCode;
				sp.shippingServiceName = sp.eligibleShippingServiceList.get(index).shippingServiceName;
				sp.shippingServiceId = sp.eligibleShippingServiceList.get(index).shippingServiceId;
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
	
	private ArrayList<MerchantShipmentPackages> callAmazonCreateShipment(ArrayList<MerchantShipmentPackages> mspList){
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = callAmazonCreateShipment(msp);
		return mspList;
	}

	// get future of create shipment response
	private MerchantShipmentPackages callAmazonCreateShipmentFuture(MerchantShipmentPackages msp){
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
				sp.rateAmount = sp.eligibleShippingServiceList.get(index).rateAmount;
				sp.rateCurrencyCode = sp.eligibleShippingServiceList.get(index).rateCurrencyCode;
				sp.shippingServiceName = sp.eligibleShippingServiceList.get(index).shippingServiceName;
				sp.shippingServiceId = sp.eligibleShippingServiceList.get(index).shippingServiceId;
				sp.createShipmentResponseFuture = future;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return msp;
	}
	
	private ArrayList<MerchantShipmentPackages> callAmazonCreateShipmentFuture(ArrayList<MerchantShipmentPackages> mspList){
		System.out.println("callAmazonCreateShipmentFuture");
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = callAmazonCreateShipmentFuture(msp);
		return mspList;
	}
	
	// get response from future
	private MerchantShipmentPackages getFromCreateShipmentFuture(MerchantShipmentPackages msp){
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
	
	private ArrayList<MerchantShipmentPackages> getFromCreateShipmentFuture(ArrayList<MerchantShipmentPackages> mspList){
		System.out.println("getFromCreateShipmentFuture");
		if (mspList == null) return null;
		for (MerchantShipmentPackages msp : mspList) msp = getFromCreateShipmentFuture(msp);
		return mspList;
	}
	
	// ------------------------------------------------------------------------------------
	
	private String base64Encode(byte[] input) {
//		if ( input == null || input.length == 0 ) return null;
//		
//		String outputString = null;
//		try {
//			BASE64Encoder encoder = new BASE64Encoder();
//			outputString = encoder.encode(input);
//		} catch ( Exception e) {
//			e.printStackTrace();
//		}
//		return outputString;
		return null;
	}
	
	private byte[] base64Decode(String inputString) {
//		if ( inputString == null || inputString.length() == 0 ) return null;
//		
//		byte[] output = null;
//		try {
//			BASE64Decoder decoder = new BASE64Decoder();
//			output = decoder.decodeBuffer(inputString);
//		} catch ( Exception e) {
//			e.printStackTrace();
//		}
//		return output;
		return null;
	}
	
	private byte[] gzipCompress(byte[] input) {
		if ( input == null || input.length == 0 ) return null;
		
		byte[] output = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(input);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			GZIPOutputStream gzipos = new GZIPOutputStream(os);
			byte[] buf = new byte[1024];
			int n;
			while ((n = is.read(buf, 0, 1024)) >= 0) {
				gzipos.write(buf, 0, n);
			}
			gzipos.finish();
			gzipos.flush();
			gzipos.close();
			
			output = os.toByteArray();
			os.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return output;
	}
	
	private byte[] gzipUncompress(byte[] input) {
		if ( input == null || input.length == 0 ) return null;
		
		byte[] output = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(input);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			GZIPInputStream gzipis = new GZIPInputStream(is);
			byte[] buf = new byte[1024];
			int n;
			while ((n = gzipis.read(buf)) >= 0) {
				os.write(buf, 0, n);
			}
			os.flush();
			output = os.toByteArray();
			os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	private ArrayList<MerchantFulfillmentResponse> generateMerchantFulfillmentResponseDataList(
			ArrayList<MerchantShipmentPackages> mspList) throws Exception{
		ArrayList<MerchantFulfillmentResponse> mfrdList = new ArrayList<MerchantFulfillmentResponse>();
		if (mspList == null) return mfrdList;
		for (MerchantShipmentPackages msp : mspList) {
			if (msp == null) continue;
			for (ShipmentPackage sp : msp.shipmentPackageList) {
				if (sp == null) continue;
				try {
					MerchantFulfillmentResponse mfrd = new MerchantFulfillmentResponse();
					mfrd.setSellerCode(msp.sellerCode);
					mfrd.setPackageId(sp.packageId);
					mfrd.setShipmentId(sp.shipment.getShipmentId());
					mfrd.setOrderId(sp.amazonOrderId);
					mfrd.setRateAmount(sp.rateAmount);
					mfrd.setRateCurrencyCode(sp.rateCurrencyCode);
					mfrd.setShippingServiceId(sp.shippingServiceId);
					mfrd.setShippingServiceName(sp.shippingServiceName);
					mfrd.setTrackingId(sp.shipment.getTrackingId());
					mfrd.setStatus(sp.shipment.getStatus());
					String labelFilePath = labelFileDir + "/"
									+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
									+ "_" + sp.packageId + "_" + mfrd.getShipmentId() + ".png";
					if (sp.shipment != null){
						byte[] labelByteArray = gzipUncompress(base64Decode(sp.shipment.getLabel().getFileContents().getContents()));
						FileOutputStream fos = new FileOutputStream(labelFilePath);
						fos.write(labelByteArray);
						fos.close();
						mfrd.setLabelFile(new FileData(labelFilePath));
					}
					mfrdList.add(mfrd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return mfrdList;
	}
	
	private void writeResponse(ArrayList<MerchantFulfillmentResponse> mfrdList) throws Exception{
		if (mfrdList == null || mfrdList.isEmpty()) return;
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				responseFileDir + "/" 
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt")));
		pw.println("OrderId"
				+ "\t" + "PackageId"
				+ "\t" + "SellerCode"
				+ "\t" + "ShipmentId"
				+ "\t" + "ShippingServiceId"
				+ "\t" + "ShippingServiceName"
				+ "\t" + "Status"
				+ "\t" + "TrackingId"
				+ "\t" + "RateAmount"
				+ "\t" + "RateCurrencyCode");
		for (MerchantFulfillmentResponse x : mfrdList) {
			pw.println(
					x.getOrderId()
					+ "\t" + x.getPackageId()
					+ "\t" + x.getSellerCode()
					+ "\t" + x.getShipmentId()
					+ "\t" + x.getShippingServiceId()
					+ "\t" + x.getShippingServiceName()
					+ "\t" + x.getStatus()
					+ "\t" + x.getTrackingId()
					+ "\t" + x.getRateAmount()
					+ "\t" + x.getRateCurrencyCode()
					);
		}
		pw.close();
	}
	
	// ------------------------------------------------------------------------------------
	
	private Message process(Message messageFromOSS, String readMessageToOSSClassName, String readMessageToOSSMethodName, boolean isAsync) throws Exception {
		ArrayList<MerchantShipmentPackages> mspList
			= isAsync ? getFromCreateShipmentFuture(
							callAmazonCreateShipmentFuture(
								getFromGetEligibleShippingServicesFuture(
										callAmazonGetEligibleShippingServicesFuture(
												readOSSData((ArrayList<MerchantFulfillmentRequest>) messageFromOSS.getData()))))) :
						callAmazonCreateShipment(
								callAmazonGetEligibleShippingServices(
										readOSSData((ArrayList<MerchantFulfillmentRequest>) messageFromOSS.getData())));
		ArrayList<MerchantFulfillmentResponse> mfrdList = generateMerchantFulfillmentResponseDataList(mspList);
		System.out.println("MerchantFulfillment: return message");
		if (mfrdList != null && !mfrdList.isEmpty()){
			for (MerchantFulfillmentResponse x : mfrdList)
				System.out.println(x.getOrderId()
						+ "\t" + x.getPackageId()
						+ "\t" + x.getSellerCode()
						+ "\t" + x.getShipmentId()
						+ "\t" + x.getShippingServiceId()
						+ "\t" + x.getShippingServiceName()
						+ "\t" + x.getStatus()
						+ "\t" + x.getTrackingId()
						+ "\t" + x.getRateAmount()
						+ "\t" + x.getRateCurrencyCode());
		} else {
			System.out.println("response to oss: mfrdList is null/empty");
		}
		
		try {
			writeResponse(mfrdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Message(readMessageToOSSClassName,readMessageToOSSMethodName,mfrdList);
	}
	
	public Message process(Message message, boolean isAsync) throws Exception {
		return process(message,readMessageToOSSClassName,readMessageToOSSMethodName, isAsync);
	}

	public Message process(Message message, String readMessageToOSSClassName, String readMessageToOSSMethodName) throws Exception {
		return process(message,readMessageToOSSClassName,readMessageToOSSMethodName, true);
	}
	
	public Message process(Message message) throws Exception {
//		ArrayList<MerchantFulfillmentRequest> list = (ArrayList<MerchantFulfillmentRequest>) message.getData();
//		if (list == null) {
//			System.out.println("request is null");
//			return null;
//		}
//		for (MerchantFulfillmentRequest r : list){
//			System.out.println(r.getOrderId()
//					+ "\t" + r.getSellerCode()
//					+ "\t" + r.getOrderId()
//					+ "\t" + r.getOrderItemId()
//					+ "\t" + r.getPackageId()
//					+ "\t" + r.getQuantityToShip()
//					+ "\t" + r.getLengthValue()
//					+ "\t" + r.getWidthValue()
//					+ "\t" + r.getHeigthValue()
//					+ "\t" + r.getDimensionUnit()
//					+ "\t" + r.getWeightValue()
//					+ "\t" + r.getWeightUnit());
//		}
//		ArrayList<MerchantShipmentPackages> list1 = readOSSData(list);
//		for (MerchantShipmentPackages msp : list1){
//			if ( msp ==null || msp .shipmentPackageList == null) continue;
//			for (ShipmentPackage sp : msp .shipmentPackageList) {
//				if (sp == null) continue;
//				System.out.println(
//						sp.amazonOrderId
//						+ "\t" + sp.packageId
//						+ "\t" + sp.sellerCode);
//			}
//		}
//		return null;
		System.out.println("MerchantFulfillment.process start...");
		return process(message,readMessageToOSSClassName,readMessageToOSSMethodName,true);
	}
	
}
