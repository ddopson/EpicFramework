package com.epic.framework.util;

import org.apache.http.client.HttpClient;

import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.epic.framework.Ui.EpicApplication;
import com.epic.framework.Ui.EpicPlatform;
import com.epic.framework.util.billing.EpicBillingResponseHandler;
import com.epic.framework.util.billing.EpicInAppService;
import com.epic.framework.util.billing.WordsPurchaseObserver;
import com.realcasualgames.words.EpicFeaturedAppCallback;
import com.realcasualgames.words.WordsHttp;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyFeaturedAppNotifier;
import com.tapjoy.TapjoyFeaturedAppObject;

public class EpicMarketplaceImplementation {
	public static final boolean DEBUG = true;

	// The response codes for a request, defined by Android Market.
	public enum ResponseCode {
		RESULT_OK,
		RESULT_USER_CANCELED,
		RESULT_SERVICE_UNAVAILABLE,
		RESULT_BILLING_UNAVAILABLE,
		RESULT_ITEM_UNAVAILABLE,
		RESULT_DEVELOPER_ERROR,
		RESULT_ERROR;


		public static ResponseCode valueOf(int index) {
			if (index < 0 || index >= values().length) {
				return RESULT_ERROR;
			}
			return values()[index];
		}
	}

	// The possible states of an in-app purchase, as defined by Android Market.
	public enum PurchaseState {
		// Responses to requestPurchase or restoreTransactions.
		PURCHASED,   // User was charged for the order.
		CANCELED,    // The charge failed on the server.
		REFUNDED;    // User received a refund for the order.

		// Converts from an ordinal value to the PurchaseState
		public static PurchaseState valueOf(int index) {
			if (index < 0 || index >= values().length) {
				return CANCELED;
			}
			return values()[index];
		}
	}

	/** This is the action we use to bind to the MarketBillingService. */
	public static final String MARKET_BILLING_SERVICE_ACTION = "com.android.vending.billing.MarketBillingService.BIND";

	// Intent actions that we send from the BillingReceiver to the
	// BillingService.  Defined by this application.
	public static final String ACTION_CONFIRM_NOTIFICATION     = "com.example.dungeons.CONFIRM_NOTIFICATION";
	public static final String ACTION_GET_PURCHASE_INFORMATION = "com.example.dungeons.GET_PURCHASE_INFORMATION";
	public static final String ACTION_RESTORE_TRANSACTIONS     = "com.example.dungeons.RESTORE_TRANSACTIONS";

	// Intent actions that we receive in the BillingReceiver from Market.
	// These are defined by Market and cannot be changed.
	public static final String ACTION_NOTIFY                 = "com.android.vending.billing.IN_APP_NOTIFY";
	public static final String ACTION_RESPONSE_CODE          = "com.android.vending.billing.RESPONSE_CODE";
	public static final String ACTION_PURCHASE_STATE_CHANGED = "com.android.vending.billing.PURCHASE_STATE_CHANGED";

	// These are the names of the extras that are passed in an intent from
	// Market to this application and cannot be changed.
	public static final String NOTIFICATION_ID     = "notification_id";
	public static final String INAPP_SIGNED_DATA   = "inapp_signed_data";
	public static final String INAPP_SIGNATURE     = "inapp_signature";
	public static final String INAPP_REQUEST_ID    = "request_id";
	public static final String INAPP_RESPONSE_CODE = "response_code";

	// These are the names of the fields in the request bundle.
	public static final String BILLING_REQUEST_METHOD              = "BILLING_REQUEST";
	public static final String BILLING_REQUEST_API_VERSION         = "API_VERSION";
	public static final String BILLING_REQUEST_PACKAGE_NAME        = "PACKAGE_NAME";
	public static final String BILLING_REQUEST_ITEM_ID             = "ITEM_ID";
	public static final String BILLING_REQUEST_DEVELOPER_PAYLOAD   = "DEVELOPER_PAYLOAD";
	public static final String BILLING_REQUEST_NOTIFY_IDS          = "NOTIFY_IDS";
	public static final String BILLING_REQUEST_NONCE               = "NONCE";

	public static final String BILLING_RESPONSE_RESPONSE_CODE      = "RESPONSE_CODE";
	public static final String BILLING_RESPONSE_PURCHASE_INTENT    = "PURCHASE_INTENT";
	public static final String BILLING_RESPONSE_REQUEST_ID         = "REQUEST_ID";
	public static final long   BILLING_RESPONSE_INVALID_REQUEST_ID = -1;


	private static WordsPurchaseObserver mWordsPurchaseObserver;
	private static Handler mHandler;
	private static EpicInAppService mBillingService;

