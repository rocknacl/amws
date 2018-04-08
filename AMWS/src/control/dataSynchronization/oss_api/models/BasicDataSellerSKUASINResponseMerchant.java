package control.dataSynchronization.oss_api.models;

import java.util.ArrayList;

public class BasicDataSellerSKUASINResponseMerchant {

	private String sellerCode;
	private ArrayList<BasicDataSellerSKUASINResponse> list;
	
	public BasicDataSellerSKUASINResponseMerchant(){
		
	}
	public BasicDataSellerSKUASINResponseMerchant(
			String sellerCode,
			ArrayList<BasicDataSellerSKUASINResponse> list){
		this.sellerCode = sellerCode;
		this.list = list;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public BasicDataSellerSKUASINResponseMerchant withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public ArrayList<BasicDataSellerSKUASINResponse> getList() {
		return list;
	}
	public void setList(ArrayList<BasicDataSellerSKUASINResponse> list) {
		this.list = list;
	}
	public BasicDataSellerSKUASINResponseMerchant withList(ArrayList<BasicDataSellerSKUASINResponse> list) {
		this.list = list;
		return this;
	}
	
}
