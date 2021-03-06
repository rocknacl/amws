package dao.entities;
// Generated 2016-8-8 16:52:31 by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * ReportFbaInventoryHealth generated by hbm2java
 */
public class ReportFbaInventoryHealth implements java.io.Serializable {

	private Integer id;
	private String store;
	private Date insertDate;
	private Date snapshotDate;
	private String sku;
	private String fnsku;
	private String asin;
	private String productName;
	private String conditions;
	private Integer salesRank;
	private String productGroup;
	private Integer totalQuantity;
	private Integer sellableQuantity;
	private Integer unsellableQuantity;
	private Integer invAge0To90Days;
	private Integer invAge91To180Days;
	private Integer invAge181To270Days;
	private Integer invAge271To365Dats;
	private Integer invAge365PlusDays;
	private Integer unitsShippedLast24Hrs;
	private Integer unitsShippedLast7Days;
	private Integer unitsShippedLast30Days;
	private Integer unitsShippedLast90Days;
	private Integer unitsShippedLast180Days;
	private Integer unitsShippedLast365Days;
	private String weeksOfCoverT7;
	private String weeksOfCoverT30;
	private String weeksOfCoverT90;
	private String weeksOfCoverT180;
	private String weeksOfCoverT365;
	private Integer numAfnNewSellers;
	private Integer numAfnUsedSellers;
	private String currency;
	private BigDecimal yourPrice;
	private BigDecimal salesPrice;
	private BigDecimal lowestAfnNewPrice;
	private BigDecimal lowestAfnUsedPrice;
	private BigDecimal lowestMfnNewPrice;
	private BigDecimal lowestMfnUsedPrice;
	private Integer qtyToBeChargedLtsf12Mo;
	private Integer qtyInLongTermStorageProgram;
	private Integer qtyWithRemovalsInProgress;
	private BigDecimal projectedLtsf12Mo;
	private Float perUnitVolumn;
	private String isHazmat;
	private Integer inBoundQuantity;
	private String asinLimit;
	private Integer inBoundRecommendQuantity;
	private Integer qtyToBeChargedLtsf6Mo;
	private BigDecimal projectedLtsf6Mo;

	public ReportFbaInventoryHealth() {
	}

