package amzint.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsync;
import com.amazonservices.mws.products.model.GetLowestPricedOffersForASINRequest;
import com.amazonservices.mws.products.model.GetLowestPricedOffersForASINResponse;

import amzint.AbstractMarketplaceWebServiceProductsAsyncService;
import dao.entities.MerchantAccount;
import model.AmazonEnums.LimitConstant;

public class GetLowestPricedOffersForASINAsyncClient extends AbstractMarketplaceWebServiceProductsAsyncService {
	private Map<String,Future<GetLowestPricedOffersForASINResponse>> responseList = new HashMap<String,Future<GetLowestPricedOffersForASINResponse>>();
	private List<String> asinList = new ArrayList<String>();

	public GetLowestPricedOffersForASINAsyncClient(MerchantAccount merchant, List<String> asinList) {
		super(merchant);
		this.asinList.addAll(asinList);
	}

	public GetLowestPricedOffersForASINAsyncClient(MarketplaceWebServiceProductsAsync service,
			MerchantAccount merchant, List<String> asinList) {
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
			// Create a request list. extract asin from asin list
	        GetLowestPricedOffersForASINRequest request = new GetLowestPricedOffersForASINRequest();
	        request.setSellerId(merchant.getId());
	        request.setMWSAuthToken(merchant.getAuthToken());
	        request.setMarketplaceId(merchant.getMarketPlaceId());
	        request.setASIN(asinList.get(0));
//	        String itemCondition = "example";
//	        request.setItemCondition(itemCondition);
			responseList.put(asinList.get(0),service.getLowestPricedOffersForASINAsync(request));
	        asinList.remove(0);
			return true;
		}
	}

	/**
	 * get future responses from asin list
	 * quantity
	 * 
	 * @author Bens
	 * @throws Exception
	 *             thread sleep exception
	 */
	public void invokeAll() throws Exception {
		while (invoke())
			Thread.sleep(60*60*1000 / LimitConstant.productLowestPricedOffersRestoreRatePerHour);
	}

	public Map<String,Future<GetLowestPricedOffersForASINResponse>> getResponseList() {
		return responseList;
	}

	public void setResponseList(HashMap<String,Future<GetLowestPricedOffersForASINResponse>> responseList) {
		this.responseList = responseList;
	}

	public List<String> getAsinList() {
		return asinList;
	}

	public void setAsinList(List<String> asinList) {
		this.asinList = asinList;
	}

}
