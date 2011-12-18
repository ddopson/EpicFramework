package com.epic.framework.util;

import com.epic.framework.Ui.EpicApplication;
import com.epic.framework.Ui.EpicBitmap;

public class EpicFeaturedOffer {
	
	public String appName;
	public int tokens;
	public String cost;
	public String icon;
	public String storeUrl;
	
	public EpicFeaturedOffer(String appName, int tokens, String cost, String storeUrl, String icon) {
		this.appName = appName;
		this.tokens = tokens;
		this.cost = cost;
		this.icon = icon;
		this.storeUrl = storeUrl;
	}
	
	public void onClickOffer() {
		EpicLog.i("Launching " + storeUrl);
		// EpicApplication.androidLaunchMarketplace(storeUrl);
		EpicMarketplace.onClickOffer(this);
	}

}
