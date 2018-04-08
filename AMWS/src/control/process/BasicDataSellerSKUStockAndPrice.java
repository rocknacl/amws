package control.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import control.dataSynchronization.oss_api.models.BasicDataSellerSKUFulfillmentChannelResponse;
import control.dataSynchronization.oss_api.models.BasicDataSellerSKUStockAndPriceResponse;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import helper.dao.ConnectionPool;

public class BasicDataSellerSKUStockAndPrice {
	
	private final String readMessageToOSSClassName = "control.dataSynchronization.DataSynchronizationOperationsForReflection";// TODO
	private final String readMessageToOSSMethodName = "updateFulfillmentChannel";// TODO
	
	private String tableName_1 = "report_fba_amazon_fulfilled_inventory"; // AFN Fulfillment Inventory
	private String columnName_1_1 = "Store"; // Seller Code('AA','UP','KO'...)
	private String columnName_1_2 = "Seller_SKU"; // Seller SKU
	private String columnName_1_3 = "Asin"; // ASIN
	private String columnName_1_4 = "Insert_Date"; // Inventory Date
	private String columnName_1_5 = "Quantity_Available"; // Quantity Available
	private String columnName_1_6 = "Warehouse_Condition_Code"; // Warehouse Condition Code
	
	private String tableName_2 = "report_inventory"; // Open Listing
	private String columnName_2_1 = "Store"; // Seller Code('AA','UP','KO'...)
	private String columnName_2_2 = "sku"; // Seller SKU
	private String columnName_2_3 = "Insert_Date"; // Open Listing Date
	private String columnName_2_4 = "asin"; // ASIN
	private String columnName_2_5 = "price"; // Price
	private String columnName_2_6 = "quantity"; // Quantity
	
	private class AFNInventory{
		public String sellerCode;
		public String sellerSKU;
		public String inventoryDate;
		public String asin;
		public Integer quantitySellable;
		public Integer quantityUnsellable;
		
		public AFNInventory(){
			
		}
		public AFNInventory(
				String sellerCode,
				String sellerSKU,
				String inventoryDate,
				String asin,
				Integer quantitySellable,
				Integer quantityUnsellable){
			this.sellerCode = sellerCode;
			this.sellerSKU = sellerSKU;
			this.inventoryDate = inventoryDate;
			this.asin = asin;
			this.quantitySellable = quantitySellable;
			this.quantityUnsellable = quantityUnsellable;
		}
	}
	
	private class OpenListing{
		public String sellerCode;
		public String sellerSKU;
		public String openListingDate;
		public String asin;
		public Double price;
		public Integer quantity;
		
		public OpenListing(){
			
		}
		public OpenListing(
				String sellerCode,
				String sellerSKU,
				String openListingDate,
				String asin,
				Double price,
				Integer quantity){
			this.sellerCode = sellerCode;
			this.sellerSKU = sellerSKU;
			this.openListingDate = openListingDate;
			this.asin = asin;
			this.price = price;
			this.quantity = quantity;
		}
	}
	
	private class OpenDate{
		public String sellerCode;
		public String sellerSKU;
		public String openDate;
		
		public OpenDate(){
			
		}
		public OpenDate(String sellerCode, String sellerSKU, String openDate) {
			super();
			this.sellerCode = sellerCode;
			this.sellerSKU = sellerSKU;
			this.openDate = openDate;
		}
		public void print(){
			System.out.println(sellerCode + "\t" + sellerSKU + "\t" + openDate);
		}
	}
	
