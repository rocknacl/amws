package amzint.report;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.ManageReportScheduleRequest;
import com.amazonaws.mws.model.ManageReportScheduleResponse;
import com.amazonaws.mws.model.ManageReportScheduleResult;
import com.amazonaws.mws.model.ReportSchedule;
import com.amazonaws.mws.model.ResponseMetadata;

import amzint.AbstractInvokeReportService;
import amzint.MarketplaceWebServiceFactory;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import model.report.ReportTypeEnum;
import model.report.Schedule;

public class APIManageReportSchedule extends AbstractInvokeReportService {

	public APIManageReportSchedule(MarketplaceWebService service, MerchantAccount merchant) {
		super(service, merchant);
		// TODO Auto-generated constructor stub
	}

	public ManageReportScheduleResponse ManageReportSchedule(ManageReportScheduleRequest request) {

		ManageReportScheduleResponse response = null;
		try {
			response = service.manageReportSchedule(request);
		} catch (MarketplaceWebServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public ManageReportScheduleRequest createManageReportScheduleRequest(String reportType, String schedule,
			XMLGregorianCalendar scheduleDate) {
		ManageReportScheduleRequest request = new ManageReportScheduleRequest();
		request.setMerchant(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());

		// @TODO: set request parameters here
		request.setReportType(reportType);
		request.setSchedule(schedule);
		if (scheduleDate != null)
			request.setScheduleDate(scheduleDate);
		return request;
	}

	public void invokeManageReportSchedule(List<ManageReportScheduleRequest> requests) {
		List<Future<ManageReportScheduleResponse>> responses = new ArrayList<Future<ManageReportScheduleResponse>>();
		for (ManageReportScheduleRequest request : requests) {
			responses.add(service.manageReportScheduleAsync(request));
		}
		for (Future<ManageReportScheduleResponse> future : responses) {
			while (!future.isDone()) {
				Thread.yield();
			}
			try {
				ManageReportScheduleResponse response = future.get();
				// Original request corresponding to this response, if needed:
				ManageReportScheduleRequest originalRequest = requests.get(responses.indexOf(future));
				System.out.println("Response request id: " + response.getResponseMetadata().getRequestId());
				System.out.println(response.getResponseHeaderMetadata());
				System.out.println();
			} catch (Exception e) {
				if (e.getCause() instanceof MarketplaceWebServiceException) {
					MarketplaceWebServiceException exception = MarketplaceWebServiceException.class.cast(e.getCause());
					System.out.println("Caught Exception: " + exception.getMessage());
					System.out.println("Response Status Code: " + exception.getStatusCode());
					System.out.println("Error Code: " + exception.getErrorCode());
					System.out.println("Error Type: " + exception.getErrorType());
					System.out.println("Request ID: " + exception.getRequestId());
					System.out.print("XML: " + exception.getXML());
					System.out.println("ResponseHeaderMetadata: " + exception.getResponseHeaderMetadata());
				} else {
					e.printStackTrace();
				}
			}
		}
	}

	public static void printManageReportScheduleResponse(ManageReportScheduleResponse response) {
		System.out.println("ManageReportSchedule Action Response");
		System.out.println("=============================================================================");
		System.out.println();

		System.out.print("    ManageReportScheduleResponse");
		System.out.println();
		if (response.isSetManageReportScheduleResult()) {
			System.out.print("        ManageReportScheduleResult");
			System.out.println();
			ManageReportScheduleResult manageReportScheduleResult = response.getManageReportScheduleResult();
			if (manageReportScheduleResult.isSetCount()) {
				System.out.print("            Count");
				System.out.println();
				System.out.print("                " + manageReportScheduleResult.getCount());
				System.out.println();
			}
			java.util.List<ReportSchedule> reportScheduleList = manageReportScheduleResult.getReportScheduleList();
			for (ReportSchedule reportSchedule : reportScheduleList) {
				System.out.print("            ReportSchedule");
				System.out.println();
				if (reportSchedule.isSetReportType()) {
					System.out.print("                ReportType");
					System.out.println();
					System.out.print("                    " + reportSchedule.getReportType());
					System.out.println();
				}
				if (reportSchedule.isSetSchedule()) {
					System.out.print("                Schedule");
					System.out.println();
					System.out.print("                    " + reportSchedule.getSchedule());
					System.out.println();
				}
				if (reportSchedule.isSetScheduledDate()) {
					System.out.print("                ScheduledDate");
					System.out.println();
					System.out.print("                    " + reportSchedule.getScheduledDate());
					System.out.println();
				}
			}
		}
		if (response.isSetResponseMetadata()) {
			System.out.print("        ResponseMetadata");
			System.out.println();
			ResponseMetadata responseMetadata = response.getResponseMetadata();
			if (responseMetadata.isSetRequestId()) {
				System.out.print("            RequestId");
				System.out.println();
				System.out.print("                " + responseMetadata.getRequestId());
				System.out.println();
			}
		}
		System.out.println();
		System.out.println(response.getResponseHeaderMetadata());
		System.out.println();
	}

}
