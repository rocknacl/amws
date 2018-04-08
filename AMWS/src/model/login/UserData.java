package model.login;

import java.io.Serializable;

/**
 * Define the data that is used to transmit user information
 * @author kerry
 *
 */
public class UserData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String version;
	public UserData(String username, String password, String version){
		this.username = username;
		this.password = password;
		this.version = version;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getVersion(){
		return version;
	}
}
