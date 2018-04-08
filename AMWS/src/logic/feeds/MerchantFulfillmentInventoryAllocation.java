package logic.feeds;

import logic.feeds.client.MerchantFulfillmentInventoryAllocationClient;

/**
 * @author D16
 * @Description merchant fulfillment inventory allocation
 * @Create_Date JUN 17, 2016
 * @Last_Update JUN 17, 2016
 */

public class MerchantFulfillmentInventoryAllocation implements Runnable {

	private String feedFileDir;
	private String feedFileHistoryDir;
	private String ossFileDir;
	private double equalAllocatedProportion;
	private int deltaDays = -7;
	
	public MerchantFulfillmentInventoryAllocation(
			String feedFileDir,
			String feedFileHistoryDir,
			String ossFileDir,
			double equalAllocatedProportion){
		this.feedFileDir = feedFileDir;
		this.feedFileHistoryDir = feedFileHistoryDir;
		this.ossFileDir = ossFileDir;
		this.equalAllocatedProportion = equalAllocatedProportion;
	}
	
	public void run() {
		try {
			new MerchantFulfillmentInventoryAllocationClient()
				.merchantFulfillmentInventoryAllocation(
						feedFileDir,
						feedFileHistoryDir, 
						ossFileDir,
						equalAllocatedProportion,
						deltaDays);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
