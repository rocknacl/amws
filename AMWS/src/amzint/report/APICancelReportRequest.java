package amzint.report;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.CancelReportRequestsRequest;
import com.amazonaws.mws.model.CancelReportRequestsResponse;
import com.amazonaws.mws.model.CancelReportRequestsResult;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.ReportRequestInfo;
import com.amazonaws.mws.model.ResponseMetadata;
import com.amazonaws.mws.model.StatusList;

import amzint.AbstractInvokeReportService;
import amzint.MarketplaceWebServiceFactory;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;

public class APICancelReportRequest extends AbstractInvokeReportService {

	public APICancelReportRequest(MarketplaceWebService service, MerchantAccount merchant) {
		super(service, merchant);
		// TODO Auto-generated constructor stub
	}

	public CancelReportRequestsRequest createRequest(IdList ids,StatusList status) {
		CancelReportRequestsRequest request = new CancelReportRequestsRequest();
		request.setMerchant(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		request.setReportRequestIdList(ids);
		request.setReportProcessingStatusList(status);
		return request;
		// @TODO: set request parameters here

		// invokeCancelReportRequests(service, request);
	}

	public CancelReportRequestsResponse cancelReportRequest(CancelReportRequestsRequest request)
			throws MarketplaceWebServiceException {
		CancelReportRequestsResponse response = service.cancelReportRequests(request);
		return response;
	}

	public static void printCancelReportRequestsResponse(CancelReportRequestsResponse response) {

		System.out.println("CancelReportRequests Action Response");
		System.out.println("=============================================================================");
		System.out.println();

		System.out.print("    CancelReportRequestsResponse");
		System.out.println();
		if (response.isSetCancelReportRequestsResult()) {
			System.out.print("        CancelReportRequestsResult");
			System.out.println();
			CancelReportRequestsResult cancelReportRequestsResult = response.getCancelReportRequestsResult();
			if (cancelReportRequestsResult.isSetCount()) {
				System.out.print("            Count");
				System.out.println();
				System.out.print("                " + cancelReportRequestsResult.getCount());
				System.out.println();
			}
			java.util.List<ReportRequestInfo> reportRequestInfoList = cancelReportRequestsResult
					.getReportRequestInfoList();
			for (ReportRequestInfo reportRequestInfo : reportRequestInfoList) {
				System.out.print("            ReportRequestInfo");
				System.out.println();
				if (reportRequestInfo.isSetReportRequestId()) {
					System.out.print("                ReportRequestId");
					System.out.println();
					System.out.print("                    " + reportRequestInfo.getReportRequestId());
					System.out.println();
				}
				if (reportRequestInfo.isSetReportType()) {
					System.out.print("                ReportType");
					System.out.println();
					System.out.print("                    " + reportRequestInfo.getReportType());
					System.out.println();
				}
				if (reportRequestInfo.isSetStartDate()) {
					System.out.print("                StartDate");
					System.out.println();
					System.out.print("                    " + reportRequestInfo.getStartDate());
					System.out.println();
				}
				if (reportRequestInfo.isSetEndDate()) {
					System.out.print("                EndDate");
					System.out.println();
					System.out.print("                    " + reportRequestInfo.getEndDate());
					System.out.println();
				}
				if (reportRequestInfo.isSetSubmittedDate()) {
					System.out.print("                SubmittedDate");
					System.out.println();
					System.out.print("                    " + reportRequestInfo.getSubmittedDate());
					System.out.println();
				}
				if (reportRequestInfo.isSetReportProcessingStatus()) {
					System.out.print("                ReportProcessingStatus");
					System.out.println();
					System.out.print("                    " + reportRequestInfo.getReportProcessingStatus());
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
