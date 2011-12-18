package com.epic.framework.Ui;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.Touchscreen;

public class EpicApplication {


	public static String getDeviceId() {
		return (Integer.toString(DeviceInfo.getDeviceId(),16)).toUpperCase();
	}

	public static void androidLaunchMarketplace(String url) {
		BrowserSession _session = Browser.getDefaultSession();
		_session.displayPage(url);
		_session.showBrowser();
	}
	public static boolean isTouchEnabledDevice() {
		return Touchscreen.isSupported();
	}
	public static String getApplicationVersion() {
		return null;
	}
	public static String getListingId() {
		return null;
	}
}