	public static boolean doPurchase(Product p) {
		mHandler = new Handler();
		mWordsPurchaseObserver = new WordsPurchaseObserver(mHandler);
		mBillingService = new EpicInAppService();
		mBillingService.setContext(EpicApplication.getAndroidContext());

		// Check if billing is supported.
		EpicBillingResponseHandler.register(mWordsPurchaseObserver);
		if (!mBillingService.checkBillingSupported()) {
			throw new EpicRuntimeException("Billing not supported! HANDLE THIS CASE");
		}

		EpicBillingResponseHandler.register(mWordsPurchaseObserver);

		mBillingService.requestPurchase(p.sku, "");

		//   EpicBillingResponseHandler.unregister(mDungeonsPurchaseObserver);
		//   mBillingService.unbind();

		return false;
	}
//	private static void bindToService() {
//		try {
//			boolean bindResult = EpicApplication.getAndroidContext().bindService(
//					new Intent("com.android.vending.billing.MarketBillingService.BIND"), 
//					new ServiceConnection() {
//						public void onServiceDisconnected(ComponentName paramComponentName) {
//					        mService = IMarketBillingService.Stub.asInterface(service);
//						}
//
//						public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder) {
//						}
//					}, 
//					Context.BIND_AUTO_CREATE
//			);
//			if (bindResult) {
//				EpicLog.i("Service bind to MarketBillingService successful.");
//			} else {
//				EpicLog.e("Could not bind to the MarketBillingService.");
//			}
//		} catch (SecurityException e) {
//			EpicLog.e("Security exception: " + e);
//		}	
//	}
//	private static Bundle sendRequestToBillingService(Bundle request) {
//		return mService.sendBillingRequest(request);
//		
//	}

//	public static boolean isInAppPurchaseSupported() {
//		Bundle request = makeRequestBundle("CHECK_BILLING_SUPPORTED");
//		Bundle response = sendRequestToBillingService(request);
//		int responseCode = response.getInt(EpicBillingConstants.BILLING_RESPONSE_RESPONSE_CODE);
//		if (EpicBillingConstants.DEBUG) {
//			EpicLog.i("CheckBillingSupported response code: " + ResponseCode.valueOf(responseCode));
//		}
//		boolean billingSupported = (responseCode == ResponseCode.RESULT_OK.ordinal());
//		EpicBillingResponseHandler.checkBillingSupportedResponse(billingSupported);
//		return EpicBillingConstants.BILLING_RESPONSE_INVALID_REQUEST_ID;
//	}
	
	public static boolean isInAppPurchaseSupported() {
		return true;
	}

	public static void displayOffers() {
		TapjoyConnect.getTapjoyConnectInstance().showOffers();
	}

	public static void checkForEarnedTokens() {
		TapjoyConnect.getTapjoyConnectInstance().getTapPoints(EpicApplication.tn);
	}

	public static boolean displayFeaturedApp() {
		TapjoyConnect.getTapjoyConnectInstance().getFeaturedApp(new TapjoyFeaturedAppNotifier() {
			public void getFeaturedAppResponseFailed(String error) {
				EpicLog.e("Error getting featured app: " + error);
			}

			public void getFeaturedAppResponse(TapjoyFeaturedAppObject featuredAppObject) {
				TapjoyConnect.getTapjoyConnectInstance().showFeaturedAppFullScreenAd();
			}
		});
		
		return true;
	}

	public static void getFeaturedOffer(final EpicFeaturedAppCallback callback) {
		EpicPlatform.runInBackground(new Runnable() {
			public void run() {
				TapjoyConnect.getTapjoyConnectInstance().getFeaturedApp(new TapjoyFeaturedAppNotifier() {
					
					public void getFeaturedAppResponseFailed(String error) {
						callback.getFeaturedAppResponseFailed(error);
					}
					
					public void getFeaturedAppResponse(TapjoyFeaturedAppObject featuredAppObject) {
						EpicFeaturedOffer offer = new EpicFeaturedOffer(featuredAppObject.name, featuredAppObject.amount, featuredAppObject.cost, featuredAppObject.redirectURL, featuredAppObject.iconURL);
						callback.getFeaturedAppResponse(offer);
					}
				});		
			}
		});
		
	}

	public static void onClickOffer(EpicFeaturedOffer epicFeaturedOffer) {		
		EpicHttpRequest request = new EpicHttpRequest(epicFeaturedOffer.storeUrl);
		request.allowRedirect = false;
		request.beginGet(new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				EpicLog.i("Request finished - response code " + response.responseCode);
				if(response.headers.containsKey("Location")) {
					EpicApplication.androidLaunchMarketplace(response.headers.get("Location"));
				} else {
					EpicLog.e("No Location header found.");
					for(String k : response.headers.keySet()) {
						EpicLog.w("Header found: " + k);	
					}
					
					EpicLog.w(response.body);
				}
				
			}
			public void handleFailure(Exception e) {
				EpicLog.e("Exception during GET: ", e);
			}
		});
	}
}
