package control.process;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.SubmitFeedRequest;
import com.amazonaws.mws.model.SubmitFeedResponse;

import amzint.MarketplaceWebServiceFactory;
import control.transmission.Message;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import model.basic.FileData;

/**
 * merchant fulfillment order confirmation.
 * @author D16
 * @note feedFileDir: dir to save merchant fulfillment order confirmation feed file.
 */
public class MerchantFulfillmentOrderConfirmation {

	private final String feedFileDir = "file/Feed/OrderConfirm";
	
	public Message process(Message message) throws Exception{
		ArrayList<FileData> fileDataList = (ArrayList<FileData>) message.getData();
		for (FileData fileData : fileDataList){
			try {
				String filePath = feedFileDir + "/" + fileData.getFilename();
				fileData.saveFile(filePath);
				
				String sellerCode = fileData.getFilename().split("_")[1];
				MerchantAccount merchant = MerchantAccountDAO.getMerchantByName(sellerCode);
				SubmitFeedResponse submitFeedResponse
					= new MarketplaceWebServiceClient(
							merchant.getAccessKey(), 
							merchant.getSecretKey(),
							new MarketplaceWebServiceFactory().getAppName(),
							new MarketplaceWebServiceFactory().getAppVersion(),
							new MarketplaceWebServiceConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl())
							).submitFeedFromFile(
									new SubmitFeedRequest()
										.withMerchant(merchant.getId())
										.withMarketplaceIdList(new IdList(Arrays.asList(merchant.getMarketPlaceId())))
										.withFeedType("_POST_FLAT_FILE_FULFILLMENT_DATA_")
										.withFeedContent(new FileInputStream(filePath))
										);
				if ( submitFeedResponse == null ) {
					System.out.println("\t" + sellerCode + "\t" +  filePath + "\tFailed");
				} else {
					System.out.println("\t" + sellerCode + "\t" + filePath
							+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedSubmissionId()
							+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedType()
							+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedProcessingStatus());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] aaaa){
		try {
			String sellerCode = "XGL";
			String filePath = "file/Feed/OrderConfirm/20160722084701_XGL_ConfirmOrders.txt";
			MerchantAccount merchant = MerchantAccountDAO.getMerchantByName("XGL");
			SubmitFeedResponse submitFeedResponse
				= new MarketplaceWebServiceClient(
					merchant.getAccessKey(), 
					merchant.getSecretKey(),
					new MarketplaceWebServiceFactory().getAppName(),
					new MarketplaceWebServiceFactory().getAppVersion(),
					new MarketplaceWebServiceConfig().withServiceURL(new MarketplaceWebServiceFactory().getDefaultServiceUrl())
					).submitFeedFromFile(
							new SubmitFeedRequest()
								.withMerchant(merchant.getId())
								.withMarketplaceIdList(new IdList(Arrays.asList(merchant.getMarketPlaceId())))
								.withFeedType("_POST_FLAT_FILE_FULFILLMENT_DATA_")
								.withFeedContent(new FileInputStream(filePath))
								);
			if ( submitFeedResponse == null ) {
				System.out.println("Order Confirmation\t" + sellerCode + "\t" +  filePath + "\tFailed");
			} else {
				System.out.println("Order Confirmation\t" + sellerCode + "\t" + filePath
					+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedSubmissionId()
					+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedType()
					+ "\t" + submitFeedResponse.getSubmitFeedResult().getFeedSubmissionInfo().getFeedProcessingStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
