package dao.recommendations;

import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.entities.RecommendationOriginalData;
import helper.dao.HibernateUtils;

/**
 * save hibernate data to database
 * 
 * @author Bens
 * @version 20160614
 */
public class RecommendationDataDAO {

	/**
	 * save List<RecommendationOriginalData> to database
	 * @author Bens
	 * @param recommendationOriginalDataList
	 * @throws Exception if no data in recommendationOriginalDataList
	 */
	public static void save(Collection<RecommendationOriginalData> recommendationOriginalDataList) throws Exception {
		if (recommendationOriginalDataList != null && !recommendationOriginalDataList.isEmpty()) {
			Session session = HibernateUtils.getSession();
			Transaction transaction = session.beginTransaction();
			for(RecommendationOriginalData recommendationOriginalData : recommendationOriginalDataList){
			RecommendationOriginalData query = (RecommendationOriginalData) session.get(RecommendationOriginalData.class, recommendationOriginalData.getRecommendationId());
			if(query==null){
				session.save(recommendationOriginalData);
			} else {
				BeanUtils.copyProperties(query, recommendationOriginalData);
			}
			}
			transaction.commit();
			HibernateUtils.closeSession(session);
		} else {
			throw new Exception("No data in recommendationOriginalDataList.");
		}
	}
}
