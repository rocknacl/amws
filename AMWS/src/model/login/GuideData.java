package model.login;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Define the data that is used to transmit guide information in the left of frame
 * @author kerry
 *
 */
public class GuideData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String user;
	private ArrayList<String[]> data;
	public GuideData(String user){
		this.user = user;
	}
	public void setData(ArrayList<String[]> data){
		this.data = data;
	}
	public String getUser(){
		return user;
	}
	public ArrayList<String[]> getData(){
		return data;
	}
}
