package amzint.report;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenRequest;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenResponse;
import com.amazonaws.mws.model.GetReportRequestListRequest;
import com.amazonaws.mws.model.GetReportRequestListResponse;
import com.amazonaws.mws.model.GetReportRequestListResult;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.ReportRequestInfo;
import com.amazonaws.mws.model.ResponseMetadata;
import com.amazonaws.mws.model.StatusList;
import com.amazonaws.mws.model.TypeList;

import amzint.AbstractInvokeReportService;
import amzint.MarketplaceWebServiceFactory;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;

public class APIGetReportRequestList extends AbstractInvokeReportService {

	public APIGetReportRequestList(MarketplaceWebService service, MerchantAccount merchant) {
		super(service, merchant);

	}

	public GetReportRequestListRequest createGetReportRequestListRequest(TypeList typelist, IdList idList,
			XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate) {
		GetReportRequestListRequest request = new GetReportRequestListRequest();
		if (typelist != null) {
			request.setReportTypeList(typelist);
		}
		request.setMerchant(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		if (fromDate != null) {
			request.setRequestedFromDate(fromDate);
		}
		if (toDate != null) {
			request.setRequestedToDate(toDate);
		}
		if (idList != null) {
			request.setReportRequestIdList(idList);
		}
		return request;
	}

	public GetReportRequestListResponse getReportRequestList(GetReportRequestListRequest request) throws MarketplaceWebServiceException {
		return service.getReportRequestList(request);
	}

	public GetReportRequestListByNextTokenRequest createGetReportRequestListByNextTokenRequest(String nextToken) {
		GetReportRequestListByNextTokenRequest nextRequest = new GetReportRequestListByNextTokenRequest();
		nextRequest.setMerchant(merchant.getId());
		nextRequest.setMWSAuthToken(merchant.getAuthToken());
		nextRequest.setNextToken(nextToken);
		return nextRequest;
	}

	public GetReportRequestListByNextTokenResponse getReportRequestListByNextToken(GetReportRequestListByNextTokenRequest request)
			throws MarketplaceWebServiceException {
		return service.getReportRequestListByNextToken(request);

	}
	public static void printGetReportRequestListResponse(GetReportRequestListResponse response) {
	        



	            System.out.println ("GetReportRequestList Action Response");
	            System.out.println ("=============================================================================");
	            System.out.println ();

	            System.out.print("    GetReportRequestListResponse");
	            System.out.println();
	            if (response.isSetGetReportRequestListResult()) {
	                System.out.print("        GetReportRequestListResult");
	                System.out.println();
	                GetReportRequestListResult  getReportRequestListResult = response.getGetReportRequestListResult();
	                if (getReportRequestListResult.isSetNextToken()) {
	                    System.out.print("            NextToken");
	                    System.out.println();
	                    System.out.print("                " + getReportRequestListResult.getNextToken());
	                    System.out.println();
	                }
	                if (getReportRequestListResult.isSetHasNext()) {
	                    System.out.print("            HasNext");
	                    System.out.println();
	                    System.out.print("                " + getReportRequestListResult.isHasNext());
	                    System.out.println();
	                }
	                java.util.List<ReportRequestInfo> reportRequestInfoList = getReportRequestListResult.getReportRequestInfoList();
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
	                    if (reportRequestInfo.isSetCompletedDate()) {
	                        System.out.print("                CompletedDate");
	                        System.out.println();
	                        System.out.print("                    " + reportRequestInfo.getCompletedDate());
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
	                ResponseMetadata  responseMetadata = response.getResponseMetadata();
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