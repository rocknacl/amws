package control.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import control.dataSynchronization.oss_api.models.BasicDataSellerSKUSalesDailyResponse;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import helper.dao.ConnectionPool;

public class BasicDataSellerSKUSalesDaily {

	private final String readMessageToOSSClassName = "control.dataSynchronization.DataSynchronizationOperationsForReflection";// TODO
	private final String readMessageToOSSMethodName = "updateSKUSalesOrderData";// TODO
	private final int salesDataDays = 3;
	
	private String tableName_1 = "report_fba_amazon_fulfilled_shipments"; // AFN Order
	private String columnName_1_1 = "Store"; // Seller Code('AA','UP','KO'...)
	private String columnName_1_2 = "sku"; // Seller SKU
	private String columnName_1_3 = "reporting_date"; // Reporting Date
	private String columnName_1_4 = "quantity_shipped"; // Quantity Shipped
	private String columnName_1_5 = "item_price"; // Item Price
	
	private String tableName_2 = "report_fba_amazon_fulfilled_shipments"; // MFN Order
	private String columnName_2_1 = "Store"; // Seller Code('AA','UP','KO'...)
	private String columnName_2_2 = "sku"; // Seller SKU
	private String columnName_2_3 = "purchase_date"; // Purchase Date
	private String columnName_2_4 = "quantity_shipped"; // Quantity Shipped
	private String columnName_2_5 = "item_price"; // Item Price
	
	// -----------------------------------------------------------------------------------------------------
	
	public ArrayList<BasicDataSellerSKUSalesDailyResponse> getDataFromDatabase() throws Exception{
		ArrayList<BasicDataSellerSKUSalesDailyResponse> list = new ArrayList<BasicDataSellerSKUSalesDailyResponse>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -salesDataDays);
		ResultSet rs = stmt.executeQuery(""
				+ " (select "
					+ " " + columnName_1_1 + " as seller, "
					+ " " + columnName_1_2 + " as sku, "
					+ " 'AFN' as fulfillment, "
					+ " date_format(" + columnName_1_3 + ",'%Y-%m-%d') as date, "
					+ " sum(" + columnName_1_4 + ") as salesVolumn, "
					+ " sum(" + columnName_1_5 + ") as salesAmount "
				+ " from " + tableName_1 + " "
				+ " where unix_timestamp(" + columnName_1_3 + ")>=unix_timestamp('" + new SimpleDateFormat("yyyyMMdd").format(c.getTime()) + "') "
				+ " group by seller,sku,date ) "
			+ " union "
				+ " (select "
					+ " " + columnName_2_1 + " as seller, "
					+ " " + columnName_2_2 + " as sku, "
					+ " 'MFN' as fulfillment, "
					+ " date_format(" + columnName_2_3 + ",'%Y-%m-%d') as date, "
					+ " sum(" + columnName_2_4 + ") as salesVolumn, "
					+ " sum(" + columnName_2_5 + ") as salesAmount "
				+ " from " + tableName_2 + " "
				+ " where unix_timestamp(" + columnName_2_3 + ")>=unix_timestamp('" + new SimpleDateFormat("yyyyMMdd").format(c.getTime()) + "') "
				+ " group by seller,sku,date )");
		while (rs.next()) {
			try {
				list.add(new BasicDataSellerSKUSalesDailyResponse(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4).replaceAll("\\D", ""),
								Integer.parseInt(rs.getString(5)),
								Double.parseDouble(rs.getString(6))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	public void process() throws Exception{
		ArrayList<BasicDataSellerSKUSalesDailyResponse> list = getDataFromDatabase();
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list));
	}
	
	public void process(String readMessageToOSSClassName, String readMessageToOSSMethodName) throws Exception{
		ArrayList<BasicDataSellerSKUSalesDailyResponse> list = getDataFromDatabase();
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list));
	}
	
}
