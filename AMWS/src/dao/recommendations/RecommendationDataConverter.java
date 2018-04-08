package dao.recommendations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.amazon.mws.recommendations.model.AdvertisingRecommendation;
import com.amazon.mws.recommendations.model.FulfillmentRecommendation;
import com.amazon.mws.recommendations.model.GlobalSellingRecommendation;
import com.amazon.mws.recommendations.model.InventoryRecommendation;
import com.amazon.mws.recommendations.model.ListRecommendationsByNextTokenResponse;
import com.amazon.mws.recommendations.model.ListRecommendationsResponse;
import com.amazon.mws.recommendations.model.ListingQualityRecommendation;
import com.amazon.mws.recommendations.model.PricingRecommendation;
import com.amazon.mws.recommendations.model.SelectionRecommendation;

import dao.entities.GeneralResponseRecord;
import dao.entities.RecommendationOriginalData;
import helper.dao.HibernateUtils;

/**
 * transfer marketplace website service response to hibernate model
 * 
 * @author Bens
 * @version 20160614
 */
public class RecommendationDataConverter {

	/**
	 * convert ListRecommendationsResponse to List<RecommendationOriginalData>
	 * 
	 * @author Bens
	 * @param response
	 * @return List<RecommendationOriginalData> if no data in response will
	 *         return new ArrayList<RecommendationOriginalData>();
	 */
	public static List<RecommendationOriginalData> resolve(ListRecommendationsResponse response) {
		List<RecommendationOriginalData> recommendationOriginalDataList = new ArrayList<RecommendationOriginalData>();
		if (response.isSetListRecommendationsResult()) {
			if (response.getListRecommendationsResult().isSetAdvertisingRecommendations())
				recommendationOriginalDataList.addAll(resolveAdvertisingRecommendation(
						response.getListRecommendationsResult().getAdvertisingRecommendations()));
			if (response.getListRecommendationsResult().isSetFulfillmentRecommendations())
				recommendationOriginalDataList.addAll(resolveFulfillmentRecommendation(
						response.getListRecommendationsResult().getFulfillmentRecommendations()));
			if (response.getListRecommendationsResult().isSetGlobalSellingRecommendations())
				recommendationOriginalDataList.addAll(resolveGlobalSellingRecommendation(
						response.getListRecommendationsResult().getGlobalSellingRecommendations()));
			if (response.getListRecommendationsResult().isSetInventoryRecommendations())
				recommendationOriginalDataList.addAll(resolveInventoryRecommendation(
						response.getListRecommendationsResult().getInventoryRecommendations()));
			if (response.getListRecommendationsResult().isSetListingQualityRecommendations())
				recommendationOriginalDataList.addAll(resolveListingQualityRecommendation(
						response.getListRecommendationsResult().getListingQualityRecommendations()));
			if (response.getListRecommendationsResult().isSetPricingRecommendations())
				recommendationOriginalDataList.addAll(resolvePricingRecommendation(
						response.getListRecommendationsResult().getPricingRecommendations()));
			if (response.getListRecommendationsResult().isSetSelectionRecommendations())
				recommendationOriginalDataList.addAll(resolveSelectionRecommendaion(
						response.getListRecommendationsResult().getSelectionRecommendations()));
		}
		if (recommendationOriginalDataList.size() > 0) {
			for (RecommendationOriginalData line : recommendationOriginalDataList) {
				Session session = HibernateUtils.getSession();
				GeneralResponseRecord generalResponseRecord = (GeneralResponseRecord) session
						.get(GeneralResponseRecord.class, response.getResponseHeaderMetadata().getRequestId());
				if (generalResponseRecord == null) {
					generalResponseRecord = new GeneralResponseRecord();
					generalResponseRecord.setRequestId(response.getResponseHeaderMetadata().getRequestId());
					generalResponseRecord.setClassName(response.getClass().getSimpleName());
					generalResponseRecord.setTstamp(response.getResponseHeaderMetadata().getTimestamp());
					generalResponseRecord.setRecordDate(new Date());
				}
				line.setGeneralResponseRecord(generalResponseRecord);
				HibernateUtils.closeSession(session);
				// line.setRequestId(response.getResponseHeaderMetadata().getRequestId());
				line.setTstamp(response.getResponseHeaderMetadata().getTimestamp());
			}
			return recommendationOriginalDataList;
		} else {
			return new ArrayList<RecommendationOriginalData>();
		}
	}

