package control.transmission;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * The thread created to receive new client from the server where updating version.
 * @author szwfh
 *
 */
public class VersionUpgrade extends Thread{
	private double progress = 0.0;
	private int result = 0;//Undone:0; Succeed:1; Fail:-1
	private String filepath;
	private Socket socket;
	private String address;
	public VersionUpgrade(String filepath, String address){
		this.filepath = filepath;
		this.address = address;
	}
	public double getUpgradeProgress(){
		return progress;
	}
	public int getUpgradeResult(){
		return result;
	}
	public void run(){
		try{
			socket = new Socket(address, 4243);
			DataInputStream reader = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			int fileLength = reader.readInt();
			File f = new File(filepath);
			if(!f.getParentFile().exists())	f.getParentFile().mkdirs();
			if(!f.exists())	f.createNewFile();
			DataOutputStream fw = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			int bufferSize = 1024;
	        byte[] buf = new byte[bufferSize];
	        int totalLength = 0;
	        while(fw!=null) {
	        	int read = 0;
	            if(totalLength>=(fileLength-bufferSize) && totalLength<=fileLength){
	            	byte[] bs = new byte[fileLength-totalLength];
	            	read = reader.read(bs);
	            	if(read==-1)	break;
	            	fw.write(bs, 0, read);
	            	fw.flush();
	        		totalLength = totalLength + read;
	        		progress = 1.0*totalLength/fileLength;
	            	if(totalLength==fileLength)	break;
	        	}
	        	else{
	        		read = reader.read(buf);
	        		if(read==-1)	break;
	            	fw.write(buf, 0, read);
	            	fw.flush();
	        		totalLength = totalLength + read;
	        		progress = 1.0*totalLength/fileLength;
	        	}
	        }
	        fw.close();
	        DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	        writer.writeUTF("Succeed");
	        writer.flush();
	        socket.close();
	        result = 1;
		}catch(Exception e){
			e.printStackTrace();
			result = -1;
			try{
				if(socket!=null)	socket.close();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
	}
}
