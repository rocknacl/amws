package helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonservices.mws.client.MwsUtl;

/**
 * @author Bens
 * @version 20160607
 */
public class XMLGregorianCalendarHelper {
	private static SimpleDateFormat standardformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
	private static SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
	private static final String endstring = "T00:00:00";
	
	/**
	 * get util Date from XMLGregorianCalendar
	 * @author Bens
	 * @param xmlGregorianCalendar
	 * @return xmlGregorianCalendar.toGregorianCalendar().getInstance().getTime()
	 */
	@SuppressWarnings("static-access")
	public static Date getDate(XMLGregorianCalendar xmlGregorianCalendar){
		return xmlGregorianCalendar.toGregorianCalendar().getTime();
	}
	
	/**
	 * general XMLGregorianCalendar getter from Date object
	 * @author Bens
	 * @param date
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date){
		return MwsUtl.getDTF().newXMLGregorianCalendar(standardformat.format(date));
	}
	
	/**
	 * general XMLGregorianCalendar getter from Calendar object
	 * @author Bens
	 * @param date
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(Calendar calendar){
		return MwsUtl.getDTF().newXMLGregorianCalendar(standardformat.format(calendar.getTime()));
	}
	
	/**
	 * general XMLGregorianCalendar getter from String object
	 * @author Bens
	 * @param date with format: yyyy-MM-ddTHH:mm:ss
	 * @return XMLGregorianCalendar if failed return null
	 */
	public static XMLGregorianCalendar tryGetXMLGregorianCalendarByStandardFormatString(String date){
		try{
			standardformat.parse(date);
			return MwsUtl.getDTF().newXMLGregorianCalendar(date);
		} catch (Exception e){
			return null;
		}
	}
	
	/**
	 * general XMLGregorianCalendar getter from string object
	 * @author Bens
	 * @param date with format: yyyy-MM-dd
	 * @return XMLGregorianCalendar if failed return null
	 */
	public static XMLGregorianCalendar tryGetXMLGregorianCalendarBySimpleFormatString(String date){
		try{
			simpleformat.parse(date);
			return MwsUtl.getDTF().newXMLGregorianCalendar(date+endstring);
		} catch (Exception e){
			return null;
		}
	}
	
	/**
	 * get midnight XMLGregorianCalendar with day distance and time zone
	 * @author Bens
	 * @param distance days; for future + ; for past -;
	 * @param timezone
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar getMidnightTimeXMLGregorianCalendar(int distance,TimeZone timezone){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, distance);
		calendar.setTimeZone(timezone);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return getXMLGregorianCalendar(calendar);
	}
	
	/**
	 * get midnight XMLGregorianCalendar with day distance and default time zone
	 * @author Bens
	 * @param distance days; for future + ; for past -;
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar getMidnightTimeXMLGregorianCalendar(int distance){
		return getMidnightTimeXMLGregorianCalendar(distance,TimeZone.getDefault());
	}

	/**
	 * get XMLGregorianCalendar with Calendar field, day distance and time zone
	 * @author Bens
	 * @param distance days; for future + ; for past -;
	 * @param timezone
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(int field,int distance, TimeZone timezone){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(field, distance);
		calendar.setTimeZone(timezone);
		return getXMLGregorianCalendar(calendar);
	}
	
	/**
	 * get XMLGregorianCalendar with Calendar field, day distance and default time zone
	 * @author Bens
	 * @param distance days; for future + ; for past -;
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(int field,int distance){
		return getXMLGregorianCalendar(field,distance,TimeZone.getDefault());
	}
}
