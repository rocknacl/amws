package Test.Bens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.MerchantAccountDAO;
import dao.entities.MerchantAccount;
import log.AMWSException;
import logic.product.LowestPricedOffersManager;

public class ProductLowestPricedOffersMainTest20160630 {

	public static void main(String[] args) throws AMWSException {
		// TODO Auto-generated method stub
		Map<MerchantAccount, List<String>> merchantASINMap = new HashMap<MerchantAccount, List<String>>();
		List<String> asinList = new ArrayList<String>();
		asinList.add("B01CJ60WEQ");
		asinList.add("B01ERLKR5K");
		asinList.add("B015XUGUMI");
		merchantASINMap.put(MerchantAccountDAO.getMerchantByName("UP"), asinList);
		LowestPricedOffersManager manager = new LowestPricedOffersManager(merchantASINMap,true);
		manager.run();
	}

}
