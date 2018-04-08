package logic.fulfillmentinboundshipment;

import logic.fulfillmentinboundshipment.client.ListInboundShipmentClient;

public class ListInboundShipment implements Runnable {

	private String fileDir;
	private String fileHistoryDir;
	
	ListInboundShipment(
			String fileDir,
			String fileHistoryDir){
		this.fileDir = fileDir;
		this.fileHistoryDir = fileHistoryDir;
	}
	
	@Override
	public void run() {
		try {
			new ListInboundShipmentClient().ListInboundShipment(fileDir, fileHistoryDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
