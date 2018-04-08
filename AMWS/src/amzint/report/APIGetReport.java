package amzint.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportRequest;
import com.amazonaws.mws.model.GetReportResponse;

import amzint.AbstractInvokeReportService;
import dao.entities.MerchantAccount;

public class APIGetReport extends AbstractInvokeReportService {

	public APIGetReport(MarketplaceWebService service, MerchantAccount merchant) {
		super(service, merchant);

	}

	public GetReportResponse getReport(String reportID, String outputFilePath)
			throws FileNotFoundException, MarketplaceWebServiceException {
		GetReportRequest request = this.createGetReportRequest(reportID, outputFilePath);
		GetReportResponse response = service.getReport(request);
		return response;
	}
	
	public GetReportResponse getReport(GetReportRequest request)
			throws FileNotFoundException, MarketplaceWebServiceException {
		GetReportResponse response = service.getReport(request);
		return response;
	}

	public GetReportRequest createGetReportRequest(String reportID, String outputFilePath)
			throws FileNotFoundException {
		GetReportRequest request = new GetReportRequest();
		request.setMerchant(this.merchant.getId());
		request.setMWSAuthToken(this.merchant.getAuthToken());
		request.setReportId(reportID);
		request.setReportOutputStream(new FileOutputStream(new File(outputFilePath)));
		return request;
	}

	public GetReportResponse[] getReportsAsync(List<GetReportRequest> requests) {
		List<Future<GetReportResponse>> responses = new ArrayList<Future<GetReportResponse>>();
		GetReportResponse[] result = new GetReportResponse[requests.size()];
		for (GetReportRequest request : requests) {
			responses.add(service.getReportAsync(request));
		}
		for (Future<GetReportResponse> future : responses) {
			while (!future.isDone()) {
				Thread.yield();
			}
			try {
				GetReportResponse response = future.get();
				// Original request corresponding to this response, if needed:
//				GetReportRequest originalRequest = requests.get(responses.indexOf(future));
				result[responses.indexOf(future)] = response;
				
				System.out.println("Result md5checksum : " + response.getGetReportResult().getMD5Checksum());
				System.out.println("Response request id: " + response.getResponseMetadata().getRequestId());
				System.out.println("Report: ");
				System.out.println(requests.get(responses.indexOf(future)).getReportOutputStream().toString());
				System.out.println(response.getResponseHeaderMetadata());
				System.out.println();
			} catch (Exception e) {
				result[responses.indexOf(future)] = null;
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
		return result;

	}

	

}
