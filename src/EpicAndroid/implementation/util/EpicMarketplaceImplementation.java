package com.epic.framework.implementation.util;

import org.apache.http.client.HttpClient;

import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicFeaturedOffer;
import com.epic.framework.common.util.EpicHttpRequest;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicHttpResponseHandler;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.Product;
import com.epic.framework.implementation.Ui.EpicApplication;
import com.epic.framework.implementation.util.billing.EpicBillingResponseHandler;
import com.epic.framework.implementation.util.billing.EpicInAppService;
import com.epic.framework.implementation.util.billing.WordsPurchaseObserver;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyFeaturedAppNotifier;
import com.tapjoy.TapjoyFeaturedAppObject;

public class EpicMarketplaceImplementation {
	public static final boolean DEBUG = true;


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
			throw EpicFail.not_implemented("Billing not supported! HANDLE THIS CASE");
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
