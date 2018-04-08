package control.transmission;

import java.io.Serializable;

/**
 * Define the general pattern that is used to transmit messages.<br>
 * CLASS_NAME_CALL: the class that will be called.<br>
 * METHOD_NAME_CALL: the method that will be called.<br>
 * METHOD_AREA: operation condition.<br>
 * DATA: main data.
 * @author kerry
 *
 */
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String CLASS_NAME_CALL;
	private String METHOD_NAME_CALL;
	private String[] METHOD_AREA;
	private Object DATA;
	
	public Message(){
	}
	public Message(String className, String methodName){
		CLASS_NAME_CALL = className;
		METHOD_NAME_CALL = methodName;
	}
	public Message(String className, String methodName, Object data){
		CLASS_NAME_CALL = className;
		METHOD_NAME_CALL = methodName;
		DATA = data;
	}
	public String getClassNameCall() {
		return CLASS_NAME_CALL;
	}
	public String getMethodNameCall() {
		return METHOD_NAME_CALL;
	}
	public String[] getMethodArea(){
		return METHOD_AREA;
	}
	public Object getData() {
		return DATA;
	}
	public void setMethodArea(String[] methodArea){
		this.METHOD_AREA = methodArea;
	}
	public void setData(Object DATA) {
		this.DATA = DATA;
	}
	
}
