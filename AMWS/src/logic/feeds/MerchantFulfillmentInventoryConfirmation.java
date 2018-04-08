package logic.feeds;

import logic.feeds.client.MerchantFulfillmentOrderConfirmationClient;

public class MerchantFulfillmentInventoryConfirmation implements Runnable{

	private String feedFileDir;
	private String feedFileHistoryDir;
	
	public MerchantFulfillmentInventoryConfirmation(
			String feedFileDir,
			String feedFileHistoryDir){
		this.feedFileDir = feedFileDir;
		this.feedFileHistoryDir = feedFileHistoryDir;
	}

	public void run() {
		try {
			new MerchantFulfillmentOrderConfirmationClient().merchantFulfillmentOrderConfirmation(feedFileDir, feedFileHistoryDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
