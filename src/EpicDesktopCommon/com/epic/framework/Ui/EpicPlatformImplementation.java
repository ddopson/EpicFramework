package com.epic.framework.Ui;

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
}
