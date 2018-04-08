package model.report;

import dao.entities.ReportActiveListings;
import dao.entities.ReportAdsAutoTargeting;
import dao.entities.ReportAdsBidByKeyword;
import dao.entities.ReportAdsMega;
import dao.entities.ReportAdsOtherAsinWeekly;
import dao.entities.ReportAdsPerformanceByPlacement;
import dao.entities.ReportAdsProductPerformanceDaily;
import dao.entities.ReportCancelledListings;
import dao.entities.ReportFbaAmazonFulfilledInventory;
import dao.entities.ReportFbaAmazonFulfilledShipments;
import dao.entities.ReportFbaInventoryHealth;
import dao.entities.ReportFeedback;
import dao.entities.ReportInventory;
import dao.entities.ReportRequestedOrScheduledFlatFileOrder;
import dao.entities.ReportUnshippedOrders;
import logic.report.AdsPerformanceByPlacement_Preprocessor;
import logic.report.ReportPreprocessor;

public enum ReportTypeEnum{
	//Listings Reports
	Inventory_Report("_GET_FLAT_FILE_OPEN_LISTINGS_DATA_","ConverterProperties/Inventory.properties",ReportInventory.class,null,null), 
	Active_Listings_Report("_GET_MERCHANT_LISTINGS_DATA_","ConverterProperties/ActiveListings.properties",ReportActiveListings.class,"yyyy-MM-dd HH:mm:ss zzz",null), 
//	Open_Listings_Report("_GET_MERCHANT_LISTINGS_DATA_BACK_COMPAT_","",null,null),
//	Open_Listings_Report_Lite("_GET_MERCHANT_LISTINGS_DATA_LITE_","",null,null),
//	Open_Listings_Report_Liter("_GET_MERCHANT_LISTINGS_DATA_LITER_","",null,null),
	Canceled_Listings_Report("_GET_MERCHANT_CANCELLED_LISTINGS_DATA_","ConverterProperties/CancelledListings.properties",ReportCancelledListings.class,null,null),
//	Sold_Listings_Report("_GET_CONVERGED_FLAT_FILE_SOLD_LISTINGS_DATA_","",null,null),
//	Listing_Quality_and_Suppressed_Listing_Report("_GET_MERCHANT_LISTINGS_DEFECT_DATA_","",null,null),
	
	//Order Reports
	Unshipped_Orders_Report("_GET_FLAT_FILE_ACTIONABLE_ORDER_DATA_","ConverterProperties/UnshippedOrders.properties",ReportUnshippedOrders.class,"yyyy-MM-dd'T'HH:mm:ssX",null),
//	Scheduled_XML_Order_Report("_GET_ORDERS_DATA_","",null,null),
	Requested_or_Scheduled_Flat_File_Order_Report("_GET_FLAT_FILE_ORDERS_DATA_","ConverterProperties/ReportRequestedOrScheduledFlatFileOrder.properties",ReportRequestedOrScheduledFlatFileOrder.class,"yyyy-MM-dd'T'HH:mm:ssX",null),
//	Flat_File_Order_Report("_GET_CONVERGED_FLAT_FILE_ORDER_REPORT_DATA_","",null,null),

	//Order Tracking Reports
//	Flat_File_Orders_By_Last_Update_Report("_GET_FLAT_FILE_ALL_ORDERS_DATA_BY_LAST_UPDATE_","",null),
//	Flat_File_Orders_By_Order_Date_Report("_GET_FLAT_FILE_ALL_ORDERS_DATA_BY_ORDER_DATE_","",null),
//	XML_Orders_By_Last_Update_Report("_GET_XML_ALL_ORDERS_DATA_BY_LAST_UPDATE_","",null),
//	XML_Orders_By_Order_Date_Report("_GET_XML_ALL_ORDERS_DATA_BY_ORDER_DATE_","",null),
	
	//Pending Order Reports
//	Flat_File_Pending_Orders_Report("_GET_FLAT_FILE_PENDING_ORDERS_DATA_","",null),
//	XML_Pending_Orders_Report("_GET_PENDING_ORDERS_DATA_","",null),
//	Converged_Flat_File_Pending_Orders_Report("_GET_CONVERGED_FLAT_FILE_PENDING_ORDERS_DATA_","",null),
	