	/**
	 * convert ListRecommendationsByNextTokenResponse to List
	 * <RecommendationOriginalData>
	 * 
	 * @author Bens
	 * @param response
	 * @return List<RecommendationOriginalData> if no data in response will
	 *         return new ArrayList<RecommendationOriginalData>();
	 */
	public static List<RecommendationOriginalData> resolve(ListRecommendationsByNextTokenResponse response) {
		List<RecommendationOriginalData> recommendationOriginalDataList = new ArrayList<RecommendationOriginalData>();
		if (response.isSetListRecommendationsByNextTokenResult()) {
			if (response.getListRecommendationsByNextTokenResult().isSetAdvertisingRecommendations())
				recommendationOriginalDataList.addAll(resolveAdvertisingRecommendation(
						response.getListRecommendationsByNextTokenResult().getAdvertisingRecommendations()));
			if (response.getListRecommendationsByNextTokenResult().isSetFulfillmentRecommendations())
				recommendationOriginalDataList.addAll(resolveFulfillmentRecommendation(
						response.getListRecommendationsByNextTokenResult().getFulfillmentRecommendations()));
			if (response.getListRecommendationsByNextTokenResult().isSetGlobalSellingRecommendations())
				recommendationOriginalDataList.addAll(resolveGlobalSellingRecommendation(
						response.getListRecommendationsByNextTokenResult().getGlobalSellingRecommendations()));
			if (response.getListRecommendationsByNextTokenResult().isSetInventoryRecommendations())
				recommendationOriginalDataList.addAll(resolveInventoryRecommendation(
						response.getListRecommendationsByNextTokenResult().getInventoryRecommendations()));
			if (response.getListRecommendationsByNextTokenResult().isSetListingQualityRecommendations())
				recommendationOriginalDataList.addAll(resolveListingQualityRecommendation(
						response.getListRecommendationsByNextTokenResult().getListingQualityRecommendations()));
			if (response.getListRecommendationsByNextTokenResult().isSetPricingRecommendations())
				recommendationOriginalDataList.addAll(resolvePricingRecommendation(
						response.getListRecommendationsByNextTokenResult().getPricingRecommendations()));
			if (response.getListRecommendationsByNextTokenResult().isSetSelectionRecommendations())
				recommendationOriginalDataList.addAll(resolveSelectionRecommendaion(
						response.getListRecommendationsByNextTokenResult().getSelectionRecommendations()));
		}
		if (recommendationOriginalDataList.size() > 0) {
			for (RecommendationOriginalData line : recommendationOriginalDataList) {
				Session session = HibernateUtils.getSession();
				GeneralResponseRecord generalResponseRecord = (GeneralResponseRecord) session
						.get(GeneralResponseRecord.class, response.getResponseHeaderMetadata().getRequestId());
				if (generalResponseRecord == null) {
					generalResponseRecord = new GeneralResponseRecord();
					generalResponseRecord.setRequestId(response.getResponseHeaderMetadata().getRequestId());
					generalResponseRecord.setClassName(response.getClass().getSimpleName());
					generalResponseRecord.setTstamp(response.getResponseHeaderMetadata().getTimestamp());
					generalResponseRecord.setRecordDate(new Date());
				}
				line.setGeneralResponseRecord(generalResponseRecord);
				HibernateUtils.closeSession(session);
				// line.setRequestId(response.getResponseHeaderMetadata().getRequestId());
				line.setTstamp(response.getResponseHeaderMetadata().getTimestamp());
			}
			return recommendationOriginalDataList;
		} else {
			return new ArrayList<RecommendationOriginalData>();
		}
	}

