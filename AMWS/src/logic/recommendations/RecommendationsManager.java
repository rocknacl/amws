package logic.recommendations;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import com.amazon.mws.recommendations.model.ListRecommendationsByNextTokenResponse;
import com.amazon.mws.recommendations.model.ListRecommendationsResponse;

import amzint.recommendations.ListRecommendationsByNextTokenClient;
import amzint.recommendations.ListRecommendationsClient;
import dao.entities.MerchantAccount;
import dao.entities.RecommendationOriginalData;
import dao.recommendations.RecommendationDataConverter;
import dao.recommendations.RecommendationDataDAO;
import log.ExceptionLogger;
import log.ResponseLogger;

/**
 * set merchant list and this manager will invoke and save data to database & file
 * @author Bens
 * @version 20160616
 */
public class RecommendationsManager implements Runnable {
	private final static int saveRate = 100;
	private final static int maxQueue = 1000;
	private final static int sleepMilliSecond = 5000;
	private final static boolean fairness = true;
	private boolean isTest = false;
	private List<MerchantAccount> merchantList;

	public RecommendationsManager(List<MerchantAccount> merchantList) {
		this.merchantList = merchantList;
	}

	public RecommendationsManager(List<MerchantAccount> merchantList, Boolean isTest) {
		this.merchantList = merchantList;
		this.isTest = isTest;
	}

	public void run() {
		if (merchantList != null && !merchantList.isEmpty()) {
			try {
				Queue<ListRecommendationsClient> ClientQueue = new ArrayBlockingQueue<ListRecommendationsClient>(
						maxQueue, fairness);
				Queue<ListRecommendationsByNextTokenClient> ClientByNextTokenQueue = new ArrayBlockingQueue<ListRecommendationsByNextTokenClient>(
						maxQueue, fairness);
				// invoke all merchant the first request
				for (MerchantAccount merchant : merchantList) {
					ListRecommendationsClient client = new ListRecommendationsClient(merchant);
					client.invoke();
					ClientQueue.add(client);
				}
				// check if done and save
				Queue<RecommendationOriginalData> recommendationOriginalDataList = new LinkedBlockingQueue<RecommendationOriginalData>();
				while (true) {
					if (isTest)
						System.out.println("Running... " + new Date());
					// check normal client if has next token invoke new request
					if (!ClientQueue.isEmpty()) {
						ListRecommendationsClient client = ClientQueue.peek();
						Future<ListRecommendationsResponse> responseFuture = client.getResponseFuture();
						if (responseFuture.isDone()) {
							ListRecommendationsResponse response = responseFuture.get();
							new ResponseLogger(response,client.getMerchant());
							ClientQueue.remove(client);
							recommendationOriginalDataList.addAll(RecommendationDataConverter.resolve(response));
							if (response.isSetListRecommendationsResult()
									&& response.getListRecommendationsResult().isSetNextToken()) {
								ListRecommendationsByNextTokenClient clientByNextToken = new ListRecommendationsByNextTokenClient(
										client.getMerchant(), response.getListRecommendationsResult().getNextToken());
								clientByNextToken.invoke();
								ClientByNextTokenQueue.add(clientByNextToken);
							}
						}
					}
					// check next token client if has next token invoke new
					// request
					if (!ClientByNextTokenQueue.isEmpty()) {
						ListRecommendationsByNextTokenClient clientByNextToken = ClientByNextTokenQueue.peek();
						Future<ListRecommendationsByNextTokenResponse> responseFuture = clientByNextToken
								.getResponseFuture();
						if (responseFuture.isDone()) {
							ListRecommendationsByNextTokenResponse response = responseFuture.get();
							new ResponseLogger(response,clientByNextToken.getMerchant());
							ClientByNextTokenQueue.remove(clientByNextToken);
							recommendationOriginalDataList.addAll(RecommendationDataConverter.resolve(response));
							if (response.isSetListRecommendationsByNextTokenResult()
									&& response.getListRecommendationsByNextTokenResult().isSetNextToken()) {
								ListRecommendationsByNextTokenClient clientByNextToken2 = new ListRecommendationsByNextTokenClient(
										clientByNextToken.getMerchant(),
										response.getListRecommendationsByNextTokenResult().getNextToken());
								clientByNextToken2.invoke();
								ClientByNextTokenQueue.add(clientByNextToken2);
							}
						}
					}
					// save data by save Rate
					if (recommendationOriginalDataList.size() > saveRate) {
						if (isTest)
							System.out.println("Saving... " + new Date());
						RecommendationDataDAO.save(recommendationOriginalDataList);
						recommendationOriginalDataList.clear();
					}
					// shut down
					if (ClientQueue.isEmpty() && ClientByNextTokenQueue.isEmpty()) {
						if (!recommendationOriginalDataList.isEmpty()) {
							System.out.println("Saving... " + new Date());
							RecommendationDataDAO.save(recommendationOriginalDataList);
						}
						if (isTest)
							System.out.println("Done.");
						break;
					}
					Thread.sleep(sleepMilliSecond);
				}
			} catch (Exception e) {
				if(isTest)e.printStackTrace();
				new ExceptionLogger(e,null);
			}
		}
	}

}
