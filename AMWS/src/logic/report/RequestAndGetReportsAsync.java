package logic.report;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportListByNextTokenResponse;
import com.amazonaws.mws.model.GetReportListResponse;
import com.amazonaws.mws.model.GetReportRequest;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenResponse;
import com.amazonaws.mws.model.GetReportRequestListResponse;
import com.amazonaws.mws.model.GetReportResponse;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.ReportInfo;
import com.amazonaws.mws.model.ReportRequestInfo;
import com.amazonaws.mws.model.RequestReportRequest;
import com.amazonaws.mws.model.RequestReportResponse;

import amzint.report.APIGetReport;
import amzint.report.APIGetReportList;
import amzint.report.APIGetReportRequestList;
import amzint.report.APIRequestReport;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import dao.entities.MerchantAccount;
import dao.report.ReportObjectConverter;
import dao.report.ReportObjectDAO;
import helper.IOHelper;

import log.ExceptionLogger;
import model.basic.FileData;
import model.report.APIReportTask;
import model.report.APIReportTaskStatus;
import model.report.ReportTypeEnum;

/**
 * This class would do the job if you want to request multiple reports at the
 * same time. Just initialize an ArrayList containing multiple APIRequestTask
 * and use it to initialize one instance of this. You can set the maxRetry and
 * waitInterval which will decide how long and how many times it would wait to
 * ask about whether the reports are generated or not again. Of course you need
 * to assign a MarketplaceWebService and a MerchantAccount.
 * 
 * @author Stone
 *
 */
public class RequestAndGetReportsAsync {
	MarketplaceWebService service;
	MerchantAccount merchant;
	APIGetReportRequestList agrrl;
	APIGetReportList agrl;
	APIRequestReport rr;
	APIGetReport agr;

	List<APIReportTask> tasks;
	int maxRetry = 1;
	int waitInterval = 0;
	boolean allDone = false;

	public int getMaxRetry() {
		return maxRetry;
	}

	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	public int getWaitInterval() {
		return waitInterval;
	}

	public void setWaitInterval(int waitInterval) {
		this.waitInterval = waitInterval;
	}

	public RequestAndGetReportsAsync(MarketplaceWebService service, MerchantAccount merchant,
			List<APIReportTask> tasks) {
		super();
		this.tasks = tasks;
		this.service = service;
		this.merchant = merchant;
		agrl = new APIGetReportList(service, merchant);
		agrrl = new APIGetReportRequestList(service, merchant);
		rr = new APIRequestReport(service, merchant);
		agr = new APIGetReport(service, merchant);
	}

