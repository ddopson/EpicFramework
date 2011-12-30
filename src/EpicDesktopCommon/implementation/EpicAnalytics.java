package com.epic.framework.implementation;

//import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class EpicAnalytics {
//	if(epicScreen instanceof ScreenGame) {
//	mChecker = EpicLicenseChecker.checkLicense(this);
//	tracker = GoogleAnalyticsTracker.getInstance();
//	tracker.start("UA-20257663-1", this);
//	ScreenGame scr = (ScreenGame) epicScreen;
//	tracker.trackPageView("Game Screen - Level " + scr.levelDef.levelNumber);
//	EpicLog.i("Got tracker and tracked pv");
//}
	private static final String GOOGLE_ANALYTICS_EPIC_ID = "UA-20257663-1";
//	static GoogleAnalyticsTracker tracker = GoogleAnalyticsTracker.getInstance();

	static {
//		tracker.start(GOOGLE_ANALYTICS_EPIC_ID, EpicApplication.getAndroidContext());
	}

	public static void trackPageView(String page) {
//		tracker.trackPageView(page);
	}

	public static void trackEvent(String category, String action, String label, int value) {
//		tracker.trackEvent(category, action, label, value);
	}

	public static void dispatch() {
//		tracker.dispatch();
	}
}
