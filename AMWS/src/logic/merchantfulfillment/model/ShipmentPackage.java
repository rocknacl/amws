package logic.merchantfulfillment.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.Future;

import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.CreateShipmentResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.GetEligibleShippingServicesResponse;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.Shipment;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.model.ShipmentRequestDetails;

/**
 * @Description This model used for merchant fulfillment only.
 * @author D16
 * @CreatedDate JUN 22, 2016
 * @LastUpdated JUN 22, 2016
 */
public class ShipmentPackage {

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
