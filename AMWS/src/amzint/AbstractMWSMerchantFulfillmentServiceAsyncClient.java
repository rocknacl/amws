package amzint;

import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceAsyncClient;

import dao.entities.MerchantAccount;

public class AbstractMWSMerchantFulfillmentServiceAsyncClient {

	protected MerchantAccount merchant;
	protected MWSMerchantFulfillmentServiceAsyncClient client;
	
	public AbstractMWSMerchantFulfillmentServiceAsyncClient(MerchantAccount merchant){
		this.merchant = merchant;
		client = MarketplaceWebServiceFactory.getMWSMerchantFulfillmentServiceAsyncClient(
				merchant.getAccessKey(),
				merchant.getSecretKey(),
				null);
	}
	
}
