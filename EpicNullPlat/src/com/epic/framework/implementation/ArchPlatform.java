package com.epic.framework.implementation;

public class ArchPlatform {
	
	public static int getAllocatedMemoryKiB() { return 0; }
	public static long getMilliseconds() { return System.currentTimeMillis(); }
	public static String getMemstatsString() { return "memstatsString"; }
	public static String randomUUID() { return "randomUUID"; }
	public static void logMemoryStats() { }

}
