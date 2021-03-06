package dao.entities;
// Generated 2016-8-8 16:52:31 by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * ReportRequestedOrScheduledFlatFileOrder generated by hbm2java
 */
public class ReportRequestedOrScheduledFlatFileOrder implements java.io.Serializable {

	private Integer id;
	private String store;
	private Date insertDate;
	private String orderId;
	private String orderItemId;
	private Date purchaseDate;
	private Date paymentsDate;
	private String buyerEmail;
	private String buyerName;
	private String buyerPhoneNumber;
	private String sku;
	private String productName;
	private Integer quantityPurchased;
	private String currency;
	private BigDecimal itemPrice;
	private BigDecimal itemTax;
	private BigDecimal shippingPrice;
	private BigDecimal shippingTax;
	private String shipServiceLevel;
	private String recipientName;
	private String shipAddress1;
	private String shipAddress2;
	private String shipAddress3;
	private String shipPostalCode;
	private String shipCity;
	private String shipState;
	private String shipCountry;
	private String shipPhoneNumber;
	private String itemPromotionDiscount;
	private String itemPromotionId;
	private String shipPromotionDiscount;
	private String shipPromotionId;
	private Date deliveryStartDate;
	private Date deliveryEndDate;
	private String deliveryTimeZone;
	private String deliveryInstructions;
	private String salesChannel;
	private Date earliestShipDate;
	private Date latestShipDate;
	private Date earliestDeliveryDate;
	private Date latestDeliveryDate;
	private String isBusinessOrder;
	private String purchaseOrderNumber;
	private BigDecimal priceDesignation;

	public ReportRequestedOrScheduledFlatFileOrder() {
	}

