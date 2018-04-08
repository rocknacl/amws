package amzint.recommendations;

import java.util.concurrent.Future;

import com.amazon.mws.recommendations.model.ListRecommendationsRequest;
import com.amazon.mws.recommendations.model.ListRecommendationsResponse;

import amzint.AbstractMWSRecommendationsSectionAsyncService;
import dao.entities.MerchantAccount;

/**
 * get first recommendations
 * @author Bens
 *
 */
public class ListRecommendationsClient extends AbstractMWSRecommendationsSectionAsyncService{
	private Future<ListRecommendationsResponse> responseFuture;

	public ListRecommendationsClient(MerchantAccount merchant) {
		super(merchant);
	}

	public ListRecommendationsClient(MerchantAccount merchant, String serviceUrl) {
		super(merchant,serviceUrl);
	}

	/**
	 * @exception invoke exception
	 */
	public void invoke() throws Exception{
		// Create a request.
        ListRecommendationsRequest request = new ListRecommendationsRequest();
        request.setMarketplaceId(merchant.getMarketPlaceId());
        request.setMWSAuthToken(merchant.getAuthToken());
        request.setSellerId(merchant.getId());
//        String recommendationCategory = "example";
//        request.setRecommendationCategory(recommendationCategory);
//        List<CategoryQuery> categoryQueryList = new ArrayList<CategoryQuery>();
//        request.setCategoryQueryList(categoryQueryList);

        // Make the call.
        responseFuture = service.listRecommendationsAsync(request);
	}

	public Future<ListRecommendationsResponse> getResponseFuture() {
		return responseFuture;
	}
}
