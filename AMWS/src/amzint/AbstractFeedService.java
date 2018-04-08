package amzint;

import com.amazonaws.mws.MarketplaceWebService;

import dao.entities.MerchantAccount;

public class AbstractFeedService {

	protected MarketplaceWebService service;
	protected MerchantAccount merchant;

	public AbstractFeedService(MerchantAccount merchant) {
		this.merchant = merchant;
		service = MarketplaceWebServiceFactory.getMarketplaceWebService(
					merchant.getAccessKey(),
					merchant.getSecretKey(),
					null);
	}
	
}
