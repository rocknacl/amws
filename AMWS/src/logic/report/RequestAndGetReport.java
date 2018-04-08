package logic.report;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportListResponse;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.ReportInfo;
import com.amazonaws.mws.model.RequestReportRequest;
import com.amazonaws.mws.model.RequestReportResponse;
import com.amazonaws.mws.model.RequestReportResult;

import amzint.MarketplaceWebServiceFactory;
import amzint.report.*;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import helper.IOHelper;
import helper.XMLGregorianCalendarHelper;
import model.report.ReportTypeEnum;
/**
 * A class who can completely download a single report. 
 * The default values are : outputPath = tmp; max_retry_times = 12, waitInterval = 5 mins and these parameters CAN be set.
 * @author Stone
 *
 */
public class RequestAndGetReport {
	protected MarketplaceWebService service;
	protected MerchantAccount merchant;
	ReportTypeEnum reportType;
	String[] reportOptions;
	XMLGregorianCalendar fromDate;
	XMLGregorianCalendar toDate;
	String requestID;
	String outputPath = "tmp";
	int maxRetry = 12;
	int waitInterval = 1000*60*5;
	public int getMaxRetry() {
		return maxRetry;
	}

	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	public int getWaitInterval() {
		return waitInterval;
	}

	public void setWaitInterval(int waitPeriod) {
		this.waitInterval = waitPeriod;
	}
	
	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public void run() throws MarketplaceWebServiceException, FileNotFoundException, InterruptedException {
		APIRequestReport request = new APIRequestReport(service, merchant);
		APIGetReportList getList = new APIGetReportList(service, merchant);
		RequestReportResponse response = request
				.requestReport(request.createRequestReportRequest(reportType.value, reportOptions, fromDate, toDate));
		APIRequestReport.printRequestReportResponse(response);
		if (response.isSetRequestReportResult()) {
			RequestReportResult result = response.getRequestReportResult();
			if (result.isSetReportRequestInfo()) {
				requestID = result.getReportRequestInfo().getReportRequestId();
				ArrayList<String> ids = new ArrayList<String>();
				ids.add(requestID);
				IdList idList = new IdList();
				idList.setId(ids);
				String reportID = null;
				int count = maxRetry;
				while(reportID==null&&count>0){
					Thread.sleep(waitInterval);
					GetReportListResponse reportList = getList.getReportList(getList.createGetReportListRequest(null, idList, null,null));
					APIGetReportList.printGetReportListResponse(reportList);
					if(reportList.isSetGetReportListResult()){
						List<ReportInfo> reportInfoList = reportList.getGetReportListResult().getReportInfoList();
						for(ReportInfo info : reportInfoList){
							System.out.println(info.getAcknowledgedDate());
							System.out.println(info.getAvailableDate());
							if(info.getReportRequestId().equals(requestID)){
								if(info.isSetReportId()){
									reportID = info.getReportId();
								}
							}
						}
					}
					count--;
				}
				if(reportID!=null){
					APIGetReport get = new APIGetReport(service, merchant);
					get.getReport(get.createGetReportRequest(reportID, outputPath));
					System.out.println("DONE");
				}
			}
		}

	}

	public RequestAndGetReport(MarketplaceWebService service, MerchantAccount merchant, ReportTypeEnum reportType,
			String[] reportOptions, XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate) {
		super();
		this.service = service;
		this.merchant = merchant;
		this.reportType = reportType;
		this.reportOptions = reportOptions;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
}