	/**
	 * convert InventoryRecommendation to RecommendationOriginalData
	 * 
	 * @author Bens
	 * @param inventoryRecommendationList
	 * @return hibernate model List<RecommendationOriginalData>
	 */
	private static List<RecommendationOriginalData> resolveInventoryRecommendation(
			List<InventoryRecommendation> inventoryRecommendationList) {
		if (inventoryRecommendationList == null || inventoryRecommendationList.size() < 1) {
			return new ArrayList<RecommendationOriginalData>();
		} else {
			List<RecommendationOriginalData> recommendationOriginalDataList = new ArrayList<RecommendationOriginalData>();
			for (InventoryRecommendation inventoryRecommendation : inventoryRecommendationList) {
				RecommendationOriginalData recommendationOriginalData = new RecommendationOriginalData();
				recommendationOriginalData.setRecommendationId(inventoryRecommendation.getRecommendationId());
				recommendationOriginalData.setRecommendationReason(inventoryRecommendation.getRecommendationReason());
				recommendationOriginalData
						.setLastUpdated(inventoryRecommendation.getLastUpdated().toGregorianCalendar().getTime());
				if (inventoryRecommendation.isSetItemIdentifier()) {
					recommendationOriginalData
							.setItemIdentifierAsin(inventoryRecommendation.getItemIdentifier().getAsin());
					recommendationOriginalData
							.setItemIdentifierSku(inventoryRecommendation.getItemIdentifier().getSku());
					recommendationOriginalData
							.setItemIdentifierUpc(inventoryRecommendation.getItemIdentifier().getUpc());
				}
				recommendationOriginalData.setFulfillmentChannel(inventoryRecommendation.getFulfillmentChannel());
				recommendationOriginalData.setSalesForTheLast14days(inventoryRecommendation.getSalesForTheLast14Days());
				recommendationOriginalData.setSalesForTheLast30days(inventoryRecommendation.getSalesForTheLast30Days());
				recommendationOriginalData.setAvailableQuantity(inventoryRecommendation.getAvailableQuantity());
				recommendationOriginalData.setDaysUntilStockRunsOut(inventoryRecommendation.getDaysUntilStockRunsOut());
				recommendationOriginalData.setInboundQuantity(inventoryRecommendation.getInboundQuantity());
				recommendationOriginalData
						.setRecommendedInboundQuantity(inventoryRecommendation.getRecommendedInboundQuantity());
				recommendationOriginalData
						.setDaysOutOfStockLast30days(inventoryRecommendation.getDaysOutOfStockLast30Days());
				recommendationOriginalData.setLostSalesInLast30days(inventoryRecommendation.getLostSalesInLast30Days());
				recommendationOriginalDataList.add(recommendationOriginalData);
			}
			return recommendationOriginalDataList;
		}
	}

