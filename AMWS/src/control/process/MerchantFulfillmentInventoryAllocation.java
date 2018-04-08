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

import amzint.feeds.FeedClient;
import control.dataSynchronization.oss_api.models.MerchantFulfillmentInventoryAllocationRequest;
import control.transmission.Message;
import dao.MerchantAccountDAO;
import helper.dao.ConnectionPool;

/**
 * merchant fulfillment inventory allocation.
 * @author D16
 * @note  equalAllocatedProportion(Double) -->
 *			Effective interval: [0,1], this parameter decide the proportion of inventory quantity which allocated equally,
 *			the rest allocated by sales. 0 means allocated by sales only, 1 means allocated equally. null or less than 0 works as 0,
 *			greater than 1 works as 1. 
 */
public class MerchantFulfillmentInventoryAllocation {

	private Double equalAllocatedProportion = 0.3;
	private int salesStatisticDays = 7;
	
	private class SellerSKUInfo{
		public String sellerCode;
		public String sellerSKU;
		public String sellerSKUStatus;
		public Integer sellerSKUSales;
		public Integer allocatedQuantity;
		
		public void printColumnName(int tabNum){
			for ( int i=0; i<tabNum; i++) System.out.print("\t");
			System.out.println("SellerCode\tSellerSKU\tSellerSKUStatus\tSellerSKUSales\tAllocatedQuantity");
		}
		
		public void printColumnValue(int tabNum){
			for ( int i=0; i<tabNum; i++) System.out.print("\t");
			System.out.println(sellerCode 
					+ "\t" + sellerSKU
					+ "\t" + sellerSKUStatus
					+ "\t" + sellerSKUSales
					+ "\t" + allocatedQuantity);
		}
		
		public void print(){
			printColumnName(0);
			printColumnValue(0);
		}
	}

	private class InventorySKUInfo{
		public String inventorySKU;
		public Integer inventorySKUQuantity;
		public ArrayList<SellerSKUInfo> sellerSKUInfoList;
		
		public void printColumnName(int tabNum){
			for ( int i=0; i<tabNum; i++) System.out.print("\t");
			System.out.print("InventorySKU\tInventorySKUQuantity\t");
			if (sellerSKUInfoList == null || sellerSKUInfoList.isEmpty()){
				System.out.println("sellerSKUUnitList");
			} else {
				sellerSKUInfoList.get(0).printColumnName(0);
			}
		}
		
		public void printColumnValue(int tabNum){
			for ( int i=0; i<tabNum; i++) System.out.print("\t");
			if (sellerSKUInfoList == null || sellerSKUInfoList.isEmpty()){
				System.out.println(inventorySKU +"\t" + inventorySKUQuantity + "\tNO_DATA");
			} else {
				for ( SellerSKUInfo sellerSKUUnit : sellerSKUInfoList ) {
					System.out.print(inventorySKU +"\t" + inventorySKUQuantity + "\t");
					sellerSKUUnit.printColumnValue(0);
				}
			}
		}
		
		public void print(int tabNum){
			printColumnName(0);
			printColumnValue(0);
		}
		
		public void write(PrintWriter pw){
			if (sellerSKUInfoList == null || sellerSKUInfoList.isEmpty()){
				pw.println(inventorySKU +"\t" + inventorySKUQuantity + "\tNO_DATA");
			} else {
				for ( SellerSKUInfo sellerSKUUnit : sellerSKUInfoList ) {
					pw.println(inventorySKU 
							+ "\t" + inventorySKUQuantity 
							+ "\t" + sellerSKUUnit.sellerCode 
							+ "\t" + sellerSKUUnit.sellerSKU 
							+ "\t" + sellerSKUUnit.sellerSKUStatus 
							+ "\t" + sellerSKUUnit.sellerSKUSales 
							+ "\t" + sellerSKUUnit.allocatedQuantity);
				}
			}
		}
	}
	
