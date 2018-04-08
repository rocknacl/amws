package control.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import control.dataSynchronization.oss_api.models.BasicDataSellerSKUASINResponse;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import helper.dao.ConnectionPool;

public class BasicDataSellerSKUASIN {

	private String readMessageToOSSClassName = "";// TODO
	private String readMessageToOSSMethodName = "";// TODO
	
	private String tableName_1 = "inventory"; // AFN Fulfillment Inventory
	private String columnName_1_1 = "merchant"; // Seller Code('AA','UP','KO'...)
	private String columnName_1_2 = "seller_sku"; // Seller SKU
	private String columnName_1_3 = "asin"; // ASIN
	private String columnName_1_4 = "inventory_date"; // Inventory Date
	
	private String tableName_2 = "open_listing"; // Open Listing
	private String columnName_2_1 = "merchant"; // Seller Code('AA','UP','KO'...)
	private String columnName_2_2 = "seller_sku"; // Seller SKU
	private String columnName_2_3 = "asin"; // ASIN
	private String columnName_2_4 = "open_listing_date"; // Open Listing Date
	
	// -------------------------------------------------------------------------------------------
	
	public ArrayList<BasicDataSellerSKUASINResponse> getSellerSKUDataFromDatabase() throws Exception{
		ArrayList<BasicDataSellerSKUASINResponse> list = new ArrayList<BasicDataSellerSKUASINResponse>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(""
						+ " (select "
							+ " " + tableName_1 + "." + columnName_1_1 + " as merchant, "
							+ " " + tableName_1 + "." + columnName_1_2 + " as seller_sku, "
							+ " " + tableName_1 + "." + columnName_1_3 + " as asin "
						+ " from "
							+ " " + tableName_1 + ", "
							+ " (select " + columnName_1_1 + " as merchant, max(" + columnName_1_4 + ") as max_date from " + tableName_1 + " group by " + columnName_1_1 + ") as t1 "
						+ " where "
							+ " " + tableName_1 + ".merchant = t1.merchant "
							+ " and " + tableName_1 + "." + columnName_1_4 + " = t1.max_date ) "
					+ " union "
						+ " (select "
							+ " " + tableName_2 + "." + columnName_2_1 + " as merchant, "
							+ " " + tableName_2 + "." + columnName_2_2 + " as seller_sku, "
							+ " " + tableName_2 + "." + columnName_2_3 + " as asin "
						+ " from "
							+ " open_listing, "
							+ " (select " + columnName_2_1 + " as merchant, max(" + columnName_2_4 + ") as max_date from " + tableName_2 + " group by " + columnName_2_1 + ") as t2 "
						+ " where "
							+ " " + tableName_2 + "." + columnName_2_1 + " = t2.merchant "
							+ " and " + tableName_2 + "." + columnName_2_4 + " = t2.max_date)"
				);
		while (rs.next()) {
			try {
				list.add(new BasicDataSellerSKUASINResponse(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	public void process() throws Exception{
		ArrayList<BasicDataSellerSKUASINResponse> list = getSellerSKUDataFromDatabase();
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list));
	}
	
	public void process(String readMessageToOSSClassName, String readMessageToOSSMethodName) throws Exception{
		ArrayList<BasicDataSellerSKUASINResponse> list = getSellerSKUDataFromDatabase();
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list));
	}
	
}
