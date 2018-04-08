import java.io.IOException;
import java.util.Date;
import java.util.Timer;

import control.report.ReportTaskGenerateTimerTask;
import control.report.ReportTaskProcessTimerTask;

public class LaunchFixAPIDownload {
	
	public static void main(String[] a){
		try {
			
			System.out.println("System Launch ...");

			Timer timer1 = new Timer();
			try {
//				Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd '06:30:00'").format(new Date()));
//				timer1.schedule(new ReportTaskGenerateTimerTask(),
//						System.currentTimeMillis() > startTime.getTime() ? new Date(startTime.getTime() + 24*60*60*1000) : startTime, 86400000);
				timer1.schedule(new ReportTaskGenerateTimerTask(),new Date(System.currentTimeMillis()+5000), 86400000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Timer timer2 = new Timer();
			try {
				timer2.schedule(new ReportTaskProcessTimerTask(), new Date(System.currentTimeMillis()+11000), 600000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			while(true){
				try{
					int in = System.in.read();
					if(in == 's'){
						System.out.println("system shutting down:");
						break;
					}
				} catch (IOException e){
					e.printStackTrace();
				}
			}
			
			if (timer1 != null) timer1.cancel();
			if (timer2 != null) timer2.cancel();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
