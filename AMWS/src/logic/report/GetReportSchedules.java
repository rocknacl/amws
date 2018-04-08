package logic.report;

import com.amazonaws.mws.MarketplaceWebService;

import dao.entities.MerchantAccount;

public class GetReportSchedules {
	MerchantAccount merchant;
	MarketplaceWebService service;
	public GetReportSchedules(MerchantAccount merchant, MarketplaceWebService service) {
		super();
		this.merchant = merchant;
		this.service = service;
	}
	
	public void run(){
		
	}

}
