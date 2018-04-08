package amzint.feeds;

import java.io.FileInputStream;
import java.util.Arrays;

import com.amazonaws.mws.model.GetFeedSubmissionResultRequest;
import com.amazonaws.mws.model.GetFeedSubmissionResultResponse;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.SubmitFeedRequest;
import com.amazonaws.mws.model.SubmitFeedResponse;

import amzint.AbstractFeedService;
import dao.entities.MerchantAccount;

public class FeedClient extends AbstractFeedService{

	public FeedClient(MerchantAccount merchant) {
		super(merchant);
	}
	
	public SubmitFeedResponse submitFeed(String feedType, String filePath) throws Exception{
		SubmitFeedRequest request = new SubmitFeedRequest();
		request.setMerchant( this.merchant.getId() );
		request.setMarketplaceIdList(new IdList(Arrays.asList(this.merchant.getMarketPlaceId())));
		request.setFeedType( feedType );
		request.setFeedContent( new FileInputStream(filePath) );
		return service.submitFeedFromFile(request);
	}
	
	public GetFeedSubmissionResultResponse getFeedSubmissionResult( String submissionId ) throws Exception{
		GetFeedSubmissionResultRequest request = new GetFeedSubmissionResultRequest();
		request.setMerchant( this.merchant.getId() );
		request.setFeedSubmissionId( submissionId );
		request.setMWSAuthToken( this.merchant.getAuthToken() );
		return service.getFeedSubmissionResult(request);
	}

}
