package logic.fulfillmentinboundshipment;

import logic.fulfillmentinboundshipment.client.ListInboundShipmentItemClient;

public class ListInboundShipmentItem implements Runnable {

	private String fileDir;
	private String fileHistoryDir;
	
	ListInboundShipmentItem(
			String fileDir,
			String fileHistoryDir){
		this.fileDir = fileDir;
		this.fileHistoryDir = fileHistoryDir;
	}
	
	@Override
	public void run() {
		try {
			new ListInboundShipmentItemClient().ListInboundShipmentItem(fileDir, fileHistoryDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
