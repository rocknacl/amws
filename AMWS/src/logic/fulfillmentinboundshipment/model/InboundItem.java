package logic.fulfillmentinboundshipment.model;

public class InboundItem {

	public String sellerSKU;
	public String fulfillmentNetworkSKU;
	public Integer quantityShipped;
	public Integer quantityReceived;
	public Integer quantityInCases;
	
	public String getColumnNameString(){
		return "SellerSKU"
				+ "\t" + "FulfillmentNetworkSKU"
				+ "\t" + "QuantityShipped"
				+ "\t" + "QuantityReceived"
				+ "\t" + "QuantityInCases";
	}
	
	public void printColumnName(int tabNum){
		String tabIndentation = "";
		for ( int i = 0; i < tabNum; i++ ) tabIndentation = tabIndentation + "\t";
		System.out.println(tabIndentation
				+ getColumnNameString());
	}
	
	public String getColumnValueString(){
		return sellerSKU
				+ "\t" + fulfillmentNetworkSKU
				+ "\t" + quantityShipped
				+ "\t" + quantityReceived
				+ "\t" + quantityInCases;
	}
	
	public void printColumnValue(int tabNum){
		String tabIndentation = "";
		for ( int i = 0; i < tabNum; i++ ) tabIndentation = tabIndentation + "\t";
		System.out.println(tabIndentation
				+ getColumnValueString());
	}
	
	public void print(int tabNum){
		printColumnName(tabNum);
		printColumnValue(tabNum);
	}
	
}
