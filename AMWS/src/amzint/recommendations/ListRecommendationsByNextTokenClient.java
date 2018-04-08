package amzint.recommendations;

import java.util.concurrent.Future;

import com.amazon.mws.recommendations.model.ListRecommendationsByNextTokenRequest;
import com.amazon.mws.recommendations.model.ListRecommendationsByNextTokenResponse;

import amzint.AbstractMWSRecommendationsSectionAsyncService;
import dao.entities.MerchantAccount;

/**
 * need next token to invoke, if next token is null, return null
 * 
 * @author Bens
 * @version 20160612
 */
public class ListRecommendationsByNextTokenClient extends AbstractMWSRecommendationsSectionAsyncService {
	private String nextToken;
	private Future<ListRecommendationsByNextTokenResponse> responseFuture;

	public ListRecommendationsByNextTokenClient(MerchantAccount merchant, String nextToken) {
		super(merchant);
		this.nextToken = nextToken;
	}

	public ListRecommendationsByNextTokenClient(MerchantAccount merchant, String nextToken, String serviceUrl) {
		super(merchant,serviceUrl);
		this.nextToken = nextToken;
	}

	/**
	 * need next token to invoke, if next token is null, set null
	 * 
	 * @exception invoke
	 *                exception
	 */
	public void invoke() throws Exception {
		if (nextToken != null){
			// Create a request.
			ListRecommendationsByNextTokenRequest request = new ListRecommendationsByNextTokenRequest();
			request.setSellerId(merchant.getId());
			request.setMWSAuthToken(merchant.getAuthToken());
			request.setNextToken(nextToken);

			// Make the call.
			responseFuture = service.listRecommendationsByNextTokenAsync(request);
		}
	}

	public String getNextToken() {
		return nextToken;
	}

	public void setNextToken(String nextToken) {
		this.nextToken = nextToken;
	}

	public Future<ListRecommendationsByNextTokenResponse> getResponseFuture() {
		return responseFuture;
	}
}
