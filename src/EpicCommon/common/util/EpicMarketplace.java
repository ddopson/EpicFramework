package com.epic.framework.common.util;

import com.epic.config.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.implementation.EpicMarketplaceImplementation;

public class EpicMarketplace {
	
	
	public static boolean doPurchase(Product p) {
		return EpicMarketplaceImplementation.doPurchase(p);
	}
	
	public static boolean isInAppPurchaseSupported() {
		return EpicMarketplaceImplementation.isInAppPurchaseSupported();
	}
	
	public static boolean areOffersSupported() {
		return EpicPlatform.isAndroid();
	}
	
	public static Product[] allProducts = EpicProjectConfig.marketplaceProducts;
	public static Product[] iosProducts = EpicProjectConfig.iosProducts;

	public static void displayOffers() {
		EpicMarketplaceImplementation.displayOffers();
	}

	public static void checkForEarnedTokens() {
		EpicMarketplaceImplementation.checkForEarnedTokens();
	}

	public static boolean showFeaturedAppOrNag() {
		if(EpicPlatform.isAndroid()) {
			return EpicMarketplaceImplementation.displayFeaturedApp();
		}
		
		return false;
	}

	public static void getFeaturedOffer(EpicFeaturedAppCallback callback) {
		EpicMarketplaceImplementation.getFeaturedOffer(callback);
	}

	public static void onClickOffer(EpicFeaturedOffer epicFeaturedOffer) {
		EpicMarketplaceImplementation.onClickOffer(epicFeaturedOffer);
	}
}