	//Settlement Reports
	Flat_File_Settlement_Report("_GET_V2_SETTLEMENT_REPORT_DATA_FLAT_FILE_","",null,null,null),
	XML_Settlement_Report("_GET_V2_SETTLEMENT_REPORT_DATA_XML_","",null,null,null),
	Flat_File_V2_Settlement_Report("_GET_V2_SETTLEMENT_REPORT_DATA_FLAT_FILE_V2_","",null,null,null),
	
	//Performance Reports
	Flat_File_Feedback_Report("_GET_SELLER_FEEDBACK_DATA_","ConverterProperties/Feedback.properties",ReportFeedback.class,"yyyy/MM/dd",null),
//	XML_Customer_Metrics_Report("_GET_V1_SELLER_PERFORMANCE_REPORT_","",null),
	//FBA by Reports
	  //FBA Sales Reports
	FBA_Amazon_Fulfilled_Shipments_Report("_GET_AMAZON_FULFILLED_SHIPMENTS_DATA_","ConverterProperties/FbaAmazonFulfilledShipments.properties",ReportFbaAmazonFulfilledShipments.class,"yyyy-MM-dd'T'HH:mm:ssX",null),
	Flat_File_All_Orders_Report_By_Last_Update("_GET_FLAT_FILE_ALL_ORDERS_DATA_BY_LAST_UPDATE_","",null,null,null),
	Flat_File_All_Orders_Report_By_Order_Date("_GET_FLAT_FILE_ALL_ORDERS_DATA_BY_ORDER_DATE_","",null,null,null),
	XML_All_Orders_Report_By_Last_Update("_GET_XML_ALL_ORDERS_DATA_BY_LAST_UPDATE_","",null,null,null),
	XML_All_Orders_Report_By_Order_Date("_GET_XML_ALL_ORDERS_DATA_BY_ORDER_DATE_","",null,null,null),
	FBA_Customer_Shipment_Sales_Report("_GET_FBA_FULFILLMENT_CUSTOMER_SHIPMENT_SALES_DATA_","",null,null,null),
	FBA_Promotions_Report("_GET_FBA_FULFILLMENT_CUSTOMER_SHIPMENT_PROMOTION_DATA_","",null,null,null),
	FBA_Customer_Taxes("_GET_FBA_FULFILLMENT_CUSTOMER_TAXES_DATA_","",null,null,null),
	
	  //FBA Inventory Reports
	FBA_Amazon_Fulfilled_Inventory_Report("_GET_AFN_INVENTORY_DATA_","ConverterProperties/FbaAmazonFulfilledInventory.properties",ReportFbaAmazonFulfilledInventory.class,null,null),
	FBA_Multi_Country_Inventory_Report("_GET_AFN_INVENTORY_DATA_BY_COUNTRY_","",null,null,null),
	FBA_Daily_Inventory_History_Report("_GET_FBA_FULFILLMENT_CURRENT_INVENTORY_DATA_","",null,null,null),
	FBA_Monthly_Inventory_History_Report("_GET_FBA_FULFILLMENT_MONTHLY_INVENTORY_DATA_","",null,null,null),
	FBA_Received_Inventory_Report("_GET_FBA_FULFILLMENT_INVENTORY_RECEIPTS_DATA_","",null,null,null),
	FBA_Reserved_Inventory_Report("_GET_RESERVED_INVENTORY_DATA_","",null,null,null),
	FBA_Inventory_Event_Detail_Report("_GET_FBA_FULFILLMENT_INVENTORY_SUMMARY_DATA_","",null,null,null),
	FBA_Inventory_Adjustments_Report("_GET_FBA_FULFILLMENT_INVENTORY_ADJUSTMENTS_DATA_","",null,null,null),
	FBA_Inventory_Health_Report("_GET_FBA_FULFILLMENT_INVENTORY_HEALTH_DATA_","ConverterProperties/FbaInventoryHealth.properties",ReportFbaInventoryHealth.class,"yyyy-MM-dd'T'HH:mm:ssX",null),
	FBA_Manage_Inventory("_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_","",null,null,null),
	FBA_Manage_Inventory_Archived(" _GET_FBA_MYI_ALL_INVENTORY_DATA_","",null,null,null),
	FBA_Cross_Border_Inventory_Movement_Report("_GET_FBA_FULFILLMENT_CROSS_BORDER_INVENTORY_MOVEMENT_DATA_","",null,null,null),
	FBA_Inbound_Performance_Report("_GET_FBA_FULFILLMENT_INBOUND_NONCOMPLIANCE_DATA_","",null,null,null),
	
