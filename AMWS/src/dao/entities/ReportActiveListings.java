package dao.entities;
// Generated 2016-8-8 16:52:31 by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * ReportActiveListings generated by hbm2java
 */
public class ReportActiveListings implements java.io.Serializable {

	private Integer id;
	private String store;
	private String listingId;
	private Date insertDate;
	private String itemName;
	private String itemDescription;
	private String sellerSku;
	private BigDecimal price;
	private Integer quantity;
	private Date openDate;
	private String imageUrl;
	private String itemIsMarketplace;
	private String productIdType;
	private String zshopShippingFee;
	private String itemNote;
	private String itemCondition;
	private String zshopCategory1;
	private String zshopBrowsePath;
	private String zshopStorefrontFeature;
	private String asin1;
	private String asin2;
	private String asin3;
	private Integer willShipInternationally;
	private String expeditedShipping;
	private String zshopBoldface;
	private String productId;
	private String bidForFeaturedPlacement;
	private String addDelete;
	private String pendingQuantity;
	private String fulfillmentChannel;
	private String businessPrice;
	private String quantityPriceType;
	private BigDecimal quantityLowerBound1;
	private BigDecimal quantityPrice1;
	private BigDecimal quantityLowerBound2;
	private BigDecimal quantityPrice2;
	private BigDecimal quantityLowerBound3;
	private BigDecimal quantityPrice3;
	private BigDecimal quantityLowerBound4;
	private BigDecimal quantityPrice4;
	private BigDecimal quantityLowerBound5;
	private BigDecimal quantityPrice5;

	public ReportActiveListings() {
	}

