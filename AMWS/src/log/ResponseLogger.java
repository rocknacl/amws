package log;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.entities.MerchantAccount;
import helper.DateConverter;
import helper.IOHelper;
import helper.dao.ConnectionPool;

/**
 * can be extended, new by mws response object and write txt log & save
 * exception simple info to database automatically
 * 
 * @author Bens
 * @version 20160622
 */
public class ResponseLogger {
	protected static String logPath = "Log/";
	protected static String operationLogPath = logPath + "Operation/";
	protected String filename = operationLogPath + "Response_" + DateConverter.getCurrentDate() + ".txt";
	protected MerchantAccount merchant;
	protected Date recordDate = new Date();
	protected Class<?> classObject;
	protected String requestId;
	protected String timestamp;
	protected List<String> responseContext;

	@SuppressWarnings("unchecked")
	public ResponseLogger(Object mwsResponseObject, MerchantAccount merchant){
		try{
		checkPath();
		Field field = mwsResponseObject.getClass().getDeclaredField("responseHeaderMetadata");
		field.setAccessible(true);
		Object responseHeaderMetadata = field.get(mwsResponseObject);
		this.merchant = merchant;
		this.classObject = mwsResponseObject.getClass();
		field = responseHeaderMetadata.getClass().getSuperclass().getDeclaredField("requestId");
		field.setAccessible(true);
		this.requestId = (String) field.get(responseHeaderMetadata);
		field = responseHeaderMetadata.getClass().getSuperclass().getDeclaredField("responseContext");
		field.setAccessible(true);
		this.responseContext = (List<String>) field.get(responseHeaderMetadata);
		field = responseHeaderMetadata.getClass().getSuperclass().getDeclaredField("timestamp");
		field.setAccessible(true);
		this.timestamp = (String) field.get(responseHeaderMetadata);
		writeTXTLog();
		saveDatabaseLog();
		} catch (Exception e){
			new ExceptionLogger(e);
		}
	}

	protected boolean writeTXTLog() {
		try {
			ArrayList<String[]> logData = new ArrayList<String[]>();
			String[] line = new String[responseContext.size() + 5];
			line[0] = requestId;
			if (merchant != null)
				line[1] = merchant.getName();
			line[2] = timestamp;
			line[3] = "" + recordDate;
			line[4] = classObject.getSimpleName();
			for (int i = 5; i < line.length; i++)
				line[i] = responseContext.get(i - 5);
			logData.add(line);
			IOHelper.writetxt(filename, logData, true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	protected boolean saveDatabaseLog() {
		try {
			Connection connection = ConnectionPool.getConnectionPool().getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO `amws`.`general_response_record` (`Request_ID`, `TStamp`, `RecordDate`, `Class_Name`, `First_Content`, `Merchant`) VALUES (?,?,?,?,?,?);");
			statement.setString(1, requestId);
			statement.setString(2, timestamp);
			statement.setTimestamp(3, new Timestamp(recordDate.getTime()));
			statement.setString(4, classObject.getSimpleName());
			statement.setString(5, responseContext.get(0).substring(0, Math.min(500, responseContext.get(0).length())));
			String merchantId = null;
			if (merchant != null)
				merchantId = merchant.getId();
			statement.setString(6, merchantId);
			statement.execute();
			statement.close();
			connection.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean checkPath() {
		try {
			File file = new File(logPath);
			if (!file.exists())
				file.mkdirs();
			file = new File(operationLogPath);
			if (!file.exists())
				file.mkdirs();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getLogPath() {
		return logPath;
	}

	public static void setLogPath(String logPath) {
		ResponseLogger.logPath = logPath;
	}

	public static String getOperationLogPath() {
		return operationLogPath;
	}

	public static void setOperationLogPath(String operationLogPath) {
		ResponseLogger.operationLogPath = operationLogPath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public MerchantAccount getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantAccount merchant) {
		this.merchant = merchant;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Class<?> getClassObject() {
		return classObject;
	}

	public void setClassObject(Class<?> classObject) {
		this.classObject = classObject;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getResponseContext() {
		return responseContext;
	}

	public void setResponseContext(List<String> responseContext) {
		this.responseContext = responseContext;
	}
}
