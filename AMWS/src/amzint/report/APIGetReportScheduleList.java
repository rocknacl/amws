package amzint.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportScheduleListByNextTokenRequest;
import com.amazonaws.mws.model.GetReportScheduleListByNextTokenResponse;
import com.amazonaws.mws.model.GetReportScheduleListByNextTokenResult;
import com.amazonaws.mws.model.GetReportScheduleListRequest;
import com.amazonaws.mws.model.GetReportScheduleListResponse;
import com.amazonaws.mws.model.GetReportScheduleListResult;
import com.amazonaws.mws.model.ReportSchedule;
import com.amazonaws.mws.model.ResponseMetadata;

import amzint.AbstractInvokeReportService;
import amzint.MarketplaceWebServiceFactory;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import helper.MyDateConverter;
import helper.dao.ConnectionPool;

public class APIGetReportScheduleList extends AbstractInvokeReportService {

	public APIGetReportScheduleList(MarketplaceWebService service, MerchantAccount merchant) {
		super(service, merchant);
		// TODO Auto-generated constructor stub
	}

	public List<ReportSchedule> getCompleteReportScheduleList() {
		List<ReportSchedule> result = new ArrayList<ReportSchedule>();
		GetReportScheduleListResponse response = this.GetReportScheduleList();
		String nextToken = response.getGetReportScheduleListResult().getNextToken();
		result.addAll(response.getGetReportScheduleListResult().getReportScheduleList());
		while (nextToken != null) {
			GetReportScheduleListByNextTokenResponse nextResponse = this.GetNextReportScheduleList(nextToken);
			nextToken = nextResponse.getGetReportScheduleListByNextTokenResult().getNextToken();
			result.addAll(nextResponse.getGetReportScheduleListByNextTokenResult().getReportScheduleList());
		}
		return result;
	}

	public GetReportScheduleListResponse GetReportScheduleList() {
		GetReportScheduleListRequest request = new GetReportScheduleListRequest();
		request.setMerchant(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());

		// @TODO: set request parameters here
		GetReportScheduleListResponse response = null;
		try {
			response = service.getReportScheduleList(request);
		} catch (MarketplaceWebServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public GetReportScheduleListByNextTokenResponse GetNextReportScheduleList(String nextToken) {

		GetReportScheduleListByNextTokenRequest request = new GetReportScheduleListByNextTokenRequest();
		request.setMerchant(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setNextToken(nextToken);
		// @TODO: set request parameters here

		GetReportScheduleListByNextTokenResponse response = null;
		try {
			response = service.getReportScheduleListByNextToken(request);
		} catch (MarketplaceWebServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public static void printGetReportScheduleListResponse(GetReportScheduleListResponse response) {
	
		System.out.println("GetReportScheduleList Action Response");
		System.out.println("=============================================================================");
		System.out.println();
	
		System.out.print("    GetReportScheduleListResponse");
		System.out.println();
		if (response.isSetGetReportScheduleListResult()) {
			System.out.print("        GetReportScheduleListResult");
			System.out.println();
			GetReportScheduleListResult getReportScheduleListResult = response.getGetReportScheduleListResult();
			if (getReportScheduleListResult.isSetNextToken()) {
				System.out.print("            NextToken");
				System.out.println();
				System.out.print("                " + getReportScheduleListResult.getNextToken());
				System.out.println();
			}
			if (getReportScheduleListResult.isSetHasNext()) {
				System.out.print("            HasNext");
				System.out.println();
				System.out.print("                " + getReportScheduleListResult.isHasNext());
				System.out.println();
			}
			java.util.List<ReportSchedule> reportScheduleList = getReportScheduleListResult.getReportScheduleList();
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

	public static void printGetReportScheduleListByNextTokenResponse(
			GetReportScheduleListByNextTokenResponse response) {
		System.out.println("GetReportScheduleListByNextToken Action Response");
		System.out.println("=============================================================================");
		System.out.println();

		System.out.print("    GetReportScheduleListByNextTokenResponse");
		System.out.println();
		if (response.isSetGetReportScheduleListByNextTokenResult()) {
			System.out.print("        GetReportScheduleListByNextTokenResult");
			System.out.println();
			GetReportScheduleListByNextTokenResult getReportScheduleListByNextTokenResult = response
					.getGetReportScheduleListByNextTokenResult();
			if (getReportScheduleListByNextTokenResult.isSetNextToken()) {
				System.out.print("            NextToken");
				System.out.println();
				System.out.print("                " + getReportScheduleListByNextTokenResult.getNextToken());
				System.out.println();
			}
			if (getReportScheduleListByNextTokenResult.isSetHasNext()) {
				System.out.print("            HasNext");
				System.out.println();
				System.out.print("                " + getReportScheduleListByNextTokenResult.isHasNext());
				System.out.println();
			}
			java.util.List<ReportSchedule> reportScheduleList = getReportScheduleListByNextTokenResult
					.getReportScheduleList();
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
