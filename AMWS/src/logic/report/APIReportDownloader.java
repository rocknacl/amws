package logic.report;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;

import amzint.MarketplaceWebServiceFactory;
import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import dao.entities.ReportDailyTaskTemplate;
import dao.report.ReportObjectConverter;
import dao.report.ReportObjectDAO;
import helper.IOHelper;
import helper.MyDateConverter;
import helper.XMLGregorianCalendarHelper;
import log.ExceptionLogger;
import model.report.APIReportTask;
import model.report.APIReportTaskStatus;
import model.report.ReportTypeEnum;

public class APIReportDownloader implements Runnable {
	public APIReportDownloader(MerchantAccount account, MarketplaceWebService service) {
		super();
		this.account = account;
		this.service = service;
	}

	private MerchantAccount account;
	private MarketplaceWebService service;
	final public static String reportsBackupFolder = "file/Reports/";

	/**
	 * generate tasks for merchant and process the tasks
	 */
	public void run() {
		System.out.println("Processing " + account.getName() + "'s Tasks");
		List<APIReportTask> tasks = APIReportTask
				.convertFromReportGetReportTaskList(ReportObjectDAO.getActiveTasksOfMerchant(account.getName()));
		try {
			this.processTasksAndSaveStatus(tasks);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("D16.log",true)));
				String className = e.getClass().getName();
				String message = e.getMessage();
				StackTraceElement[] trace = e.getStackTrace();
				pw.println(new Date()  + "\t\t\t\t" + "class: " + className + ", tmessage: " + message);
				for (int i=0; i<trace.length; i++){
					pw.println(trace[i].toString());
				}
				pw.println();
				pw.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Generate daily report task and save them into database in preparation for
	 * execute.
	 * 
	 * @param merchant
	 * @return the task list in case you need.
	 */
	public List<APIReportTask> generateDailyTask() {
		System.out.println("Generating " + account.getName() + "'s Tasks");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		List<APIReportTask> taskList = new ArrayList<APIReportTask>();
		List<ReportDailyTaskTemplate> templates = ReportObjectDAO.getEnabledTaskTemplate();
		// List<ReportDailyTaskTemplate> templates =
		// ReportObjectDAO.getNotEnabledTaskTemplate();
		for (ReportDailyTaskTemplate t : templates) {

			Date date = new Date();
			XMLGregorianCalendar to = null;

			if (t.getEndDateSetRequired() != null && t.getEndDateSetRequired() > 0) {
				to = XMLGregorianCalendarHelper.getXMLGregorianCalendar(date);
			}
			XMLGregorianCalendar from = null;
			if (t.getStartDateSetRequired() != null && t.getStartDateSetRequired() > 0 && t.getDistanceDays() != null
					&& t.getDistanceDays() < 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, t.getDistanceDays());
				from = MyDateConverter.convertToXMLGregorianCalendar(cal.getTime());
			}
			APIReportTask task = new APIReportTask(account.getName(), ReportTypeEnum.valueOf(t.getReportType()), null,
					from, to, reportsBackupFolder 
//					+ account.getName() + "/" + t.getReportType().toString() + "/"
					+ account.getName() + "_" + sdf.format(date) + "_" + t.getReportType().toString(),
					date);

			task.setStatus(APIReportTaskStatus.INIT);
			taskList.add(task);
		}

		try {
			ReportObjectDAO.saveOrUpdateMultipleObjects(APIReportTask.convertToReportGetReportTaskList(taskList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskList;
	}

	/**
	 * A complete process of tasks, request, query, download and save. The
	 * result including the task status will be saved at the end.
	 * 
	 * @param taskList
	 * @throws Exception
	 */
	private void processTasksAndSaveStatus(List<APIReportTask> taskList) throws Exception {
		if (taskList.isEmpty()) {
			System.out.println(account.getName() + " : No Task undone");
			return;
		}
		// Execute the tasks
		RequestAndGetReportsAsync ragra = new RequestAndGetReportsAsync(service, account, taskList);
		try {
			ragra.run();
			// save the downloaded reports

		} catch (FileNotFoundException | MarketplaceWebServiceException | InterruptedException e1) {
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
		} finally {
			/*
			 * Save the tasks' statuses back into database, because some tasks
			 * are possibly undone, so we can continue the tasks later
			 */
			ReportObjectDAO.saveOrUpdateMultipleObjects(APIReportTask.convertToReportGetReportTaskList(taskList));
		}
	}

}
