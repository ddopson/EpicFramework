package com.epic.framework.implementation;

public class ArchPlatform {
	public static int getAllocatedMemoryKiB() {
		return 0;
	}

	public static int _to_kib(int bytes) {
		return (bytes + 512) / 1024;
	}
	public static long _to_kib(long bytes) {
		return (bytes + 512) / 1024;
	}

	public static long getMilliseconds() {
		return System.currentTimeMillis();
	}

	public static String getMemstatsString() {
		return "memstatsString";
	}

	public static String randomUUID() {
		return "randomUUID";
	}

	public static void logMemoryStats() {
	}
}
