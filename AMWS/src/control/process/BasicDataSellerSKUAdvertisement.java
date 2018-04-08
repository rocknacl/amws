package control.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import control.dataSynchronization.oss_api.models.BasicDataSellerSKUAdvertisementResponse;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import helper.dao.ConnectionPool;

public class BasicDataSellerSKUAdvertisement {

	private String readMessageToOSSClassName = "control.dataSynchronization.DataSynchronizationOperationsForReflection";
	private String readMessageToOSSMethodName = "updateSKUSalesAdvertisementData";
	private int advertisementDataDays = 3;
	
	public ArrayList<BasicDataSellerSKUAdvertisementResponse> getAdvertisementDataFromDatabase() throws Exception{
		ArrayList<BasicDataSellerSKUAdvertisementResponse> adList = new ArrayList<BasicDataSellerSKUAdvertisementResponse>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -advertisementDataDays);
		ResultSet rs = stmt.executeQuery(" select start_date,store,sku,clicks,impressions,total_spend "
										+ " from report_ads_product_performance_daily "
										+ " where unix_timestamp(start_date)>=unix_timestamp('"
										+ new SimpleDateFormat("yyyyMMdd").format(c.getTime())
										+ "')");
		while (rs.next()) {
			try {
				adList.add(new BasicDataSellerSKUAdvertisementResponse(
								rs.getString(2),
								rs.getString(1),
								rs.getString(3),
								Integer.parseInt(rs.getString(4)),
								Integer.parseInt(rs.getString(5)),
								Double.parseDouble(rs.getString(6))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return adList;
	}
	
	public void process() throws Exception{
		ArrayList<BasicDataSellerSKUAdvertisementResponse> adList = getAdvertisementDataFromDatabase();
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,adList));
	}
	
	public void process(String readMessageToOSSClassName, String readMessageToOSSMethodName) throws Exception{
		ArrayList<BasicDataSellerSKUAdvertisementResponse> adList = getAdvertisementDataFromDatabase();
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,adList));
	}
	
	public static void main(String[] a){
		try {
			ArrayList<BasicDataSellerSKUAdvertisementResponse> list
				= new BasicDataSellerSKUAdvertisement().getAdvertisementDataFromDatabase();
			for (BasicDataSellerSKUAdvertisementResponse r : list)
				r.printColumnValue(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