	/**
	 * convert SelectionRecommendation to RecommendationOriginalData
	 * 
	 * @author Bens
	 * @param selectionRecommendationList
	 * @return hibernate model List<RecommendationOriginalData>
	 */
	private static List<RecommendationOriginalData> resolveSelectionRecommendaion(
			List<SelectionRecommendation> selectionRecommendationList) {
		if (selectionRecommendationList == null || selectionRecommendationList.size() < 1) {
			return new ArrayList<RecommendationOriginalData>();
		} else {
			List<RecommendationOriginalData> recommendationOriginalDataList = new ArrayList<RecommendationOriginalData>();
			for (SelectionRecommendation selectionRecommendation : selectionRecommendationList) {
				RecommendationOriginalData recommendationOriginalData = new RecommendationOriginalData();
				recommendationOriginalData.setRecommendationId(selectionRecommendation.getRecommendationId());
				recommendationOriginalData.setRecommendationReason(selectionRecommendation.getRecommendationReason());
				recommendationOriginalData
						.setLastUpdated(selectionRecommendation.getLastUpdated().toGregorianCalendar().getTime());
				if (selectionRecommendation.isSetItemIdentifier()) {
					recommendationOriginalData
							.setItemIdentifierAsin(selectionRecommendation.getItemIdentifier().getAsin());
					recommendationOriginalData
							.setItemIdentifierSku(selectionRecommendation.getItemIdentifier().getSku());
					recommendationOriginalData
							.setItemIdentifierUpc(selectionRecommendation.getItemIdentifier().getUpc());
				}
				recommendationOriginalData.setItemName(selectionRecommendation.getItemName());
				recommendationOriginalData.setBrandName(selectionRecommendation.getBrandName());
				recommendationOriginalData.setProductCategory(selectionRecommendation.getProductCategory());
				recommendationOriginalData.setSalesRank(selectionRecommendation.getSalesRank());
				if (selectionRecommendation.isSetBuyboxPrice()) {
					recommendationOriginalData
							.setBuyboxPriceCurrency(selectionRecommendation.getBuyboxPrice().getCurrencyCode());
					recommendationOriginalData
							.setBuyboxPriceAmount(selectionRecommendation.getBuyboxPrice().getAmount());
				}
				recommendationOriginalData.setNumberOfOffers(selectionRecommendation.getNumberOfOffers());
				recommendationOriginalData.setAverageCustomerReview(selectionRecommendation.getAverageCustomerReview());
				recommendationOriginalData
						.setNumberOfCustomerReviews(selectionRecommendation.getNumberOfCustomerReviews());
				recommendationOriginalDataList.add(recommendationOriginalData);
			}
			return recommendationOriginalDataList;
		}
	}

	/**
	 * convert PricingRecommendation to RecommendationOriginalData
	 * 
	 * @author Bens
	 * @param inventoryRecommendationList
	 * @return hibernate model List<RecommendationOriginalData>
	 */
	private static List<RecommendationOriginalData> resolvePricingRecommendation(
			List<PricingRecommendation> pricingRecommendationList) {
		if (pricingRecommendationList == null || pricingRecommendationList.size() < 1) {
			return new ArrayList<RecommendationOriginalData>();
		} else {
			List<RecommendationOriginalData> recommendationOriginalDataList = new ArrayList<RecommendationOriginalData>();
			for (PricingRecommendation pricingRecommendation : pricingRecommendationList) {
				RecommendationOriginalData recommendationOriginalData = new RecommendationOriginalData();
				recommendationOriginalData.setRecommendationId(pricingRecommendation.getRecommendationId());
				recommendationOriginalData.setRecommendationReason(pricingRecommendation.getRecommendationReason());
				recommendationOriginalData
						.setLastUpdated(pricingRecommendation.getLastUpdated().toGregorianCalendar().getTime());
				if (pricingRecommendation.isSetItemIdentifier()) {
					recommendationOriginalData
							.setItemIdentifierAsin(pricingRecommendation.getItemIdentifier().getAsin());
					recommendationOriginalData.setItemIdentifierSku(pricingRecommendation.getItemIdentifier().getSku());
					recommendationOriginalData.setItemIdentifierUpc(pricingRecommendation.getItemIdentifier().getUpc());
				}
				recommendationOriginalData.setItemName(pricingRecommendation.getItemName());
				recommendationOriginalData.setItemCondition(pricingRecommendation.getCondition());
				recommendationOriginalData.setSubCondition(pricingRecommendation.getSubCondition());
				recommendationOriginalData.setFulfillmentChannel(pricingRecommendation.getFulfillmentChannel());
				if (pricingRecommendation.isSetYourPricePlusShipping()) {
					recommendationOriginalData.setYourPricePlusShippingAmount(
							pricingRecommendation.getYourPricePlusShipping().getAmount());
					recommendationOriginalData.setYourPricePlusShippingCurrency(
							pricingRecommendation.getYourPricePlusShipping().getCurrencyCode());
				}
				if (pricingRecommendation.isSetLowestPricePlusShipping()) {
					recommendationOriginalData.setLowestPricePlusShippingAmount(
							pricingRecommendation.getLowestPricePlusShipping().getAmount());
					recommendationOriginalData.setLowestPricePlusShippingCurrency(
							pricingRecommendation.getLowestPricePlusShipping().getCurrencyCode());
				}
				if (pricingRecommendation.isSetMedianPricePlusShipping()) {
					recommendationOriginalData.setMedianPricePlusShippingAmount(
							pricingRecommendation.getMedianPricePlusShipping().getAmount());
					recommendationOriginalData.setMedianPricePlusShippingCurrency(
							pricingRecommendation.getMedianPricePlusShipping().getCurrencyCode());
				}
				if (pricingRecommendation.isSetLowestAmazonFulfilledOfferPrice()) {
					recommendationOriginalData.setLowestAmazonFulfilledOfferPriceAmount(
							pricingRecommendation.getLowestAmazonFulfilledOfferPrice().getAmount());
					recommendationOriginalData.setLowestAmazonFulfilledOfferPriceCurrency(
							pricingRecommendation.getLowestAmazonFulfilledOfferPrice().getCurrencyCode());
				}
				if (pricingRecommendation.isSetLowestMerchantFulfilledOfferPrice()) {
					recommendationOriginalData.setLowestMerchantFulfilledOfferPriceAmount(
							pricingRecommendation.getLowestMerchantFulfilledOfferPrice().getAmount());
					recommendationOriginalData.setLowestMerchantFulfilledOfferPriceCurrency(
							pricingRecommendation.getLowestMerchantFulfilledOfferPrice().getCurrencyCode());
				}
				recommendationOriginalData.setNumberOfOffers(pricingRecommendation.getNumberOfOffers());
				recommendationOriginalData
						.setNumberOfMerchantFulfilledOffers(pricingRecommendation.getNumberOfMerchantFulfilledOffers());
				recommendationOriginalData
						.setNumberOfAmazonFulfilledOffers(pricingRecommendation.getNumberOfAmazonFulfilledOffers());
				recommendationOriginalDataList.add(recommendationOriginalData);
			}
			return recommendationOriginalDataList;
		}
	}

