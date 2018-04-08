package control.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import control.dataSynchronization.oss_api.models.BasicDataSellerSKUAFNRestockResponse;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import helper.dao.ConnectionPool;

public class BasicDataSellerSKUAFNRestock {

	private final String readMessageToOSSClassName = "control.dataSynchronization.DataSynchronizationOperationsForReflection";
	private final String readMessageToOSSMethodName = "updateRestockDataSynchronization";
	
	private String tableName_1 = "report_fba_amazon_fulfilled_inventory"; // AFN Fulfillment Inventory
	private String columnName_1_1 = "Store"; // Seller Code('AA','UP','KO'...)
	private String columnName_1_2 = "Seller_SKU"; // Seller SKU
	private String columnName_1_3 = "Asin"; // ASIN
	private String columnName_1_4 = "Insert_Date"; // Inventory Date
	private String columnName_1_5 = "Quantity_Available"; // Quantity Available
	private String columnName_1_6 = "Warehouse_Condition_Code"; // Warehouse Condition Code
	
	private String tableName_2 = "report_fba_amazon_fulfilled_shipments"; // AFN Order
	private String columnName_2_1 = "Store"; // Seller Code('AA','UP','KO'...)
	private String columnName_2_2 = "sku"; // Seller SKU
	private String columnName_2_3 = "reporting_date"; // Reporting Date
	private String columnName_2_4 = "quantity_shipped"; // Quantity Shipped
	
	// ------------------------------------------------------------------------
	
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
	
	private class AFNStockStatusStatistics{
		public String sellerCode;
		public String sellerSKU;
		public String asin;
		public Integer outOfStockDaysIn30Days;
		public Integer onSaleDaysIn30Days;
		
		public AFNStockStatusStatistics(){
			
		}
		public AFNStockStatusStatistics(
				String sellerCode,
				String sellerSKU,
				String asin,
				Integer outOfStockDaysIn30Days,
				Integer onSaleDaysIn30Days){
			this.sellerCode = sellerCode;
			this.sellerSKU = sellerSKU;
			this.asin = asin;
			this.outOfStockDaysIn30Days = outOfStockDaysIn30Days;
			this.onSaleDaysIn30Days = onSaleDaysIn30Days;
		}
	}
	
	private class AFNSales{
		public String sellerCode;
		public String sellerSKU;
		public Integer salesVolumn03To02;
		public Integer salesVolumn09To02;
		public Integer salesVolumn16To02;
		public Integer salesVolumn23To02;
		public Integer salesVolumn30To02;
		public Integer salesVolumn32To02;
		public Integer salesVolumn37To02;
		public Integer salesVolumn92To02;
		public Integer salesVolumnTotal;
		
		public AFNSales(){
			
		}
		public AFNSales(
				String sellerCode,
				String sellerSKU,
				Integer salesVolumn03To02,
				Integer salesVolumn09To02,
				Integer salesVolumn16To02,
				Integer salesVolumn23To02,
				Integer salesVolumn30To02,
				Integer salesVolumn32To02,
				Integer salesVolumn37To02,
				Integer salesVolumn92To02,
				Integer salesVolumnTotal){
			this.sellerCode = sellerCode;
			this.sellerSKU = sellerSKU;
			this.salesVolumn03To02 = salesVolumn03To02;
			this.salesVolumn09To02 = salesVolumn09To02;
			this.salesVolumn16To02 = salesVolumn16To02;
			this.salesVolumn23To02 = salesVolumn23To02;
			this.salesVolumn30To02 = salesVolumn30To02;
			this.salesVolumn32To02 = salesVolumn32To02;
			this.salesVolumn37To02 = salesVolumn37To02;
			this.salesVolumn92To02 = salesVolumn92To02;
			this.salesVolumnTotal = salesVolumnTotal;
		}
	}
	
	// ------------------------------------------------------------------------
	
	public String getDate(int deltaDays){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, deltaDays);
		return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
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
								rs.getString(3).replaceAll("\\D", ""),
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
	
