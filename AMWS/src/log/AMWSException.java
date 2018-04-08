package log;

import java.util.Date;
import java.util.List;

import dao.entities.MerchantAccount;

public class AMWSException extends Exception {
	private static final long serialVersionUID = -4241641647351520119L;

	protected ExceptionLogger exceptionLogger;
	protected ResponseLogger responseLogger;

	protected String errorTitle = "AMWS Exception";
	protected List<String> errorDetail;
	protected MerchantAccount merchant;
	protected String requestId;
	protected Date timeStamp = new Date();
	
	public AMWSException(){
		super();
		this.exceptionLogger = new ExceptionLogger(this);
		print();
	}

	public AMWSException(String message) {
		super(message);
		this.exceptionLogger = new ExceptionLogger(this);
		print();
	}

	public AMWSException(String message, MerchantAccount merchant) {
		super(message);
		this.exceptionLogger = new ExceptionLogger(this,merchant);
		this.merchant = merchant;
		print();
	}

	public AMWSException(String message, ResponseLogger responseLogger) {
		super(message);
		this.responseLogger = responseLogger;
		this.exceptionLogger = new ExceptionLogger(this,responseLogger.getMerchant());
		this.merchant = responseLogger.getMerchant();
		this.requestId = responseLogger.getRequestId();
		print();
	}

	public void print() {
		System.out.println("**********AMWS Exception**********");
		if (merchant != null) {
			System.out.println("Merchant ID: " + merchant.getId());
			System.out.println("Merchant Name: " + merchant.getName());
		}
		System.out.println("Request ID: " + requestId);
		System.out.println("TimeStamp: " + timeStamp);
		System.out.println("Error Title: " + errorTitle);
		for (String errorLine : errorDetail)
			System.out.println("Error Detail: " + errorLine);
		System.out.println("Exception Message: " + this.getMessage());
		for (StackTraceElement stackTraceElement : this.getStackTrace())
			System.out.println("Stack Trace Element: " + stackTraceElement.getClassName() + "\t"
					+ stackTraceElement.getMethodName() + "\t" + stackTraceElement.getLineNumber());
		System.out.println("**********AMWS Exception END**********");
	}

	public String getErrorTitle() {
		return errorTitle;
	}

	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}

	public List<String> getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(List<String> errorDetail) {
		this.errorDetail = errorDetail;
	}

	public MerchantAccount getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantAccount merchant) {
		this.merchant = merchant;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
