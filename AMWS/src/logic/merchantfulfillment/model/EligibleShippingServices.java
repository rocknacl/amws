package logic.merchantfulfillment.model;

import java.math.BigDecimal;

/*
 * D16
 * eligible shipping services of package
 * Create Date: JUN 15, 2016
 * Last Update: JUN 15, 2016
 */

public class EligibleShippingServices {

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
