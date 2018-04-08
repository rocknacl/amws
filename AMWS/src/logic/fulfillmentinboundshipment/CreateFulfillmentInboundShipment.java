package logic.fulfillmentinboundshipment;

import logic.fulfillmentinboundshipment.client.CreateFulfillmentInboundShipmentClient;

public class CreateFulfillmentInboundShipment implements Runnable{

	private String fileDir;
	private String fileHistoryDir;
	
	CreateFulfillmentInboundShipment(
			String fileDir,
			String fileHistoryDir){
		this.fileDir = fileDir;
		this.fileHistoryDir = fileHistoryDir;
	}
	
	@Override
	public void run() {
		try {
			new CreateFulfillmentInboundShipmentClient().CreateFulfillmentInboundShipment(fileDir, fileHistoryDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
