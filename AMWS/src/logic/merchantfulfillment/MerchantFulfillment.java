package logic.merchantfulfillment;

import logic.merchantfulfillment.client.MerchantFulfillmentClient;

public class MerchantFulfillment implements Runnable {

	private String fileDir;
	private String fileHistoryDir;
	private boolean isAsync = false;
	
	MerchantFulfillment(
			String fileDir,
			String fileHistoryDir,
			Boolean isAsync){
		this.fileDir = fileDir;
		this.fileHistoryDir = fileHistoryDir;
		this.isAsync = isAsync == null || !isAsync ? false : true;
	}
	
	public void run() {
		try {
			if (isAsync) {
				new MerchantFulfillmentClient().merchantFulfillmentAsync(fileDir, fileHistoryDir);
			} else {
				new MerchantFulfillmentClient().merchantFulfillment(fileDir, fileHistoryDir);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

}
