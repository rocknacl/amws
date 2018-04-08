package control.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import control.dataSynchronization.oss_api.models.AdvertisementActionData;
import control.dataSynchronization.oss_api.models.BasicDataAdvertisementBidByKeywordResponse;
import control.dataSynchronization.oss_api.models.BasicDataAdvertisementMegaBidResponse;
import control.dataSynchronization.oss_api.models.BasicDataAdvertisementMegaResponse;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import helper.dao.ConnectionPool;

public class AdvertisementAction {

	public ArrayList<BasicDataAdvertisementBidByKeywordResponse> getBasicDataAdvertisementBidByKeyword() throws Exception{
		ArrayList<BasicDataAdvertisementBidByKeywordResponse> list = new ArrayList<BasicDataAdvertisementBidByKeywordResponse>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		String sql = 
				"select "
					+ "report_ads_bid_by_keyword.Store,"
					+ "tmp.max_date,"
					+ "report_ads_bid_by_keyword.campaign_name,"
					+ "report_ads_bid_by_keyword.ad_group_name,"
					+ "report_ads_bid_by_keyword.keyword,"
					+ "report_ads_bid_by_keyword.currency,"
					+ "report_ads_bid_by_keyword.your_maximun_cpc_bid,"
					+ "report_ads_bid_by_keyword.est_page_1_bid "
				+ "from "
					+ "report_ads_bid_by_keyword,"
					+ "(select Store as Store,max(report_date) as max_date from report_ads_bid_by_keyword group by Store) as tmp "
				+ "where "
					+ "report_ads_bid_by_keyword.Store=tmp.Store and "
					+ "report_ads_bid_by_keyword.report_date=tmp.max_date";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			try {
				list.add(new BasicDataAdvertisementBidByKeywordResponse(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						(rs.getString(7) == null || rs.getString(7).equals("N/A")) ? null : new BigDecimal(rs.getString(7)),
						(rs.getString(8) == null || rs.getString(8).equals("N/A")) ? null : new BigDecimal(rs.getString(8))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	public ArrayList<BasicDataAdvertisementBidByKeywordResponse> getBasicDataAdvertisementBidByKeyword(int deltaDays) throws Exception{
		ArrayList<BasicDataAdvertisementBidByKeywordResponse> list = new ArrayList<BasicDataAdvertisementBidByKeywordResponse>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		String sql = 
				"select "
					+ "Store,"
					+ "report_date,"
					+ "campaign_name,"
					+ "ad_group_name,"
					+ "keyword,"
					+ "currency,"
					+ "your_maximun_cpc_bid,"
					+ "est_page_1_bid "
				+ "from "
					+ "report_ads_bid_by_keyword "
				+ "where "
					+ "report_date>=" + new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()-(long)deltaDays*86400000));
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			try {
				list.add(new BasicDataAdvertisementBidByKeywordResponse(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						(rs.getString(7) == null || rs.getString(7).equals("N/A")) ? null : new BigDecimal(rs.getString(7)),
						(rs.getString(8) == null || rs.getString(8).equals("N/A")) ? null : new BigDecimal(rs.getString(8))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	public ArrayList<BasicDataAdvertisementMegaResponse> getBasicDataAdvertisementMegaResponse(int deltaDays) throws Exception{
		ArrayList<BasicDataAdvertisementMegaResponse> list = new ArrayList<BasicDataAdvertisementMegaResponse>();
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		String sql = 
				"select "
					+ "report_ads_mega.Store,"
					+ "report_ads_mega.campaign_name,"
					+ "report_ads_mega.ad_group_name,"
					+ "report_ads_mega.advertised_sku,"
					+ "report_ads_mega.keyword,"
					+ "report_ads_mega.match_type,"
					+ "report_ads_mega.start_date,"
					+ "report_ads_mega.end_date,"
					+ "report_ads_mega.clicks,"
					+ "report_ads_mega.impressions,"
					+ "report_ads_mega.total_spend,"
					+ "report_ads_mega.currency,"
					+ "report_ads_mega.one_day_orders_placed,"
					+ "report_ads_mega.one_day_ordered_product_sales "
				+ "from report_ads_mega "
				+ "where report_ads_mega.start_date>=" + new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()-(long)deltaDays*86400000));
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			try {
				list.add(new BasicDataAdvertisementMegaResponse(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getInt(9),
						rs.getInt(10),
						rs.getString(11) == null ? null : new BigDecimal(rs.getString(11)),
						rs.getString(12),
						rs.getInt(13),
						rs.getString(14) == null ? null : new BigDecimal(rs.getString(14))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		return list;
	}
	
	// =========================================================================================
	
	public ArrayList<BasicDataAdvertisementMegaBidResponse> getBasicDataAdvertisementMegaBidResponse(
			ArrayList<BasicDataAdvertisementMegaResponse> list1,
			ArrayList<BasicDataAdvertisementBidByKeywordResponse> list2) {
		ArrayList<BasicDataAdvertisementMegaBidResponse> list = new ArrayList<BasicDataAdvertisementMegaBidResponse>();
		for (BasicDataAdvertisementMegaResponse r1 : list1){
			BasicDataAdvertisementMegaBidResponse r = new BasicDataAdvertisementMegaBidResponse(r1);
			for (BasicDataAdvertisementBidByKeywordResponse r2 : list2) 
				try {
					if (r.getSellerCode().equals(r2.getSellerCode())
							&& r.getStartDate().equals(r2.getReportDate())
							&& r.getCampaignName().equals(r2.getCampaignName())
							&& r.getAdGroupName().equals(r2.getAdGroupName())
							&& r.getKeyword().equals(r2.getKeyword())) {
						r.setMaxCPCBid(r2.getMaxCPCBid());
						r.setEstPage1Bid(r2.getEstPage1Bid());
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			list.add(r);
		}
		return list;
	}
	
	public ArrayList<AdvertisementActionData> getAdvertisementActionData(){
		return null;//TODO
	}
	
	// ======================================== message process ================================================
	
	public void getBasicDataAdvertisementMegaBidResponse() throws Exception{
		String readMessageToOSSClassName = "control.dataSynchronization.DataSynchronizationOperationsForReflection";
		String readMessageToOSSMethodName = "updateBasicDataAdvertisementMegaBid";
		ArrayList<BasicDataAdvertisementMegaBidResponse> list = getBasicDataAdvertisementMegaBidResponse(
				getBasicDataAdvertisementMegaResponse(30), getBasicDataAdvertisementBidByKeyword(30));
		System.out.println("List Size: " + list.size());
//		for (BasicDataAdvertisementMegaBidResponse x : list) {
//			if (x.getKeyword().equals("dock speakers") || x.getKeyword().equals("Solar Lamp"))
//				System.out.println(x.getCampaignName()+"\t"+x.getAdGroupName()+"\t"+x.getKeyword()+"\t"+x.getAdSKU()+"\t"+x.getStartDate()
//									+"\t"+x.getEndDate()+"\t"+x.getImpressions()+"\t"+x.getClicks()+"\t"+x.getTotalSpend()
//									+"\t"+x.getOrderQuantity()+"\t"+x.getSales());
//		}
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list));
	}
	
	public void callOSSGetAdvertisementRealAction() throws Exception{
		MessageBuffer.getInstance().putMessage(new Message(
							"control.dataSynchronization.DataSynchronizationOperationsForReflection",
							"getAdvertisementRealAction",
							null));
	}
	
	public void callOSSSendAdvertisementAction() throws Exception{
		String readMessageToOSSClassName = "control.dataSynchronization.DataSynchronizationOperationsForReflection";
		String readMessageToOSSMethodName = "updateAdvertisementAction";
		ArrayList<BasicDataAdvertisementMegaBidResponse> list = getBasicDataAdvertisementMegaBidResponse(
				getBasicDataAdvertisementMegaResponse(15), getBasicDataAdvertisementBidByKeyword(15));
		ArrayList<AdvertisementActionData> list1 = new ArrayList<AdvertisementActionData>();
		for (BasicDataAdvertisementMegaBidResponse x : list) list1.add(new AdvertisementActionData(x));
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,list1));
	}
	
	// ================================================================================
	
	public static void test1() throws Exception{
		AdvertisementAction a = new AdvertisementAction();
		ArrayList<BasicDataAdvertisementBidByKeywordResponse> list = a.getBasicDataAdvertisementBidByKeyword(15);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("1.txt")));
		for (BasicDataAdvertisementBidByKeywordResponse r : list){
//			System.out.println(
//					r.getSellerCode()
//					+ "\t" + r.getReportDate()
//					+ "\t" + r.getCampaignName()
//					+ "\t" + r.getAdGroupName()
//					+ "\t" + r.getKeyword()
//					+ "\t" + r.getCurrency()
//					+ "\t" + r.getMaxCPCBid()
//					+ "\t" + r.getEstPage1Bid()
//					);
			pw.println(
					r.getSellerCode()
					+ "\t" + r.getReportDate()
					+ "\t" + r.getCampaignName()
					+ "\t" + r.getAdGroupName()
					+ "\t" + r.getKeyword()
					+ "\t" + r.getCurrency()
					+ "\t" + r.getMaxCPCBid()
					+ "\t" + r.getEstPage1Bid()
					);
		}
		pw.close();
	}
	
	public static void test2() throws Exception{
		AdvertisementAction a = new AdvertisementAction();
		ArrayList<BasicDataAdvertisementMegaResponse> list = a.getBasicDataAdvertisementMegaResponse(15);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("2.txt")));
		for (BasicDataAdvertisementMegaResponse r : list)
			pw.println(
					r.getSellerCode()
					+ "\t" + r.getCampaignName()
					+ "\t" + r.getAdGroupName()
					+ "\t" + r.getAdSKU()
					+ "\t" + r.getKeyword()
					+ "\t" + r.getMatchType()
					+ "\t" + r.getStartDate()
					+ "\t" + r.getEndDate()
					+ "\t" + r.getClicks()
					+ "\t" + r.getImpressions()
					+ "\t" + r.getTotalSpend()
					+ "\t" + r.getCurrency()
					+ "\t" + r.getOrderQuantity()
					+ "\t" + r.getSales()
					);
		pw.close();
	}
	
	// ===============================================================
	
	public static void main(String[] aaaaaa) {
		try {
			AdvertisementAction a = new AdvertisementAction();
			ArrayList<BasicDataAdvertisementMegaBidResponse> list = a.getBasicDataAdvertisementMegaBidResponse(
					a.getBasicDataAdvertisementMegaResponse(30), a.getBasicDataAdvertisementBidByKeyword(30));
			System.out.println("List Size: " + list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
