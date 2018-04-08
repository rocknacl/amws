package model.login;

import java.io.Serializable;

/**
 * Define the data that is used to transmit the server information
 * @author kerry
 *
 */
public class ServerData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String address;
	private int port;
	public ServerData(String address, int port){
		this.address = address;
		this.port = port;
	}
	public String getAddress() {
		return address;
	}
	public int getPort() {
		return port;
	}
	
}
