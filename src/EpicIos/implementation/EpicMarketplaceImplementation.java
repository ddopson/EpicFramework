package com.epic.framework.implementation;

import java.util.ArrayList;

import org.xmlvm.iphone.SKPayment;
import org.xmlvm.iphone.SKPaymentQueue;
import org.xmlvm.iphone.SKPaymentTransaction;
import org.xmlvm.iphone.SKPaymentTransactionObserver;
import org.xmlvm.iphone.SKPaymentTransactionState;

import com.epic.framework.common.util.EpicFeaturedAppCallback;
import com.epic.framework.common.util.EpicFeaturedOffer;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.Product;
import com.realcasualgames.words.PlayerState;

public class EpicMarketplaceImplementation {
	public static boolean observerSet = false;
	public static SKPaymentTransactionObserver purchaseObserver = new SKPaymentTransactionObserver() {
		public void updatedTransactions(SKPaymentQueue queue, ArrayList<SKPaymentTransaction> transactions) {
			for(SKPaymentTransaction t : transactions) {
				if(t.getTransactionState() == SKPaymentTransactionState.Purchased) {
					String iden = t.getPayment().getProductIdentifier();
					EpicLog.i("Purchase complete for " + iden);
					if(iden.equals("tokens_sm")) {
						PlayerState.updateLocalTokens(25000);
					} else if(iden.equals("tokens_med")) {
						PlayerState.updateLocalTokens(60000);
					} else if(iden.equals("tokens_lg")) {
						PlayerState.updateLocalTokens(100000);
					} else if(iden.equals("tokens_max")) {
						PlayerState.updateLocalTokens(9999999);
					} else {
						EpicLog.e("Unknown product ID purchased: " + iden);
					}
					
					queue.finishTransaction(t);
				}
			}
		}
	};
	
	public static boolean doPurchase(Product p) {
		if(!observerSet) SKPaymentQueue.defaultQueue().addTransactionObserver(purchaseObserver);
		SKPayment payment = SKPayment.paymentWithProductIdentifier(p.sku);
		SKPaymentQueue.defaultQueue().addPayment(payment);
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
