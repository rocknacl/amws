package logic.feeds.model;

/**
 * @author D16
 * @Description Seller SKU Unit 
 * @Create_Date JUN 16, 2016
 * @Last_Update JUN 17, 2016
 */

public class SellerSKUInfo {

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