	/**
	 * convert FulfillmentRecommendation to RecommendationOriginalData
	 * 
	 * @author Bens
	 * @param inventoryRecommendationList
	 * @return hibernate model List<RecommendationOriginalData>
	 */
	private static List<RecommendationOriginalData> resolveFulfillmentRecommendation(
			List<FulfillmentRecommendation> fulfillmentRecommendationList) {
		if (fulfillmentRecommendationList == null || fulfillmentRecommendationList.size() < 1) {
			return new ArrayList<RecommendationOriginalData>();
		} else {
			List<RecommendationOriginalData> recommendationOriginalDataList = new ArrayList<RecommendationOriginalData>();
			for (FulfillmentRecommendation fulfillmentRecommendation : fulfillmentRecommendationList) {
				RecommendationOriginalData recommendationOriginalData = new RecommendationOriginalData();
				recommendationOriginalData.setRecommendationId(fulfillmentRecommendation.getRecommendationId());
				recommendationOriginalData.setRecommendationReason(fulfillmentRecommendation.getRecommendationReason());
				recommendationOriginalData
						.setLastUpdated(fulfillmentRecommendation.getLastUpdated().toGregorianCalendar().getTime());
				if (fulfillmentRecommendation.isSetItemIdentifier()) {
					recommendationOriginalData
							.setItemIdentifierAsin(fulfillmentRecommendation.getItemIdentifier().getAsin());
					recommendationOriginalData
							.setItemIdentifierSku(fulfillmentRecommendation.getItemIdentifier().getSku());
					recommendationOriginalData
							.setItemIdentifierUpc(fulfillmentRecommendation.getItemIdentifier().getUpc());
				}
				recommendationOriginalData.setItemName(fulfillmentRecommendation.getItemName());
				recommendationOriginalData.setBrandName(fulfillmentRecommendation.getBrandName());
				recommendationOriginalData.setProductCategory(fulfillmentRecommendation.getProductCategory());
				recommendationOriginalData.setSalesRank(fulfillmentRecommendation.getSalesRank());
				if (fulfillmentRecommendation.isSetBuyboxPrice()) {
					recommendationOriginalData
							.setBuyboxPriceCurrency(fulfillmentRecommendation.getBuyboxPrice().getCurrencyCode());
					recommendationOriginalData
							.setBuyboxPriceAmount(fulfillmentRecommendation.getBuyboxPrice().getAmount());
				}
				recommendationOriginalData.setNumberOfOffers(fulfillmentRecommendation.getNumberOfOffers());
				recommendationOriginalData.setNumberOfOffersFulfilledByAmazon(
						fulfillmentRecommendation.getNumberOfOffersFulfilledByAmazon());
				recommendationOriginalData
						.setAverageCustomerReview(fulfillmentRecommendation.getAverageCustomerReview());
				recommendationOriginalData
						.setNumberOfCustomerReviews(fulfillmentRecommendation.getNumberOfCustomerReviews());
				if (fulfillmentRecommendation.isSetItemDimensions()) {
					recommendationOriginalData.setItemDimensionsHeightUnit(
							fulfillmentRecommendation.getItemDimensions().getHeight().getUnit());
					recommendationOriginalData.setItemDimensionsHeightValue(
							fulfillmentRecommendation.getItemDimensions().getHeight().getValue());
					recommendationOriginalData.setItemDimensionsLengthUnit(
							fulfillmentRecommendation.getItemDimensions().getLength().getUnit());
					recommendationOriginalData.setItemDimensionsLengthValue(
							fulfillmentRecommendation.getItemDimensions().getLength().getValue());
					recommendationOriginalData.setItemDimensionsWeightUnit(
							fulfillmentRecommendation.getItemDimensions().getWeight().getUnit());
					recommendationOriginalData.setItemDimensionsWeightValue(
							fulfillmentRecommendation.getItemDimensions().getWeight().getValue());
					recommendationOriginalData.setItemDimensionsWidthUnit(
							fulfillmentRecommendation.getItemDimensions().getWidth().getUnit());
					recommendationOriginalData.setItemDimensionsWidthValue(
							fulfillmentRecommendation.getItemDimensions().getWidth().getValue());
				}
				recommendationOriginalDataList.add(recommendationOriginalData);
			}
			return recommendationOriginalDataList;
		}
	}