	  //FBA Payments Report
	FBA_Fee_Preview_Report("_GET_FBA_ESTIMATED_FBA_FEES_TXT_DATA_","",null,null,null),
	FBA_Reimbursements_Report("_GET_FBA_REIMBURSEMENTS_DATA_","",null,null,null),
	
	  //FBA Customer Concessions Reports
	FBA_Returns_Report("_GET_FBA_FULFILLMENT_CUSTOMER_RETURNS_DATA_","",null,null,null),
	FBA_Replacements_Report("_GET_FBA_FULFILLMENT_CUSTOMER_SHIPMENT_REPLACEMENT_DATA_","",null,null,null),
	
	  //FBA Removals Report
	FBA_Recommended_Removal_Report("_GET_FBA_RECOMMENDED_REMOVAL_DATA_","",null,null,null),
	FBA_Removal_Order_Detail_Report("_GET_FBA_FULFILLMENT_REMOVAL_ORDER_DETAIL_DATA_","",null,null,null),
	FBA_Removal_Shipment_Detail_Report("_GET_FBA_FULFILLMENT_REMOVAL_SHIPMENT_DETAIL_DATA_","",null,null,null),
	
	  //Sales Tax Reports
//	Sales_Tax_Report("_GET_FLAT_FILE_SALES_TAX_DATA_","",null),
	
	//Amazon Webstore Reports
//	Webstore_Product_Catalog_Report("_GET_WEBSTORE_PRODUCT_CATALOG_","",null),
	
	//Browse Tree Reports
//	Browse_Tree_Report("_GET_XML_BROWSE_TREE_DATA_","",null),
	
	//Advertisement
	Ads_Product_Performance_Daily("_GET_PADS_PRODUCT_PERFORMANCE_OVER_TIME_DAILY_DATA_TSV_","ConverterProperties/ReportAdsProductPerformanceDaily.properties",ReportAdsProductPerformanceDaily.class,"yyyy-MM-dd HH:mm:ss zzz",null),
	Ads_Performance_By_Placement("_GET_SP_PERFORMANCE_BY_PLACEMENT_REPORT_","ConverterProperties/ReportAdsPerformanceByPlacement.properties",ReportAdsPerformanceByPlacement.class,"MM/dd/yyyy",AdsPerformanceByPlacement_Preprocessor.class),
	Ads_Other_ASIN_WEEKLY("_GET_SP_OTHER_ASIN_REPORT_WEEKLY_DATA_","ConverterProperties/ReportAdsOtherAsinWeekly.properties",ReportAdsOtherAsinWeekly.class,"yyyy-MM-dd zzz",null),
	Ads_Auto_Targeting("_GET_SP_AUTO_TARGETING_REPORT_","ConverterProperties/ReportAdsAutoTargeting.properties",ReportAdsAutoTargeting.class,"MM/dd/yyyy",null),
	Ads_Mega("_GET_SP_MEGA_REPORT_","ConverterProperties/ReportAdsMega.properties",ReportAdsMega.class,"MM/dd/yyyy",null),
	Ads_Bid_By_Keyword("_GET_SP_BID_BY_KEYWORD_REPORT_","ConverterProperties/ReportAdsBidByKeyword.properties",ReportAdsBidByKeyword.class,"yyyy-MM-dd zzz",null);
	public String value;
	public String propertiesFilePath;
	public String dateFormat;
	private Class<?> classCorrespond;
	private Class<? extends ReportPreprocessor> beforehandProcessor;
	public Class<?> getClassCorrespond() {
		return classCorrespond;
	}
	public void setClassCorrespond(Class<?> classCorrespond) {
		this.classCorrespond = classCorrespond;
	}
	
	private ReportTypeEnum(String val,String path,Class<?> c,String dateFormat,Class<? extends ReportPreprocessor> processor) {
		this.value = val;
		this.propertiesFilePath = path;
		this.classCorrespond = c;
		this.dateFormat = dateFormat;
		this.beforehandProcessor = processor;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public Class<? extends ReportPreprocessor> getBeforehandProcessor() {
		return beforehandProcessor;
	}
	public static ReportTypeEnum getEnumByValue(String reportType) {
		for(ReportTypeEnum e : ReportTypeEnum.values()){
			if(e.value.equals(reportType))
				return e;
		}
		return null;
	}

}
