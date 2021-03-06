package dao.entities;
// Generated 2016-8-8 16:52:31 by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * MerchantAccount generated by hbm2java
 */
public class MerchantAccount implements java.io.Serializable {

	private String id;
	private String marketPlaceId;
	private String name;
	private String accessKey;
	private String secretKey;
	private String authToken;
	private Set merchantInboundAddresses = new HashSet(0);
	private Set merchantMfnAddresses = new HashSet(0);

	public MerchantAccount() {
	}

	public MerchantAccount(String id) {
		this.id = id;
	}

	public MerchantAccount(String id, String marketPlaceId, String name, String accessKey, String secretKey,
			String authToken, Set merchantInboundAddresses, Set merchantMfnAddresses) {
		this.id = id;
		this.marketPlaceId = marketPlaceId;
		this.name = name;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.authToken = authToken;
		this.merchantInboundAddresses = merchantInboundAddresses;
		this.merchantMfnAddresses = merchantMfnAddresses;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarketPlaceId() {
		return this.marketPlaceId;
	}

	public void setMarketPlaceId(String marketPlaceId) {
		this.marketPlaceId = marketPlaceId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessKey() {
		return this.accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return this.secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getAuthToken() {
		return this.authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Set getMerchantInboundAddresses() {
		return this.merchantInboundAddresses;
	}

	public void setMerchantInboundAddresses(Set merchantInboundAddresses) {
		this.merchantInboundAddresses = merchantInboundAddresses;
	}

	public Set getMerchantMfnAddresses() {
		return this.merchantMfnAddresses;
	}

	public void setMerchantMfnAddresses(Set merchantMfnAddresses) {
		this.merchantMfnAddresses = merchantMfnAddresses;
	}

}
