package com.epic.framework.implementation;

import javax.swing.SwingUtilities;

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
		return false;
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
}