	public void run() throws Exception {
		ArrayList<APIReportTask> tasksNotRequested = new ArrayList<APIReportTask>();
		for (APIReportTask re : tasks) {
			if (re.getStatus().equals(APIReportTaskStatus.INIT)) {
				tasksNotRequested.add(re);
			}
		}
		this.requestReportForTasks(tasksNotRequested);
		ReportObjectDAO.saveOrUpdateMultipleObjects(APIReportTask.convertToReportGetReportTaskList(tasksNotRequested));
		int retry = maxRetry;
		while (!allDone && retry > 0) {
			ArrayList<APIReportTask> tasksReportNotGenerated = new ArrayList<APIReportTask>();
			for (APIReportTask re : tasks) {
				if (re.getStatus().equals(APIReportTaskStatus.REQUESTED)) {
					tasksReportNotGenerated.add(re);
				}
			}
			boolean existsTaskToGenerate = !tasksReportNotGenerated.isEmpty();
			if (existsTaskToGenerate) {
				this.getReportRequestList(tasksReportNotGenerated);
			}
			ReportObjectDAO.saveOrUpdateMultipleObjects(APIReportTask.convertToReportGetReportTaskList(tasksReportNotGenerated));
			ArrayList<APIReportTask> tasksReportNotDownloaded = new ArrayList<APIReportTask>();

			for (APIReportTask re : tasks) {
				if (re.getReportID() != null && (re.getStatus().equals(APIReportTaskStatus.GENERATED)))
					tasksReportNotDownloaded.add(re);
			}
			boolean existsTaskToDownload = !tasksReportNotDownloaded.isEmpty();
			if (existsTaskToDownload) {
				this.getReportForTasks(tasksReportNotDownloaded);
			}

			for (APIReportTask t : tasks) {
				if (t.getStatus().equals(APIReportTaskStatus.DOWNLOADED)) {
					
					ReportObjectConverter c;
					try {
						String propertiesFilePath = null;
						propertiesFilePath = t.getReportType().propertiesFilePath;
						
						if(propertiesFilePath==null||propertiesFilePath.isEmpty()) {
							continue;
						}
						c = new ReportObjectConverter(t.getReportType().propertiesFilePath,
								t.getReportType().getClassCorrespond(), t.getReportType().getDateFormat());
						System.out.println("Inserting :" + t.getOutputPath());
						ReportObjectDAO.saveOrUpdateMultipleObjects(c.readObjectsFromFlatFile(t.getOutputPath(),
								merchant.getName(), t.getReportType().getBeforehandProcessor()));
						t.setStatus(APIReportTaskStatus.INSERTED);
						
						// IOHelper.moveFile(t.getOutputPath(), +
						// t.getOutputPath());

					} catch (Exception e1) {

						new ExceptionLogger(e1);
						e1.printStackTrace();
						try {
							PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("D16.log",true)));
							String className = e1.getClass().getName();
							String message = e1.getMessage();
							StackTraceElement[] trace = e1.getStackTrace();
							pw.println(new Date()  + "\t\t\t\t" + "class: " + className + ", tmessage: " + message);
							for (int i=0; i<trace.length; i++){
								pw.println(trace[i].toString());
							}
							pw.println();
							pw.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						continue;
					}
				}
			}
			if (!(existsTaskToGenerate || existsTaskToDownload))
				allDone = true;
			else {
				// System.out.println("waiting");
				Thread.sleep(waitInterval);
				retry--;
			}

		}
		System.out.println("tasks done : " + allDone);
	}

	private Set<String> getReportList(ArrayList<APIReportTask> tasksToGetReportList)
			throws MarketplaceWebServiceException {
		List<String> reportRequestIDList = new ArrayList<String>();
		Set<String> downloadableReportIDs = new HashSet<String>();
		for (APIReportTask task : tasksToGetReportList) {
			reportRequestIDList.add(task.getReportRequestID());
		}
		IdList ids = new IdList();
		ids.setId(reportRequestIDList);
		GetReportListResponse response = agrl.getReportList(agrl.createGetReportListRequest(null, ids, null, null));
		for (ReportInfo info : response.getGetReportListResult().getReportInfoList()) {
			String reportID = info.getReportId();
			String requestID = info.getReportRequestId();
			downloadableReportIDs.add(reportID);
			for (APIReportTask t : tasksToGetReportList) {
				if (t.getReportRequestID().equals(requestID) && reportID != null) {
					t.setReportID(reportID);
					t.setStatus(APIReportTaskStatus.GENERATED);
				}
			}
		}
		boolean hasNext = response.getGetReportListResult().isSetNextToken();
		String nextToken = response.getGetReportListResult().getNextToken();
		while (hasNext) {
			GetReportListByNextTokenResponse nextResponse = agrl
					.getReportListByNextToken(agrl.createGetReportListByNextTokenRequest(nextToken));
			for (ReportInfo info : nextResponse.getGetReportListByNextTokenResult().getReportInfoList()) {
				String reportID = info.getReportId();
				String requestID = info.getReportRequestId();
				downloadableReportIDs.add(reportID);
				for (APIReportTask t : tasksToGetReportList) {
					if (t.getReportRequestID().equals(requestID) && reportID != null) {
						t.setReportID(reportID);
						t.setStatus(APIReportTaskStatus.GENERATED);
					}
				}
			}
			hasNext = nextResponse.getGetReportListByNextTokenResult().isSetNextToken();
			nextToken = nextResponse.getGetReportListByNextTokenResult().getNextToken();
		}
		return downloadableReportIDs;

	}

	private Set<String> getReportRequestList(ArrayList<APIReportTask> tasksToGetReportList)
			throws MarketplaceWebServiceException {
		List<String> reportRequestIDList = new ArrayList<String>();
		Set<String> downloadableReportIDs = new HashSet<String>();
		for (APIReportTask task : tasksToGetReportList) {
			reportRequestIDList.add(task.getReportRequestID());
		}
		IdList ids = new IdList();
		ids.setId(reportRequestIDList);
		GetReportRequestListResponse response = agrrl
				.getReportRequestList(agrrl.createGetReportRequestListRequest(null, ids, null, null));
		for (ReportRequestInfo info : response.getGetReportRequestListResult().getReportRequestInfoList()) {
			String reportProcessingStatus = info.getReportProcessingStatus();
			String reportID = info.getGeneratedReportId();
			String requestID = info.getReportRequestId();
			downloadableReportIDs.add(reportID);
			for (APIReportTask t : tasksToGetReportList) {
				if (t.getReportRequestID().equals(requestID)) {
					t.setReportProcessingStatus(reportProcessingStatus);

					if (reportProcessingStatus.contains("NO_DATA") || reportProcessingStatus.contains("CANCELLED"))
						t.setStatus(APIReportTaskStatus.CANCELLED);
					if (reportID != null) {
						t.setReportID(reportID);
						t.setOutputPath(t.getOutputPath()+"_"+reportID);
						t.setStatus(APIReportTaskStatus.GENERATED);
					}
				}
			}
		}
		boolean hasNext = response.getGetReportRequestListResult().isSetNextToken();
		String nextToken = response.getGetReportRequestListResult().getNextToken();
		while (hasNext) {
			GetReportRequestListByNextTokenResponse nextResponse = agrrl
					.getReportRequestListByNextToken(agrrl.createGetReportRequestListByNextTokenRequest(nextToken));
			for (ReportRequestInfo info : nextResponse.getGetReportRequestListByNextTokenResult()
					.getReportRequestInfoList()) {
				String reportProcessingStatus = info.getReportProcessingStatus();
				String reportID = info.getGeneratedReportId();
				String requestID = info.getReportRequestId();
				downloadableReportIDs.add(reportID);
				for (APIReportTask t : tasksToGetReportList) {
					if (t.getReportRequestID().equals(requestID)) {
						t.setReportProcessingStatus(reportProcessingStatus);
						if (reportProcessingStatus.contains("NO_DATA"))
							t.setStatus(APIReportTaskStatus.CANCELLED);
						if (reportID != null) {
							t.setReportID(reportID);
							t.setOutputPath(t.getOutputPath()+"_"+reportID);
							t.setStatus(APIReportTaskStatus.GENERATED);
						}
					}
				}
			}
			hasNext = nextResponse.getGetReportRequestListByNextTokenResult().isSetNextToken();
			nextToken = nextResponse.getGetReportRequestListByNextTokenResult().getNextToken();
		}
		return downloadableReportIDs;

	}

	private void requestReportForTasks(List<APIReportTask> tasksToRequest) {
		ArrayList<RequestReportRequest> requestList = new ArrayList<RequestReportRequest>();
		for (APIReportTask re : tasksToRequest) {
			re.setRequest(rr.createRequestReportRequest(re.getReportType().value, re.getReportOptions(),
					re.getFromDate(), re.getToDate()));
			requestList.add(re.getRequest());
		}
		RequestReportResponse[] rrrs = rr.requestReportAsync(requestList);
		for (int i = 0; i < rrrs.length; i++) {
			if (rrrs[i] != null) {
				tasksToRequest.get(i).setReportRequestID(
						rrrs[i].getRequestReportResult().getReportRequestInfo().getReportRequestId());
				tasksToRequest.get(i).setStatus(APIReportTaskStatus.REQUESTED);
			}
		}
	}

	private void getReportForTasks(List<APIReportTask> tasksToBeDownloaded) throws FileNotFoundException {
		List<GetReportRequest> requests = new ArrayList<GetReportRequest>();
		for (APIReportTask t : tasksToBeDownloaded) {
			if (t.getReportID() != null) {
				requests.add(agr.createGetReportRequest(t.getReportID(), t.getOutputPath()));
				t.setStatus(APIReportTaskStatus.DOWNLOADING);
				ReportObjectDAO.updateReportGetReportTask(t);
			}
		}
		
			GetReportResponse[] responses= agr.getReportsAsync(requests);
			for(int i = 0;i<responses.length;i++){
				APIReportTask t  = tasksToBeDownloaded.get(i);
				if(responses[i]!=null){
					
					File file = new File(t.getOutputPath());
					if (file.exists()) {
						t.setStatus(APIReportTaskStatus.DOWNLOADED);					
					}
				}else{
					t.setStatus(APIReportTaskStatus.GENERATED);
				}
			}
	}
}
