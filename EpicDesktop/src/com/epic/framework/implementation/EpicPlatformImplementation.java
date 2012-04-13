package com.epic.framework.implementation;

import java.io.IOException;

import javax.swing.SwingUtilities;

import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.Ui.EpicPercentLayout.LayoutChild;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.vendor.org.json.simple.JSONException;

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

	public static void clear() {
		EpicNativeGameFrame.get().clear();
	}

	public static void requestRepaint() {
		EpicNativeGameFrame.get().requestRepaint();
	}

	public static void layoutChild(LayoutChild child, int l, int r, int t, int b, boolean firstLayout) {
		EpicNativeGameFrame.get().layoutChild(child, l, r, t, b, firstLayout);
	}

	public static void Main(String[] args) {
		try {
			DesktopMainUiV2.main(new EpicFile("config.json"));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
