package control.report;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.StatusList;

import amzint.MarketplaceWebServiceFactory;
import amzint.report.APICancelReportRequest;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import logic.report.APIReportDownloader;
import logic.report.ScheduledReportQuery;

public class ReportTaskGenerateTimerTask extends TimerTask{

	@Override
	public void run() {
		try {
			List<MerchantAccount> merchantList = MerchantAccountDAO.getAllMerchantAccount();
	//		List<MerchantAccount> merchantList = MerchantAccountDAO.getTestMerchantAccount();
			for(MerchantAccount merchant : merchantList){
				MarketplaceWebService service = MarketplaceWebServiceFactory.getMarketplaceWebService(merchant.getAccessKey(),
						merchant.getSecretKey(), null);
	//			APICancelReportRequest a = new APICancelReportRequest(service, merchant);
	//			StatusList status = new StatusList();
	//			List<String> l = new ArrayList<String>();
	//			l.add("_IN_PROGRESS_");
	//			l.add("_SUBMITTED_");
	//			status.setStatus(l);
	//			try {
	//				APICancelReportRequest.printCancelReportRequestsResponse(a.cancelReportRequest(a.createRequest(null, status)));
	//			} catch (MarketplaceWebServiceException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
				APIReportDownloader a = new APIReportDownloader(merchant, service);
				ScheduledReportQuery s = new ScheduledReportQuery(service,merchant);
				s.run();
				s.generateAPIGetReportTask();
				a.generateDailyTask();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("D16.log",true)));
				String className = e.getClass().getName();
				String message = e.getMessage();
				StackTraceElement[] trace = e.getStackTrace();
				pw.println(new Date()  + "\t\t\t\t" + "class: " + className + ", tmessage: " + message);
				for (int i=0; i<trace.length; i++){
					pw.println(trace[i].toString());
				}
				pw.println();
				pw.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
