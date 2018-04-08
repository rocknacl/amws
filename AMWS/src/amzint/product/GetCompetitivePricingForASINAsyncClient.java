package amzint.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsync;
import com.amazonservices.mws.products.model.ASINListType;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINRequest;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINResponse;

import amzint.AbstractMarketplaceWebServiceProductsAsyncService;
import dao.entities.MerchantAccount;
import model.AmazonEnums.LimitConstant;

/**
 * invoke method return one future response from asin list; invokeAll return
 * Future responses
 * 
 * @author Bens
 * @version 20160621
 */
public class GetCompetitivePricingForASINAsyncClient extends AbstractMarketplaceWebServiceProductsAsyncService {
	private Map<List<String>, Future<GetCompetitivePricingForASINResponse>> responseList = new HashMap<List<String>, Future<GetCompetitivePricingForASINResponse>>();
	private List<String> asinList = new ArrayList<String>();

	public GetCompetitivePricingForASINAsyncClient(MerchantAccount merchant, List<String> asinList) {
		super(merchant);
		this.asinList.addAll(asinList);
	}

	public GetCompetitivePricingForASINAsyncClient(MarketplaceWebServiceProductsAsync service, MerchantAccount merchant,
			List<String> asinList) {
		super(service, merchant);
		this.asinList.addAll(asinList);
	}

	/**
	 * if asin list is null or size < 1 will return false
	 */
	public boolean invoke() throws Exception {
		if (asinList == null || asinList.isEmpty()) {
			return false;
		} else {
			// Create a request list. extract skus from asin list by max asin
			// quantity
			GetCompetitivePricingForASINRequest request = new GetCompetitivePricingForASINRequest();
			request.setSellerId(merchant.getId());
			request.setMWSAuthToken(merchant.getAuthToken());
			request.setMarketplaceId(merchant.getMarketPlaceId());
			ASINListType ASINList = new ASINListType();
			ArrayList<String> asinTempList = new ArrayList<String>();
			int count = 0;
			while (asinList.size() > 0 && count < LimitConstant.maxProductPriceQueryQuantity) {
				ASINList.withASIN(asinList.get(0));
				asinTempList.add(asinList.get(0));
				asinList.remove(0);
				count++;
			}
			request.setASINList(ASINList);
			responseList.put(asinTempList, service.getCompetitivePricingForASINAsync(request));
			if (asinList.isEmpty()) {
				return false;
			} else {
				return true;
			}

		}
	}

	/**
	 * get future responses from sku list and separated by max request sku
	 * quantity
	 * 
	 * @author Bens
	 * @throws Exception
	 *             thread sleep exception
	 */
	public void invokeAll() throws Exception {
		while (invoke())
			Thread.sleep(60 * 60 * 1000 / LimitConstant.productPriceQueryRestoreRatePerHour);
	}

	public Map<List<String>, Future<GetCompetitivePricingForASINResponse>> getResponseList() {
		return responseList;
	}

	public void setResponseList(HashMap<List<String>, Future<GetCompetitivePricingForASINResponse>> responselist) {
		this.responseList = responselist;
	}

	public List<String> getAsinList() {
		return asinList;
	}

	public void setAsinList(List<String> asinList) {
		this.asinList = asinList;
	}

}
