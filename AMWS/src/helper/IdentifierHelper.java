package helper;

import java.util.Calendar;
import java.util.Random;

/**
 * 
 * @author Bens
 * @version 20160615
 */
public class IdentifierHelper {
	private static final int radix = 36;
	private final static int year = 2015;
	private final static int month = 0;
	private final static int date = 1;
	private final static int hour = 0;
	private final static int minute = 0;
	private final static int second = 1;

	public static String getEncryptedCurrentDate() {

		Calendar reference = Calendar.getInstance();
		reference.set(year, month, date, hour, minute, second);
		Calendar calendar = Calendar.getInstance();

		long tempparam = (calendar.getTimeInMillis() - reference
				.getTimeInMillis()) / 1000 / 60 / 60 / 24;

		if (tempparam < radix * radix) {
			return "0" + Long.toString(tempparam, radix).toUpperCase();
		} else {
			return Long.toString(tempparam, radix).toUpperCase();
		}
	}

	public static String getEncryptedCurrentTime() {
		Calendar reference = Calendar.getInstance();
		reference.set(year, month, date, hour, minute, second);
		Calendar calendar = Calendar.getInstance();

		long tempparam = (calendar.getTimeInMillis() - reference
				.getTimeInMillis()) / 1000;

		if (tempparam < radix * radix * radix * radix * radix) {
			return "0" + Long.toString(tempparam, radix).toUpperCase();
		} else {
			return Long.toString(tempparam, radix).toUpperCase();
		}
	}

	public static String getRandomLetters(int bit) {
		String[] symbol = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
				"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		String result = "";
		for (int i = 0; i < bit; i++) {
			Random r = new Random();
			int rn = r.nextInt(symbol.length);
			result += symbol[rn];
		}
		return result;
	}

	public static String getRandomNumbers(int bit) {
		String[] symbol = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String result = "";
		for (int i = 0; i < bit; i++) {
			Random r = new Random();
			int rn = r.nextInt(symbol.length);
			result += symbol[rn];
		}
		return result;
	}
	
	public static void main(String[] args){
		System.out.println(getEncryptedCurrentTime());
	}
}
