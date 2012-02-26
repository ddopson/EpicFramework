package com.epic.framework.Ui;

import net.rim.device.api.ui.UiApplication;

import com.epic.framework.util.EpicFail;
import com.epic.framework.util.EpicLog;

public class EpicPlatformImplementation {
	public static void doToastNotification(String text, int duration) {
		EpicLog.e("TOAST IS NOT IMPLEMENTED");
//		throw EpicFail.not_implemented();
	}

	public static void runOnUiThread(Runnable runnable) {
		synchronized(UiApplication.getEventLock()) {
			BlackberryMain.main.invokeLater(runnable);
		}
	}

	public static void runInBackground(Runnable runnable) {
		new Thread(runnable).start();
	}

	public static void dismissNotifications() {
		// TODO Auto-generated method stub
		
	}
}
