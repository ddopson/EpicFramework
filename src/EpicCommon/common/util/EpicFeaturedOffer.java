package com.epic.framework.common.util;

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
		EpicMarketplace.onClickOffer(this);
	}

}
