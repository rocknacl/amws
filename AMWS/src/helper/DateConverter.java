package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author Bens
 * @version 20160615
 */
public class DateConverter {

	public static Date getOrderDate(String originaldate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HHmmss.S");
		originaldate = originaldate.replaceAll(":", "");
		return sdf.parse(originaldate);
	}

	public static Date getManualDate(String originaldate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.parse(originaldate);
	}
	
	public static Date getTransactionDate(String orifinaldate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy h:mm:ss a z",Locale.ENGLISH);
		return df.parse(orifinaldate);
	}
	
	public static String getCurrentDate(){
		return new SimpleDateFormat("yyyyMMdd Z").format(new Date());
	}

}
