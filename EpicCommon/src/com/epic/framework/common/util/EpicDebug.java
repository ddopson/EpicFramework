package com.epic.framework.common.util;

import com.epic.framework.implementation.EpicDebugImplementation;

public class EpicDebug {
	public static void startMethodTracing(String filename, int size) {
		EpicDebugImplementation.startMethodTracing(filename, size);
	}

	public static void stopMethodTracing() {
		EpicDebugImplementation.stopMethodTracing();
	}

	public static void alert(String msg) {
		EpicLog.w("DEBUG_ALERT: msg='" + msg + "'");
		EpicDebugImplementation.alert(msg);
	}
}
