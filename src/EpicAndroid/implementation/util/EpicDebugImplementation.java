package com.epic.framework.implementation.util;

import android.os.Debug;

public class EpicDebugImplementation {
	public static void startMethodTracing(String filename, int size) {
		Debug.startMethodTracing(filename, size);
	}

	public static void stopMethodTracing() {
		Debug.stopMethodTracing();
	}

	public static void alert(String msg) {
		// Not implemented
	}
}
