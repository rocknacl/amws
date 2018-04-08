import java.text.SimpleDateFormat;
import java.util.Date;

import control.process.AdvertisementAction;
import control.transmission.Connection;
import model.login.ServerData;
import model.login.UserData;

public class LaunchLocalTest {

	private static ServerData serverData = new ServerData("112.74.206.141", 9527); //112.74.206.141
	private static UserData userData = new UserData("OSSSync","OSSSync",null);
	
	public static void main(String[] asd){
		try {
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
			System.out.println("System Launch ...");
			Connection.getInstance().connect(serverData,userData);
			System.out.println(Connection.getInstance().isConnected() ? "OSS Connect Succeed!" : "OSS Connect Failed!");
//			new AdvertisementAction().getBasicDataAdvertisementMegaBidResponse();
//			new AdvertisementAction().callOSSSendAdvertisementAction();
			Connection.getInstance().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
