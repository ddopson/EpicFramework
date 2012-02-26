package com.epic.framework.implementation;

public class EpicTimeImplementation {
	public static long getMicroTime() {
		return System.currentTimeMillis() * 1000;
	}

	public static long getMilliTime() {
		return System.currentTimeMillis();
	}
}
