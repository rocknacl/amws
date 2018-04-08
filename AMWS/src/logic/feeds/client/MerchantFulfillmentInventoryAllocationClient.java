package logic.feeds.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.amazonaws.mws.model.SubmitFeedResponse;

import amzint.feeds.FeedClient;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import logic.feeds.model.InventorySKUInfo;
import logic.feeds.model.SellerSKUInfo;

/**
 * @author D16
 * @Description merchant fulfillment inventory allocation client
 * @Create_Date JUN 20, 2016
 * @Last_Update JUN 20, 2016
 */

public class MerchantFulfillmentInventoryAllocationClient {
	
	/**
	 * @Description read file get from OSS
	 */
	private ArrayList<InventorySKUInfo> readFile(String ossFilePath) throws Exception{

		ArrayList<InventorySKUInfo> inventoryUnitList = new ArrayList<InventorySKUInfo>();
		InventorySKUInfo inventoryUnit = new InventorySKUInfo();
		
		ArrayList<String> columnNameList = new ArrayList<String>();
		ArrayList<Integer> columnNumList = new ArrayList<Integer>();
		columnNameList.add("Tongtool_SKU");
		columnNameList.add("Store_Name");
		columnNameList.add("Merchant_SKU");
		columnNameList.add("Inventory");
		columnNameList.add("Status");
		
		String line;
		String[] sa;
		int maxColumnNum = 0;
		
		String inventorySKU;
		String sellerCode;
		String sellerSKU;
		Integer inventorySKUQuantity;
		String sellerSKUStatus;
		
		String previousInventorySKU = "";
		
		BufferedReader br = new BufferedReader(new FileReader(ossFilePath));
		
		// check head line
		line = br.readLine();
		sa = line.split("\t");
		for ( int i=0; i < columnNameList.size(); i++ ) {
			String columnName = columnNameList.get(i);
			for ( int j=0; j< sa.length; j++ ) {
				if ( columnName.equals(sa[j]) ){
					Integer columnNum = j;
					columnNumList.add(columnNum);
					if ( j > maxColumnNum ) maxColumnNum = j; 
				}
			}
		}
		if ( columnNumList.size() < columnNameList.size() ) {
			// add log "head line error"
			try { br.close(); } catch (Exception e) {}
			return null;
		}
		
		// read line(2-$)
		while ((line = br.readLine()) != null) {
			try {
				sa = line.split("\t");
				
				// check line column number and column value
				if ( sa.length < maxColumnNum + 1 ) {
					// add log "line column miss error"
					continue;
				} else {
					boolean isColumnValueValid = true;
					for ( Integer columnNum : columnNumList ) {
						if ( sa[columnNum].isEmpty() ) {
							isColumnValueValid = false;
							break;
						}
					}
					if ( !isColumnValueValid ) {
						// add log " column value is empty "
						continue;
					}
				}
				
				// read line value
				inventorySKU = sa[columnNumList.get(0)];
				sellerCode = sa[columnNumList.get(1)];
				sellerSKU = sa[columnNumList.get(2)];
				inventorySKUQuantity = Integer.parseInt(sa[columnNumList.get(3)].replaceAll("\\D",""));
				sellerSKUStatus = sa[columnNumList.get(4)];
				
				SellerSKUInfo sellerSKUUnit = new SellerSKUInfo();
				sellerSKUUnit.sellerCode = sellerCode;
				sellerSKUUnit.sellerSKU = sellerSKU;
				sellerSKUUnit.sellerSKUStatus = sellerSKUStatus;
				
				if ( inventorySKU.equals(previousInventorySKU) ) {
					inventoryUnit.sellerSKUInfoList.add(sellerSKUUnit);
				} else {
					// add previous inventoryUnit to inventoryUnitList
					if ( !previousInventorySKU.equals("")
							&& inventoryUnit != null 
							&& inventoryUnit.sellerSKUInfoList != null
							&& !inventoryUnit.sellerSKUInfoList.isEmpty() ) {
						inventoryUnitList.add(inventoryUnit);
					}
					// new inventoryUnit
					inventoryUnit = new InventorySKUInfo();
					previousInventorySKU = "";
					
					inventoryUnit.inventorySKU = inventorySKU;
					inventoryUnit.inventorySKUQuantity = inventorySKUQuantity;
					inventoryUnit.sellerSKUInfoList = new ArrayList<SellerSKUInfo>();
					
					inventoryUnit.sellerSKUInfoList.add(sellerSKUUnit);
					previousInventorySKU = inventorySKU;
					
				}
				
			} catch ( Exception e ) {
				e.printStackTrace();
				// add log "read line error"
			}
		}
		// add last inventoryUnit to inventoryUnitList
		if ( !previousInventorySKU.equals("")
				&& inventoryUnit != null 
				&& inventoryUnit.sellerSKUInfoList != null
				&& !inventoryUnit.sellerSKUInfoList.isEmpty() ) {
			inventoryUnitList.add(inventoryUnit);
		}
		
		try { br.close(); } catch (Exception e) {}
		
		return inventoryUnitList;
	}

