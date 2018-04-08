package helper.amzint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
/**
 * XMLGregorianCalendar类型和Date类型之间的相互转换
 * @author Xin
 * 2010-06-12
 */
public class DateConverter {
	    public static XMLGregorianCalendar date2XMLGregorianCalendar(Date date) {

	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(date);
	        XMLGregorianCalendar gc = null;
	        try {
	            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        } catch (Exception e) {

	             e.printStackTrace();
	        }
	        return gc;
	    }
	 
	     public  static Date XMLGregorianCalendar2Date(XMLGregorianCalendar cal) throws Exception{
	         GregorianCalendar ca = cal.toGregorianCalendar();
	         return ca.getTime();
	     }
	 
	     public static void main(String[] args) {
	         XMLGregorianCalendar d = DateConverter.date2XMLGregorianCalendar(new Date());
	         System.out.println(d.getDay());
	         XMLGregorianCalendar cal = null;
	         try {
	             cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
	             cal.setMonth(06);
	             cal.setYear(2010); 
	             Date date = DateConverter.XMLGregorianCalendar2Date(cal);
	             String format = "yyyy-MM-dd HH:mm:ss";
	             SimpleDateFormat formatter = new SimpleDateFormat(format);
	             System.out.println(formatter.format(date));
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	     }

}