	public ReportActiveListings(String store, String listingId, Date insertDate, String itemName,
			String itemDescription, String sellerSku, BigDecimal price, Integer quantity, Date openDate,
			String imageUrl, String itemIsMarketplace, String productIdType, String zshopShippingFee, String itemNote,
			String itemCondition, String zshopCategory1, String zshopBrowsePath, String zshopStorefrontFeature,
			String asin1, String asin2, String asin3, Integer willShipInternationally, String expeditedShipping,
			String zshopBoldface, String productId, String bidForFeaturedPlacement, String addDelete,
			String pendingQuantity, String fulfillmentChannel, String businessPrice, String quantityPriceType,
			BigDecimal quantityLowerBound1, BigDecimal quantityPrice1, BigDecimal quantityLowerBound2,
			BigDecimal quantityPrice2, BigDecimal quantityLowerBound3, BigDecimal quantityPrice3,
			BigDecimal quantityLowerBound4, BigDecimal quantityPrice4, BigDecimal quantityLowerBound5,
			BigDecimal quantityPrice5) {
		this.store = store;
		this.listingId = listingId;
		this.insertDate = insertDate;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.sellerSku = sellerSku;
		this.price = price;
		this.quantity = quantity;
		this.openDate = openDate;
		this.imageUrl = imageUrl;
		this.itemIsMarketplace = itemIsMarketplace;
		this.productIdType = productIdType;
		this.zshopShippingFee = zshopShippingFee;
		this.itemNote = itemNote;
		this.itemCondition = itemCondition;
		this.zshopCategory1 = zshopCategory1;
		this.zshopBrowsePath = zshopBrowsePath;
		this.zshopStorefrontFeature = zshopStorefrontFeature;
		this.asin1 = asin1;
		this.asin2 = asin2;
		this.asin3 = asin3;
		this.willShipInternationally = willShipInternationally;
		this.expeditedShipping = expeditedShipping;
		this.zshopBoldface = zshopBoldface;
		this.productId = productId;
		this.bidForFeaturedPlacement = bidForFeaturedPlacement;
		this.addDelete = addDelete;
		this.pendingQuantity = pendingQuantity;
		this.fulfillmentChannel = fulfillmentChannel;
		this.businessPrice = businessPrice;
		this.quantityPriceType = quantityPriceType;
		this.quantityLowerBound1 = quantityLowerBound1;
		this.quantityPrice1 = quantityPrice1;
		this.quantityLowerBound2 = quantityLowerBound2;
		this.quantityPrice2 = quantityPrice2;
		this.quantityLowerBound3 = quantityLowerBound3;
		this.quantityPrice3 = quantityPrice3;
		this.quantityLowerBound4 = quantityLowerBound4;
		this.quantityPrice4 = quantityPrice4;
		this.quantityLowerBound5 = quantityLowerBound5;
		this.quantityPrice5 = quantityPrice5;
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

	public String getListingId() {
		return this.listingId;
	}

	public void setListingId(String listingId) {
		this.listingId = listingId;
	}

	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return this.itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getSellerSku() {
		return this.sellerSku;
	}

	public void setSellerSku(String sellerSku) {
		this.sellerSku = sellerSku;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getOpenDate() {
		return this.openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getItemIsMarketplace() {
		return this.itemIsMarketplace;
	}

	public void setItemIsMarketplace(String itemIsMarketplace) {
		this.itemIsMarketplace = itemIsMarketplace;
	}

	public String getProductIdType() {
		return this.productIdType;
	}

	public void setProductIdType(String productIdType) {
		this.productIdType = productIdType;
	}

	public String getZshopShippingFee() {
		return this.zshopShippingFee;
	}

	public void setZshopShippingFee(String zshopShippingFee) {
		this.zshopShippingFee = zshopShippingFee;
	}

	public String getItemNote() {
		return this.itemNote;
	}

	public void setItemNote(String itemNote) {
		this.itemNote = itemNote;
	}

	public String getItemCondition() {
		return this.itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}

	public String getZshopCategory1() {
		return this.zshopCategory1;
	}

	public void setZshopCategory1(String zshopCategory1) {
		this.zshopCategory1 = zshopCategory1;
	}

	public String getZshopBrowsePath() {
		return this.zshopBrowsePath;
	}

	public void setZshopBrowsePath(String zshopBrowsePath) {
		this.zshopBrowsePath = zshopBrowsePath;
	}

	public String getZshopStorefrontFeature() {
		return this.zshopStorefrontFeature;
	}

	public void setZshopStorefrontFeature(String zshopStorefrontFeature) {
		this.zshopStorefrontFeature = zshopStorefrontFeature;
	}

	public String getAsin1() {
		return this.asin1;
	}

	public void setAsin1(String asin1) {
		this.asin1 = asin1;
	}

	public String getAsin2() {
		return this.asin2;
	}

	public void setAsin2(String asin2) {
		this.asin2 = asin2;
	}

	public String getAsin3() {
		return this.asin3;
	}

	public void setAsin3(String asin3) {
		this.asin3 = asin3;
	}

	public Integer getWillShipInternationally() {
		return this.willShipInternationally;
	}

	public void setWillShipInternationally(Integer willShipInternationally) {
		this.willShipInternationally = willShipInternationally;
	}

	public String getExpeditedShipping() {
		return this.expeditedShipping;
	}

	public void setExpeditedShipping(String expeditedShipping) {
		this.expeditedShipping = expeditedShipping;
	}

	public String getZshopBoldface() {
		return this.zshopBoldface;
	}

	public void setZshopBoldface(String zshopBoldface) {
		this.zshopBoldface = zshopBoldface;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getBidForFeaturedPlacement() {
		return this.bidForFeaturedPlacement;
	}

	public void setBidForFeaturedPlacement(String bidForFeaturedPlacement) {
		this.bidForFeaturedPlacement = bidForFeaturedPlacement;
	}

	public String getAddDelete() {
		return this.addDelete;
	}

	public void setAddDelete(String addDelete) {
		this.addDelete = addDelete;
	}

	public String getPendingQuantity() {
		return this.pendingQuantity;
	}

	public void setPendingQuantity(String pendingQuantity) {
		this.pendingQuantity = pendingQuantity;
	}

	public String getFulfillmentChannel() {
		return this.fulfillmentChannel;
	}

	public void setFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
	}

	public String getBusinessPrice() {
		return this.businessPrice;
	}

	public void setBusinessPrice(String businessPrice) {
		this.businessPrice = businessPrice;
	}

	public String getQuantityPriceType() {
		return this.quantityPriceType;
	}

	public void setQuantityPriceType(String quantityPriceType) {
		this.quantityPriceType = quantityPriceType;
	}

	public BigDecimal getQuantityLowerBound1() {
		return this.quantityLowerBound1;
	}

	public void setQuantityLowerBound1(BigDecimal quantityLowerBound1) {
		this.quantityLowerBound1 = quantityLowerBound1;
	}

	public BigDecimal getQuantityPrice1() {
		return this.quantityPrice1;
	}

	public void setQuantityPrice1(BigDecimal quantityPrice1) {
		this.quantityPrice1 = quantityPrice1;
	}

	public BigDecimal getQuantityLowerBound2() {
		return this.quantityLowerBound2;
	}

	public void setQuantityLowerBound2(BigDecimal quantityLowerBound2) {
		this.quantityLowerBound2 = quantityLowerBound2;
	}

	public BigDecimal getQuantityPrice2() {
		return this.quantityPrice2;
	}

	public void setQuantityPrice2(BigDecimal quantityPrice2) {
		this.quantityPrice2 = quantityPrice2;
	}

	public BigDecimal getQuantityLowerBound3() {
		return this.quantityLowerBound3;
	}

	public void setQuantityLowerBound3(BigDecimal quantityLowerBound3) {
		this.quantityLowerBound3 = quantityLowerBound3;
	}

	public BigDecimal getQuantityPrice3() {
		return this.quantityPrice3;
	}

	public void setQuantityPrice3(BigDecimal quantityPrice3) {
		this.quantityPrice3 = quantityPrice3;
	}

	public BigDecimal getQuantityLowerBound4() {
		return this.quantityLowerBound4;
	}

	public void setQuantityLowerBound4(BigDecimal quantityLowerBound4) {
		this.quantityLowerBound4 = quantityLowerBound4;
	}

	public BigDecimal getQuantityPrice4() {
		return this.quantityPrice4;
	}

	public void setQuantityPrice4(BigDecimal quantityPrice4) {
		this.quantityPrice4 = quantityPrice4;
	}

	public BigDecimal getQuantityLowerBound5() {
		return this.quantityLowerBound5;
	}

	public void setQuantityLowerBound5(BigDecimal quantityLowerBound5) {
		this.quantityLowerBound5 = quantityLowerBound5;
	}

	public BigDecimal getQuantityPrice5() {
		return this.quantityPrice5;
	}

	public void setQuantityPrice5(BigDecimal quantityPrice5) {
		this.quantityPrice5 = quantityPrice5;
	}

}
