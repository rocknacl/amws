package model.login;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Define the data that is used to transmit the authorized operations of the user
 * @author kerry
 *
 */
public class OperationsData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String version;
	private HashMap<String,String[]> operations;
	public OperationsData(String version, HashMap<String,String[]> operations){
		this.version = version;
		this.operations = operations;
	}
	public HashMap<String,String[]> getOperations() {
		return operations;
	}
	public String getVersion(){
		return version;
	}
}
