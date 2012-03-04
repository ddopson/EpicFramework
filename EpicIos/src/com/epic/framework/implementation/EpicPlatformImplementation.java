package com.epic.framework.implementation;

import com.epic.framework.common.util.callbacks.EpicPushNotificationCallback;

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

	public static String getPushId(EpicPushNotificationCallback callback) {
		return EpicPlatformImplementationNative.getPushId();
	}
}
