package amzint;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsync;
import dao.entities.MerchantAccount;

/**
 * for amazon market place web service product part
 * @author Bens
 * @version 2016021
 */
public abstract class AbstractMarketplaceWebServiceProductsAsyncService {
	protected MarketplaceWebServiceProductsAsync service;
	protected MerchantAccount merchant;

	public AbstractMarketplaceWebServiceProductsAsyncService(MarketplaceWebServiceProductsAsync service, MerchantAccount merchant) {
		super();
		this.service = service;
		this.merchant = merchant;
	}

	public AbstractMarketplaceWebServiceProductsAsyncService(MerchantAccount merchant) {
		super();
		this.service = MarketplaceWebServiceFactory.getMarketplaceWebServiceProductsAsync(merchant.getAccessKey(), merchant.getSecretKey(), null);
		this.merchant = merchant;
	}

	public MerchantAccount getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantAccount merchant) {
		this.merchant = merchant;
	}

}
