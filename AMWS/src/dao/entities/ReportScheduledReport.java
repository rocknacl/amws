package dao.entities;
// Generated 2016-8-8 16:52:31 by Hibernate Tools 4.3.1.Final

import java.util.Date;

/**
 * ReportScheduledReport generated by hbm2java
 */
public class ReportScheduledReport implements java.io.Serializable {

	private String reportId;
	private String store;
	private Date insertDate;
	private String reportType;
	private Date availableDate;
	private Date acknowledgedDate;
	private Integer taskGenerated;

	public ReportScheduledReport() {
	}

	public ReportScheduledReport(String reportId) {
		this.reportId = reportId;
	}

	public ReportScheduledReport(String reportId, String store, Date insertDate, String reportType, Date availableDate,
			Date acknowledgedDate, Integer taskGenerated) {
		this.reportId = reportId;
		this.store = store;
		this.insertDate = insertDate;
		this.reportType = reportType;
		this.availableDate = availableDate;
		this.acknowledgedDate = acknowledgedDate;
		this.taskGenerated = taskGenerated;
	}

	public String getReportId() {
		return this.reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getStore() {
		return this.store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getReportType() {
		return this.reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Date getAvailableDate() {
		return this.availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public Date getAcknowledgedDate() {
		return this.acknowledgedDate;
	}

	public void setAcknowledgedDate(Date acknowledgedDate) {
		this.acknowledgedDate = acknowledgedDate;
	}

	public Integer getTaskGenerated() {
		return this.taskGenerated;
	}

	public void setTaskGenerated(Integer taskGenerated) {
		this.taskGenerated = taskGenerated;
	}

}
