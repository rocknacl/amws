package model.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.model.RequestReportRequest;

import dao.entities.MerchantAccount;
import dao.entities.ReportGetReportTask;
import helper.MyDateConverter;
import helper.XMLGregorianCalendarHelper;

public class APIReportTask {

	private ReportTypeEnum reportType;
	private String[] reportOptions;
	private XMLGregorianCalendar fromDate;
	private XMLGregorianCalendar toDate;
	private String outputPath;
	private String reportProcessingStatus;

	public String getReportProcessingStatus() {
		return reportProcessingStatus;
	}

	public void setReportProcessingStatus(String reportProcessingStatus) {
		this.reportProcessingStatus = reportProcessingStatus;
	}

	public APIReportTask(String merchantName,ReportTypeEnum reportType, String[] reportOptions, XMLGregorianCalendar fromDate,
			XMLGregorianCalendar toDate, String outputPath, Date workDate) {
		super();
		this.merchantName = merchantName;
		this.reportType = reportType;
		this.reportOptions = reportOptions;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.outputPath = outputPath;
		this.workDate = workDate;
	}

	private String merchantName;
	private Date workDate;
	private RequestReportRequest request;
	private String reportRequestID;
	private String reportID;
	private APIReportTaskStatus status;
	private int databaseID;

	public APIReportTaskStatus getStatus() {
		return status;
	}

	public void setStatus(APIReportTaskStatus status) {
		this.status = status;
	}

	public ReportTypeEnum getReportType() {
		return reportType;
	}

	public void setReportType(ReportTypeEnum reportType) {
		this.reportType = reportType;
	}

	public String[] getReportOptions() {
		return reportOptions;
	}

	public void setReportOptions(String[] reportOptions) {
		this.reportOptions = reportOptions;
	}

	public XMLGregorianCalendar getFromDate() {
		return fromDate;
	}

	public void setFromDate(XMLGregorianCalendar fromDate) {
		this.fromDate = fromDate;
	}

	public XMLGregorianCalendar getToDate() {
		return toDate;
	}

	public void setToDate(XMLGregorianCalendar toDate) {
		this.toDate = toDate;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public RequestReportRequest getRequest() {
		return request;
	}

	public void setRequest(RequestReportRequest request) {
		this.request = request;
	}

	public String getReportRequestID() {
		return reportRequestID;
	}

	public void setReportRequestID(String reportRequestID) {
		this.reportRequestID = reportRequestID;
	}

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public int getDatabaseID() {
		return databaseID;
	}

	public void setDatabaseID(int databaseID) {
		this.databaseID = databaseID;
	}

	public static ReportGetReportTask convertToReportGetReportTask(APIReportTask target) {
		ReportGetReportTask t = new ReportGetReportTask();
		t.setMerchant(target.merchantName);
		t.setOutputPath(target.outputPath);
		t.setReportType(target.reportType.toString());
		t.setFromDate(MyDateConverter.convertToDate(target.fromDate));
		t.setToDate(MyDateConverter.convertToDate(target.toDate));
		t.setWorkDate(target.getWorkDate());
		if (target.status != null)
			t.setStatus(target.status.toString());
		if (target.getDatabaseID() != 0) {
			t.setId(target.getDatabaseID());
		}
		if(target.reportProcessingStatus!=null)
			t.setReportProcessingStatus(target.reportProcessingStatus);
		t.setReportRequestId(target.getReportRequestID());
		t.setReportId(target.getReportID());
		return t;
	}

	public static APIReportTask convertFromReportGetReportTask(ReportGetReportTask t) {
		APIReportTask r = new APIReportTask(t.getMerchant(),ReportTypeEnum.valueOf(t.getReportType()), null,
				MyDateConverter.convertToXMLGregorianCalendar(t.getFromDate()),
				MyDateConverter.convertToXMLGregorianCalendar(t.getToDate()), t.getOutputPath(), t.getWorkDate());
		r.setStatus(APIReportTaskStatus.valueOf(t.getStatus()));
		r.setDatabaseID(t.getId());
		r.setReportRequestID(t.getReportRequestId());
		r.setReportID(t.getReportId());
		r.setReportProcessingStatus(t.getReportProcessingStatus());
		return r;
	}

	public static List<APIReportTask> convertFromReportGetReportTaskList(List<ReportGetReportTask> tList) {
		List<APIReportTask> resultList = new ArrayList<APIReportTask>();
		for (ReportGetReportTask t : tList) {
			resultList.add(APIReportTask.convertFromReportGetReportTask(t));
		}
		return resultList;
	}

	public static List<Object> convertToReportGetReportTaskList(List<APIReportTask> aList) {
		List<Object> resultList = new ArrayList<Object>();
		for (APIReportTask a : aList) {
			resultList.add(APIReportTask.convertToReportGetReportTask(a));
		}
		return resultList;
	}

}