	public ReportFbaInventoryHealth(String store, Date insertDate, Date snapshotDate, String sku, String fnsku,
			String asin, String productName, String conditions, Integer salesRank, String productGroup,
			Integer totalQuantity, Integer sellableQuantity, Integer unsellableQuantity, Integer invAge0To90Days,
			Integer invAge91To180Days, Integer invAge181To270Days, Integer invAge271To365Dats,
			Integer invAge365PlusDays, Integer unitsShippedLast24Hrs, Integer unitsShippedLast7Days,
			Integer unitsShippedLast30Days, Integer unitsShippedLast90Days, Integer unitsShippedLast180Days,
			Integer unitsShippedLast365Days, String weeksOfCoverT7, String weeksOfCoverT30, String weeksOfCoverT90,
			String weeksOfCoverT180, String weeksOfCoverT365, Integer numAfnNewSellers, Integer numAfnUsedSellers,
			String currency, BigDecimal yourPrice, BigDecimal salesPrice, BigDecimal lowestAfnNewPrice,
			BigDecimal lowestAfnUsedPrice, BigDecimal lowestMfnNewPrice, BigDecimal lowestMfnUsedPrice,
			Integer qtyToBeChargedLtsf12Mo, Integer qtyInLongTermStorageProgram, Integer qtyWithRemovalsInProgress,
			BigDecimal projectedLtsf12Mo, Float perUnitVolumn, String isHazmat, Integer inBoundQuantity,
			String asinLimit, Integer inBoundRecommendQuantity, Integer qtyToBeChargedLtsf6Mo,
			BigDecimal projectedLtsf6Mo) {
		this.store = store;
		this.insertDate = insertDate;
		this.snapshotDate = snapshotDate;
		this.sku = sku;
		this.fnsku = fnsku;
		this.asin = asin;
		this.productName = productName;
		this.conditions = conditions;
		this.salesRank = salesRank;
		this.productGroup = productGroup;
		this.totalQuantity = totalQuantity;
		this.sellableQuantity = sellableQuantity;
		this.unsellableQuantity = unsellableQuantity;
		this.invAge0To90Days = invAge0To90Days;
		this.invAge91To180Days = invAge91To180Days;
		this.invAge181To270Days = invAge181To270Days;
		this.invAge271To365Dats = invAge271To365Dats;
		this.invAge365PlusDays = invAge365PlusDays;
		this.unitsShippedLast24Hrs = unitsShippedLast24Hrs;
		this.unitsShippedLast7Days = unitsShippedLast7Days;
		this.unitsShippedLast30Days = unitsShippedLast30Days;
		this.unitsShippedLast90Days = unitsShippedLast90Days;
		this.unitsShippedLast180Days = unitsShippedLast180Days;
		this.unitsShippedLast365Days = unitsShippedLast365Days;
		this.weeksOfCoverT7 = weeksOfCoverT7;
		this.weeksOfCoverT30 = weeksOfCoverT30;
		this.weeksOfCoverT90 = weeksOfCoverT90;
		this.weeksOfCoverT180 = weeksOfCoverT180;
		this.weeksOfCoverT365 = weeksOfCoverT365;
		this.numAfnNewSellers = numAfnNewSellers;
		this.numAfnUsedSellers = numAfnUsedSellers;
		this.currency = currency;
		this.yourPrice = yourPrice;
		this.salesPrice = salesPrice;
		this.lowestAfnNewPrice = lowestAfnNewPrice;
		this.lowestAfnUsedPrice = lowestAfnUsedPrice;
		this.lowestMfnNewPrice = lowestMfnNewPrice;
		this.lowestMfnUsedPrice = lowestMfnUsedPrice;
		this.qtyToBeChargedLtsf12Mo = qtyToBeChargedLtsf12Mo;
		this.qtyInLongTermStorageProgram = qtyInLongTermStorageProgram;
		this.qtyWithRemovalsInProgress = qtyWithRemovalsInProgress;
		this.projectedLtsf12Mo = projectedLtsf12Mo;
		this.perUnitVolumn = perUnitVolumn;
		this.isHazmat = isHazmat;
		this.inBoundQuantity = inBoundQuantity;
		this.asinLimit = asinLimit;
		this.inBoundRecommendQuantity = inBoundRecommendQuantity;
		this.qtyToBeChargedLtsf6Mo = qtyToBeChargedLtsf6Mo;
		this.projectedLtsf6Mo = projectedLtsf6Mo;
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

	public Date getSnapshotDate() {
		return this.snapshotDate;
	}

	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getFnsku() {
		return this.fnsku;
	}

	public void setFnsku(String fnsku) {
		this.fnsku = fnsku;
	}

	public String getAsin() {
		return this.asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getConditions() {
		return this.conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public Integer getSalesRank() {
		return this.salesRank;
	}

	public void setSalesRank(Integer salesRank) {
		this.salesRank = salesRank;
	}

	public String getProductGroup() {
		return this.productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public Integer getTotalQuantity() {
		return this.totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getSellableQuantity() {
		return this.sellableQuantity;
	}

	public void setSellableQuantity(Integer sellableQuantity) {
		this.sellableQuantity = sellableQuantity;
	}

	public Integer getUnsellableQuantity() {
		return this.unsellableQuantity;
	}

	public void setUnsellableQuantity(Integer unsellableQuantity) {
		this.unsellableQuantity = unsellableQuantity;
	}

	public Integer getInvAge0To90Days() {
		return this.invAge0To90Days;
	}

	public void setInvAge0To90Days(Integer invAge0To90Days) {
		this.invAge0To90Days = invAge0To90Days;
	}

	public Integer getInvAge91To180Days() {
		return this.invAge91To180Days;
	}

	public void setInvAge91To180Days(Integer invAge91To180Days) {
		this.invAge91To180Days = invAge91To180Days;
	}

	public Integer getInvAge181To270Days() {
		return this.invAge181To270Days;
	}

	public void setInvAge181To270Days(Integer invAge181To270Days) {
		this.invAge181To270Days = invAge181To270Days;
	}

	public Integer getInvAge271To365Dats() {
		return this.invAge271To365Dats;
	}

	public void setInvAge271To365Dats(Integer invAge271To365Dats) {
		this.invAge271To365Dats = invAge271To365Dats;
	}

	public Integer getInvAge365PlusDays() {
		return this.invAge365PlusDays;
	}

	public void setInvAge365PlusDays(Integer invAge365PlusDays) {
		this.invAge365PlusDays = invAge365PlusDays;
	}

	public Integer getUnitsShippedLast24Hrs() {
		return this.unitsShippedLast24Hrs;
	}

	public void setUnitsShippedLast24Hrs(Integer unitsShippedLast24Hrs) {
		this.unitsShippedLast24Hrs = unitsShippedLast24Hrs;
	}

	public Integer getUnitsShippedLast7Days() {
		return this.unitsShippedLast7Days;
	}

	public void setUnitsShippedLast7Days(Integer unitsShippedLast7Days) {
		this.unitsShippedLast7Days = unitsShippedLast7Days;
	}

	public Integer getUnitsShippedLast30Days() {
		return this.unitsShippedLast30Days;
	}

	public void setUnitsShippedLast30Days(Integer unitsShippedLast30Days) {
		this.unitsShippedLast30Days = unitsShippedLast30Days;
	}

	public Integer getUnitsShippedLast90Days() {
		return this.unitsShippedLast90Days;
	}

	public void setUnitsShippedLast90Days(Integer unitsShippedLast90Days) {
		this.unitsShippedLast90Days = unitsShippedLast90Days;
	}

	public Integer getUnitsShippedLast180Days() {
		return this.unitsShippedLast180Days;
	}

	public void setUnitsShippedLast180Days(Integer unitsShippedLast180Days) {
		this.unitsShippedLast180Days = unitsShippedLast180Days;
	}

	public Integer getUnitsShippedLast365Days() {
		return this.unitsShippedLast365Days;
	}

	public void setUnitsShippedLast365Days(Integer unitsShippedLast365Days) {
		this.unitsShippedLast365Days = unitsShippedLast365Days;
	}

	public String getWeeksOfCoverT7() {
		return this.weeksOfCoverT7;
	}

	public void setWeeksOfCoverT7(String weeksOfCoverT7) {
		this.weeksOfCoverT7 = weeksOfCoverT7;
	}

	public String getWeeksOfCoverT30() {
		return this.weeksOfCoverT30;
	}

	public void setWeeksOfCoverT30(String weeksOfCoverT30) {
		this.weeksOfCoverT30 = weeksOfCoverT30;
	}

	public String getWeeksOfCoverT90() {
		return this.weeksOfCoverT90;
	}

	public void setWeeksOfCoverT90(String weeksOfCoverT90) {
		this.weeksOfCoverT90 = weeksOfCoverT90;
	}

	public String getWeeksOfCoverT180() {
		return this.weeksOfCoverT180;
	}

	public void setWeeksOfCoverT180(String weeksOfCoverT180) {
		this.weeksOfCoverT180 = weeksOfCoverT180;
	}

	public String getWeeksOfCoverT365() {
		return this.weeksOfCoverT365;
	}

	public void setWeeksOfCoverT365(String weeksOfCoverT365) {
		this.weeksOfCoverT365 = weeksOfCoverT365;
	}

	public Integer getNumAfnNewSellers() {
		return this.numAfnNewSellers;
	}

	public void setNumAfnNewSellers(Integer numAfnNewSellers) {
		this.numAfnNewSellers = numAfnNewSellers;
	}

	public Integer getNumAfnUsedSellers() {
		return this.numAfnUsedSellers;
	}

	public void setNumAfnUsedSellers(Integer numAfnUsedSellers) {
		this.numAfnUsedSellers = numAfnUsedSellers;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getYourPrice() {
		return this.yourPrice;
	}

	public void setYourPrice(BigDecimal yourPrice) {
		this.yourPrice = yourPrice;
	}

	public BigDecimal getSalesPrice() {
		return this.salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

	public BigDecimal getLowestAfnNewPrice() {
		return this.lowestAfnNewPrice;
	}

	public void setLowestAfnNewPrice(BigDecimal lowestAfnNewPrice) {
		this.lowestAfnNewPrice = lowestAfnNewPrice;
	}

	public BigDecimal getLowestAfnUsedPrice() {
		return this.lowestAfnUsedPrice;
	}

	public void setLowestAfnUsedPrice(BigDecimal lowestAfnUsedPrice) {
		this.lowestAfnUsedPrice = lowestAfnUsedPrice;
	}

	public BigDecimal getLowestMfnNewPrice() {
		return this.lowestMfnNewPrice;
	}

	public void setLowestMfnNewPrice(BigDecimal lowestMfnNewPrice) {
		this.lowestMfnNewPrice = lowestMfnNewPrice;
	}

	public BigDecimal getLowestMfnUsedPrice() {
		return this.lowestMfnUsedPrice;
	}

	public void setLowestMfnUsedPrice(BigDecimal lowestMfnUsedPrice) {
		this.lowestMfnUsedPrice = lowestMfnUsedPrice;
	}

	public Integer getQtyToBeChargedLtsf12Mo() {
		return this.qtyToBeChargedLtsf12Mo;
	}

	public void setQtyToBeChargedLtsf12Mo(Integer qtyToBeChargedLtsf12Mo) {
		this.qtyToBeChargedLtsf12Mo = qtyToBeChargedLtsf12Mo;
	}

	public Integer getQtyInLongTermStorageProgram() {
		return this.qtyInLongTermStorageProgram;
	}

	public void setQtyInLongTermStorageProgram(Integer qtyInLongTermStorageProgram) {
		this.qtyInLongTermStorageProgram = qtyInLongTermStorageProgram;
	}

	public Integer getQtyWithRemovalsInProgress() {
		return this.qtyWithRemovalsInProgress;
	}

	public void setQtyWithRemovalsInProgress(Integer qtyWithRemovalsInProgress) {
		this.qtyWithRemovalsInProgress = qtyWithRemovalsInProgress;
	}

	public BigDecimal getProjectedLtsf12Mo() {
		return this.projectedLtsf12Mo;
	}

	public void setProjectedLtsf12Mo(BigDecimal projectedLtsf12Mo) {
		this.projectedLtsf12Mo = projectedLtsf12Mo;
	}

	public Float getPerUnitVolumn() {
		return this.perUnitVolumn;
	}

	public void setPerUnitVolumn(Float perUnitVolumn) {
		this.perUnitVolumn = perUnitVolumn;
	}

	public String getIsHazmat() {
		return this.isHazmat;
	}

	public void setIsHazmat(String isHazmat) {
		this.isHazmat = isHazmat;
	}

	public Integer getInBoundQuantity() {
		return this.inBoundQuantity;
	}

	public void setInBoundQuantity(Integer inBoundQuantity) {
		this.inBoundQuantity = inBoundQuantity;
	}

	public String getAsinLimit() {
		return this.asinLimit;
	}

	public void setAsinLimit(String asinLimit) {
		this.asinLimit = asinLimit;
	}

	public Integer getInBoundRecommendQuantity() {
		return this.inBoundRecommendQuantity;
	}

	public void setInBoundRecommendQuantity(Integer inBoundRecommendQuantity) {
		this.inBoundRecommendQuantity = inBoundRecommendQuantity;
	}

	public Integer getQtyToBeChargedLtsf6Mo() {
		return this.qtyToBeChargedLtsf6Mo;
	}

	public void setQtyToBeChargedLtsf6Mo(Integer qtyToBeChargedLtsf6Mo) {
		this.qtyToBeChargedLtsf6Mo = qtyToBeChargedLtsf6Mo;
	}

	public BigDecimal getProjectedLtsf6Mo() {
		return this.projectedLtsf6Mo;
	}

	public void setProjectedLtsf6Mo(BigDecimal projectedLtsf6Mo) {
		this.projectedLtsf6Mo = projectedLtsf6Mo;
	}

}
