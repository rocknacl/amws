package control.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.amazonaws.mws.MarketplaceWebService;

import amzint.MarketplaceWebServiceFactory;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import logic.report.APIReportDownloader;

public class ReportTaskProcessTimerTask extends TimerTask{

	@Override
	public void run() {
		List<Thread> threads = new ArrayList<Thread>();
		List<MerchantAccount> merchantList = MerchantAccountDAO.getAllMerchantAccount();
//		List<MerchantAccount> merchantList = MerchantAccountDAO.getTestMerchantAccount();
		for(MerchantAccount merchant : merchantList){
			
			MarketplaceWebService service = MarketplaceWebServiceFactory.getMarketplaceWebService(merchant.getAccessKey(),
					merchant.getSecretKey(), null);
			APIReportDownloader a = new APIReportDownloader(merchant, service);
			Thread t = new Thread(a);
			t.setName("thread-APIReportDownloader-" + merchant.getName());
			threads.add(t);
		}
		for(Thread d : threads){
			d.start();
		}
		for (int i=0;i<60;i++){
			try {
				boolean isDone = true;
				for(Thread d : threads){
					System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())
							+ "\t" + i + "/60(30s)"
							+ "\t" + d.getId()
							+ "\t" + d.getName()
							+ "\t" + d.getState()
							+ "\t" + d.isAlive());
					if(d.isAlive()) {
						isDone = false;
					}
				}
				if (isDone) break;
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