	/**
	 * convert ListingQualityRecommendation to RecommendationOriginalData
	 * 
	 * @author Bens
	 * @param inventoryRecommendationList
	 * @return hibernate model List<RecommendationOriginalData>
	 */
	private static List<RecommendationOriginalData> resolveListingQualityRecommendation(
			List<ListingQualityRecommendation> qualityRecommendationList) {
		if (qualityRecommendationList == null || qualityRecommendationList.size() < 1) {
			return new ArrayList<RecommendationOriginalData>();
		} else {
			List<RecommendationOriginalData> recommendationOriginalDataList = new ArrayList<RecommendationOriginalData>();
			for (ListingQualityRecommendation listQualityRecommendation : qualityRecommendationList) {
				RecommendationOriginalData recommendationOriginalData = new RecommendationOriginalData();
				recommendationOriginalData.setRecommendationId(listQualityRecommendation.getRecommendationId());
				recommendationOriginalData.setRecommendationReason(listQualityRecommendation.getRecommendationReason());
				recommendationOriginalData.setQualitySet(listQualityRecommendation.getQualitySet());
				recommendationOriginalData.setDefectGroup(listQualityRecommendation.getDefectGroup());
				recommendationOriginalData.setDefectAttribute(listQualityRecommendation.getDefectAttribute());
				if (listQualityRecommendation.isSetItemIdentifier()) {
					recommendationOriginalData
							.setItemIdentifierAsin(listQualityRecommendation.getItemIdentifier().getAsin());
					recommendationOriginalData
							.setItemIdentifierSku(listQualityRecommendation.getItemIdentifier().getSku());
					recommendationOriginalData
							.setItemIdentifierUpc(listQualityRecommendation.getItemIdentifier().getUpc());
				}
				recommendationOriginalData.setItemName(listQualityRecommendation.getItemName());
				recommendationOriginalDataList.add(recommendationOriginalData);
			}
			return recommendationOriginalDataList;
		}
	}