	/**
	 * @Description result set --> ArrayList<SellerSKUUnit>
	 */
	private ArrayList<SellerSKUInfo> getSellerSKUSales(ResultSet rs) throws Exception{
		ArrayList<SellerSKUInfo> sellerSKUUnitList = new ArrayList<SellerSKUInfo>();
		
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
	private ArrayList<InventorySKUInfo> prepareAllocationUnit(
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
	private InventorySKUInfo allocation(InventorySKUInfo inventoryUnit,Double equalAllocatedProportion){
		
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

	private ArrayList<InventorySKUInfo> allocation(ArrayList<InventorySKUInfo> iuList, Double equalAllocatedProportion){
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

	/**
	 * @Description inventoryUnitList --> feed file
	 * 
	 * @param fileDir
	 * 			fielDir can't end with '/', correct example: 'data/feed', incorrect example: 'data/feed/'
	 */
	private void generateFeedFile(ArrayList<InventorySKUInfo> inventoryUnitList, String feedFileDir){
		if ( inventoryUnitList == null
				|| inventoryUnitList.isEmpty()) {
			// add log
			return;
		}
		
		ArrayList<String> sellerCodeList = new ArrayList<String>();
		ArrayList<PrintWriter> pwList = new ArrayList<PrintWriter>();
		boolean isSellerFeedFileExists = false;
		
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
	}

	/**
	 * Merchant Fulfillment Inventory Allocation.
	 * 
	 * @param ossFilePath
	 * 			path of file download from OSS, include dir and file name.
	 * 
	 * @param rs
	 * 			result set of query result from mysql, three columns: merchant,seller SKU,sales.
	 * 
	 * @param feedFileDir
	 * 			this method will generate some feed files for amazon, the feed file dir is the place to put those files
	 * 
	 * @param equalAllocatedProportion
	 *			Effective interval: [0,1], this parameter decide the proportion of inventory quantity which allocated equally,
	 *			the rest allocated by sales. 0 means allocated by sales only, 1 means allocated equally. null or less than 0 works as 0,
	 *			greater than 1 works as 1. 
	 */
	public void merchantFulfillmentInventoryAllocation (
			String feedFileDir,
			String feedFileHistoryDir,
			String ossFileDir,
			double equalAllocatedProportion,
			int deltaDays) throws Exception{

		String ossFilePath = ossFileDir
				+ "/"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ "_PriceAndQuantityInventory";
		
		// download file from oss
		// TODO
		
		// get sales info from mysql
		ResultSet rs = null;
		
		ArrayList<InventorySKUInfo> iuList = readFile(ossFilePath);
		ArrayList<SellerSKUInfo> ssuList = getSellerSKUSales(rs);
		iuList = prepareAllocationUnit(iuList,ssuList);
		iuList = allocation(iuList,equalAllocatedProportion);
		generateFeedFile(iuList,feedFileDir);
		
		File[] feedFileList = new File( feedFileDir ).listFiles();
		for ( File file : feedFileList ) {
			try {
				String sellerCode = file.getName().split("_")[1];
				MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(sellerCode);
				SubmitFeedResponse submitFeedResponse
					= new FeedClient(merchant).submitFeed("_POST_FLAT_FILE_PRICEANDQUANTITYONLY_UPDATE_DATA_", file.getPath());
			
				if ( submitFeedResponse == null ) {
					System.out.println("\t" + sellerCode + "\t" +  file.getName() + "\tFailed");
				} else {
					System.out.println("\t" + sellerCode + "\t" + file.getName()
							+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedSubmissionId()
							+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedType()
							+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedProcessingStatus());
				}
				
				// move feed file --> feed file history
				file.renameTo(new File(feedFileHistoryDir + "/" + file.getName()));
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
}
