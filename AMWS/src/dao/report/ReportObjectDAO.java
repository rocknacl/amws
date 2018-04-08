package dao.report;

import java.util.Date;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.amazonaws.mws.model.ReportSchedule;

import dao.entities.ReportDailyTaskTemplate;
import dao.entities.ReportGetReportTask;
import dao.entities.ReportScheduledReport;
import dao.entities.ReportScheduledReportType;
import helper.dao.HibernateUtils;
import model.report.APIReportTask;

public class ReportObjectDAO {
	public static void saveOrUpdateMultipleObjects(List<Object> list) throws Exception {
		Session session = null;

		session = HibernateUtils.getSession();
		Transaction t = session.beginTransaction();
		for (Object o : list) {
			session.saveOrUpdate(o);
//			session.save(o);
//			session.merge(o);
		}
		t.commit();

		session.close();

	}

	public static List<ReportGetReportTask> getTasksNotInCertainStatusOfMerchant(String status, String merchant) {
		Session session = HibernateUtils.getSession();
		Query query = session.createQuery(
				"from ReportGetReportTask where status!= '" + status + "'" + " and merchant='" + merchant + "'");
		List<ReportGetReportTask> result = query.list();
		session.close();
		return result;
	}

	public static List<ReportGetReportTask> getActiveTasksOfMerchant(String merchant) {
		Session session = HibernateUtils.getSession();
		Query query = session
				.createQuery("from ReportGetReportTask where status!='INSERTED' and status!='CANCELLED' and status!='DOWNLOADING' and merchant='"
						+ merchant + "'");
		List<ReportGetReportTask> result = query.list();
		session.close();
		return result;
	}

	public static List<Object> getAllObjectsFromCertainTable(Class<?> cls) {
		Session session = HibernateUtils.getSession();
		Query query = session.createQuery("from " + cls.getSimpleName());
		List<Object> result = query.list();
		session.close();
		return result;
	}
	public static List<ReportDailyTaskTemplate> getEnabledTaskTemplate(){
		Session session = HibernateUtils.getSession();
		Query query = session.createQuery("from ReportDailyTaskTemplate where enabled>0" );
		List<ReportDailyTaskTemplate> result = query.list();
		session.close();
		return result;
	}
	public static List<ReportDailyTaskTemplate> getNotEnabledTaskTemplate(){
		Session session = HibernateUtils.getSession();
		Query query = session.createQuery("from ReportDailyTaskTemplate where enabled is null or enabled =0" );
		List<ReportDailyTaskTemplate> result = query.list();
		session.close();
		return result;
	}
	public static List<ReportScheduledReportType> getEnabledScheduledReportTaskTemplate(){
		Session session = HibernateUtils.getSession();
		Query query = session.createQuery("from ReportScheduledReportType where enabled>0" );
		List<ReportScheduledReportType> result = query.list();
		session.close();
		return result;
	}

	public static List<Object> getUnDownloadedScheduledReport(String name) {
		Session session = HibernateUtils.getSession();
		Query query = session.createQuery("from ReportScheduledReport where Store = '"+name+"' and task_generated is null or task_generated =0"  );
		List<Object> result = query.list();
		session.close();
		return result;
	}

	public static void saveScheduledReports(List<ReportScheduledReport> scheduledReports) {
		Session session = null;

		session = HibernateUtils.getSession();
		Transaction t = session.beginTransaction();
		for (ReportScheduledReport r : scheduledReports) {
			ReportScheduledReport report = (ReportScheduledReport) session.get(ReportScheduledReport.class, r.getReportId());
			if(report==null)
				session.save(r);
			
		}
		t.commit();

		session.close();
		
	}
	public static void updateReportGetReportTask(APIReportTask task){
		Session session = HibernateUtils.getSession();
		Transaction tx = session.beginTransaction();
		ReportGetReportTask t = APIReportTask.convertToReportGetReportTask(task);
		t.setLastUpdateDatetime(new Date());
		session.update(t);
		tx.commit();
		session.close();
	}
	

}
