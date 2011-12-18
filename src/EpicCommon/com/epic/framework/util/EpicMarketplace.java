package com.epic.framework.util;

import com.epic.cfg.EpicPlatformConfig;
import com.epic.framework.Ui.EpicPlatform;
import com.epic.framework.Ui.EpicScreen;
import com.realcasualgames.words.EpicFeaturedAppCallback;
import com.realcasualgames.words.ScreenAdvertisement;

public class EpicMarketplace {
	
	public static final int FREE_BONUSES = 0;
	
	public static boolean doPurchase(Product p) {
		return EpicMarketplaceImplementation.doPurchase(p);
	}
	
	public static boolean isInAppPurchaseSupported() {
		return EpicMarketplaceImplementation.isInAppPurchaseSupported();
	}
	
	public static boolean areOffersSupported() {
		return EpicPlatformConfig.platform == EpicPlatformConfig.PLATFORM_ANDROID;
	}
	
	public static Product[] allProducts = new Product[] { 
		new Product(EpicPlatformConfig.platform == EpicPlatformConfig.PLATFORM_BLACKBERRY ? "token_sm" : "tokens_sm", "Small Token Pack and Premium Player Upgrade", " 25,000", "$1.99", 25000),
		new Product("tokens_med", "Medium Token Pack and Premium Player Upgrade", "60,000", "$3.99", 60000),
		new Product("tokens_lg", "Large Token Pack and Premium Player Upgrade", "100,000", "$5.99", 100000),
		new Product("tokens_max", "Unlimited Token Pack and Premium Player Upgrade", "Unlimited", "$29.99", 9999999)
	};

	public static void displayOffers() {
		if(EpicPlatformConfig.platform == EpicPlatformConfig.PLATFORM_ANDROID) {
			EpicMarketplaceImplementation.displayOffers();
		}
	}

	public static void checkForEarnedTokens() {
		if(EpicPlatformConfig.platform == EpicPlatformConfig.PLATFORM_ANDROID) {
			EpicMarketplaceImplementation.checkForEarnedTokens();
		}
	}

	public static boolean showFeaturedAppOrNag() {
		if(EpicPlatformConfig.platform == EpicPlatformConfig.PLATFORM_ANDROID) {
			return EpicMarketplaceImplementation.displayFeaturedApp();
		}
		
		return false;
	}

	public static void getFeaturedOffer(EpicFeaturedAppCallback callback) {
		if(EpicPlatformConfig.platform == EpicPlatformConfig.PLATFORM_ANDROID) {
			EpicMarketplaceImplementation.getFeaturedOffer(callback);
		}
	}

	public static void onClickOffer(EpicFeaturedOffer epicFeaturedOffer) {
		EpicMarketplaceImplementation.onClickOffer(epicFeaturedOffer);
	}
}
