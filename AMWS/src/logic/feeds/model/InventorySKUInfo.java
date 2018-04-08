package logic.feeds.model;

import java.util.ArrayList;

/**
 * @author D16
 * @Description InventoryUnit 
 * @Create_Date JUN 16, 2016
 * @Last_Update JUN 17, 2016
 */

public class InventorySKUInfo {

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
	
}
