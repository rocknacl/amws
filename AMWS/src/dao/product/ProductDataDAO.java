package dao.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.amazonservices.mws.products.model.ASINOfferDetail;
import com.amazonservices.mws.products.model.BuyBoxPriceType;
import com.amazonservices.mws.products.model.Categories;
import com.amazonservices.mws.products.model.CompetitivePriceType;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINResponse;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINResult;
import com.amazonservices.mws.products.model.GetLowestPricedOffersForASINResponse;
import com.amazonservices.mws.products.model.GetLowestPricedOffersForASINResult;
import com.amazonservices.mws.products.model.GetProductCategoriesForASINResponse;
import com.amazonservices.mws.products.model.LowestOfferListingType;
import com.amazonservices.mws.products.model.LowestPriceType;
import com.amazonservices.mws.products.model.OfferCountType;
import com.amazonservices.mws.products.model.OfferType;
import com.amazonservices.mws.products.model.SalesRankType;
import com.amazonservices.mws.products.model.Summary;

import dao.entities.GeneralResponseRecord;
import dao.entities.ProductAsinCategoryData;
import dao.entities.ProductCategories;
import dao.entities.ProductCompetitiveLowestPricedOffersData;
import dao.entities.ProductCompetitivePrice;
import dao.entities.ProductCompetitivePricingData;
import dao.entities.ProductLowestPricedOffersData;
import dao.entities.ProductOfferCountData;
import dao.entities.ProductOfferDetailData;
import dao.entities.ProductOfferListing;
import dao.entities.ProductOfferPriceData;
import dao.entities.ProductSalesRank;
import helper.amzint.DateConverter;
import helper.dao.HibernateUtils;

/**
 * Product application program interface data converter
 * 
 * @author Bens
 * @version 20160628
 */
public class ProductDataDAO {
	private static final String yes = "ture";
	// private static final String no = "false";
	
	public static void saveCompetitivePricingResponse(Map<List<String>, Future<GetCompetitivePricingForASINResponse>> finishedResponse)throws Exception {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		saveCompetitivePricingData(session,finishedResponse);
		transaction.commit();
		HibernateUtils.closeSession(session);
	}
	
	public static void saveCategoriesResponse(Map<String, Future<GetProductCategoriesForASINResponse>> finishedResponse)throws Exception {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		saveCategoriesData(session,finishedResponse);
		transaction.commit();
		HibernateUtils.closeSession(session);
	}

	public static void saveLowestPricedOffersResponse(Map<String, Future<GetLowestPricedOffersForASINResponse>> finishedResponse)throws Exception {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		saveLowestPricedOffersData(session,finishedResponse);
		transaction.commit();
		HibernateUtils.closeSession(session);
	}
	
	private static ProductCategories saveCategories(Session session, Categories category) throws Exception {
		ProductCategories categories = (ProductCategories) session.get(ProductCategories.class,
				category.getProductCategoryId());
		if (categories == null) {
			categories = new ProductCategories();
			categories.setProductCategoryId(category.getProductCategoryId());
			categories.setProductCategoryName(category.getProductCategoryName());
			if (category.getParent() == null) {
				session.save(categories);
				return categories;
			} else {
				categories.setProductCategories(saveCategories(session, category.getParent()));
				session.save(categories);
				return categories;
			}
		} else {
			if (category.getParent() == null) {
				categories.setProductCategoryName(category.getProductCategoryName());
				return categories;
			} else {
				categories.setProductCategoryName(category.getProductCategoryName());
				categories.setProductCategories(saveCategories(session, category.getParent()));
				return categories;
			}
		}
	}

	private static void saveCategoriesData(Session session, Map<String, Future<GetProductCategoriesForASINResponse>> responseMap) throws Exception {
		for (Entry<String, Future<GetProductCategoriesForASINResponse>> entry : responseMap.entrySet()) {
			GetProductCategoriesForASINResponse response = entry.getValue().get();
			ProductAsinCategoryData data = new ProductAsinCategoryData();
			data.setAsin(entry.getKey());
			data.setGeneralResponseRecord((GeneralResponseRecord) session.get(GeneralResponseRecord.class,
					response.getResponseHeaderMetadata().getRequestId()));
			data.setTstamp(new Date());
			if (response.isSetGetProductCategoriesForASINResult()
					&& response.getGetProductCategoriesForASINResult().isSetSelf()
					&& !response.getGetProductCategoriesForASINResult().getSelf().isEmpty())
				for (Categories category : response.getGetProductCategoriesForASINResult().getSelf()) {
					data.setProductCategories(saveCategories(session, category));
				}
			session.save(data);
		}
	}

