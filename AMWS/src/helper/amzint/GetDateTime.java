package helper.amzint;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/*
 * D16
 */
public class GetDateTime {

	public static String getLocalDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String getLocalYear() {
		return new SimpleDateFormat("yyyy").format(new Date());
	}

	public static String getLocalDateTimeWithMilliSec() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date());
	}

	public static String getLocalDateByDeltaDay(int deltaDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, deltaDay);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	public static String getDateByDeltaHour(int deltaHour, String sourceDateString) {
		try {
			Date sourceDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(sourceDateString);
			Calendar targetCalendar = Calendar.getInstance();
			targetCalendar.setTime(sourceDate);
			targetCalendar.add(Calendar.HOUR_OF_DAY, deltaHour);
			return new SimpleDateFormat("yyyyMMddHHmmss").format(targetCalendar.getTime());
		} catch (Exception e) {
			return null;
		}
	}
}
