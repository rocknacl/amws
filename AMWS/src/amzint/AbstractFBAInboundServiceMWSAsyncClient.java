package amzint;

import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.FBAInboundServiceMWSAsyncClient;

import dao.entities.MerchantAccount;

public class AbstractFBAInboundServiceMWSAsyncClient {

	protected FBAInboundServiceMWSAsyncClient client;
	protected MerchantAccount merchant;
	
	public AbstractFBAInboundServiceMWSAsyncClient(MerchantAccount merchant){
		this.merchant = merchant;
		client = MarketplaceWebServiceFactory.getFBAInboundServiceMWSAsyncClient(
				merchant.getAccessKey(),
				merchant.getSecretKey(),
				null);
	}
	
}
