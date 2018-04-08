package model.basic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The class is used to transmit files. 
 * @author kerry
 *
 */
public class FileData implements Serializable{

	private static final long serialVersionUID = 1L;
	private String filename;
	private transient InputStream inputStream;
	/**
	 * transmit file by object
	 * @param filename the name of file
	 * @param inputStream the input stream of the file you want to tranmit
	 * @throws FileNotFoundException 
	 */
	public FileData(String filepath) throws FileNotFoundException{
		this(new File(filepath).getName(), new FileInputStream(filepath));
	}
	public FileData(String filename, InputStream inputStream){
		this.filename = filename;
		this.inputStream = inputStream;
	}
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {  
		ois.defaultReadObject();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int ret = -1;
		while((ret = ois.read(buffer, 0, 1024)) != -1) {
			baos.write(buffer, 0, ret);
		}
		byte[] data = baos.toByteArray();
		inputStream = new ByteArrayInputStream(data);
	}
      
	private void writeObject(ObjectOutputStream oos) throws IOException {  
    	oos.defaultWriteObject();
    	byte[] buffer = new byte[1024];
    	int ret = -1;
    	while((ret = inputStream.read(buffer, 0, 1024)) != -1) {
    		oos.write(buffer, 0, ret);
    	}
    	oos.flush();
	}
	
	/**
	 * Get the filename.
	 * @return
	 */
    public String getFilename(){
    	return filename;
    }
    
    /**
     * Save the file to specific path.
     * @param filepath
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void saveFile(String filepath) throws IOException, FileNotFoundException {
    	File file = new File(filepath);
    	if(!file.getParentFile().getName().trim().isEmpty() && !file.getParentFile().exists())	file.getParentFile().mkdirs();
    	if(!file.exists())	file.createNewFile();
    	FileOutputStream fos = null;
    	fos = new FileOutputStream(filepath);
    	byte[] buffer = new byte[1024];
    	int ret = -1;
    	while((ret = inputStream.read(buffer, 0, 1024)) != -1) {
    		fos.write(buffer, 0, ret);
    	}
    	if(fos != null) {
    		fos.close();
    	}
	}
    
    /**
     * Get the input stream that is stored,
     * @return
     */
    public InputStream getInputStream(){
    	return this.inputStream;
    }
}
