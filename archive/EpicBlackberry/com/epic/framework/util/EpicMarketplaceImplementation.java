package com.epic.framework.util;

import com.realcasualgames.words.EpicFeaturedAppCallback;

import net.rimlib.blackberry.api.payment.PaymentEngine;
import net.rimlib.blackberry.api.payment.PaymentException;
import net.rimlib.blackberry.api.payment.Purchase;
import net.rimlib.blackberry.api.payment.PurchaseArgumentsBuilder;

public class EpicMarketplaceImplementation {
	public static boolean doPurchase(Product p) {
		PaymentEngine engine = PaymentEngine.getInstance();
		if (engine != null)
		{
			PurchaseArgumentsBuilder arguments = new PurchaseArgumentsBuilder()
		    .withDigitalGoodSku( p.sku )
		    .withDigitalGoodName( p.name )
		    .withPurchasingAppName( "Word Farm" )
		    .withMetadata ( p.sku );
			
			
			try 
			{
				// TODO: TESTING ONLY
//				engine.setConnectionMode(PaymentEngine.CONNECTION_MODE_LOCAL);
			    Purchase purchase = engine.purchase(arguments.build());
			    return true;
			} 
			catch (PaymentException ex) 
			{
				EpicLog.e("Error during purchase: " + ex.toString());
			}
			catch( Exception e) {
				EpicLog.e("Other exception: " + e.toString());
			}
		}  
		
		return false;
	}
	
	
	public static boolean isInAppPurchaseSupported() {
		return true;
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
