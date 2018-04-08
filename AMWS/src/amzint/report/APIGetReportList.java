package amzint.report;

import java.util.ArrayList;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportListByNextTokenRequest;
import com.amazonaws.mws.model.GetReportListByNextTokenResponse;
import com.amazonaws.mws.model.GetReportListByNextTokenResult;
import com.amazonaws.mws.model.GetReportListRequest;
import com.amazonaws.mws.model.GetReportListResponse;
import com.amazonaws.mws.model.GetReportListResult;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenRequest;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.ReportInfo;
import com.amazonaws.mws.model.ResponseMetadata;
import com.amazonaws.mws.model.TypeList;

import amzint.AbstractInvokeReportService;
import amzint.MarketplaceWebServiceFactory;
import antlr.collections.List;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;

public class APIGetReportList extends AbstractInvokeReportService {

	public APIGetReportList(MarketplaceWebService service, MerchantAccount merchant) {
		super(service, merchant);

	}

	public GetReportListRequest createGetReportListRequest(TypeList typelist, IdList idList,
			XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate) {
		GetReportListRequest request = new GetReportListRequest();
		if (typelist != null) {
			request.setReportTypeList(typelist);
		}
		request.setMerchant(merchant.getId());
		request.setMWSAuthToken(merchant.getAuthToken());
		if (fromDate != null) {
			request.setAvailableFromDate(fromDate);
		}
		if (toDate != null) {
			request.setAvailableToDate(toDate);
		}
		if (idList != null) {
			request.setReportRequestIdList(idList);
		}
		return request;
	}

	public GetReportListResponse getReportList(GetReportListRequest request) throws MarketplaceWebServiceException {
		return service.getReportList(request);
	}

	public GetReportListByNextTokenRequest createGetReportListByNextTokenRequest(String nextToken) {
		GetReportListByNextTokenRequest nextRequest = new GetReportListByNextTokenRequest();
		nextRequest.setMerchant(merchant.getId());
		nextRequest.setMWSAuthToken(merchant.getAuthToken());
		nextRequest.setNextToken(nextToken);
		return nextRequest;
	}

	public GetReportListByNextTokenResponse getReportListByNextToken(GetReportListByNextTokenRequest request)
			throws MarketplaceWebServiceException {
		return service.getReportListByNextToken(request);

	}

	public static void printGetReportListResponse(GetReportListResponse response) {
		if (response.isSetGetReportListResult()) {
			System.out.print("        GetReportListResult");
			System.out.println();
			GetReportListResult getReportListResult = response.getGetReportListResult();
			if (getReportListResult.isSetNextToken()) {
				System.out.print("            NextToken");
				System.out.println();
				System.out.print("                " + getReportListResult.getNextToken());
				System.out.println();
			}
			if (getReportListResult.isSetHasNext()) {
				System.out.print("            HasNext");
				System.out.println();
				System.out.print("                " + getReportListResult.isHasNext());
				System.out.println();
			}
			java.util.List<ReportInfo> reportInfoListList = getReportListResult.getReportInfoList();
			for (ReportInfo reportInfoList : reportInfoListList) {
				System.out.print("            ReportInfoList");
				System.out.println();
				if (reportInfoList.isSetReportId()) {
					System.out.print("                ReportId");
					System.out.println();
					System.out.print("                    " + reportInfoList.getReportId());
					System.out.println();
				}
				if (reportInfoList.isSetReportType()) {
					System.out.print("                ReportType");
					System.out.println();
					System.out.print("                    " + reportInfoList.getReportType());
					System.out.println();
				}
				if (reportInfoList.isSetReportRequestId()) {
					System.out.print("                ReportRequestId");
					System.out.println();
					System.out.print("                    " + reportInfoList.getReportRequestId());
					System.out.println();
				}
				if (reportInfoList.isSetAvailableDate()) {
					System.out.print("                AvailableDate");
					System.out.println();
					System.out.print("                    " + reportInfoList.getAvailableDate());
					System.out.println();
				}
				if (reportInfoList.isSetAcknowledged()) {
					System.out.print("                Acknowledged");
					System.out.println();
					System.out.print("                    " + reportInfoList.isAcknowledged());
					System.out.println();
				}
				if (reportInfoList.isSetAcknowledgedDate()) {
					System.out.print("                AcknowledgedDate");
					System.out.println();
					System.out.print("                    " + reportInfoList.getAcknowledgedDate());
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

	public static void printGetReportListResponse(GetReportListByNextTokenResponse response) {
		if (response.isSetGetReportListByNextTokenResult()) {
			System.out.print("        GetReportListResult");
			System.out.println();
			GetReportListByNextTokenResult getReportListResult = response.getGetReportListByNextTokenResult();
			if (getReportListResult.isSetNextToken()) {
				System.out.print("            NextToken");
				System.out.println();
				System.out.print("                " + getReportListResult.getNextToken());
				System.out.println();
			}
			if (getReportListResult.isSetHasNext()) {
				System.out.print("            HasNext");
				System.out.println();
				System.out.print("                " + getReportListResult.isHasNext());
				System.out.println();
			}
			java.util.List<ReportInfo> reportInfoListList = getReportListResult.getReportInfoList();
			for (ReportInfo reportInfoList : reportInfoListList) {
				System.out.print("            ReportInfoList");
				System.out.println();
				if (reportInfoList.isSetReportId()) {
					System.out.print("                ReportId");
					System.out.println();
					System.out.print("                    " + reportInfoList.getReportId());
					System.out.println();
				}
				if (reportInfoList.isSetReportType()) {
					System.out.print("                ReportType");
					System.out.println();
					System.out.print("                    " + reportInfoList.getReportType());
					System.out.println();
				}
				if (reportInfoList.isSetReportRequestId()) {
					System.out.print("                ReportRequestId");
					System.out.println();
					System.out.print("                    " + reportInfoList.getReportRequestId());
					System.out.println();
				}
				if (reportInfoList.isSetAvailableDate()) {
					System.out.print("                AvailableDate");
					System.out.println();
					System.out.print("                    " + reportInfoList.getAvailableDate());
					System.out.println();
				}
				if (reportInfoList.isSetAcknowledged()) {
					System.out.print("                Acknowledged");
					System.out.println();
					System.out.print("                    " + reportInfoList.isAcknowledged());
					System.out.println();
				}
				if (reportInfoList.isSetAcknowledgedDate()) {
					System.out.print("                AcknowledgedDate");
					System.out.println();
					System.out.print("                    " + reportInfoList.getAcknowledgedDate());
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

	public ArrayList<ReportInfo> getReportListTotalResult(ArrayList<String> types, XMLGregorianCalendar fromDate,
			XMLGregorianCalendar toDate) throws MarketplaceWebServiceException {
		ArrayList<ReportInfo> result = new ArrayList<ReportInfo>();
		TypeList tList = new TypeList();
		tList.setType(types);
		GetReportListResponse response = this
				.getReportList(this.createGetReportListRequest(tList, null, fromDate, toDate));
		result.addAll(response.getGetReportListResult().getReportInfoList());
		boolean hasNext = response.getGetReportListResult().isHasNext();
		String nextToken = response.getGetReportListResult().getNextToken();
		while (hasNext) {
			GetReportListByNextTokenResponse nextResponse = this
					.getReportListByNextToken(this.createGetReportListByNextTokenRequest(nextToken));
			result.addAll(nextResponse.getGetReportListByNextTokenResult().getReportInfoList());
			hasNext = nextResponse.getGetReportListByNextTokenResult().isSetNextToken();
			nextToken = nextResponse.getGetReportListByNextTokenResult().getNextToken();
		}
		return result;

	}

}