	private static void saveCompetitivePricingData(Session session, Map<List<String>, Future<GetCompetitivePricingForASINResponse>> responseMap) throws Exception {
		for (Entry<List<String>, Future<GetCompetitivePricingForASINResponse>> entry : responseMap.entrySet()) {
			GetCompetitivePricingForASINResponse response = entry.getValue().get();
			if (response.isSetGetCompetitivePricingForASINResult()) {
				List<GetCompetitivePricingForASINResult> resultList = response.getGetCompetitivePricingForASINResult();
				if (resultList != null && !resultList.isEmpty()) {
					for (GetCompetitivePricingForASINResult result : resultList) {
						// save result data
						ProductCompetitivePricingData data = new ProductCompetitivePricingData();
						data.setAsin(result.getASIN());
						data.setStatus(result.getStatus());
						data.setTstamp(new Date());
						if (result.isSetProduct() && result.getProduct().isSetIdentifiers()
								&& result.getProduct().getIdentifiers().isSetSKUIdentifier()) {
							data.setMarketplaceId(
									result.getProduct().getIdentifiers().getSKUIdentifier().getMarketplaceId());
							data.setSellerId(result.getProduct().getIdentifiers().getSKUIdentifier().getSellerId());
							data.setSellerSku(result.getProduct().getIdentifiers().getSKUIdentifier().getSellerSKU());
						}
						session.save(data);
						// save competitive pricing list
						if (result.isSetProduct() && result.getProduct().isSetCompetitivePricing()
								&& result.getProduct().getCompetitivePricing().isSetCompetitivePrices()
								&& result.getProduct().getCompetitivePricing().getCompetitivePrices()
										.isSetCompetitivePrice()
								&& !result.getProduct().getCompetitivePricing().getCompetitivePrices()
										.getCompetitivePrice().isEmpty()) {
							for (CompetitivePriceType competitivePriceType : result.getProduct().getCompetitivePricing()
									.getCompetitivePrices().getCompetitivePrice()) {
								ProductCompetitivePrice competitivePriceData = new ProductCompetitivePrice();
								competitivePriceData.setProductCompetitivePricingData(data);	
								competitivePriceData
										.setCompetitivePriceId(competitivePriceType.getCompetitivePriceId());
								competitivePriceData.setConditions(competitivePriceType.getCondition());
								competitivePriceData.setSubcondition(competitivePriceType.getSubcondition());
								competitivePriceData.setPriceLandedAmount(
										competitivePriceType.getPrice().getLandedPrice().getAmount());
								competitivePriceData.setPriceLandedCurrency(
										competitivePriceType.getPrice().getLandedPrice().getCurrencyCode());
								competitivePriceData.setPriceListingAmount(
										competitivePriceType.getPrice().getListingPrice().getAmount());
								competitivePriceData.setPriceListingCurrency(
										competitivePriceType.getPrice().getListingPrice().getCurrencyCode());
								competitivePriceData.setPriceShippingAmount(
										competitivePriceType.getPrice().getShipping().getAmount());
								competitivePriceData.setPriceShippingCurrency(
										competitivePriceType.getPrice().getShipping().getCurrencyCode());
								session.save(competitivePriceData);
							}
						}
						// save lowest offer list
						if (result.isSetProduct() && result.getProduct().isSetLowestOfferListings()
								&& result.getProduct().getLowestOfferListings().isSetLowestOfferListing()
								&& !result.getProduct().getLowestOfferListings().getLowestOfferListing().isEmpty()) {
							for (LowestOfferListingType lowestOfferListing : result.getProduct()
									.getLowestOfferListings().getLowestOfferListing()) {
								ProductCompetitiveLowestPricedOffersData lowestPricedOffersData = new ProductCompetitiveLowestPricedOffersData();
								lowestPricedOffersData.setProductCompetitivePricingData(data);
								lowestPricedOffersData.setMultipleOffersAtLowestPrice(
										lowestOfferListing.getMultipleOffersAtLowestPrice());
								lowestPricedOffersData
										.setSellerFeedbackCount(lowestOfferListing.getSellerFeedbackCount());
								lowestPricedOffersData.setNumberOfOfferListingsConsidered(
										lowestOfferListing.getNumberOfOfferListingsConsidered());
								lowestPricedOffersData.setPriceLandedAmount(
										lowestOfferListing.getPrice().getLandedPrice().getAmount());
								lowestPricedOffersData.setPriceLandedCurrency(
										lowestOfferListing.getPrice().getLandedPrice().getCurrencyCode());
								lowestPricedOffersData.setPriceListingAmount(
										lowestOfferListing.getPrice().getListingPrice().getAmount());
								lowestPricedOffersData.setPriceListingCurrency(
										lowestOfferListing.getPrice().getListingPrice().getCurrencyCode());
								lowestPricedOffersData.setPriceShippingAmount(
										lowestOfferListing.getPrice().getShipping().getAmount());
								lowestPricedOffersData.setPriceShippingCurrency(
										lowestOfferListing.getPrice().getShipping().getCurrencyCode());
								lowestPricedOffersData.setFulfillmentChannel(
										lowestOfferListing.getQualifiers().getFulfillmentChannel());
								lowestPricedOffersData
										.setItemCondition(lowestOfferListing.getQualifiers().getItemCondition());
								lowestPricedOffersData
										.setItemSubcondition(lowestOfferListing.getQualifiers().getItemSubcondition());
								lowestPricedOffersData.setSellerPositiveFeedbackRating(
										lowestOfferListing.getQualifiers().getSellerPositiveFeedbackRating());
								lowestPricedOffersData.setShipsDomestically(
										lowestOfferListing.getQualifiers().getShipsDomestically());
								lowestPricedOffersData.setMaxShippingTime(
										lowestOfferListing.getQualifiers().getShippingTime().getMax());
								session.save(lowestPricedOffersData);
							}
						}
						// save offer list
						if (result.isSetProduct() && result.getProduct().isSetOffers()
								&& result.getProduct().getOffers().isSetOffer()
								&& !result.getProduct().getOffers().getOffer().isEmpty()) {
							for (OfferType offerType : result.getProduct().getOffers().getOffer()) {
								ProductOfferListing offerListingData = new ProductOfferListing();
								offerListingData.setProductCompetitivePricingData(data);
								offerListingData.setBuyingPriceLandedAmount(
										offerType.getBuyingPrice().getLandedPrice().getAmount());
								offerListingData.setBuyingPriceLandedCurrency(
										offerType.getBuyingPrice().getLandedPrice().getCurrencyCode());
								offerListingData.setBuyingPriceListingAmount(
										offerType.getBuyingPrice().getListingPrice().getAmount());
								offerListingData.setBuyingPriceListingCurrency(
										offerType.getBuyingPrice().getListingPrice().getCurrencyCode());
								offerListingData.setBuyingPriceShippingAmount(
										offerType.getBuyingPrice().getShipping().getAmount());
								offerListingData.setBuyingPriceShippingCurrency(
										offerType.getBuyingPrice().getShipping().getCurrencyCode());
								offerListingData.setFulfillmentChannel(offerType.getFulfillmentChannel());
								offerListingData.setItemCondition(offerType.getItemCondition());
								offerListingData.setItemSubCondition(offerType.getItemSubCondition());
								offerListingData.setSellerId(offerType.getSellerId());
								offerListingData.setSellerSku(offerType.getSellerSKU());
								offerListingData.setRegularPriceAmount(offerType.getRegularPrice().getAmount());
								offerListingData.setRegularPriceCurrency(offerType.getRegularPrice().getCurrencyCode());
								session.save(offerListingData);
							}
						}
						if (result.isSetProduct() && result.getProduct().isSetSalesRankings()
								&& result.getProduct().getSalesRankings().isSetSalesRank()
								&& !result.getProduct().getSalesRankings().getSalesRank().isEmpty()) {
							for (SalesRankType salesRankType : result.getProduct().getSalesRankings().getSalesRank()) {
								ProductCategories productCategories = (ProductCategories) session
										.get(ProductCategories.class, salesRankType.getProductCategoryId());
								if (productCategories == null) {
									productCategories = new ProductCategories();
									productCategories.setProductCategoryId(salesRankType.getProductCategoryId());
									session.save(productCategories);
								}
								ProductSalesRank salesRankData = new ProductSalesRank();
								salesRankData.setProductCompetitivePricingData(data);
								salesRankData.setProductCategories(productCategories);
								salesRankData.setRank(salesRankType.getRank());
								session.save(salesRankData);
							}
						}
					}
				}
			}
		}
	}

