
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import control.process.AdvertisementAction;
import control.process.BasicDataOrderUnshipped;
import control.process.BasicDataSellerSKUAFNRestock;
import control.process.BasicDataSellerSKUAdvertisement;
import control.process.BasicDataSellerSKUSalesDaily;
import control.process.BasicDataSellerSKUStockAndPrice;
import control.transmission.Connection;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import model.login.ServerData;
import model.login.UserData;

public class LaunchFixOSSUpdate {

	private static boolean lauchStatus = false;
	
	private static ServerData serverData = new ServerData("112.74.206.141", 9527); //112.74.206.141
	private static UserData userData = new UserData("OSSSync","OSSSync",null);
	
	private static Timer basicDataTaskTimer;
	private static boolean basicDataTaskStatus = false;
	private static boolean basicDataTaskTimerStatus = false;
	private static class BasicDataTask extends TimerTask{
		public void run(){
			if (lauchStatus && basicDataTaskTimerStatus) {
				basicDataTaskStatus = true;
				try {
					new BasicDataSellerSKUStockAndPrice().process();
					System.out.println("BasicDataSellerSKUStockAndPrice Done");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					new BasicDataSellerSKUAdvertisement().process();
					System.out.println("BasicDataSellerSKUAdvertisement Done");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					new BasicDataSellerSKUAFNRestock().process();
					System.out.println("BasicDataSellerSKUAFNRestock Done");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					new BasicDataSellerSKUSalesDaily().process();
					System.out.println("BasicDataSellerSKUSalesDaily Done");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					new BasicDataOrderUnshipped().process();
					System.out.println("BasicDataOrderUnshipped Done");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					new AdvertisementAction().getBasicDataAdvertisementMegaBidResponse();
					System.out.println("BasicDataAdvertisementMegaBid Done");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					MessageBuffer
					.getInstance()
						.putMessage(new Message(
										"control.dataSynchronization.DataSynchronizationOperationsForReflection",
										"getShipmentIDDataSynchronization",
										null));
					System.out.println("GetShipmentID Done");
				} catch (Exception e) {
					e.printStackTrace();
				}
				basicDataTaskStatus = false;
			}
		}
	}
	
	// -----------------------------------------------------------------------------------------------------
	// =====================================================================================================
	// -----------------------------------------------------------------------------------------------------
	
	public static void main(String[] a){
		try {

//			PrintWriter pwLogDebug = new PrintWriter(new BufferedWriter(new FileWriter("D16.log",true)));
			
			lauchStatus = true;
			System.out.println("System Launch ...");
			Connection.getInstance().connect(serverData,userData);
			System.out.println(Connection.getInstance().isConnected() ? "OSS Connect Succeed!" : "OSS Connect Failed!");
			
			basicDataTaskTimer = new Timer();
			try {
				basicDataTaskTimer.schedule(new BasicDataTask(), new Date(System.currentTimeMillis()+5000), 86400000);
				basicDataTaskTimerStatus = true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Basic data task start time set error: system shutdown!");
			}
			
			while(true){
				try{
					int in = System.in.read();
					if(in == 's'){
						lauchStatus = false;
						System.out.println("system shutting down:");
						break;
					}
				} catch (IOException e){
					e.printStackTrace();
				}
			}
			
			if (basicDataTaskTimer != null) basicDataTaskTimer.cancel();
			basicDataTaskTimerStatus = false;
			
			while(true){
				if (!basicDataTaskStatus){
					System.out.println("Basic Data Task Closing ........................................	[OK]");
					break;
				}
				try {Thread.sleep(20000);} catch (InterruptedException e) {e.printStackTrace();}
			}
			
			try {
				Connection.getInstance().disconnectSychronization();
				System.out.println("System Shutdown!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

