package temp;

import java.util.Timer;

import control.report.ReportTaskGenerateTimerTask;
import control.report.ReportTaskProcessTimerTask;

public class StoneTest {
	public static void main(String[] args){
		Timer timer = new Timer();
		ReportTaskGenerateTimerTask gt = new ReportTaskGenerateTimerTask();
//		timer.schedule(gt, 1000*60*60*5,1000*60*60*24);
//		timer.schedule(gt, 1000);
		ReportTaskProcessTimerTask pt = new ReportTaskProcessTimerTask();
//		timer.schedule(pt, 1000*60*60*6 ,1000*60*5);
		timer.schedule(pt, 1000);
		
	}

}
