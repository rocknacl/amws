package amzint;

import com.amazon.mws.recommendations.MWSRecommendationsSectionServiceAsync;
import com.amazon.mws.recommendations.MWSRecommendationsSectionServiceAsyncClient;
import com.amazon.mws.recommendations.MWSRecommendationsSectionServiceConfig;
import com.amazon.webstore.mws.cart.MWSCartService;
import com.amazon.webstore.mws.cart.MWSCartServiceAsyncClient;
import com.amazon.webstore.mws.cart.MWSCartServiceConfig;
import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.FBAInboundServiceMWSAsyncClient;
import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.FBAInboundServiceMWSConfig;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceAsyncClient;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceConfig;
import com.amazonservices.mws.products.MarketplaceWebServiceProducts;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsync;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsyncClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsConfig;

public class MarketplaceWebServiceFactory {
	private String appName = "Optimal Seller System";
	private String appVersion = "v1.05.12";
	private String defaultServiceUrl = "https://mws.amazonservices.com";

	private static MarketplaceWebServiceFactory onlyInstance;

	static {
		onlyInstance = new MarketplaceWebServiceFactory();
	}

	public MarketplaceWebServiceFactory(String appName, String appVersion, String serviceURL) {
		this.appName = appName;
		this.appVersion = appVersion;
	}

	public MarketplaceWebServiceFactory() {
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDefaultServiceUrl() {
		return defaultServiceUrl;
	}

	public static MarketplaceWebService getMarketplaceWebService(String accessKeyId, String secretAccessKey,
			String serviceUrl) {
		MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
		if (serviceUrl != null) {
			config.setServiceURL(serviceUrl);
		} else {
			config.setServiceURL(onlyInstance.defaultServiceUrl);
		}

		/************************************************************************
		 * The argument (35) set below is the number of threads client should
		 * spawn for processing.
		 ***********************************************************************/

		// config.setMaxAsyncThreads(35);
		MarketplaceWebService service = new MarketplaceWebServiceClient(accessKeyId, secretAccessKey,
				onlyInstance.appName, onlyInstance.appVersion, config);
		return service;
	}

	public static FBAInboundServiceMWSAsyncClient getFBAInboundServiceMWSAsyncClient(String accessKey, String secretKey,
			String serviceUrl) {
		FBAInboundServiceMWSConfig config = new FBAInboundServiceMWSConfig();
		if (serviceUrl != null) {
			config.setServiceURL(serviceUrl);
		} else {
			config.setServiceURL(onlyInstance.defaultServiceUrl);
		}
		FBAInboundServiceMWSAsyncClient client = new FBAInboundServiceMWSAsyncClient(accessKey, secretKey,
				onlyInstance.appName, onlyInstance.appVersion, config, null);
		return client;
	}

	public static MWSCartService getMWSCartService(String accessKeyId, String secretAccessKey, String serviceUrl) {
		MWSCartServiceConfig config = new MWSCartServiceConfig();
		if (serviceUrl != null) {
			config.setServiceURL(serviceUrl);
		} else {
			config.setServiceURL(onlyInstance.defaultServiceUrl);
		}
		MWSCartService service = new MWSCartServiceAsyncClient(accessKeyId, secretAccessKey, onlyInstance.appName,
				onlyInstance.appVersion, config);
		return service;
	}

	public static MWSRecommendationsSectionServiceAsync getMWSRecommendationsSectionService(String accessKeyId,
			String secretAccessKey, String serviceUrl) {
		MWSRecommendationsSectionServiceConfig config = new MWSRecommendationsSectionServiceConfig();
		if (serviceUrl != null) {
			config.setServiceURL(serviceUrl);
		} else {
			config.setServiceURL(onlyInstance.defaultServiceUrl);
		}
		MWSRecommendationsSectionServiceAsync service = new MWSRecommendationsSectionServiceAsyncClient(accessKeyId,
				secretAccessKey, onlyInstance.appName, onlyInstance.appVersion, config);
		return service;
	}

	public static MarketplaceWebServiceProducts getMarketplaceWebServiceProducts(String accessKeyId,
			String secretAccessKey, String serviceUrl) {
		MarketplaceWebServiceProductsConfig config = new MarketplaceWebServiceProductsConfig();
		if (serviceUrl != null) {
			config.setServiceURL(serviceUrl);
		} else {
			config.setServiceURL(onlyInstance.defaultServiceUrl);
		}
		MarketplaceWebServiceProducts service = new MarketplaceWebServiceProductsClient(accessKeyId, secretAccessKey,
				onlyInstance.appName, onlyInstance.appVersion, config);
		return service;
	}

	public static MarketplaceWebServiceProductsAsync getMarketplaceWebServiceProductsAsync(String accessKeyId,
			String secretAccessKey, String serviceUrl) {
		MarketplaceWebServiceProductsConfig config = new MarketplaceWebServiceProductsConfig();
		if (serviceUrl != null) {
			config.setServiceURL(serviceUrl);
		} else {
			config.setServiceURL(onlyInstance.defaultServiceUrl);
		}
		MarketplaceWebServiceProductsAsync service = new MarketplaceWebServiceProductsAsyncClient(accessKeyId,
				secretAccessKey, onlyInstance.appName, onlyInstance.appVersion, config);
		return service;
	}

	public static MWSMerchantFulfillmentServiceAsyncClient getMWSMerchantFulfillmentServiceAsyncClient(String accessKey,
			String secretKey, String serviceUrl) {
		MWSMerchantFulfillmentServiceConfig config = new MWSMerchantFulfillmentServiceConfig();
		if (serviceUrl != null) {
			config.setServiceURL(serviceUrl);
		} else {
		}
		MWSMerchantFulfillmentServiceAsyncClient client = new MWSMerchantFulfillmentServiceAsyncClient(accessKey,
				secretKey, onlyInstance.appName, onlyInstance.appVersion, config, null);
		return client;
	}

}