	public ArrayList<InventorySKUInfo> readOSSData(ArrayList<MerchantFulfillmentInventoryAllocationRequest> dataList) throws Exception{
		if (dataList == null) return null;
		ArrayList<InventorySKUInfo> inventoryUnitList = new ArrayList<InventorySKUInfo>();
		
		for (MerchantFulfillmentInventoryAllocationRequest data : dataList) {
			SellerSKUInfo sellerSKUInfo = new SellerSKUInfo();
			sellerSKUInfo.sellerCode = data.getSellerCode();
			sellerSKUInfo.sellerSKU = data.getSellerSKU();
			sellerSKUInfo.sellerSKUStatus = data.getSellerSKUStatus();
			boolean isInventorySKUMatch = false;
			for (InventorySKUInfo isi : inventoryUnitList) {
				if (isi.inventorySKU.equals(data.getInventorySKU())) {
					isi.sellerSKUInfoList.add(sellerSKUInfo);
					isInventorySKUMatch = true;
					break;
				}
			}
			if (!isInventorySKUMatch){
				InventorySKUInfo inventorySKUInfo = new InventorySKUInfo();
				inventorySKUInfo.inventorySKU = data.getInventorySKU();
				inventorySKUInfo.inventorySKUQuantity = data.getInventoryQuantity();
				inventorySKUInfo.sellerSKUInfoList = new ArrayList<SellerSKUInfo>();
				inventorySKUInfo.sellerSKUInfoList.add(sellerSKUInfo);
				inventoryUnitList.add(inventorySKUInfo);
			}
		}
		
		return inventoryUnitList;
	}
	
