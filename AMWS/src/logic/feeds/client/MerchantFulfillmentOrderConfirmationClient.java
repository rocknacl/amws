package logic.feeds.client;

import java.io.File;

import com.amazonaws.mws.model.SubmitFeedResponse;

import amzint.feeds.FeedClient;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;

/**
 * 
 * @author D16
 * @Description confirm merchant fulfillment order(DI).
 * @Create_Date JUN 20, 2016
 * @Last_Update JUN 20, 2016
 *
 */
public class MerchantFulfillmentOrderConfirmationClient {

	public void getFeedFile(String feedFileDir){
		// TODO
	}
	
	public void callAmazon(String feedFileDir, String feedFileHistoryDir){
		getFeedFile(feedFileDir);
		
		File[] feedFileList = new File( feedFileDir ).listFiles();
		for ( File file : feedFileList ) {
			try {
				String sellerCode = file.getName().split("_")[1];
				MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(sellerCode);
				SubmitFeedResponse submitFeedResponse
					= new FeedClient(merchant).submitFeed("_POST_FLAT_FILE_FULFILLMENT_DATA_", file.getPath());

				if ( submitFeedResponse == null ) {
					System.out.println("\t" + sellerCode + "\t" +  file.getName() + "\tFailed");
				} else {
					System.out.println("\t" + sellerCode + "\t" + file.getName()
							+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedSubmissionId()
							+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedType()
							+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedProcessingStatus());
				}
				
				// move feed file --> feed file history
				file.renameTo(new File(feedFileHistoryDir + "/" + file.getName()));
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	public void merchantFulfillmentOrderConfirmation(String feedFileDir, String feedFileHistoryDir){
		getFeedFile(feedFileDir);
		callAmazon(feedFileDir, feedFileHistoryDir);
	}
	
}
