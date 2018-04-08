package amzint;


import com.amazon.mws.recommendations.MWSRecommendationsSectionServiceAsync;

import dao.entities.MerchantAccount;

/**
 * @author Bens
 * @version 20160612
 */
public abstract class AbstractMWSRecommendationsSectionAsyncService {
	protected MWSRecommendationsSectionServiceAsync service;
	protected MerchantAccount merchant;

	public AbstractMWSRecommendationsSectionAsyncService(MerchantAccount merchant) {
		super();
		this.service = MarketplaceWebServiceFactory.getMWSRecommendationsSectionService(merchant.getAccessKey(), merchant.getSecretKey(), null);
		this.merchant = merchant;
	}

	public AbstractMWSRecommendationsSectionAsyncService(MerchantAccount merchant, String serviceUrl) {
		super();
		this.service = MarketplaceWebServiceFactory.getMWSRecommendationsSectionService(merchant.getAccessKey(), merchant.getSecretKey(), serviceUrl);
		this.merchant = merchant;
	}

	public MerchantAccount getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantAccount merchant) {
		this.merchant = merchant;
	}

}
