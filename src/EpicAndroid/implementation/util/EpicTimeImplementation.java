package com.epic.framework.implementation.util;

public class EpicTimeImplementation {
	public static long getMicroTime() {
		return 1000 * android.os.SystemClock.uptimeMillis();
	}

	public static long getMilliTime() {
		return android.os.SystemClock.uptimeMillis();
	}
}