	public ReportRequestedOrScheduledFlatFileOrder(String store, Date insertDate, String orderId, String orderItemId,
			Date purchaseDate, Date paymentsDate, String buyerEmail, String buyerName, String buyerPhoneNumber,
			String sku, String productName, Integer quantityPurchased, String currency, BigDecimal itemPrice,
			BigDecimal itemTax, BigDecimal shippingPrice, BigDecimal shippingTax, String shipServiceLevel,
			String recipientName, String shipAddress1, String shipAddress2, String shipAddress3, String shipPostalCode,
			String shipCity, String shipState, String shipCountry, String shipPhoneNumber, String itemPromotionDiscount,
			String itemPromotionId, String shipPromotionDiscount, String shipPromotionId, Date deliveryStartDate,
			Date deliveryEndDate, String deliveryTimeZone, String deliveryInstructions, String salesChannel,
			Date earliestShipDate, Date latestShipDate, Date earliestDeliveryDate, Date latestDeliveryDate,
			String isBusinessOrder, String purchaseOrderNumber, BigDecimal priceDesignation) {
		this.store = store;
		this.insertDate = insertDate;
		this.orderId = orderId;
		this.orderItemId = orderItemId;
		this.purchaseDate = purchaseDate;
		this.paymentsDate = paymentsDate;
		this.buyerEmail = buyerEmail;
		this.buyerName = buyerName;
		this.buyerPhoneNumber = buyerPhoneNumber;
		this.sku = sku;
		this.productName = productName;
		this.quantityPurchased = quantityPurchased;
		this.currency = currency;
		this.itemPrice = itemPrice;
		this.itemTax = itemTax;
		this.shippingPrice = shippingPrice;
		this.shippingTax = shippingTax;
		this.shipServiceLevel = shipServiceLevel;
		this.recipientName = recipientName;
		this.shipAddress1 = shipAddress1;
		this.shipAddress2 = shipAddress2;
		this.shipAddress3 = shipAddress3;
		this.shipPostalCode = shipPostalCode;
		this.shipCity = shipCity;
		this.shipState = shipState;
		this.shipCountry = shipCountry;
		this.shipPhoneNumber = shipPhoneNumber;
		this.itemPromotionDiscount = itemPromotionDiscount;
		this.itemPromotionId = itemPromotionId;
		this.shipPromotionDiscount = shipPromotionDiscount;
		this.shipPromotionId = shipPromotionId;
		this.deliveryStartDate = deliveryStartDate;
		this.deliveryEndDate = deliveryEndDate;
		this.deliveryTimeZone = deliveryTimeZone;
		this.deliveryInstructions = deliveryInstructions;
		this.salesChannel = salesChannel;
		this.earliestShipDate = earliestShipDate;
		this.latestShipDate = latestShipDate;
		this.earliestDeliveryDate = earliestDeliveryDate;
		this.latestDeliveryDate = latestDeliveryDate;
		this.isBusinessOrder = isBusinessOrder;
		this.purchaseOrderNumber = purchaseOrderNumber;
		this.priceDesignation = priceDesignation;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStore() {
		return this.store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderItemId() {
		return this.orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getPaymentsDate() {
		return this.paymentsDate;
	}

	public void setPaymentsDate(Date paymentsDate) {
		this.paymentsDate = paymentsDate;
	}

	public String getBuyerEmail() {
		return this.buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerName() {
		return this.buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerPhoneNumber() {
		return this.buyerPhoneNumber;
	}

	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		this.buyerPhoneNumber = buyerPhoneNumber;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantityPurchased() {
		return this.quantityPurchased;
	}

	public void setQuantityPurchased(Integer quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getItemTax() {
		return this.itemTax;
	}

	public void setItemTax(BigDecimal itemTax) {
		this.itemTax = itemTax;
	}

	public BigDecimal getShippingPrice() {
		return this.shippingPrice;
	}

	public void setShippingPrice(BigDecimal shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public BigDecimal getShippingTax() {
		return this.shippingTax;
	}

	public void setShippingTax(BigDecimal shippingTax) {
		this.shippingTax = shippingTax;
	}

	public String getShipServiceLevel() {
		return this.shipServiceLevel;
	}

	public void setShipServiceLevel(String shipServiceLevel) {
		this.shipServiceLevel = shipServiceLevel;
	}

	public String getRecipientName() {
		return this.recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getShipAddress1() {
		return this.shipAddress1;
	}

	public void setShipAddress1(String shipAddress1) {
		this.shipAddress1 = shipAddress1;
	}

	public String getShipAddress2() {
		return this.shipAddress2;
	}

	public void setShipAddress2(String shipAddress2) {
		this.shipAddress2 = shipAddress2;
	}

	public String getShipAddress3() {
		return this.shipAddress3;
	}

	public void setShipAddress3(String shipAddress3) {
		this.shipAddress3 = shipAddress3;
	}

	public String getShipPostalCode() {
		return this.shipPostalCode;
	}

	public void setShipPostalCode(String shipPostalCode) {
		this.shipPostalCode = shipPostalCode;
	}

	public String getShipCity() {
		return this.shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipState() {
		return this.shipState;
	}

	public void setShipState(String shipState) {
		this.shipState = shipState;
	}

	public String getShipCountry() {
		return this.shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

	public String getShipPhoneNumber() {
		return this.shipPhoneNumber;
	}

	public void setShipPhoneNumber(String shipPhoneNumber) {
		this.shipPhoneNumber = shipPhoneNumber;
	}

	public String getItemPromotionDiscount() {
		return this.itemPromotionDiscount;
	}

	public void setItemPromotionDiscount(String itemPromotionDiscount) {
		this.itemPromotionDiscount = itemPromotionDiscount;
	}

	public String getItemPromotionId() {
		return this.itemPromotionId;
	}

	public void setItemPromotionId(String itemPromotionId) {
		this.itemPromotionId = itemPromotionId;
	}

	public String getShipPromotionDiscount() {
		return this.shipPromotionDiscount;
	}

	public void setShipPromotionDiscount(String shipPromotionDiscount) {
		this.shipPromotionDiscount = shipPromotionDiscount;
	}

	public String getShipPromotionId() {
		return this.shipPromotionId;
	}

	public void setShipPromotionId(String shipPromotionId) {
		this.shipPromotionId = shipPromotionId;
	}

	public Date getDeliveryStartDate() {
		return this.deliveryStartDate;
	}

	public void setDeliveryStartDate(Date deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}

	public Date getDeliveryEndDate() {
		return this.deliveryEndDate;
	}

	public void setDeliveryEndDate(Date deliveryEndDate) {
		this.deliveryEndDate = deliveryEndDate;
	}

	public String getDeliveryTimeZone() {
		return this.deliveryTimeZone;
	}

	public void setDeliveryTimeZone(String deliveryTimeZone) {
		this.deliveryTimeZone = deliveryTimeZone;
	}

	public String getDeliveryInstructions() {
		return this.deliveryInstructions;
	}

	public void setDeliveryInstructions(String deliveryInstructions) {
		this.deliveryInstructions = deliveryInstructions;
	}

	public String getSalesChannel() {
		return this.salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public Date getEarliestShipDate() {
		return this.earliestShipDate;
	}

	public void setEarliestShipDate(Date earliestShipDate) {
		this.earliestShipDate = earliestShipDate;
	}

	public Date getLatestShipDate() {
		return this.latestShipDate;
	}

	public void setLatestShipDate(Date latestShipDate) {
		this.latestShipDate = latestShipDate;
	}

	public Date getEarliestDeliveryDate() {
		return this.earliestDeliveryDate;
	}

	public void setEarliestDeliveryDate(Date earliestDeliveryDate) {
		this.earliestDeliveryDate = earliestDeliveryDate;
	}

	public Date getLatestDeliveryDate() {
		return this.latestDeliveryDate;
	}

	public void setLatestDeliveryDate(Date latestDeliveryDate) {
		this.latestDeliveryDate = latestDeliveryDate;
	}

	public String getIsBusinessOrder() {
		return this.isBusinessOrder;
	}

	public void setIsBusinessOrder(String isBusinessOrder) {
		this.isBusinessOrder = isBusinessOrder;
	}

	public String getPurchaseOrderNumber() {
		return this.purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public BigDecimal getPriceDesignation() {
		return this.priceDesignation;
	}

	public void setPriceDesignation(BigDecimal priceDesignation) {
		this.priceDesignation = priceDesignation;
	}

}
