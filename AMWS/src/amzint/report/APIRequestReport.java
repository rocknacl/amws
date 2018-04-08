package amzint.report;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.ReportRequestInfo;
import com.amazonaws.mws.model.RequestReportRequest;
import com.amazonaws.mws.model.RequestReportResponse;
import com.amazonaws.mws.model.RequestReportResult;
import com.amazonaws.mws.model.ResponseMetadata;

import amzint.AbstractInvokeReportService;
import dao.entities.MerchantAccount;
import model.AbstractInvokeAPIServiceResult;

public class APIRequestReport extends AbstractInvokeReportService {

	public APIRequestReport(MarketplaceWebService service, MerchantAccount merchant) {
		super(service, merchant);

	}

	public RequestReportRequest createRequestReportRequest(String reportType, String[] reportOptions,
			XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate) {
		final IdList marketplaces = new IdList(Arrays.asList(merchant.getMarketPlaceId()));
		RequestReportRequest request = new RequestReportRequest().withMerchant(merchant.getId())
				.withMarketplaceIdList(marketplaces).withReportType(reportType);
		if (reportOptions != null)
			for (String option : reportOptions) {
				request = request.withReportOptions(option);
			}
		request = request.withMWSAuthToken(merchant.getAuthToken());
		if (fromDate != null)
			request.setStartDate(fromDate);
		if (toDate != null)
			request.setEndDate(toDate);
		return request;
	}

	public RequestReportResponse requestReport(RequestReportRequest request) throws MarketplaceWebServiceException {
		RequestReportResponse response = service.requestReport(request);
		return response;
	}

	public RequestReportResponse[] requestReportAsync(List<RequestReportRequest> requests) {
		List<Future<RequestReportResponse>> responses = new ArrayList<Future<RequestReportResponse>>();
		RequestReportResponse[] rrrs = new RequestReportResponse[requests.size()];		
		for (RequestReportRequest request : requests) {
			responses.add(service.requestReportAsync(request));
		}
		for (Future<RequestReportResponse> future : responses) {
			while (!future.isDone()) {
				Thread.yield();
			}
			try {
				RequestReportResponse response = future.get();
				// Original request corresponding to this response, if needed:
				RequestReportRequest originalRequest = requests.get(responses.indexOf(future));
				rrrs[responses.indexOf(future)] = response;

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
		return rrrs;

	}

	public static void printRequestReportResponse(RequestReportResponse response) {

		System.out.println("RequestReport Action Response");
		System.out.println("=============================================================================");
		System.out.println();

		System.out.print("    RequestReportResponse");
		System.out.println();
		if (response.isSetRequestReportResult()) {
			System.out.print("        RequestReportResult");
			System.out.println();
			RequestReportResult requestReportResult = response.getRequestReportResult();
			if (requestReportResult.isSetReportRequestInfo()) {
				System.out.print("            ReportRequestInfo");
				System.out.println();
				ReportRequestInfo reportRequestInfo = requestReportResult.getReportRequestInfo();
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

	private void printMarketplaceWebServiceException(MarketplaceWebServiceException ex) {
		System.out.println("Caught Exception: " + ex.getMessage());
		System.out.println("Response Status Code: " + ex.getStatusCode());
		System.out.println("Error Code: " + ex.getErrorCode());
		System.out.println("Error Type: " + ex.getErrorType());
		System.out.println("Request ID: " + ex.getRequestId());
		System.out.print("XML: " + ex.getXML());
		System.out.println("ResponseHeaderMetadata: " + ex.getResponseHeaderMetadata());
	}

}
