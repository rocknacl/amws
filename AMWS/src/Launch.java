
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import control.process.*;
import control.report.ReportTaskGenerateTimerTask;
import control.report.ReportTaskProcessTimerTask;
import control.transmission.Connection;
import control.transmission.Message;
import control.transmission.MessageBuffer;
import model.login.ServerData;
import model.login.UserData;

public class Launch {
	
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
	
	private static Timer merchantFulfillmentInventoryAllocationTaskTimer;
	private static boolean merchantFulfillmentInventoryAllocationTaskStatus = false;
	private static boolean merchantFulfillmentInventoryAllocationTaskTimerStatus = false;
	private static class MerchantFulfillmentInventoryAllocationTask extends TimerTask{
		public void run(){
			if (lauchStatus && merchantFulfillmentInventoryAllocationTaskTimerStatus) {
				merchantFulfillmentInventoryAllocationTaskStatus = true;
				try {
					System.out.println("Merchant Fulfillment Inventory Allocation Task Start ...");
					MessageBuffer
					.getInstance()
						.putMessage(new Message(
										"control.dataSynchronization.DataSynchronizationOperationsForReflection",
										"getInventorySynchronizationInformation",
										null));
				} catch ( Exception e) {
					e.printStackTrace();
				}
				merchantFulfillmentInventoryAllocationTaskStatus = false;
			}
		}
	}
	
	private static Timer ossConnectionMaintainTaskTimer;
	private static boolean ossConnectionMaintainTaskStatus = false;
	private static boolean ossConnectionMaintainTaskTimerStatus = false;
	private static class OSSConnectionMaintainTask extends TimerTask{
		public void run(){
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " OSS Connection Maintain Task:");
			if (lauchStatus && ossConnectionMaintainTaskTimerStatus) {
				ossConnectionMaintainTaskStatus = true;
				// test connection, if failed, reconnect
				try {
					System.out.println("OSS Connection Check ...");
					if (!Connection.getInstance().isConnected())
						Connection.getInstance().connect(serverData,userData);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ossConnectionMaintainTaskStatus = false;
			}
		}
	}
	
	private static Timer merchantFulfillmentTaskTimer;
	private static boolean merchantFulfillmentTaskStatus = false;
	private static boolean merchantFulfillmentTaskTimerStatus = false;
	private static class merchantFulfillmentTask extends TimerTask{
		public void run(){
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " Merchant Fulfillment Task:");
			if (lauchStatus && merchantFulfillmentTaskTimerStatus) {
				merchantFulfillmentTaskStatus = true;
				try {
					System.out.println("merchant fulfillment task start: message(oss server/get orders to print)");
					MessageBuffer
						.getInstance()
							.putMessage(new Message(
											"control.dataSynchronization.DataSynchronizationOperationsForReflection",
											"getOrdersToPrint",
											null));
				} catch (Exception e) {
					e.printStackTrace();;
				}
				merchantFulfillmentTaskStatus = false;
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
				Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd '08:30:00'").format(new Date()));
				basicDataTaskTimer.schedule(new BasicDataTask(),
						System.currentTimeMillis() > startTime.getTime() ? new Date(startTime.getTime() + 24*60*60*1000) : startTime, 86400000);
				basicDataTaskTimerStatus = true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Basic data task start time set error: system shutdown!");
			}
			
			merchantFulfillmentInventoryAllocationTaskTimer = new Timer();
			try {
				Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd '06:00:00'").format(new Date()));
				merchantFulfillmentInventoryAllocationTaskTimer.schedule(new MerchantFulfillmentInventoryAllocationTask(),
						System.currentTimeMillis() > startTime.getTime() ? new Date(startTime.getTime() + 24*60*60*1000) : startTime, 86400000);
				merchantFulfillmentInventoryAllocationTaskTimerStatus = true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Amazon report download task start time set error: system shutdown!");
			}
			
			ossConnectionMaintainTaskTimer = new Timer();
			try {
				ossConnectionMaintainTaskTimer.schedule(new OSSConnectionMaintainTask(),new Date(System.currentTimeMillis()+11000), 300000);
				ossConnectionMaintainTaskTimerStatus = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			merchantFulfillmentTaskTimer = new Timer();
			try {
				merchantFulfillmentTaskTimer.schedule(new merchantFulfillmentTask(), new Date(System.currentTimeMillis()+10000), 600000);
				merchantFulfillmentTaskTimerStatus = true;
			} catch (Exception e) {
				e.printStackTrace();
			}

			Timer timer1 = new Timer();
			try {
				Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd '06:30:00'").format(new Date()));
				timer1.schedule(new ReportTaskGenerateTimerTask(),
						System.currentTimeMillis() > startTime.getTime() ? new Date(startTime.getTime() + 24*60*60*1000) : startTime, 86400000);
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
			if (merchantFulfillmentInventoryAllocationTaskTimer != null) merchantFulfillmentInventoryAllocationTaskTimer.cancel();
			merchantFulfillmentInventoryAllocationTaskTimerStatus = false;
			if (ossConnectionMaintainTaskTimer != null) ossConnectionMaintainTaskTimer.cancel();
			ossConnectionMaintainTaskTimerStatus = false;
			if (merchantFulfillmentTaskTimer != null) merchantFulfillmentTaskTimer.cancel();
			merchantFulfillmentTaskTimerStatus = false;
			if (timer1 != null) timer1.cancel();
			if (timer2 != null) timer2.cancel();
			
			while(true){
				if (!basicDataTaskStatus)
					System.out.println("Basic Data Task Closing ........................................	[OK]");
				if (!merchantFulfillmentInventoryAllocationTaskStatus)
					System.out.println("Merchant Fulfillment Inventory Allocation Task Closing .........	[OK]");
				if (!ossConnectionMaintainTaskStatus)
					System.out.println("OSS Connection Maintain Task Closing ...........................	[OK]");
				if (!merchantFulfillmentTaskStatus)
					System.out.println("Merchant Fulfillment Task Closing ..............................	[OK]");
				if (!basicDataTaskStatus
						&& !merchantFulfillmentInventoryAllocationTaskStatus
						&& !ossConnectionMaintainTaskStatus
						&& !merchantFulfillmentTaskStatus)
					break;
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
