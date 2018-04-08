package control.transmission;

import javax.swing.JOptionPane;

/**
 * The thread created to send messages to the server.
 * @author kerry
 *
 */
public class SocketWriter extends Thread{

	private int connectErrorTimes = 0;
	private final int connectMaxErrorTimes = 3;
	private Connection connection = Connection.getInstance();
	private MessageBuffer buffer = MessageBuffer.getInstance();
	
	public SocketWriter(){
	}
	
	@Override
	public void run() {
		while(connection.isConnected()){
			System.out.println("Socket Writer ...");
			Message message = null;
			try {
				message = buffer.getMessage();
				if(message!=null){
					connection.writeMessage(message);
					connectErrorTimes = 0;
				}
			}catch (Exception e) {
				try {
					if (message != null) buffer.putMessage(message);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				e.printStackTrace();
				
				if(connectErrorTimes>=connectMaxErrorTimes){
					try{
						connection.disconnect();
					}catch(Exception e1){
						e1.printStackTrace();
					}
				}
				connectErrorTimes++;
			}
		}
		System.out.println("Socket Writer Close!");
	}

}
