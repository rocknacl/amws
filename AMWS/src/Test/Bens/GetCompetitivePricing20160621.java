package Test.Bens;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.amazonservices.mws.products.model.CompetitivePriceType;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINResponse;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINResult;
import com.amazonservices.mws.products.model.IdentifierType;
import com.amazonservices.mws.products.model.LowestOfferListingType;
import com.amazonservices.mws.products.model.OfferType;
import com.amazonservices.mws.products.model.Product;
import com.amazonservices.mws.products.model.ResponseHeaderMetadata;
import com.amazonservices.mws.products.model.SalesRankType;

import amzint.product.GetCompetitivePricingForASINAsyncClient;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;

public class GetCompetitivePricing20160621 {

	public static void main(String[] args) throws Exception {
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName("UP");
		List<String> asinList = new ArrayList<String>();
//		asinList.add("B0188FPWOM");
		asinList.add("B00OZBQMNY");
		GetCompetitivePricingForASINAsyncClient client = new GetCompetitivePricingForASINAsyncClient(merchant,asinList);
		client.invoke();
		 GetCompetitivePricingForASINResponse response = client.getResponseList().get(0).get();
         ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
         // We recommend logging every the request id and timestamp of every call.
         System.out.println("Response:");
         System.out.println("RequestId: "+rhmd.getRequestId());
         System.out.println("Timestamp: "+rhmd.getTimestamp());
         String responseXml = response.toXML();
         GetCompetitivePricingForASINResult result = response.getGetCompetitivePricingForASINResult().get(0);
         System.out.println(result.getASIN());// String
         System.out.println(result.getStatus());// String
         System.out.println(result.getError());// String
         System.out.println(result.getProduct());// Product
         Product product = result.getProduct();
//         System.out.println("AttributeSets Start");// null
//         System.out.println(product.getAttributeSets().getAny().get(0).getClass().getName());
//         for(Field field :product.getAttributeSets().getAny().get(0).getClass().getFields())
//         System.out.println(field.getName());
//         System.out.println("AttributeSets End");
//         System.out.println("Relationships Start");
//         System.out.println(product.getRelationships().getAny().get(0).getClass().getName());
//         for(Field field :product.getRelationships().getAny().get(0).getClass().getFields())
//         System.out.println(field.getName());
//         System.out.println("Relationships End");
         CompetitivePriceType competitiveprice = product.getCompetitivePricing().getCompetitivePrices().getCompetitivePrice().get(0);// competitive list
         System.out.println(competitiveprice.getCompetitivePriceId());// String
         System.out.println(competitiveprice.getCondition());// String
         System.out.println(competitiveprice.getSubcondition());// String
         System.out.println(competitiveprice.getPrice());//PriceType
         IdentifierType identifier = product.getIdentifiers();
         System.out.println(identifier.getMarketplaceASIN().getASIN());// String
         System.out.println(identifier.getMarketplaceASIN().getMarketplaceId());// String
         System.out.println(identifier.getSKUIdentifier().getMarketplaceId());// String
         System.out.println(identifier.getSKUIdentifier().getSellerId());// String
         System.out.println(identifier.getSKUIdentifier().getSellerSKU());// String
         LowestOfferListingType lowest = product.getLowestOfferListings().getLowestOfferListing().get(0);// lowest list
         System.out.println(lowest.getMultipleOffersAtLowestPrice());// String
         System.out.println(lowest.getSellerFeedbackCount());// Integer
         System.out.println(lowest.getNumberOfOfferListingsConsidered());// Integer
         System.out.println(lowest.getPrice());// PriceType
         System.out.println(lowest.getQualifiers().getFulfillmentChannel());// String
         System.out.println(lowest.getQualifiers().getItemCondition());// String
         System.out.println(lowest.getQualifiers().getItemSubcondition());// String
         System.out.println(lowest.getQualifiers().getSellerPositiveFeedbackRating());// String
         System.out.println(lowest.getQualifiers().getShipsDomestically());// String
         System.out.println(lowest.getQualifiers().getShippingTime().getMax());// String
         OfferType offer = product.getOffers().getOffer().get(0);// offer list
         System.out.println(offer.getBuyingPrice());// PriceType
         System.out.println(offer.getFulfillmentChannel());// String
         System.out.println(offer.getItemCondition());// String
         System.out.println(offer.getItemSubCondition());// String
         System.out.println(offer.getSellerId());// String
         System.out.println(offer.getSellerSKU());// String
         System.out.println(offer.getRegularPrice());// MoneyType
         SalesRankType  rank = product.getSalesRankings().getSalesRank().get(0);// rank list
         System.out.println(rank.getProductCategoryId());// String
         System.out.println(rank.getRank());// Integer
         System.out.println("XML");
         System.out.println(responseXml);
	}

}
