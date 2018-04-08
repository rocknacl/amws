package control.dataSynchronization.oss_api.models;

import java.io.PrintWriter;
import java.io.Serializable;

public class BasicDataSellerSKUAFNRestockResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static String dateFormat = "yyyyMMddHHmmss";
	private String inventoryDate;
	private String sellerCode;
	private String sellerSKU;
	private String asin;
	private Integer quantitySellable;
	private Integer quantityUnsellable;
	private Integer salesVolumnDay1;
	private Integer salesVolumnDay7;
	private Integer salesVolumnDay30;
	private Integer salesVolumnDay90;
	private Integer salesVolumnTotal;
	private Integer outOfStockDaysIn30Days;
	private Integer onSaleDaysIn30Days;
	private Double predictedSalesVolumnWeekly;
	
	public void writeName(PrintWriter pw){
		pw.println("Inventory Name"
					+ "\t" + "Seller Code"
					+ "\t" + "Seller SKU"
					+ "\t" + "ASIN"
					+ "\t" + "Quantity Sellable"
					+ "\t" + "Quantity Unsellable"
					+ "\t" + "Seles Volumn Day 1"
					+ "\t" + "Seles Volumn Day 7"
					+ "\t" + "Seles Volumn Day 30"
					+ "\t" + "Seles Volumn Day 90"
					+ "\t" + "Seles Volumn Day Total"
					+ "\t" + "Out Of Stock Days In 30 Days"
					+ "\t" + "On Sale Days In 30 Days"
					+ "\t" + "Predicted Sales Volumn Weekly");
	}
	
	public void writeValue(PrintWriter pw){
		pw.println(inventoryDate
					+ "\t" + sellerCode
					+ "\t" + sellerSKU
					+ "\t" + asin
					+ "\t" + quantitySellable
					+ "\t" + quantityUnsellable
					+ "\t" + salesVolumnDay1
					+ "\t" + salesVolumnDay7
					+ "\t" + salesVolumnDay30
					+ "\t" + salesVolumnDay90
					+ "\t" + salesVolumnTotal
					+ "\t" + outOfStockDaysIn30Days
					+ "\t" + onSaleDaysIn30Days
					+ "\t" + predictedSalesVolumnWeekly);
	}
	
	public BasicDataSellerSKUAFNRestockResponse(){
		
	}
	public BasicDataSellerSKUAFNRestockResponse(
			String inventoryDate,
			String sellerCode,
			String sellerSKU,
			String asin,
			Integer quantitySellable,
			Integer quantityUnsellable,
			Integer salesVolumnDay1,
			Integer salesVolumnDay7,
			Integer salesVolumnDay30,
			Integer salesVolumnDay90,
			Integer salesVolumnTotal,
			Integer outOfStockDaysIn30Days,
			Integer onSaleDaysIn30Days,
			Double predictedSalesVolumnWeekly){
		this.inventoryDate = inventoryDate;
		this.sellerCode = sellerCode;
		this.sellerSKU = sellerSKU;
		this.asin = asin;
		this.quantitySellable = quantitySellable;
		this.quantityUnsellable = quantityUnsellable;
		this.salesVolumnDay1 = salesVolumnDay1;
		this.salesVolumnDay7 = salesVolumnDay7;
		this.salesVolumnDay30 = salesVolumnDay30;
		this.salesVolumnDay90 = salesVolumnDay90;
		this.salesVolumnTotal = salesVolumnTotal;
		this.outOfStockDaysIn30Days = outOfStockDaysIn30Days;
		this.onSaleDaysIn30Days = onSaleDaysIn30Days;
		this.predictedSalesVolumnWeekly = predictedSalesVolumnWeekly;
	}

	public String getInventoryDate() {
		return inventoryDate;
	}
	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
	}
	public BasicDataSellerSKUAFNRestockResponse withInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
		return this;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public BasicDataSellerSKUAFNRestockResponse withSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
		return this;
	}
	public String getSellerSKU() {
		return sellerSKU;
	}
	public void setSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
	}
	public BasicDataSellerSKUAFNRestockResponse withSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
		return this;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public BasicDataSellerSKUAFNRestockResponse withAsin(String asin) {
		this.asin = asin;
		return this;
	}
	public Integer getQuantitySellable() {
		return quantitySellable;
	}
	public void setQuantitySellable(Integer quantitySellable) {
		this.quantitySellable = quantitySellable;
	}
	public BasicDataSellerSKUAFNRestockResponse withQuantitySellable(Integer quantitySellable) {
		this.quantitySellable = quantitySellable;
		return this;
	}
	public Integer getQuantityUnsellable() {
		return quantityUnsellable;
	}
	public void setQuantityUnsellable(Integer quantityUnsellable) {
		this.quantityUnsellable = quantityUnsellable;
	}
	public BasicDataSellerSKUAFNRestockResponse withQuantityUnsellable(Integer quantityUnsellable) {
		this.quantityUnsellable = quantityUnsellable;
		return this;
	}
	public Integer getSalesVolumnDay1() {
		return salesVolumnDay1;
	}
	public void setSalesVolumnDay1(Integer salesVolumnDay1) {
		this.salesVolumnDay1 = salesVolumnDay1;
	}
	public BasicDataSellerSKUAFNRestockResponse withSalesVolumnDay1(Integer salesVolumnDay1) {
		this.salesVolumnDay1 = salesVolumnDay1;
		return this;
	}
	public Integer getSalesVolumnDay7() {
		return salesVolumnDay7;
	}
	public void setSalesVolumnDay7(Integer salesVolumnDay7) {
		this.salesVolumnDay7 = salesVolumnDay7;
	}
	public BasicDataSellerSKUAFNRestockResponse withSalesVolumnDay7(Integer salesVolumnDay7) {
		this.salesVolumnDay7 = salesVolumnDay7;
		return this;
	}
	public Integer getSalesVolumnDay30() {
		return salesVolumnDay30;
	}
	public void setSalesVolumnDay30(Integer salesVolumnDay30) {
		this.salesVolumnDay30 = salesVolumnDay30;
	}
	public BasicDataSellerSKUAFNRestockResponse withSalesVolumnDay30(Integer salesVolumnDay30) {
		this.salesVolumnDay30 = salesVolumnDay30;
		return this;
	}
	public Integer getSalesVolumnDay90() {
		return salesVolumnDay90;
	}
	public void setSalesVolumnDay90(Integer salesVolumnDay90) {
		this.salesVolumnDay90 = salesVolumnDay90;
	}
	public BasicDataSellerSKUAFNRestockResponse withSalesVolumnDay90(Integer salesVolumnDay90) {
		this.salesVolumnDay90 = salesVolumnDay90;
		return this;
	}
	public Integer getSalesVolumnTotal() {
		return salesVolumnTotal;
	}
	public void setSalesVolumnTotal(Integer salesVolumnTotal) {
		this.salesVolumnTotal = salesVolumnTotal;
	}
	public BasicDataSellerSKUAFNRestockResponse withSalesVolumnTotal(Integer salesVolumnTotal) {
		this.salesVolumnTotal = salesVolumnTotal;
		return this;
	}
	public Integer getOutOfStockDaysIn30Days() {
		return outOfStockDaysIn30Days;
	}
	public void setOutOfStockDaysIn30Days(Integer outOfStockDaysIn30Days) {
		this.outOfStockDaysIn30Days = outOfStockDaysIn30Days;
	}
	public BasicDataSellerSKUAFNRestockResponse withOutOfStockDaysIn30Days(Integer outOfStockDaysIn30Days) {
		this.outOfStockDaysIn30Days = outOfStockDaysIn30Days;
		return this;
	}
	public Integer getOnSaleDaysIn30Days() {
		return onSaleDaysIn30Days;
	}
	public void setOnSaleDaysIn30Days(Integer onSaleDaysIn30Days) {
		this.onSaleDaysIn30Days = onSaleDaysIn30Days;
	}
	public BasicDataSellerSKUAFNRestockResponse withOnSaleDaysIn30Days(Integer onSaleDaysIn30Days) {
		this.onSaleDaysIn30Days = onSaleDaysIn30Days;
		return this;
	}
	public Double getPredictedSalesVolumnWeekly() {
		return predictedSalesVolumnWeekly;
	}
	public void setPredictedSalesVolumnWeekly(Double predictedSalesVolumnWeekly) {
		this.predictedSalesVolumnWeekly = predictedSalesVolumnWeekly;
	}
	public BasicDataSellerSKUAFNRestockResponse withPredictedSalesVolumnWeekly(Double predictedSalesVolumnWeekly) {
		this.predictedSalesVolumnWeekly = predictedSalesVolumnWeekly;
		return this;
	}
}