	private static void saveLowestPricedOffersData(Session session, Map<String, Future<GetLowestPricedOffersForASINResponse>> responseMap) throws Exception {
		for (Entry<String, Future<GetLowestPricedOffersForASINResponse>> entry : responseMap.entrySet()) {
			GetLowestPricedOffersForASINResponse response = entry.getValue().get();
			if (response.isSetGetLowestPricedOffersForASINResult()) {
				GetLowestPricedOffersForASINResult result = response.getGetLowestPricedOffersForASINResult();
				ProductLowestPricedOffersData data = new ProductLowestPricedOffersData();
				data.setAsin(result.getASIN());
				data.setStatus(result.getStatus());
				data.setMarketplaceId(result.getMarketplaceID());
				data.setItemCondition(result.getItemCondition());
				data.setGeneralResponseRecord((GeneralResponseRecord) session.get(GeneralResponseRecord.class,
						response.getResponseHeaderMetadata().getRequestId()));
				data.setTstamp(new Date());
				if (result.isSetIdentifier()) {
					data.setLowestOfferMarketplaceId(result.getIdentifier().getMarketplaceId());
					data.setLowestOfferAsin(result.getIdentifier().getASIN());
					data.setLowestOfferItemCondition(result.getIdentifier().getItemCondition());
					data.setLowestOfferTimeOfOfferChange(
							DateConverter.XMLGregorianCalendar2Date(result.getIdentifier().getTimeOfOfferChange()));
				}
				session.save(data);
				if (result.isSetSummary()) {
					Summary summary = result.getSummary();
					if (summary.isSetListPrice()) {
						data.setListPriceAmount(summary.getListPrice().getAmount());
						data.setListPriceCurrency(summary.getListPrice().getCurrencyCode());
					}
					if (summary.isSetSuggestedLowerPricePlusShipping()) {
						data.setSuggestedLowerPricePlusShippingAmount(
								summary.getSuggestedLowerPricePlusShipping().getAmount());
						data.setSuggestedLowerPricePlusShippingCurrency(
								summary.getSuggestedLowerPricePlusShipping().getCurrencyCode());
					}
					if (summary.isSetNumberOfOffers() && summary.getNumberOfOffers().isSetOfferCount()
							&& !summary.getNumberOfOffers().getOfferCount().isEmpty()) {
						for (OfferCountType offerCountType : summary.getNumberOfOffers().getOfferCount()) {
							ProductOfferCountData offerCountData = new ProductOfferCountData();
							offerCountData.setProductLowestPricedOffersData(data);
							offerCountData.setConditions(offerCountType.getCondition());
							offerCountData.setFulfillmentChannel(offerCountType.getFulfillmentChannel());
							offerCountData.setOfferCount(offerCountType.getOfferCount());
							session.save(offerCountData);
						}
					}
					if (summary.isSetBuyBoxEligibleOffers() && summary.getBuyBoxEligibleOffers().isSetOfferCount()
							&& !summary.getBuyBoxEligibleOffers().getOfferCount().isEmpty()) {
						for (OfferCountType offerCountType : summary.getBuyBoxEligibleOffers().getOfferCount()) {
							ProductOfferCountData offerCountData = new ProductOfferCountData();
							offerCountData.setProductLowestPricedOffersData(data);
							offerCountData.setConditions(offerCountType.getCondition());
							offerCountData.setFulfillmentChannel(offerCountType.getFulfillmentChannel());
							offerCountData.setOfferCount(offerCountType.getOfferCount());
							offerCountData.setBuyBoxEligible(yes);
							session.save(offerCountData);
						}
					}
					if (summary.isSetLowestPrices() && summary.getLowestPrices().isSetLowestPrice()
							&& !summary.getLowestPrices().getLowestPrice().isEmpty()) {
						for (LowestPriceType lowestPriceType : summary.getLowestPrices().getLowestPrice()) {
							ProductOfferPriceData offerPriceData = new ProductOfferPriceData();
							offerPriceData.setProductLowestPricedOffersData(data);
							offerPriceData.setConditions(lowestPriceType.getCondition());
							offerPriceData.setFulfillmentChannel(lowestPriceType.getFulfillmentChannel());
							offerPriceData.setLandedPriceAmount(lowestPriceType.getLandedPrice().getAmount());
							offerPriceData.setLandedPriceCurrency(lowestPriceType.getLandedPrice().getCurrencyCode());
							offerPriceData.setListingPriceAmount(lowestPriceType.getListingPrice().getAmount());
							offerPriceData.setListingPriceCurrency(lowestPriceType.getListingPrice().getCurrencyCode());
							offerPriceData.setShippingAmount(lowestPriceType.getShipping().getAmount());
							offerPriceData.setShippingCurrency(lowestPriceType.getShipping().getCurrencyCode());
							session.save(offerPriceData);
						}
					}
					if (summary.isSetBuyBoxPrices() && summary.getBuyBoxPrices().isSetBuyBoxPrice()
							&& !summary.getBuyBoxPrices().getBuyBoxPrice().isEmpty()) {
						for (BuyBoxPriceType buyBoxPriceType : summary.getBuyBoxPrices().getBuyBoxPrice()) {
							ProductOfferPriceData offerPriceData = new ProductOfferPriceData();
							offerPriceData.setProductLowestPricedOffersData(data);
							offerPriceData.setConditions(buyBoxPriceType.getCondition());
							offerPriceData.setLandedPriceAmount(buyBoxPriceType.getLandedPrice().getAmount());
							offerPriceData.setLandedPriceCurrency(buyBoxPriceType.getLandedPrice().getCurrencyCode());
							offerPriceData.setListingPriceAmount(buyBoxPriceType.getListingPrice().getAmount());
							offerPriceData.setListingPriceCurrency(buyBoxPriceType.getListingPrice().getCurrencyCode());
							offerPriceData.setShippingAmount(buyBoxPriceType.getShipping().getAmount());
							offerPriceData.setShippingCurrency(buyBoxPriceType.getShipping().getCurrencyCode());
							offerPriceData.setIsBuyBoxPrice(yes);
							session.save(offerPriceData);
						}
					}
				}
				if (result.isSetOffers() && result.getOffers().isSetOffer()
						&& !result.getOffers().getOffer().isEmpty()) {
					for (ASINOfferDetail asinOfferDetail : result.getOffers().getOffer()) {
						ProductOfferDetailData offerDetailData = new ProductOfferDetailData();
						offerDetailData.setProductLowestPricedOffersData(data);
						offerDetailData.setSubCondition(asinOfferDetail.getSubCondition());
						if (asinOfferDetail.isSetSellerFeedbackRating()) {
							offerDetailData.setSellerFeedbackRatingCount(
									new BigDecimal(asinOfferDetail.getSellerFeedbackRating().getFeedbackCount())
											.intValue());
							offerDetailData.setSellerFeedbackRatingPositiveRate(new BigDecimal(
									asinOfferDetail.getSellerFeedbackRating().getSellerPositiveFeedbackRating())
											.floatValue());
						}
						if (asinOfferDetail.isSetShippingTime()) {
							offerDetailData.setShippingTimeMinHours(
									new BigDecimal(asinOfferDetail.getShippingTime().getMinimumHours()).intValue());
							offerDetailData.setShippingTimeMaxHours(
									new BigDecimal(asinOfferDetail.getShippingTime().getMaximumHours()).intValue());
							offerDetailData.setShippingTimeAvailabilityType(
									asinOfferDetail.getShippingTime().getAvailabilityType());
							offerDetailData.setShippingTimeAvailableDate(DateConverter
									.XMLGregorianCalendar2Date(asinOfferDetail.getShippingTime().getAvailableDate()));
						}
						if(asinOfferDetail.isSetListingPrice()){
							offerDetailData.setListingPriceAmount(asinOfferDetail.getListingPrice().getAmount());
							offerDetailData.setListingPriceCurrency(asinOfferDetail.getListingPrice().getCurrencyCode());
						}
						if(asinOfferDetail.isSetShipping()){
							offerDetailData.setShippingAmount(asinOfferDetail.getShipping().getAmount());
							offerDetailData.setShippingCurrency(asinOfferDetail.getShipping().getCurrencyCode());
						}
						if(asinOfferDetail.isSetShipsFrom()){
							offerDetailData.setShipsFromCountry(asinOfferDetail.getShipsFrom().getCountry());
							offerDetailData.setShipsFromState(asinOfferDetail.getShipsFrom().getState());
						}
						offerDetailData.setIsBuyBoxWinner(""+asinOfferDetail.getIsBuyBoxWinner().booleanValue());
						offerDetailData.setIsFeaturedMerchant(""+asinOfferDetail.getIsFeaturedMerchant().booleanValue());
						offerDetailData.setIsFulfilledByAmazon(""+asinOfferDetail.getIsFulfilledByAmazon());
						session.save(offerDetailData);
					}
				}
			}
		}
	}

}
