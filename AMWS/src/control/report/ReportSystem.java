package control.report;

import java.util.Timer;

public class ReportSystem {
	public void run(){
		Timer timer = new Timer();
		ReportTaskGenerateTimerTask gt = new ReportTaskGenerateTimerTask();
		timer.schedule(gt, 1000,1000*60*60*24);
		ReportTaskProcessTimerTask pt = new ReportTaskProcessTimerTask();
		timer.schedule(pt, 1000*60,1000*60*10);
	}

}
