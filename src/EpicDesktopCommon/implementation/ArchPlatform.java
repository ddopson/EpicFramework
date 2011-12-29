package com.epic.framework.implementation

import java.util.UUID;



public class ArchPlatform {
	public static int _to_kib(int bytes) {
		return (bytes + 512) / 1024;
	}
	public static long _to_kib(long bytes) {
		return (bytes + 512) / 1024;
	}
	private static String toMib(long bytes) {
		return String.format(bytes > 1024*1024 ? "%.1fM" : "%.2fM", (double)bytes / 1024 / 1024);
	}

	public static String getMemoryStats() {
		//		EpicLog.d("ArchPlatform[android].logStats() - "
		//		+ " globalAllocSize=" + _to_kib(Debug.getGlobalAllocSize())
		//		+ " globalExternalAllocSize=" + _to_kib(Debug.getGlobalExternalAllocSize())
		//		+ " gcCount=" + Debug.getGlobalGcInvocationCount()
		//		+ " freedSize=" + _to_kib(Debug.getGlobalFreedSize())
		//		+ " freedSizeExtern=" + _to_kib(Debug.getGlobalExternalFreedSize())
		//		+ " nativeAllocSize=" + _to_kib(Debug.getNativeHeapAllocatedSize())
		//		+ " nativeFreedSize=" + _to_kib(Debug.getNativeHeapFreeSize())
		//		+ " nativeSize=" + _to_kib(Debug.getNativeHeapSize())
		String text = "";
		text += "\ntotalMemory()=" + toMib(Runtime.getRuntime().totalMemory());
		text += "\nmaxMemory()=" + toMib(Runtime.getRuntime().maxMemory());
		text += "\nfreeMemory()=" + toMib(Runtime.getRuntime().freeMemory());
		//		Runtime.getRuntime().
		return text;
	}

	public static void logMemoryStats() {
		String text = getMemoryStats();
		EpicLog.d("ArchPlatform[desktop].logMemoryStats() - " + text);
	}

	public static long getMilliseconds() {
		return System.currentTimeMillis();
	}
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}
}
