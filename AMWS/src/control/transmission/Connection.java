package control.transmission;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import model.basic.Roll;
import model.login.ServerData;
import model.login.UserData;

/** 
 * Manage the connection to oss server.
 * @author kerry
 */
public class Connection {
	
	private static final Connection connection = new Connection();
	private Connection(){
	}
	public static Connection getInstance(){
		return connection;
	}
	
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private SocketReader reader;
	private SocketWriter writer;
	private boolean isConnected = false;
	private ConnectionMaintain maintain;
	
	/**
	 * Connect to the oss server when Logging in and call the corresponding method by the message returned. 
	 * Three methods may be called: log in unsuccessfully; log in successfully and open main frame; version update.	
	 * @param server
	 * @param user
	 * @throws Exception
	 */
	public void connect(ServerData server, UserData user) throws Exception{
		socket = new Socket(server.getAddress(), server.getPort());
		output = new ObjectOutputStream(socket.getOutputStream());
		Message message = new Message();
		message.setData(user);
		writeMessage(message);
		input = new ObjectInputStream(socket.getInputStream());
		boolean available = false;
		for (int i=0; i<15; i++) {
			System.out.println(input.available() + "\t" + socket.getInputStream().available());
			if (input.available() > 0 || socket.getInputStream().available() > 0) {
				available = true;
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!available) throw new Exception("OSS Connection Time Out!");
		message = readMessage();
		Class<?> cls = Class.forName(message.getClassNameCall());
		Constructor<?> con = cls.getConstructor();
		Object object = con.newInstance();
		Method m = object.getClass().getDeclaredMethod(message.getMethodNameCall(), Message.class);
		m.invoke(object, message);
	}
	
	/**
	 * Close the socket after logging in unsuccessfully or version update. 
	 * A message will first be sent to the server to guarantee the last transmission be complete. 
	 * @throws IOException
	 */
	public void close() throws IOException{
		writeMessage(new Message());
		if(socket!=null)	socket.close();
	}
	
	/**
	 * Save the connection after logging in successfully. 
	 * Three threads will be established: one to send message; one to receive message; one to maintain connection.
	 * @throws IOException
	 */
	public void connectSucceed() throws IOException{
		isConnected = true;
		reader = new SocketReader();
		writer = new SocketWriter();
		maintain = new ConnectionMaintain();
		reader.start();
		writer.start();
		maintain.start();
	}
	
	/**
	 * Close the connection when closing the frame or connection is wrong.
	 */
	public void disconnect(){
		isConnected = false;
		if(reader!=null){
			reader.interrupt();
			reader = null;
		}
		if(writer!=null){
			writer.interrupt();
			writer = null;
		}
		if(socket!=null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			socket = null;
		}
	}
	
	/**
	 * Send message to server to set user offline and close the connection.
	 * @throws IOException
	 */
	public void disconnectSychronization() throws IOException{
		Message message = new Message(Roll.classUserOperations,Roll.methodUserOperationssetUserOffline);
		writeMessage(message);
		disconnect();
	}
	
	/**
	 * Check the connection's status
	 * @return
	 */
	public boolean isConnected(){
		return isConnected;
	}
	
	/**
	 * Read the message from server.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Message readMessage() throws ClassNotFoundException, IOException{
		return (Message) input.readObject();
	}
	
	/**
	 * Send the message to the server
	 * @param message
	 * @throws IOException
	 */
	public void writeMessage(Message message) throws IOException{
		output.writeObject(message);
		output.flush();
		output.reset();
	}
	
	/**
	 * Send heart beat packets to the server every 1 minute to maintain the connection.
	 * @author kerry
	 *
	 */
	private class ConnectionMaintain extends Thread{
		private final long period = 1*60*1000;
		public void run(){
			Timer timer = new Timer();
			timer.schedule(new TimerTask(){
				public void run() {
					Message message = new Message(Roll.classUserOperations, Roll.methodUserOperationssetUserConnectionTime);
					try {
						if(isConnected){
							System.out.println("Connection Maintain");
							MessageBuffer.getInstance().putMessage(message);
						}
						else{
							System.out.println("Connection Maintain Close");
							timer.cancel();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 30*1000, period);
		}
	}
	
}
