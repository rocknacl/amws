package log;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import dao.entities.MerchantAccount;
import helper.IOHelper;
import helper.IdentifierHelper;
import helper.dao.ConnectionPool;

/**
 * can be extended, new by Exception object and write txt log & save exception
 * simple info to database automatically
 * 
 * @author Bens
 * @version 20160615
 */
public class ExceptionLogger {
	protected static String logPath = "Log/";
	protected static String exceptionLogPath = logPath + "Exception/";
	protected static SimpleDateFormat detialDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss Z", Locale.ENGLISH);

	protected MerchantAccount merchant;
	protected Exception exception;
	protected String exceptionID;
	protected String exceptionMessage;
	protected String firstExceptionClass;
	protected Integer firstExceptionClassLine;
	protected Date timeStamp;

	public ExceptionLogger(Exception exception, MerchantAccount merchant) {
		checkPath();
		this.merchant = merchant;
		this.exception = exception;
		this.exceptionMessage = exception.getMessage();
		if (exception.getStackTrace().length > 0) {
			this.firstExceptionClass = exception.getStackTrace()[0].getClassName();
			this.firstExceptionClassLine = exception.getStackTrace()[0].getLineNumber();
		}
		this.exceptionID = IdentifierHelper.getEncryptedCurrentTime() + IdentifierHelper.getRandomLetters(4);
		this.timeStamp = new Date();
		writeExceptionLog();
		saveException();
	}

	public ExceptionLogger(Exception exception) {
		this(exception,null);
	}

	private boolean checkPath() {
		try {
			File file = new File(logPath);
			if (!file.exists())
				file.mkdirs();
			file = new File(exceptionLogPath);
			if (!file.exists())
				file.mkdirs();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	protected boolean writeExceptionLog() {
		try {
			ArrayList<String[]> logData = new ArrayList<String[]>();
			if(merchant!=null){
			String[] line1 = { merchant.getName(),merchant.getMarketPlaceId(),merchant.getId() };
			logData.add(line1);}
			String[] line2 = { exceptionMessage };
			logData.add(line2);
			for (StackTraceElement element : exception.getStackTrace()) {
				String[] line = new String[3];
				line[0] = element.getClassName();
				line[1] = element.getMethodName();
				line[2] = "" + element.getLineNumber();
				logData.add(line);
			}
			IOHelper.writetxt(exceptionLogPath + exceptionID + " " + detialDateFormat.format(timeStamp) + ".txt",
					logData);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	protected boolean saveException() {
		try {
			Connection connection = ConnectionPool.getConnectionPool().getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO `amws`.`general_exception_record` (`Exception_ID`, `Exception_Message`, `FirstExceptionClass`, `FirstExceptionClassLine`, `TStamp`, `Merchant`) VALUES (?,?,?,?,?,?);");
			statement.setString(1, exceptionID);
			statement.setString(2, exceptionMessage);
			statement.setString(3, firstExceptionClass);
			statement.setInt(4, firstExceptionClassLine);
			statement.setTimestamp(5, new Timestamp(timeStamp.getTime()));
			String merchantId=null;
			if(merchant!=null)merchantId = merchant.getId();
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

	public static String getLogPath() {
		return logPath;
	}

	public static void setLogPath(String logPath) {
		ExceptionLogger.logPath = logPath;
	}

	public static String getExceptionLogPath() {
		return exceptionLogPath;
	}

	public static void setExceptionLogPath(String exceptionLogPath) {
		ExceptionLogger.exceptionLogPath = exceptionLogPath;
	}

	public static SimpleDateFormat getDetialDateFormat() {
		return detialDateFormat;
	}

	public static void setDetialDateFormat(SimpleDateFormat detialDateFormat) {
		ExceptionLogger.detialDateFormat = detialDateFormat;
	}

	public MerchantAccount getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantAccount merchant) {
		this.merchant = merchant;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getExceptionID() {
		return exceptionID;
	}

	public void setExceptionID(String exceptionID) {
		this.exceptionID = exceptionID;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getFirstExceptionClass() {
		return firstExceptionClass;
	}

	public void setFirstExceptionClass(String firstExceptionClass) {
		this.firstExceptionClass = firstExceptionClass;
	}

	public Integer getFirstExceptionClassLine() {
		return firstExceptionClassLine;
	}

	public void setFirstExceptionClassLine(Integer firstExceptionClassLine) {
		this.firstExceptionClassLine = firstExceptionClassLine;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
