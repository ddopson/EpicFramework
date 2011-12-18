package com.epic.framework.util;

import javax.microedition.media.TimeBase;

public class EpicTimeImplementation {
	private static TimeBase timeBase = javax.microedition.media.Manager.getSystemTimeBase();
	public static long getMicroTime() {
		return timeBase.getTime();
	}

	public static long getMilliTime() {
		return System.currentTimeMillis();
	}
}
