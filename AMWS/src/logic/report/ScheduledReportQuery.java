package logic.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.ReportInfo;

import amzint.report.APIGetReportList;
import dao.entities.MerchantAccount;
import dao.entities.ReportGetReportTask;
import dao.entities.ReportScheduledReport;
import dao.entities.ReportScheduledReportType;
import dao.report.ReportObjectDAO;
import helper.MyDateConverter;
import model.report.APIReportTask;
import model.report.APIReportTaskStatus;
import model.report.ReportTypeEnum;

public class ScheduledReportQuery {
	protected MarketplaceWebService service;
	protected MerchantAccount merchant;
	final int distanceDays = -30;
	APIGetReportList agrl;
	ArrayList<ReportInfo> result;

	public ScheduledReportQuery(MarketplaceWebService service, MerchantAccount merchant) {
		super();
		this.service = service;
		this.merchant = merchant;
		agrl = new APIGetReportList(service, merchant);
	}

	public void run() {
		List<ReportScheduledReportType> types = ReportObjectDAO.getEnabledScheduledReportTaskTemplate();
		if (types.isEmpty())
			return;
		ArrayList<String> typeNames = new ArrayList<String>();
		for (ReportScheduledReportType type : types) {
			typeNames.add(ReportTypeEnum.valueOf(type.getReportType()).value);
		}
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, distanceDays);
		System.out.println(cal.getTime());
		XMLGregorianCalendar fromDate = MyDateConverter.convertToXMLGregorianCalendar(cal.getTime());
		try {
			result = agrl.getReportListTotalResult(typeNames, fromDate, null);
		} catch (MarketplaceWebServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<ReportScheduledReport> scheduledReports = new ArrayList<ReportScheduledReport>();
		for (ReportInfo info : result) {
			ReportScheduledReport report = new ReportScheduledReport();
			report.setStore(merchant.getName());
			if (ReportTypeEnum.getEnumByValue(info.getReportType()) != null)
				report.setReportType(ReportTypeEnum.getEnumByValue(info.getReportType()).toString());
			else
				report.setReportType(info.getReportType());
			report.setAvailableDate(MyDateConverter.convertToDate(info.getAvailableDate()));
			report.setReportId(info.getReportId());
			report.setAcknowledgedDate(MyDateConverter.convertToDate(info.getAcknowledgedDate()));
			report.setInsertDate(new Date());
			scheduledReports.add(report);
		}
		try {
			ReportObjectDAO.saveScheduledReports(scheduledReports);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generateAPIGetReportTask() {
		List<APIReportTask> taskList = new ArrayList<APIReportTask>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		List<Object> reports = ReportObjectDAO.getUnDownloadedScheduledReport(merchant.getName());
		for (Object o : reports) {
			ReportScheduledReport r = (ReportScheduledReport) o;
			APIReportTask task = new APIReportTask(merchant.getName(), ReportTypeEnum.valueOf(r.getReportType()), null,
					null, null,
					APIReportDownloader.reportsBackupFolder 
//					+merchant.getName() + "/"+ r.getReportType().toString() + "/" 
					+ merchant.getName() + "_"
					+ r.getReportType().toString()+"_"+sdf.format(date) + "_" + r.getReportId(),
					date);
			task.setReportID(r.getReportId());
			task.setStatus(APIReportTaskStatus.GENERATED);
			r.setTaskGenerated(1);
			taskList.add(task);
		}
		try {
			ReportObjectDAO.saveOrUpdateMultipleObjects(APIReportTask.convertToReportGetReportTaskList(taskList));
			ReportObjectDAO.saveOrUpdateMultipleObjects(reports);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