	public ArrayList<AFNStockStatusStatistics> getAFNStockStatusStatistics() throws Exception {
		ArrayList<AFNStockStatusStatistics> list = new ArrayList<AFNStockStatusStatistics>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(""
					+ " select "
						+ " store, "
						+ " sku, "
						+ " asin, "
						+ " sum(if(q=0,1,0)) as OutOfStockDays,"
						+ " sum(if(q>0,1,0)) as OnSaleDays "
					+ " from "
						+ " (select "
							+ " " + columnName_1_1 + " as store, "
							+ " " + columnName_1_2 + " as sku, "
							+ " " + columnName_1_3 + " as asin, "
							+ " date_format(" + columnName_1_4 + ",'%Y%m%d') as date, "
							+ " max(" + columnName_1_5 + ") as q "
						+ " from " + tableName_1 + " "
						+ " where "
							+ " " + columnName_1_4 + ">'" + getDate(-29) + "' "
							+ " and " + columnName_1_6 + "='SELLABLE' "
						+ " group by store,sku,asin,date ) as t1 "
					+ " group by store,sku,asin "
					);
		while (rs.next()) {
			try {
				list.add(new AFNStockStatusStatistics(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3).replaceAll("\\D", ""),
								Integer.parseInt(rs.getString(4)),
								Integer.parseInt(rs.getString(5))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	public ArrayList<AFNSales> getAFNSales() throws Exception{
		ArrayList<AFNSales> list = new ArrayList<AFNSales>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(""
				+ "select "
					+ columnName_2_1 + " as store,"
					+ columnName_2_2 + " as sku,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-3) + "'," + columnName_2_4 + ",0)) as q1,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-9) + "'," + columnName_2_4 + ",0)) as q2,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-16) + "'," + columnName_2_4 + ",0)) as q3,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-23) + "'," + columnName_2_4 + ",0)) as q4,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-30) + "'," + columnName_2_4 + ",0)) as q5,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-32) + "'," + columnName_2_4 + ",0)) as q6,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-37) + "'," + columnName_2_4 + ",0)) as q7,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-92) + "'," + columnName_2_4 + ",0)) as q8,"
					+ "sum(" + columnName_2_4 + ") as q9"
				+ " from " + tableName_2
				+ " where " + columnName_2_3 + "<'" + getDate(-2) + "' "
				+ " group by store,sku "
				);
		while (rs.next()) {
			try {
				list.add(new AFNSales(
								rs.getString(1),
								rs.getString(2),
								Integer.parseInt(rs.getString(3)),
								Integer.parseInt(rs.getString(4)),
								Integer.parseInt(rs.getString(5)),
								Integer.parseInt(rs.getString(6)),
								Integer.parseInt(rs.getString(7)),
								Integer.parseInt(rs.getString(8)),
								Integer.parseInt(rs.getString(9)),
								Integer.parseInt(rs.getString(10)),
								Integer.parseInt(rs.getString(11))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	public ArrayList<BasicDataSellerSKUAFNRestockResponse> getData(
			ArrayList<AFNInventory> list1,
			ArrayList<AFNStockStatusStatistics> list2,
			ArrayList<AFNSales> list3) throws Exception{
		ArrayList<BasicDataSellerSKUAFNRestockResponse> list
			= new ArrayList<BasicDataSellerSKUAFNRestockResponse>();
		for (AFNInventory ai : list1) {
			BasicDataSellerSKUAFNRestockResponse r = new BasicDataSellerSKUAFNRestockResponse();
			r.setInventoryDate(ai.inventoryDate);
			r.setSellerCode(ai.sellerCode);
			r.setSellerSKU(ai.sellerSKU);
			r.setAsin(ai.asin);
			r.setQuantitySellable(ai.quantitySellable);
			r.setQuantityUnsellable(ai.quantityUnsellable);
			for ( AFNStockStatusStatistics asss : list2 )
				if ( ai.sellerCode.equals(asss.sellerCode) && ai.sellerSKU.equals(asss.sellerSKU) ) {
					r.setOnSaleDaysIn30Days(asss.onSaleDaysIn30Days);
					r.setOutOfStockDaysIn30Days(asss.outOfStockDaysIn30Days);
					break;
				}
			for ( AFNSales as : list3 )
				if ( ai.sellerCode.equals(as.sellerCode) && ai.sellerSKU.equals(as.sellerSKU) ) {
					r.setSalesVolumnDay1(as.salesVolumn03To02);
					r.setSalesVolumnDay7(as.salesVolumn09To02);
					r.setSalesVolumnDay30(as.salesVolumn32To02);
					r.setSalesVolumnDay90(as.salesVolumn92To02);
					r.setSalesVolumnTotal(as.salesVolumnTotal);
					r.setPredictedSalesVolumnWeekly((double) ((as.salesVolumn09To02 + 2*as.salesVolumn16To02 + 3*as.salesVolumn23To02 + 4*as.salesVolumn30To02 + 5*as.salesVolumn37To02 - 3*(as.salesVolumn09To02 + as.salesVolumn16To02 + as.salesVolumn23To02 + as.salesVolumn30To02 + as.salesVolumn37To02)))/10);
					break;
				}
			list.add(r);
		}
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
					"file/BasicData/RestockResponse" + "/" 
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt")));
			list.get(0).writeName(pw);
			for (BasicDataSellerSKUAFNRestockResponse response : list) {
				response.writeValue(pw);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void process(String readMessageToOSSClassName, String readMessageToOSSMethodName) throws Exception{
		ArrayList<BasicDataSellerSKUAFNRestockResponse> list 
			= getData(getAFNInventory(),
					getAFNStockStatusStatistics(),
					getAFNSales());
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list));
	}

	public void process() throws Exception{
		process(readMessageToOSSClassName,readMessageToOSSMethodName);
	}
	
	public void test(){
		System.out.println("select "
					+ columnName_2_1 + " as store,"
					+ columnName_2_2 + " as sku,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-3) + "'," + columnName_2_4 + ",0)) as q1,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-9) + "'," + columnName_2_4 + ",0)) as q2,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-16) + "'," + columnName_2_4 + ",0)) as q3,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-23) + "'," + columnName_2_4 + ",0)) as q4,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-30) + "'," + columnName_2_4 + ",0)) as q5,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-32) + "'," + columnName_2_4 + ",0)) as q6,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-37) + "'," + columnName_2_4 + ",0)) as q7,"
					+ "sum(if(" + columnName_2_3 + ">'" + getDate(-92) + "'," + columnName_2_4 + ",0)) as q8,"
					+ "sum(" + columnName_2_4 + ") as q9"
				+ " from " + tableName_2
				+ " where " + columnName_2_3 + "<'" + getDate(-2) + "' "
				+ " group by store,sku ");
	}
	
	public static void main(String[] aaaa){
		try {
			BasicDataSellerSKUAFNRestock a = new BasicDataSellerSKUAFNRestock();
			ArrayList<AFNSales> list1 = a.getAFNSales();
			ArrayList<AFNStockStatusStatistics> list2 = a.getAFNStockStatusStatistics();
			ArrayList<AFNInventory> list3 = a.getAFNInventory();
			ArrayList<BasicDataSellerSKUAFNRestockResponse> list = a.getData(list3,
																			list2,
																			list1);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Restock.txt",true)));
			pw.println(
					"SellerCode"
					+ "\t" + "SellerSKU"
					+ "\t" + "ASIN"
					+ "\t" + "InventoryDate"
					+ "\t" + "SalesVolumnDay1"
					+ "\t" + "SalesVolumnDay7"
					+ "\t" + "SalesVolumnDay30"
					+ "\t" + "SalesVolumnDay90"
					+ "\t" + "SalesVolumnDayTotal"
					+ "\t" + "QuantitySellable"
					+ "\t" + "QuantityUnsellable"
					+ "\t" + "OnSaleDaysIn30Days"
					+ "\t" + "OutOfStockDaysIn30Days"
					+ "\t" + "PredictedSalesVolumnWeekly");
			for (BasicDataSellerSKUAFNRestockResponse r : list)
				pw.println(
						r.getSellerCode()
						+ "\t" + r.getSellerSKU()
						+ "\t" + r.getAsin()
						+ "\t" + r.getInventoryDate()
						+ "\t" + r.getSalesVolumnDay1()
						+ "\t" + r.getSalesVolumnDay7()
						+ "\t" + r.getSalesVolumnDay30()
						+ "\t" + r.getSalesVolumnDay90()
						+ "\t" + r.getSalesVolumnTotal()
						+ "\t" + r.getQuantitySellable()
						+ "\t" + r.getQuantityUnsellable()
						+ "\t" + r.getOnSaleDaysIn30Days()
						+ "\t" + r.getOutOfStockDaysIn30Days()
						+ "\t" + r.getPredictedSalesVolumnWeekly()
						);
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
