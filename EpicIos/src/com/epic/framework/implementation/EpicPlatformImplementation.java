package com.epic.framework.implementation;

import org.xmlvm.iphone.UIApplication;

import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.Ui.EpicPercentLayout.LayoutChild;
import com.epic.framework.common.Ui2.InitRoutine;
import com.epic.framework.common.Ui2.Registry;

public class EpicPlatformImplementation {
	public static void doToastNotification(String text, int duration) {
	}

	public static void runOnUiThread(Runnable runnable) {
		EpicPlatformImplementationNative.runOnUiThread(runnable);
	}

	public static void runInBackground(Runnable runnable) {
		new Thread(runnable).start();
	}

	public static void dismissNotifications() {
	}

	public static boolean isTouchEnabledDevice() {
		return true;
	}

	public static String getListingId() {
		// TODO Auto-generated method stub
		return "listing_id";
	}

	public static String getApplicationVersion() {
		// TODO Auto-generated method stub
		return "0.01i";
	}

	public static String getUniqueDeviceId() {
		return EpicPlatformImplementationNative.getUniqueDeviceId();
	}

	public static void setAppBadge(int newCount) {
		EpicPlatformImplementationNative.setAppBadge(newCount);
	}

	public static void deepLinkToMarket(String string) {
		EpicPlatformImplementationNative.launchBrowserTo(string);
	}
	
	public static void requestFacebookFriends(String message) {
		EpicPlatformImplementationNative.requestFacebookFriends(message);
	}

	public static String getDeviceName() {
		return EpicPlatformImplementationNative.getDeviceName();
	}

	public static void clear() {
		EpicUiView.singleton.clear();
	}

	public static void layoutChild(LayoutChild child, int l, int r, int t, int b, boolean firstLayout) {
		EpicUiView.singleton.layoutChild(child, l, r, t, b, firstLayout);
	}

	public static void requestRepaint() {
		EpicUiView.singleton.requestRepaint();
	}

	public static void Main(String[] args) {
        UIApplication.main(args, null, Main.class);
	}
}
