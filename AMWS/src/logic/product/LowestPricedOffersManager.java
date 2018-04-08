package logic.product;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;

import com.amazonservices.mws.products.model.GetLowestPricedOffersForASINResponse;
import amzint.product.GetLowestPricedOffersForASINAsyncClient;
import dao.entities.MerchantAccount;
import dao.product.ProductDataDAO;
import log.AMWSException;
import log.ExceptionLogger;
import log.ResponseLogger;

public class LowestPricedOffersManager implements Runnable {
	private final static int saveRate = 10;
	private final static int maxQueue = 1000;
	private final static boolean fairness = true;
	private final static int sleepMilliSecond = 5000;
	private boolean isTest = false;
	private Map<MerchantAccount, List<String>> merchantASINMap = new HashMap<MerchantAccount, List<String>>();;

	public LowestPricedOffersManager(Map<MerchantAccount, List<String>> merchantASINMap) throws AMWSException {
		this.merchantASINMap.putAll(merchantASINMap);
		check();
	}

	public LowestPricedOffersManager(Map<MerchantAccount, List<String>> merchantASINMap, Boolean isTest)
			throws AMWSException {
		this.merchantASINMap.putAll(merchantASINMap);
		this.isTest = isTest;
		check();
	}

	public void run() {
		try {
			if (isTest)
				System.out.println("LowestPricedOffersManager Start... at " + new Date());
			Queue<GetLowestPricedOffersForASINAsyncClient> clientQueue = new ArrayBlockingQueue<GetLowestPricedOffersForASINAsyncClient>(
					maxQueue, fairness);
			// invoke competitive pricing request
			for (Entry<MerchantAccount, List<String>> entry : merchantASINMap.entrySet()) {
				try {
					GetLowestPricedOffersForASINAsyncClient client = new GetLowestPricedOffersForASINAsyncClient(
							entry.getKey(), entry.getValue());
					client.invokeAll();
					clientQueue.add(client);
				} catch (Exception e) {
					if (isTest)
						e.printStackTrace();
					new ExceptionLogger(e, entry.getKey());
				}
			}
			Map<String, Future<GetLowestPricedOffersForASINResponse>> finishedResponse = new HashMap<String, Future<GetLowestPricedOffersForASINResponse>>();
			// check queue
			while (true) {
				if (isTest)
					System.out.println("LowestPricedOffersManager Running... at " + new Date());
				// check competitive pricing client queue
				if (!clientQueue.isEmpty()) {
					GetLowestPricedOffersForASINAsyncClient client = clientQueue.peek();
					for (Entry<String, Future<GetLowestPricedOffersForASINResponse>> entry : client
							.getResponseList().entrySet()) {
						if (entry.getValue().isDone()) {
							finishedResponse.put(entry.getKey(), entry.getValue());
							new ResponseLogger(entry.getValue().get(), client.getMerchant());
						}
					}
					for(Entry<String, Future<GetLowestPricedOffersForASINResponse>> entry : finishedResponse.entrySet()){
						if(client.getResponseList().containsKey(entry.getKey())){
							client.getResponseList().remove(entry.getKey());
						}
					}
					if (client.getResponseList().isEmpty())
						clientQueue.poll();
				}
				// save data according save rate
				if (finishedResponse.size() > saveRate) {
					if (isTest)
						System.out
								.println("LowestPricedOffersManager GetCompetitivePricingForASINResponse Saving... at "
										+ new Date());
					ProductDataDAO.saveLowestPricedOffersResponse(finishedResponse);
					finishedResponse.clear();
				}

				// shut down
				if (clientQueue.isEmpty()) {
					if (!finishedResponse.isEmpty()) {
						if (isTest)
							System.out.println(
									"LowestPricedOffersManager GetCompetitivePricingForASINResponse Saving... at "
											+ new Date());
						ProductDataDAO.saveLowestPricedOffersResponse(finishedResponse);
						finishedResponse.clear();
					}
					break;
				}
				Thread.sleep(sleepMilliSecond);
			}
			if (isTest)
				System.out.println("LowestPricedOffersManager Done... at " + new Date());
		} catch (Exception e) {
			if (isTest)
				e.printStackTrace();
			new ExceptionLogger(e, null);
		}
	}

	private void check() throws AMWSException {
		if (merchantASINMap == null || merchantASINMap.isEmpty())
			throw new AMWSException("Empty Merchant List");
		int asinCount = 0;
		for (Entry<MerchantAccount, List<String>> entry : merchantASINMap.entrySet()) {
			if (entry.getKey() == null)
				throw new AMWSException("Invalid Merchant Account.");
			if (entry.getValue() == null || entry.getValue().isEmpty())
				throw new AMWSException("Empty ASIN List for Merchant: " + entry.getKey().getName());
			asinCount += entry.getValue().size();
		}
		if (asinCount > maxQueue)
			throw new AMWSException("Too much ASIN for all sellers. Max ASIN Quantity: " + maxQueue);
	}

}