	/**
	 * convert GlobalSellingRecommendation to RecommendationOriginalData
	 * 
	 * @author Bens
	 * @param inventoryRecommendationList
	 * @return hibernate model List<RecommendationOriginalData>
	 */
	private static List<RecommendationOriginalData> resolveGlobalSellingRecommendation(
			List<GlobalSellingRecommendation> globalSellingRecommendationList) {
		if (globalSellingRecommendationList == null || globalSellingRecommendationList.size() < 1) {
			return new ArrayList<RecommendationOriginalData>();
		} else {
			List<RecommendationOriginalData> recommendationOriginalDataList = new ArrayList<RecommendationOriginalData>();
			for (GlobalSellingRecommendation globalSellingRecommendation : globalSellingRecommendationList) {
				RecommendationOriginalData recommendationOriginalData = new RecommendationOriginalData();
				recommendationOriginalData.setRecommendationId(globalSellingRecommendation.getRecommendationId());
				recommendationOriginalData
						.setRecommendationReason(globalSellingRecommendation.getRecommendationReason());
				recommendationOriginalData
						.setLastUpdated(globalSellingRecommendation.getLastUpdated().toGregorianCalendar().getTime());
				if (globalSellingRecommendation.isSetItemIdentifier()) {
					recommendationOriginalData
							.setItemIdentifierAsin(globalSellingRecommendation.getItemIdentifier().getAsin());
					recommendationOriginalData
							.setItemIdentifierSku(globalSellingRecommendation.getItemIdentifier().getSku());
					recommendationOriginalData
							.setItemIdentifierUpc(globalSellingRecommendation.getItemIdentifier().getUpc());
				}
				recommendationOriginalData.setItemName(globalSellingRecommendation.getItemName());
				recommendationOriginalData.setBrandName(globalSellingRecommendation.getBrandName());
				recommendationOriginalData.setProductCategory(globalSellingRecommendation.getProductCategory());
				recommendationOriginalData.setSalesRank(globalSellingRecommendation.getSalesRank());
				if (globalSellingRecommendation.isSetBuyboxPrice()) {
					recommendationOriginalData
							.setBuyboxPriceCurrency(globalSellingRecommendation.getBuyboxPrice().getCurrencyCode());
					recommendationOriginalData
							.setBuyboxPriceAmount(globalSellingRecommendation.getBuyboxPrice().getAmount());
				}
				recommendationOriginalData.setNumberOfOffers(globalSellingRecommendation.getNumberOfOffers());
				recommendationOriginalData.setNumberOfOffersFulfilledByAmazon(
						globalSellingRecommendation.getNumberOfOffersFulfilledByAmazon());
				recommendationOriginalData
						.setAverageCustomerReview(globalSellingRecommendation.getAverageCustomerReview());
				recommendationOriginalData
						.setNumberOfCustomerReviews(globalSellingRecommendation.getNumberOfCustomerReviews());
				if (globalSellingRecommendation.isSetItemDimensions()) {
					recommendationOriginalData.setItemDimensionsHeightUnit(
							globalSellingRecommendation.getItemDimensions().getHeight().getUnit());
					recommendationOriginalData.setItemDimensionsHeightValue(
							globalSellingRecommendation.getItemDimensions().getHeight().getValue());
					recommendationOriginalData.setItemDimensionsLengthUnit(
							globalSellingRecommendation.getItemDimensions().getLength().getUnit());
					recommendationOriginalData.setItemDimensionsLengthValue(
							globalSellingRecommendation.getItemDimensions().getLength().getValue());
					recommendationOriginalData.setItemDimensionsWeightUnit(
							globalSellingRecommendation.getItemDimensions().getWeight().getUnit());
					recommendationOriginalData.setItemDimensionsWeightValue(
							globalSellingRecommendation.getItemDimensions().getWeight().getValue());
					recommendationOriginalData.setItemDimensionsWidthUnit(
							globalSellingRecommendation.getItemDimensions().getWidth().getUnit());
					recommendationOriginalData.setItemDimensionsWidthValue(
							globalSellingRecommendation.getItemDimensions().getWidth().getValue());
				}
				recommendationOriginalDataList.add(recommendationOriginalData);
			}
			return recommendationOriginalDataList;
		}
	}

