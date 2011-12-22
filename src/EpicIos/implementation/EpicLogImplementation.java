package com.epic.framework.implementation;

import org.xmlvm.iphone.NSLog;

public class EpicLogImplementation {
	public static final void log(String tag, String msg, int level, Throwable e) {
		NSLog.log(msg);
		if(e != null) {
			logStack();
		}
	}

	public static void logStack() {
		NSLog.logStack();
	}
}
