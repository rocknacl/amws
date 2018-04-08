package logic.product;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;

import com.amazonservices.mws.products.model.GetCompetitivePricingForASINResponse;
import com.amazonservices.mws.products.model.GetProductCategoriesForASINResponse;

import amzint.product.GetCompetitivePricingForASINAsyncClient;
import amzint.product.GetProductCategoriesForASINAsyncClient;
import dao.entities.MerchantAccount;
import dao.product.ProductDataDAO;
import log.AMWSException;
import log.ExceptionLogger;
import log.ResponseLogger;

public class CompetitivePricingManager implements Runnable {
	private final static int saveRate = 10;
	private final static int maxQueue = 1000;
	private final static boolean fairness = true;
	private final static int sleepMilliSecond = 5000;
	private boolean isTest = false;
	private Map<MerchantAccount, List<String>> merchantASINMap = new HashMap<MerchantAccount, List<String>>();;

	public CompetitivePricingManager(Map<MerchantAccount, List<String>> merchantASINMap) throws AMWSException {
		this.merchantASINMap.putAll(merchantASINMap);
		check();
	}

	public CompetitivePricingManager(Map<MerchantAccount, List<String>> merchantASINMap, Boolean isTest)
			throws AMWSException {
		this.merchantASINMap.putAll(merchantASINMap);
		this.isTest = isTest;
		check();
	}

	public void run() {
		try {
			if (isTest)
				System.out.println("CompetitivePricingManager Start... at " + new Date());
			Queue<GetCompetitivePricingForASINAsyncClient> clientQueue = new ArrayBlockingQueue<GetCompetitivePricingForASINAsyncClient>(
					maxQueue, fairness);
			Queue<GetProductCategoriesForASINAsyncClient> categoryClientQueue = new ArrayBlockingQueue<GetProductCategoriesForASINAsyncClient>(
					maxQueue, fairness);
			// invoke competitive pricing request
			for (Entry<MerchantAccount, List<String>> entry : merchantASINMap.entrySet()) {
				try {
					GetCompetitivePricingForASINAsyncClient client = new GetCompetitivePricingForASINAsyncClient(
							entry.getKey(), entry.getValue());
					client.invokeAll();
					clientQueue.add(client);
				} catch (Exception e) {
					if (isTest)
						e.printStackTrace();
					new ExceptionLogger(e, entry.getKey());
				}
			}
			// invoke category query request
			for (Entry<MerchantAccount, List<String>> entry : merchantASINMap.entrySet()) {
				try {
					GetProductCategoriesForASINAsyncClient client = new GetProductCategoriesForASINAsyncClient(
							entry.getKey(), entry.getValue());
					client.invokeAll();
					categoryClientQueue.add(client);
				} catch (Exception e) {
					if (isTest)
						e.printStackTrace();
					new ExceptionLogger(e, entry.getKey());
				}
			}
			Map<List<String>, Future<GetCompetitivePricingForASINResponse>> finishedResponse = new HashMap<List<String>, Future<GetCompetitivePricingForASINResponse>>();
			Map<String, Future<GetProductCategoriesForASINResponse>> finishedCategoryResponse = new HashMap<String, Future<GetProductCategoriesForASINResponse>>();
			// check queue
			while (true) {
				if (isTest)
					System.out.println("CompetitivePricingManager Running... at " + new Date());
				// check competitive pricing client queue
				if (!clientQueue.isEmpty()) {
					GetCompetitivePricingForASINAsyncClient client = clientQueue.peek();
					for (Entry<List<String>, Future<GetCompetitivePricingForASINResponse>> entry : client
							.getResponseList().entrySet()) {
						if (entry.getValue().isDone()) {
							finishedResponse.put(entry.getKey(), entry.getValue());
							new ResponseLogger(entry.getValue().get(), client.getMerchant());
						}
					}
					for(Entry<List<String>, Future<GetCompetitivePricingForASINResponse>> entry : finishedResponse.entrySet()){
						if(client.getResponseList().containsKey(entry.getKey())){
							client.getResponseList().remove(entry.getKey());
						}
					}
					if (client.getResponseList().isEmpty())
						clientQueue.poll();
				}
				// check category query client queue
				if (!categoryClientQueue.isEmpty()) {
					GetProductCategoriesForASINAsyncClient client = categoryClientQueue.peek();
					for (Entry<String, Future<GetProductCategoriesForASINResponse>> entry : client.getResponseList()
							.entrySet()) {
						if (entry.getValue().isDone()) {
							finishedCategoryResponse.put(entry.getKey(), entry.getValue());
							new ResponseLogger(entry.getValue().get(), client.getMerchant());
						}
					}
					for(Entry<String, Future<GetProductCategoriesForASINResponse>> entry : finishedCategoryResponse.entrySet()){
						if(client.getResponseList().containsKey(entry.getKey())){
							client.getResponseList().remove(entry.getKey());
						}
					}
					if (client.getResponseList().isEmpty())
						categoryClientQueue.poll();
				}
				// save data according save rate
				if (finishedResponse.size() > saveRate) {
					if (isTest)
						System.out
								.println("CompetitivePricingManager GetCompetitivePricingForASINResponse Saving... at "
										+ new Date());
					ProductDataDAO.saveCompetitivePricingResponse(finishedResponse);
					finishedResponse.clear();
				}
				if (finishedCategoryResponse.size() > saveRate) {
					if (isTest)
						System.out.println("CompetitivePricingManager GetProductCategoriesForASINResponse Saving... at "
								+ new Date());
					ProductDataDAO.saveCategoriesResponse(finishedCategoryResponse);
					finishedCategoryResponse.clear();
				}
				// shut down
				if (clientQueue.isEmpty() && categoryClientQueue.isEmpty()) {
					if (!finishedResponse.isEmpty()) {
						if (isTest)
							System.out.println(
									"CompetitivePricingManager GetCompetitivePricingForASINResponse Saving... at "
											+ new Date());
						ProductDataDAO.saveCompetitivePricingResponse(finishedResponse);
						finishedResponse.clear();
					}
					if (!finishedCategoryResponse.isEmpty()) {
						if (isTest)
							System.out.println(
									"CompetitivePricingManager GetProductCategoriesForASINResponse Saving... at "
											+ new Date());
						ProductDataDAO.saveCategoriesResponse(finishedCategoryResponse);
						finishedCategoryResponse.clear();
					}
					break;
				}
				Thread.sleep(sleepMilliSecond);
			}
			if (isTest)
				System.out.println("CompetitivePricingManager Done... at " + new Date());
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
