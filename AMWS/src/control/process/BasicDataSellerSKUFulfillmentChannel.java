package control.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import control.dataSynchronization.oss_api.models.BasicDataSellerSKUFulfillmentChannelResponse;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import helper.dao.ConnectionPool;

public class BasicDataSellerSKUFulfillmentChannel {

	private String readMessageToOSSClassName = "";// TODO
	private String readMessageToOSSMethodName = "";// TODO
	
	private String tableName_1 = "report_fba_amazon_fulfilled_shipments"; // AFN Order
	private String columnName_1_1 = "Store"; // Seller Code('AA','UP','KO'...)
	private String columnName_1_2 = "sku"; // Seller SKU
	private String columnName_1_3 = "purchase_date"; // Purchase Date
	
	private String tableName_2 = "report_fba_amazon_fulfilled_shipments"; // MFN Order
	private String columnName_2_1 = "Store"; // Seller Code('AA','UP','KO'...)
	private String columnName_2_2 = "sku"; // Seller SKU
	private String columnName_2_3 = "purchase_date"; // Purchase Date
	
	// -------------------------------------------------------------------------------------------
	
	public ArrayList<BasicDataSellerSKUFulfillmentChannelResponse> getDataFromDatabase() throws Exception{
		ArrayList<BasicDataSellerSKUFulfillmentChannelResponse> list = new ArrayList<BasicDataSellerSKUFulfillmentChannelResponse>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(""
				+ " select "
					+ " c.seller as seller, "
					+ " c.sku as sku, "
					+ " if(c.max_date_AFN>c.max_date_MFN,'FBA','CBM') as fulfillment "
				+ " from "
						+ " (select "
							+ " a.seller as seller, "
							+ " a.sku as sku, "
							+ " a.max_date as max_date_AFN, "
							+ " if(b.max_date is null,'2000-01-01 00:00:00',b.max_date) as max_date_MFN "
						+ " from "
							+ " (select "
								+ " " + columnName_1_1 + " as seller, "
								+ " " + columnName_1_2 + " as sku, "
								+ " max(" + columnName_1_3 + ") as max_date "
							+ " from " + tableName_1 +"	"
							+ " group by sku) as a "
						+ " left join "
							+ " (select "
								+ " " + columnName_2_1 + " as seller, "
								+ " " + columnName_2_2 + " as sku, "
								+ " max(" + columnName_2_3 + ") as max_date "
							+ " from " + tableName_2 +" "
							+ " group by sku) as b "
						+ " on a.seller = b.seller and a.sku = b.sku "
					+ " union "
						+ " select "
							+ " b.seller as seller, "
							+ " b.sku as sku, "
							+ " if(a.max_date is null,'2000-01-01 00:00:00',a.max_date) as max_date_AFN, "
							+ " b.max_date as max_date_MFN "
						+ " from "
							+ " (select "
								+ " " + columnName_1_1 + " as seller, "
								+ " " + columnName_1_2 + " as sku, "
								+ " max(" + columnName_1_3 + ") as max_date "
							+ " from " + tableName_1 +" "
							+ " group by sku) as a "
						+ " right join "
							+ " (select "
								+ " " + columnName_2_1 + " as seller, "
								+ " " + columnName_2_2 + " as sku, "
								+ " max(" + columnName_2_3 + ") as max_date "
							+ " from " + tableName_2 +" "
							+ " group by sku) as b "
						+ " on a.seller = b.seller and a.sku = b.sku ) "
					+ " as c");//TODO
		while (rs.next()) {
			try {
				list.add(new BasicDataSellerSKUFulfillmentChannelResponse(
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
		ArrayList<BasicDataSellerSKUFulfillmentChannelResponse> list = getDataFromDatabase();
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list));
	}
	
	public void process(String readMessageToOSSClassName, String readMessageToOSSMethodName) throws Exception{
		ArrayList<BasicDataSellerSKUFulfillmentChannelResponse> list = getDataFromDatabase();
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list));
	}
	
}