	public ArrayList<SellerSKUInfo> getSellerSKUSales() throws Exception{
		ArrayList<SellerSKUInfo> sellerSKUUnitList = new ArrayList<SellerSKUInfo>();
		
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -salesStatisticDays);
		ResultSet rs = stmt.executeQuery(
				"select Store,sku,sum(quantity_purchased) as quantity from report_requested_or_scheduled_flat_file_order where purchase_date > '"
				+ new SimpleDateFormat("yyyyMMdd").format( c.getTime() )
				+ "' group by Store,sku");
		SellerSKUInfo sellerSKUUnit;
		while (rs.next()) {
			try {
				sellerSKUUnit = new SellerSKUInfo();
				sellerSKUUnit.sellerCode = rs.getString(1);
				sellerSKUUnit.sellerSKU = rs.getString(2);
				sellerSKUUnit.sellerSKUSales = Integer.parseInt(rs.getString(3));
				sellerSKUUnitList.add(sellerSKUUnit);
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		
		return sellerSKUUnitList;
	}
	
	/**
	 * @Description add sales info to inventoryUnitList
	 */
	public ArrayList<InventorySKUInfo> prepareAllocationUnit(
			ArrayList<InventorySKUInfo> inventoryUnitList,
			ArrayList<SellerSKUInfo> sellerSKUUnitList){
		
		if ( inventoryUnitList != null && sellerSKUUnitList != null && !sellerSKUUnitList.isEmpty() ) {
			for ( InventorySKUInfo inventoryUnit : inventoryUnitList ) {
				for ( SellerSKUInfo ssu1 : inventoryUnit.sellerSKUInfoList ) {
					for ( SellerSKUInfo ssu2 : sellerSKUUnitList ) {
						if ( ssu1.sellerCode.equalsIgnoreCase(ssu2.sellerCode)
								&& ssu1.sellerSKU.equals(ssu2.sellerSKU) ) 
							ssu1.sellerSKUSales = ssu2.sellerSKUSales;
					}
				}
			}
		}
		return inventoryUnitList;
	}
	
	/**
	 * @Description allocate inventory quantity (fulfill by merchant only), sellerSKU will get 0 while its status is "Off Shelf".
	 * 
	 * @param equalAllocatedProportion
	 *			Effective interval: [0,1], this parameter decide the proportion of inventory quantity which allocated equally,
	 *			the rest allocated by sales. 0 means allocated by sales only, 1 means allocated equally. null or less than 0 works as 0,
	 *			greater than 1 works as 1.
	 */
	public InventorySKUInfo allocation(InventorySKUInfo inventoryUnit,Double equalAllocatedProportion){
		
		if ( equalAllocatedProportion == null || equalAllocatedProportion < 0 ) equalAllocatedProportion = (double) 0;
		if ( equalAllocatedProportion > 1 ) equalAllocatedProportion = (double) 1;
		
		if ( inventoryUnit.inventorySKUQuantity <= 0 ) {
			for ( SellerSKUInfo sellerSKUUnit : inventoryUnit.sellerSKUInfoList ) {
				sellerSKUUnit.allocatedQuantity = 0;
			}
		} else {
			int totalSales = 0;
			int sellerSKUNum = 0;
			for ( SellerSKUInfo sellerSKUUnit : inventoryUnit.sellerSKUInfoList ) {
				if ( sellerSKUUnit.sellerSKUStatus == null
						|| !sellerSKUUnit.sellerSKUStatus.equals("Off Shelf") ) {
					sellerSKUNum++;
					if ( sellerSKUUnit.sellerSKUSales != null
							&& sellerSKUUnit.sellerSKUSales > 0 ) totalSales += sellerSKUUnit.sellerSKUSales;
				}
			}
			if ( inventoryUnit.inventorySKUQuantity < sellerSKUNum ){
				SellerSKUInfo maxSalesSellerSKUUnit = inventoryUnit.sellerSKUInfoList.get(0);
				int maxSales = 0;
				for ( SellerSKUInfo sellerSKUUnit : inventoryUnit.sellerSKUInfoList ) {
					sellerSKUUnit.allocatedQuantity = 0;
					if ( sellerSKUUnit.sellerSKUStatus == null
							|| !sellerSKUUnit.sellerSKUStatus.equals("Off Shelf")
							&& sellerSKUUnit.sellerSKUSales != null
							&& sellerSKUUnit.sellerSKUSales > maxSales ) maxSalesSellerSKUUnit = sellerSKUUnit;
				}
				maxSalesSellerSKUUnit.allocatedQuantity = inventoryUnit.inventorySKUQuantity;
			} else {
				int equalAllocatedQuantity = 1;
				try {
					equalAllocatedQuantity = new Double(inventoryUnit.inventorySKUQuantity*equalAllocatedProportion/sellerSKUNum).intValue();
					if ( equalAllocatedQuantity < 1 ) equalAllocatedQuantity = 1;
				} catch ( Exception e ) {}
				
				int remainingQuantity = inventoryUnit.inventorySKUQuantity - equalAllocatedQuantity * sellerSKUNum;
				int salesAllocatedQuantity = 0;
				
				for ( SellerSKUInfo sellerSKUUnit : inventoryUnit.sellerSKUInfoList ) {
					if ( sellerSKUUnit.sellerSKUStatus == null
							|| !sellerSKUUnit.sellerSKUStatus.equals("Off Shelf") ){
						if ( totalSales == 0 ) {
							salesAllocatedQuantity = remainingQuantity / sellerSKUNum;
							sellerSKUUnit.allocatedQuantity = salesAllocatedQuantity + equalAllocatedQuantity;
							remainingQuantity -= salesAllocatedQuantity;
							sellerSKUNum--;
						} else if (sellerSKUUnit.sellerSKUSales == null) {
							sellerSKUUnit.allocatedQuantity = equalAllocatedQuantity;
						} else {
							salesAllocatedQuantity = remainingQuantity * sellerSKUUnit.sellerSKUSales / totalSales;
							sellerSKUUnit.allocatedQuantity = salesAllocatedQuantity + equalAllocatedQuantity;
							remainingQuantity -= salesAllocatedQuantity;
							totalSales -= sellerSKUUnit.sellerSKUSales;
						}
					} else {
						sellerSKUUnit.allocatedQuantity = 0;
					}
				}
			}
		}
		return inventoryUnit;
	}
	
	public ArrayList<InventorySKUInfo> allocation(ArrayList<InventorySKUInfo> iuList, Double equalAllocatedProportion){
		ArrayList<InventorySKUInfo> inventoryUnitList = new ArrayList<InventorySKUInfo>();
		
		for ( InventorySKUInfo iu : iuList ) {
			try {
				InventorySKUInfo inventoryUnit = allocation(iu, equalAllocatedProportion);
				inventoryUnitList.add(inventoryUnit);
			} catch ( Exception e) {
				e.printStackTrace();
				// add log
			}
		}
		
		return inventoryUnitList;
	}
	
	public void generateFeedFileAndCallAmazon(ArrayList<InventorySKUInfo> inventoryUnitList, String feedFileDir){
		if ( inventoryUnitList == null
				|| inventoryUnitList.isEmpty()) {
			// add log
			return;
		}
		
		ArrayList<String> sellerCodeList = new ArrayList<String>();
		ArrayList<PrintWriter> pwList = new ArrayList<PrintWriter>();
		ArrayList<String> feedFilePathList = new ArrayList<String>();
		boolean isSellerFeedFileExists = false;
		
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
					"file/Feed/QuantityAndPrice/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_AllocationResult.txt",
					true)));
			pw.println("inventorySKU\tinventorySKUQuantity\tsellerCode\tsellerSKU\tsellerSKUStatus\tsellerSKUSales\tallocatedQuantity");
			for ( InventorySKUInfo inventoryUnit : inventoryUnitList )
				try {
					inventoryUnit.write(pw);
				} catch (Exception e) {
					e.printStackTrace();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for ( InventorySKUInfo inventoryUnit : inventoryUnitList ) {
			
			if ( inventoryUnit != null
					&& inventoryUnit.sellerSKUInfoList != null ) {
				for ( SellerSKUInfo sellerSKUUnit : inventoryUnit.sellerSKUInfoList ) {
					if ( sellerSKUUnit != null
							&& sellerSKUUnit.sellerSKU != null
							&& !sellerSKUUnit.sellerSKU.isEmpty()
							&& sellerSKUUnit.allocatedQuantity != null ) {
						isSellerFeedFileExists = false;
						for ( int i=0; i < sellerCodeList.size(); i++ ) {
							String sellerCode = sellerCodeList.get(i);
							if ( sellerSKUUnit.sellerCode.equals(sellerCode) ) {
								isSellerFeedFileExists = true;
								try {
									PrintWriter pw = pwList.get(i);
									pw.println( sellerSKUUnit.sellerSKU
											+ "\t" // price
											+ "\t" // minimum price
											+ "\t" // maximum price
											+ "\t" + sellerSKUUnit.allocatedQuantity
											+ "\t" // lead time to ship
											+ "\t" // fulfillment channel
											);
								} catch ( Exception e ) {
									e.printStackTrace();
									// add log
								}
								break;
							}
						}
						if ( !isSellerFeedFileExists ) {
							try {
								String FilePath = feedFileDir
										+ "/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
										+ "_" + sellerSKUUnit.sellerCode
										+ "_PriceAndQuantityInventoryFeed.txt";
								PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FilePath,true)));
								feedFilePathList.add(FilePath);
								pw.println("sku"
										+ "\t" + "price"
										+ "\t" + "minimum-seller-allowed-price"
										+ "\t" + "maximum-seller-allowed-price"
										+ "\t" + "quantity"
										+ "\t" + "leadtime-to-ship"
										+ "\t" + "fulfillment-channel");
								pwList.add(pw);
								sellerCodeList.add(sellerSKUUnit.sellerCode);
								pw.println( sellerSKUUnit.sellerSKU
										+ "\t" // price
										+ "\t" // minimum price
										+ "\t" // maximum price
										+ "\t" + sellerSKUUnit.allocatedQuantity
										+ "\t" // lead time to ship
										+ "\t" // fulfillment channel
										);
							} catch ( Exception e ) {
								e.printStackTrace();
								// add log
							}
						}
					} else {
						// add log "SellerSKUUnit invalid: sellerSKU/allocatedQuantity is null/empty"
					}
				}
			} else {
				// add log "InventoryUnit invalid: InventoryUnit/InventoryUnit.sellerSKUUnitList is null"
			}
		}
		// close pw
		for (PrintWriter pw : pwList) {
			try { pw.close(); } catch (Exception e) {}
		}
		
		for (String feedFilePath : feedFilePathList )
			try {
				new FeedClient(MerchantAccountDAO.getMerchantByName(feedFilePath.split("_")[1]))
					.submitFeed("_POST_FLAT_FILE_PRICEANDQUANTITYONLY_UPDATE_DATA_", feedFilePath);
			} catch ( Exception e ) {
				e.printStackTrace();
			}
	}
	
	public Message process(Message message) throws Exception{
		ArrayList<InventorySKUInfo> isiList = readOSSData((ArrayList<MerchantFulfillmentInventoryAllocationRequest>) message.getData());
		ArrayList<SellerSKUInfo> ssiList = getSellerSKUSales();
		isiList = prepareAllocationUnit(isiList, ssiList);
		isiList = allocation(isiList, equalAllocatedProportion);
		generateFeedFileAndCallAmazon(isiList, "file/Feed/QuantityAndPrice");
		System.out.println(new Date()
				+ "\t" + "Merchant Fulfillment Inventory Allocation --- Done!" );
		return null;
	}

	public static void main(String[] aaa){
		try {
//			String[] x = {"UP","PW","UK","KO","SS","XGL"};
//			for (String s : x)
			new FeedClient(MerchantAccountDAO.getMerchantByName("XGL")).submitFeed("_POST_FLAT_FILE_PRICEANDQUANTITYONLY_UPDATE_DATA_", "XGL.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
