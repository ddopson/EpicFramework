package com.epic.framework.util;

import net.rim.device.api.system.Memory;

public class ArchPlatform {
	public static int getAllocatedMemoryKiB() {
		return ArchPlatform._to_kib(Memory.getRAMStats().getAllocated());
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
		long jmem = Runtime.getRuntime().totalMemory();
		return "[r] " + Memory.getRAMStats().toString() + " [f] " + Memory.getFlashStats().toString() + " [j] " + jmem;
	}

	public static String randomUUID() {
		return null;
	}

	public static void logMemoryStats() {
		EpicLog.d("ArchPlatform[android].logStats() - " + ArchPlatform.getMemstatsString());
	}
}
