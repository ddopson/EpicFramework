package com.epic.framework.implementation;

public class EpicPlatformImplementation {
	public static void doToastNotification(String text, int duration) {
	}

	public static void runOnUiThread(Runnable runnable) {
	}

	public static void runInBackground(Runnable runnable) {
		// new Thread(runnable).start();
	}

	public static void dismissNotifications() {
	}

	public static boolean isTouchEnabledDevice() {
		return true;
	}

	public static String getListingId() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getApplicationVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getUniqueDeviceId() {
		return EpicPlatformImplementationNative.getUniqueDeviceId();
	}
}