 	public ArrayList<AFNInventory> getAFNInventory() throws Exception{
		ArrayList<AFNInventory> list = new ArrayList<AFNInventory>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(""
					+ " (select"
						+ " t2.store as store,"
						+ " t2.sku as sku,"
						+ " t2.date as date,"
						+ " t2.asin as asin,"
						+ " if(t2.quantitySellable is null,0,t2.quantitySellable) as quantitySellable,"
						+ " if(t4.quantityUnsellable is null,0,t4.quantityUnsellable) as quantityUnsellable "
					+ " from "
						+ " (select "
							+ " " + tableName_1 + "." + columnName_1_1 + " as store, "
							+ " " + tableName_1 + "." + columnName_1_2 + " as sku, "
							+ " " + tableName_1 + "." + columnName_1_4 + " as date, "
							+ " " + tableName_1 + "." + columnName_1_3 + " as asin, "
							+ " " + tableName_1 + "." + columnName_1_5 + " as quantitySellable "
						+ " from "
							+ " " + tableName_1 + ", "
							+ " (select " + columnName_1_1 + " as store, max(" + columnName_1_4 + ") as max_date from " + tableName_1 + " group by " + columnName_1_1 + ") as t1 "
						+ " where "
							+ " " + tableName_1 + "." + columnName_1_4 + " = t1.max_date "
							+ " and " + tableName_1 + "." + columnName_1_1 + " = t1.store "
							+ " and " + tableName_1 + "." + columnName_1_6 + " = 'SELLABLE' "
						+ " ) as t2 "
					+ " left join "
						+ " (select "
							+ " " + tableName_1 + "." + columnName_1_1 + " as store, "
							+ " " + tableName_1 + "." + columnName_1_2 + " as sku, "
							+ " " + tableName_1 + "." + columnName_1_4 + " as date, "
							+ " " + tableName_1 + "." + columnName_1_3 + " as asin, "
							+ " " + tableName_1 + "." + columnName_1_5 + " as quantityUnsellable "
						+ " from "
							+ " " + tableName_1 + ", "
							+ " (select " + columnName_1_1 + " as store, max(" + columnName_1_4 + ") as max_date from " + tableName_1 + " group by " + columnName_1_1 + ") as t3 "
						+ " where " + tableName_1 + "." + columnName_1_4 + " = t3.max_date "
							+ " and " + tableName_1 + "." + columnName_1_1 + " = t3.store "
							+ " and " + tableName_1 + "." + columnName_1_6 + " = 'UNSELLABLE' "
						+ " ) as t4 "
					+ " on "
						+ " t2.store = t4.store "
						+ " and t2.sku = t4.sku "
						+ " and t2.date = t4.date "
						+ " and t2.asin = t4.asin )"
				+ " union "
					+ " (select"
						+ " t8.store as store,"
						+ " t8.sku as sku,"
						+ " t8.date as date,"
						+ " t8.asin as asin,"
						+ " if(t6.quantitySellable is null,0,t6.quantitySellable) as quantitySellable,"
						+ " if(t8.quantityUnsellable is null,0,t8.quantityUnsellable) as quantityUnsellable "
					+ " from "
						+ " (select "
							+ " " + tableName_1 + "." + columnName_1_1 + " as store, "
							+ " " + tableName_1 + "." + columnName_1_2 + " as sku, "
							+ " " + tableName_1 + "." + columnName_1_4 + " as date, "
							+ " " + tableName_1 + "." + columnName_1_3 + " as asin, "
							+ " " + tableName_1 + "." + columnName_1_5 + " as quantitySellable "
						+ " from "
							+ " " + tableName_1 + ", "
							+ " (select " + columnName_1_1 + " as store, max(" + columnName_1_4 + ") as max_date from " + tableName_1 + " group by " + columnName_1_1 + ") as t5 "
						+ " where "
							+ " " + tableName_1 + "." + columnName_1_4 + " = t5.max_date "
							+ " and " + tableName_1 + "." + columnName_1_1 + " = t5.store "
							+ " and " + tableName_1 + "." + columnName_1_6 + " = 'SELLABLE' "
						+ " ) as t6 "
					+ " right join "
						+ " (select "
							+ " " + tableName_1 + "." + columnName_1_1 + " as store, "
							+ " " + tableName_1 + "." + columnName_1_2 + " as sku, "
							+ " " + tableName_1 + "." + columnName_1_4 + " as date, "
							+ " " + tableName_1 + "." + columnName_1_3 + " as asin, "
							+ " " + tableName_1 + "." + columnName_1_5 + " as quantityUnsellable "
						+ " from "
							+ " " + tableName_1 + ", "
							+ " (select " + columnName_1_1 + " as store, max(" + columnName_1_4 + ") as max_date from " + tableName_1 + " group by " + columnName_1_1 + ") as t7 "
						+ " where " + tableName_1 + "." + columnName_1_4 + " = t7.max_date "
							+ " and " + tableName_1 + "." + columnName_1_1 + " = t7.store "
							+ " and " + tableName_1 + "." + columnName_1_6 + "='UNSELLABLE' "
						+ " ) as t8 "
					+ " on "
						+ " t6.store=t8.store "
						+ " and t6.sku=t8.sku "
						+ " and t6.date=t8.date "
						+ " and t6.asin=t8.asin )"
				);
		while (rs.next()) {
			try {
				list.add(new AFNInventory(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3).replaceAll("\\D", "").substring(0, 14),
								rs.getString(4),
								Integer.parseInt(rs.getString(5)),
								Integer.parseInt(rs.getString(6))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	public ArrayList<OpenListing> getOpenListing() throws Exception{
		ArrayList<OpenListing> list = new ArrayList<OpenListing>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(""
				+ " select "
					+ " " + tableName_2 + "." +  columnName_2_1 + " as seller, "
					+ " " + tableName_2 + "." +  columnName_2_2 + " as sku, "
					+ " " + tableName_2 + "." +  columnName_2_3 + " as date, "
					+ " " + tableName_2 + "." +  columnName_2_4 + " as asin, "
					+ " " + tableName_2 + "." +  columnName_2_5 + " as price, "
					+ " " + tableName_2 + "." +  columnName_2_6 + " as quantity "
				+ " from "
					+ " " + tableName_2 + ", "
					+ " (select " +  columnName_2_1 + " as seller, max(" +  columnName_2_3 + ") as max_date from " + tableName_2 + " group by " +  columnName_2_1 + ") as t1 "
				+ " where "
					+ " " + tableName_2 + "." +  columnName_2_1 + " = t1.seller "
					+ " and " + tableName_2 + "." +  columnName_2_3 + " = t1.max_date "
				);
		while (rs.next()) {
			try {
				list.add(new OpenListing(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3).replaceAll("\\D", "").substring(0, 14),
								rs.getString(4),
								Double.parseDouble(rs.getString(5)),
								rs.getString(6) == null ? null : Integer.parseInt(rs.getString(6))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	public ArrayList<OpenDate> getOpenDate() throws Exception{
		ArrayList<OpenDate> list = new ArrayList<OpenDate>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select Store,Seller_SKU,Open_Date from report_active_listings group by Store,Seller_SKU");
		while (rs.next()) {
			try {
				list.add(new OpenDate(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3).replaceAll("\\D", "").substring(0, 14)
						));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	public ArrayList<BasicDataSellerSKUStockAndPriceResponse> getData(
			ArrayList<AFNInventory> list1,
			ArrayList<OpenListing> list2,
			ArrayList<OpenDate> list3) throws Exception{
		ArrayList<BasicDataSellerSKUStockAndPriceResponse> list = new ArrayList<BasicDataSellerSKUStockAndPriceResponse>();
		for ( AFNInventory ai : list1){
			BasicDataSellerSKUStockAndPriceResponse r = new BasicDataSellerSKUStockAndPriceResponse();
			r.setSellerCode(ai.sellerCode);
			r.setSellerSKU(ai.sellerSKU);
			r.setInventoryDate(ai.inventoryDate);
			r.setAsin(ai.asin);
			r.setQuantityAFNSellable(ai.quantitySellable);
			r.setQuantityAFNUnsellable(ai.quantityUnsellable);
			list.add(r);
		}
		for ( OpenListing ol : list2 ) {
			boolean isMatch = false;
			for ( BasicDataSellerSKUStockAndPriceResponse rr : list ) {
				if ( rr.getSellerCode().equals(ol.sellerCode)
						&& rr.getSellerSKU().equals(ol.sellerSKU)
						&& rr.getAsin().equals(ol.asin)) {
					rr.setOpenListingDate(ol.openListingDate);
					rr.setPrice(ol.price);
					rr.setQuantityMFN(ol.quantity);
					isMatch = true;
					break;
				}
			}
			if (!isMatch)
				list.add(new BasicDataSellerSKUStockAndPriceResponse(
						ol.sellerCode,
						ol.sellerSKU,
						ol.asin,
						null,
						ol.openListingDate,
						null,
						ol.price,
						ol.quantity,
						null,
						null,
						null));
		}
		for (BasicDataSellerSKUStockAndPriceResponse x : list) {
			for (OpenDate od : list3) {
				if (x.getSellerCode().equals(od.sellerCode)
						&& x.getSellerSKU().equals(od.sellerSKU)){
					x.setOpenDate(od.openDate);
					break;
				}
			}
		}
		return list;
	}
	
	public void process(String readMessageToOSSClassName, String readMessageToOSSMethodName) throws Exception{
		ArrayList<BasicDataSellerSKUStockAndPriceResponse> list = getData(getAFNInventory(),getOpenListing(),getOpenDate());
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list));
	}
	public void process() throws Exception{
		process(readMessageToOSSClassName,readMessageToOSSMethodName);
	}
	
	public static void main(String[] aaaa){
		try {
			ArrayList<OpenDate> list = new BasicDataSellerSKUStockAndPrice().getOpenDate();
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("111.txt",true)));
			for (OpenDate r : list)
				pw.println(
						r.sellerCode
						+ "\t" + r.sellerSKU
						+ "\t" + r.openDate);
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
