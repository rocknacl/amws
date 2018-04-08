package Test.Bens;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportListByNextTokenRequest;
import com.amazonaws.mws.model.GetReportListByNextTokenResponse;
import com.amazonaws.mws.model.GetReportListRequest;
import com.amazonaws.mws.model.GetReportListResponse;
import com.amazonaws.mws.model.ReportInfo;

import amzint.MarketplaceWebServiceFactory;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;

public class GetReportListToFile20160616 {

    public static void main(String... args) throws MarketplaceWebServiceException {
    	String filename = "2016061601.txt";
    	
    	MerchantAccount merchant = MerchantAccountDAO.getMerchantByName("UP");
    	MarketplaceWebService service = MarketplaceWebServiceFactory.getMarketplaceWebService(merchant.getAccessKey(), merchant.getSecretKey(), null);

        GetReportListRequest request = new GetReportListRequest();
        request.setMerchant( merchant.getId() );
        request.setMWSAuthToken(merchant.getAuthToken());
        request.setMaxCount(100);
        GetReportListResponse response = service.getReportList(request);
        if(response.isSetGetReportListResult()){
        for(ReportInfo reportInfo:response.getGetReportListResult().getReportInfoList()){
        	ArrayList<String[]> data = new ArrayList<String[]>();
        	String[] line = new String[5];
        	line[0] = reportInfo.getReportId();
        	line[1] = reportInfo.getReportRequestId();
        	line[2] = reportInfo.getReportType();
        	line[3] = ""+reportInfo.getAcknowledgedDate();
        	line[4] = ""+reportInfo.getAvailableDate();
        	data.add(line);
        	writetxt(filename,data);
        }
        boolean isSetNextToken = response.getGetReportListResult().isSetNextToken();
        String nextToken = response.getGetReportListResult().getNextToken();
        while(isSetNextToken){
        	GetReportListByNextTokenRequest requestt = new GetReportListByNextTokenRequest();
        	requestt.setMerchant(merchant.getId());
        	requestt.setMWSAuthToken(merchant.getAuthToken());
        	requestt.setNextToken(nextToken);
        	GetReportListByNextTokenResponse responsee = service.getReportListByNextToken(requestt);
        	if(responsee.isSetGetReportListByNextTokenResult()){
                for(ReportInfo reportInfo:responsee.getGetReportListByNextTokenResult().getReportInfoList()){
                	ArrayList<String[]> data = new ArrayList<String[]>();
                	String[] line = new String[5];
                	line[0] = reportInfo.getReportId();
                	line[1] = reportInfo.getReportRequestId();
                	line[2] = reportInfo.getReportType();
                	line[3] = ""+reportInfo.getAcknowledgedDate();
                	line[4] = ""+reportInfo.getAvailableDate();
                	data.add(line);
                	writetxt(filename,data);
                }
            isSetNextToken = responsee.getGetReportListByNextTokenResult().isSetNextToken();
            nextToken = responsee.getGetReportListByNextTokenResult().getNextToken();
        	}
        }
        }
        System.out.println("Done");
    }


	public static void writetxt(String filename, ArrayList<String[]> data){
		try{
			File f = new File(filename);
			if (f.getParentFile() != null && !f.getParentFile().exists())
				f.getParentFile().mkdirs();
			if (!f.exists())	f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f,true);
			FileChannel fc = fos.getChannel();
			FileLock lock = fc.lock();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			if(data!=null&&!data.isEmpty()){
			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < data.get(i).length; j++)
					writer.append(data.get(i)[j] + "\t");
				writer.append("\r\n");
				writer.flush();
			}}
			lock.release();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
