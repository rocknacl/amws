package amzint.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsync;
import com.amazonservices.mws.products.model.GetProductCategoriesForASINRequest;
import com.amazonservices.mws.products.model.GetProductCategoriesForASINResponse;

import amzint.AbstractMarketplaceWebServiceProductsAsyncService;
import dao.entities.MerchantAccount;
import model.AmazonEnums.LimitConstant;

/**
 * Constructed by asin list and get all response to future list
 * 
 * @author Bens
 * @version 20160627
 */
public class GetProductCategoriesForASINAsyncClient extends AbstractMarketplaceWebServiceProductsAsyncService {
	private Map<String, Future<GetProductCategoriesForASINResponse>> responseList = new HashMap<String, Future<GetProductCategoriesForASINResponse>>();
	private List<String> asinList = new ArrayList<String>();

	public GetProductCategoriesForASINAsyncClient(MerchantAccount merchant, List<String> asinList) {
		super(merchant);
		this.asinList.addAll(asinList);
	}

	public GetProductCategoriesForASINAsyncClient(MarketplaceWebServiceProductsAsync service, MerchantAccount merchant,
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
			// Create a request list. extract asin from asin list
			GetProductCategoriesForASINRequest request = new GetProductCategoriesForASINRequest();
			request.setSellerId(merchant.getId());
			request.setMWSAuthToken(merchant.getAuthToken());
			request.setMarketplaceId(merchant.getMarketPlaceId());
			request.setASIN(asinList.get(0));
			responseList.put(asinList.get(0), service.getProductCategoriesForASINAsync(request));
			asinList.remove(0);
			if (asinList.isEmpty()) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * get future responses from asin list quantity
	 * 
	 * @author Bens
	 * @throws Exception
	 *             thread sleep exception
	 */
	public void invokeAll() throws Exception {
		while (invoke())
			Thread.sleep(60 * 60 * 1000 / LimitConstant.productCategoryQueryRestoreRatePerHour);
	}

	public List<String> getAsinList() {
		return asinList;
	}

	public void setAsinList(List<String> asinList) {
		this.asinList = asinList;
	}

	public Map<String, Future<GetProductCategoriesForASINResponse>> getResponseList() {
		return responseList;
	}

	public void setResponseList(Map<String, Future<GetProductCategoriesForASINResponse>> responseList) {
		this.responseList = responseList;
	}

}
