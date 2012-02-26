package com.epic.framework.implementation;

import javax.swing.SwingUtilities;

import com.epic.framework.common.util.EpicLog;

public class EpicPlatformImplementation {
	public static void doToastNotification(String text, int duration) {
	}

	public static void runOnUiThread(Runnable runnable) {
		SwingUtilities.invokeLater(runnable);
	}

	public static void runInBackground(Runnable runnable) {
		new Thread(runnable).start();
	}

	public static boolean isTouchEnabledDevice() {
		// TODO Auto-generated method stub
		return true;
	}

	public static String getApplicationVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getListingId() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void dismissNotifications() {
		// TODO Auto-generated method stub
		
	}

	public static void deepLinkToMarket(String string) {
		EpicLog.w("NOT_IMPLEMENTED");
	}

	public static String getUniqueDeviceId() {
		EpicLog.w("NOT_IMPLEMENTED");
		return "device_id";
	}

	public static void setAppBadge(int newCount) {
		EpicLog.w("NOT_IMPLEMENTED");
	}

	public static void requestFacebookFriends(String string) {
		// TODO Auto-generated method stub
		
	}

	public static String getDeviceName() {
		return "device_name";
	}
}
