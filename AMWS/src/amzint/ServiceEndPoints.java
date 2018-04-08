package amzint;

public enum ServiceEndPoints {
	NA("https://mws.amazonservices.com"), EU("https://mws-eu.amazonservices.com"), JP(
			"https://mws.amazonservices.jp"), CN(
					"https://mws.amazonservices.com.cn"), IN("https://mws.amazonservices.in");
	String serviceUrl;

	ServiceEndPoints(String s) {
		serviceUrl = s;
	}
	//NA
	final static String CAm = "A2EUQ1WTGCTBG2";
	final static String MXm = "A1AM78C64UM0Y8";
	final static String USm = "ATVPDKIKX0DER";
	//EU
	final static String DEm = "A1PA6795UKMFR9";
	final static String ESm = "A1RKKUPIHCS9HS";
	final static String FRm = "A13V1IB3VIYZZH";
	final static String ITm = "APJ6JRA9NG5V4";
	final static String UKm = "A1F83G8C2ARO7P";
	//IN
	final static String INm = "A21TJRUUN4KGV";
	//JP
	final static String JPm = "A1VC38T7YXB528";
	//CN
	final static String CNm = "AAHKV2X7AFYLW";
	public static String getServiceUtlByMarketplaceID(String marketplaceID){
		if(marketplaceID.equals(CAm)||marketplaceID.equals(MXm)||marketplaceID.equals(USm)){
			return NA.serviceUrl;
		}
		if(marketplaceID.equals(DEm)||marketplaceID.equals(ESm)||marketplaceID.equals(FRm)||marketplaceID.equals(ITm)||marketplaceID.equals(UKm)){
			return EU.serviceUrl;
		}
		if(marketplaceID.equals(INm)){
			return IN.serviceUrl;
		}
		if(marketplaceID.equals(JPm)){
			return JP.serviceUrl;
		}
		if(marketplaceID.equals(CNm)){
			return CN.serviceUrl;
		}
		return NA.serviceUrl;
	}
}
