package amzint;
import com.amazonaws.mws.MarketplaceWebService;
import dao.entities.MerchantAccount;


public abstract class AbstractInvokeReportService {
	protected MarketplaceWebService service;
	protected MerchantAccount merchant;


	public AbstractInvokeReportService(MarketplaceWebService service, MerchantAccount merchant) {
		this.service = service;
		this.merchant = merchant;

	}
	

}
