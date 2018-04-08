package control.transmission;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.swing.JOptionPane;

/**
 * The thread created to handle the messages received from the server.
 * @author kerry
 *
 */
public class SocketReader extends Thread{
	private int connectErrorTimes = 0;
	private final int connectMaxErrorTimes = 3;
	private Connection connection = Connection.getInstance();
	private MessageBuffer buffer = MessageBuffer.getInstance();
	
	public SocketReader(){
	}
	
	@Override
	public void run() {
		while(connection.isConnected()){
			System.out.println("Socket Reader ...");
			Message message = null;
			try {
				message = (Message) connection.readMessage();
				if(message!=null){
					connectErrorTimes = 0;
					Class<?> cls = Class.forName(message.getClassNameCall());
					Constructor<?> con = cls.getConstructor();
					Object object = con.newInstance();
					Method m = object.getClass().getDeclaredMethod(message.getMethodNameCall(), Message.class);
					Message result = (Message) m.invoke(object, message);
					if(result!=null)	buffer.putMessage(result);
				}
			}catch (Exception e) {
				if(connectErrorTimes<connectMaxErrorTimes){
					e.printStackTrace();
				}else{
					try{
						connection.disconnect();
//						JOptionPane.showMessageDialog(null, "Connection is closed! Please reConnect!");
					}catch(Exception e1){
						e1.printStackTrace();
					}
				}
				connectErrorTimes++;
			}			
		}
		System.out.println("Socket Reader Close!");
	}
}
