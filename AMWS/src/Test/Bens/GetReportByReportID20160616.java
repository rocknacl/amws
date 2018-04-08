package Test.Bens;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.amazonaws.mws.MarketplaceWebService;

import amzint.MarketplaceWebServiceFactory;
import amzint.report.APIGetReport;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;

public class GetReportByReportID20160616 {
	public static void main(String[] args) throws Exception{
		MerchantAccount merchant = MerchantAccountDAO.getMerchantByName("UP");
		MarketplaceWebService service = MarketplaceWebServiceFactory.getMarketplaceWebService(merchant.getAccessKey(), merchant.getSecretKey(), null);
		BufferedReader br = new BufferedReader(new FileReader(new File("2016061602.txt")));
		String line;
		while((line=br.readLine())!=null){
			String[] se = line.split("\t");
			APIGetReport getter = new APIGetReport(service,merchant);
			getter.getReport(se[0],se[0]+"_"+se[2]+".txt");
			System.out.println("Finish report: "+se[0]+"_"+se[2]+".txt");
		}
		br.close();
		System.out.println("DONE");
	}
}
