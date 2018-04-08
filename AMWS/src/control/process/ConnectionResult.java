package control.process;

import java.io.IOException;

import control.transmission.Connection;
import control.transmission.Message;

/** 
 * Manage the connection to oss server.
 * @author kerry
 */
public class ConnectionResult {
	
	private final Connection connection = Connection.getInstance();
	
	public Message connectSucceed(Message message){
		try {
			connection.connectSucceed();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Message connectFail(Message message){
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
