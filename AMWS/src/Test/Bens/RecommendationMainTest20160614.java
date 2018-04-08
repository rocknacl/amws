package Test.Bens;

import java.util.ArrayList;
import java.util.List;


import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import logic.recommendations.RecommendationsManager;

public class RecommendationMainTest20160614 {
	
	/**
	 * system out "Running... + new Date()" every 5 second, when done, system out "Done."
	 * @author Bens
	 * @param args
	 */
	public static void main(String[] args){
		List<MerchantAccount> merchantList = new ArrayList<MerchantAccount>();
		MerchantAccount merchant1 = MerchantAccountDAO.getMerchantByName("PW");
		merchantList.add(merchant1);
		RecommendationsManager manager = new RecommendationsManager(merchantList,true);
		manager.run();
	}
}
