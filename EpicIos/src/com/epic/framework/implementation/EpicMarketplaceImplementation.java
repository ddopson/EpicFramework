package com.epic.framework.implementation;

import java.util.ArrayList;

import org.xmlvm.iphone.SKPayment;
import org.xmlvm.iphone.SKPaymentQueue;
import org.xmlvm.iphone.SKPaymentTransaction;
import org.xmlvm.iphone.SKPaymentTransactionObserver;
import org.xmlvm.iphone.SKPaymentTransactionState;

import com.epic.framework.common.util.EpicFeaturedOffer;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.Product;
import com.epic.framework.common.util.callbacks.EpicFeaturedAppCallback;
import com.realcasualgames.words.PlayerState;

public class EpicMarketplaceImplementation {
	
	public static boolean doPurchase(Product p) {
		EpicPlatformImplementationNative.requestPurchase(p.sku);
		return false;
	}
	
	public static boolean isInAppPurchaseSupported() {
		return SKPaymentQueue.canMakePayments();
	}

	public static void displayOffers() {}


	public static void checkForEarnedTokens() {}


	public static boolean displayFeaturedApp() {
		return false;
	}

	public static void getFeaturedOffer(EpicFeaturedAppCallback callback) {
		// TODO Auto-generated method stub
		
	}

	public static void onClickOffer(EpicFeaturedOffer epicFeaturedOffer) {
		// TODO Auto-generated method stub
		
	}
}
