package com.epic.framework.util;

import net.rim.device.api.ui.component.Dialog;

public class EpicDebugImplementation {
	public static void startMethodTracing(String filename, int size) {
		// No built in tracing on BB
	}

	public static void stopMethodTracing() {
		// No built in tracing on BB
	}

	public static void alert(String msg) {
		Dialog.alert(msg);
	}
}