	/**
	 * convert AdvertisingRecommendation to RecommendationOriginalData
	 * 
	 * @author Bens
	 * @param inventoryRecommendationList
	 * @return hibernate model List<RecommendationOriginalData>
	 */
	private static List<RecommendationOriginalData> resolveAdvertisingRecommendation(
			List<AdvertisingRecommendation> advertisingRecommendationList) {
		if (advertisingRecommendationList == null || advertisingRecommendationList.size() < 1) {
			return new ArrayList<RecommendationOriginalData>();
		} else {
			List<RecommendationOriginalData> recommendationOriginalDataList = new ArrayList<RecommendationOriginalData>();
			for (AdvertisingRecommendation advertisingRecommendation : advertisingRecommendationList) {
				RecommendationOriginalData recommendationOriginalData = new RecommendationOriginalData();
				recommendationOriginalData.setRecommendationId(advertisingRecommendation.getRecommendationId());
				recommendationOriginalData.setRecommendationReason(advertisingRecommendation.getRecommendationReason());
				recommendationOriginalData
						.setLastUpdated(advertisingRecommendation.getLastUpdated().toGregorianCalendar().getTime());
				if (advertisingRecommendation.isSetItemIdentifier()) {
					recommendationOriginalData
							.setItemIdentifierAsin(advertisingRecommendation.getItemIdentifier().getAsin());
					recommendationOriginalData
							.setItemIdentifierSku(advertisingRecommendation.getItemIdentifier().getSku());
					recommendationOriginalData
							.setItemIdentifierUpc(advertisingRecommendation.getItemIdentifier().getUpc());
				}
				recommendationOriginalData.setItemName(advertisingRecommendation.getItemName());
				recommendationOriginalData.setBrandName(advertisingRecommendation.getBrandName());
				recommendationOriginalData.setProductCategory(advertisingRecommendation.getProductCategory());
				recommendationOriginalData.setSalesRank(advertisingRecommendation.getSalesRank());
				if (advertisingRecommendation.isSetYourPricePlusShipping()) {
					recommendationOriginalData.setYourPricePlusShippingAmount(
							advertisingRecommendation.getYourPricePlusShipping().getAmount());
					recommendationOriginalData.setYourPricePlusShippingCurrency(
							advertisingRecommendation.getYourPricePlusShipping().getCurrencyCode());
				}
				if (advertisingRecommendation.isSetLowestPricePlusShipping()) {
					recommendationOriginalData.setLowestPricePlusShippingAmount(
							advertisingRecommendation.getLowestPricePlusShipping().getAmount());
					recommendationOriginalData.setLowestPricePlusShippingCurrency(
							advertisingRecommendation.getLowestPricePlusShipping().getCurrencyCode());
				}
				recommendationOriginalData.setAvailableQuantity(advertisingRecommendation.getAvailableQuantity());
				recommendationOriginalData
						.setSalesForTheLast30days(advertisingRecommendation.getSalesForTheLast30Days());
				recommendationOriginalDataList.add(recommendationOriginalData);
			}
			return recommendationOriginalDataList;
		}
	}
}
