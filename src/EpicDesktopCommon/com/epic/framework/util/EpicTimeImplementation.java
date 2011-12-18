package com.epic.framework.util;

public class EpicTimeImplementation {
	public static long getMicroTime() {
		return System.currentTimeMillis() * 1000;
	}
}
