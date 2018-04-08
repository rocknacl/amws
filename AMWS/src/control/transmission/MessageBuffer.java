package control.transmission;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Create a queue used to save and take messages.
 * @author kerry
 *
 */
public class MessageBuffer {
	private final int capacity = 100;
	private ArrayBlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(capacity);
	private final static MessageBuffer buffer = new MessageBuffer();
	private MessageBuffer(){
		
	}
	public static MessageBuffer getInstance(){
		return buffer;
	}
	public Message getMessage() throws InterruptedException{
		return queue.take();
	}
	public void putMessage(Message message) throws InterruptedException{
		queue.put(message);
	}
}
