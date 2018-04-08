package dao;

import java.util.ArrayList;
import java.util.List;

import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import dao.entities.MerchantAccount;
import dao.entities.MerchantInboundAddress;
import dao.entities.MerchantMfnAddress;
import helper.dao.HibernateUtils;

public class MerchantAccountDAO {
	public static MerchantAccount getMerchantByName(String merchantName) {
		Session session = HibernateUtils.getSession();
		String hql = "from MerchantAccount where name =:name1";
		Query query = session.createQuery(hql);
		query.setString("name1", merchantName);
		@SuppressWarnings("unchecked")
		List<MerchantAccount> merchantList = query.list();
		MerchantAccount result = null;
		if (merchantList.size() > 0) {
			result = merchantList.get(0);
		}
		session.close();
		return result;
	}

	public static MerchantInboundAddress getInBoundAddressByMerchantName(String merchantName) {
		Session session = HibernateUtils.getSession();
		String hql = "from MerchantAccount where name =:name";
		Query query = session.createQuery(hql);
		query.setString("name", merchantName);
		@SuppressWarnings("unchecked")
		List<MerchantAccount> merchantList = query.list();
		MerchantAccount merchant = null;
		if (merchantList.size() > 0) {
			merchant = merchantList.get(0);
		}
		@SuppressWarnings("unchecked")
		Set<MerchantInboundAddress> addresses = merchant.getMerchantInboundAddresses();
		MerchantInboundAddress result;
		if (addresses.size() > 0) {
			result = addresses.toArray(new MerchantInboundAddress[addresses.size()])[0];
		} else {
			result = null;
		}
		session.close();
		return result;

	}

	public static MerchantMfnAddress getMfnAddressByMerchantName(String merchantName) {
		Session session = HibernateUtils.getSession();
		String hql = "From MerchantAccount where name =:name";
		Query query = session.createQuery(hql);
		query.setString("name", merchantName);
		@SuppressWarnings("unchecked")
		List<MerchantAccount> merchantList = query.list();
		MerchantAccount merchant = null;
		if (merchantList.size() > 0) {
			merchant = merchantList.get(0);
		}
		@SuppressWarnings("unchecked")
		Set<MerchantMfnAddress> addresses = merchant.getMerchantMfnAddresses();
		MerchantMfnAddress result;
		if (addresses.size() > 0) {
			result = addresses.toArray(new MerchantMfnAddress[addresses.size()])[0];
		} else {
			result = null;
		}
		session.close();
		return result;

	}
	public static List<MerchantAccount> getAllMerchantAccount(){
		Session session = HibernateUtils.getSession();
		String hql = "From MerchantAccount where enabled = 1";
		Query query = session.createQuery(hql);
		List<MerchantAccount> merchantList = query.list();
		session.close();
		return merchantList;
	}
	public static List<MerchantAccount> getTestMerchantAccount(){
	    ArrayList<MerchantAccount> l = new ArrayList<MerchantAccount>();
		l.add(getMerchantByName("AA"));
		return l;
	}

	public static void main(String[] args) {

		MerchantAccount account = MerchantAccountDAO.getMerchantByName("XGL");
		System.out.println(account.getName());
		System.out.println(account.getId());
	}

}
