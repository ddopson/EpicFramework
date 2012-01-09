package com.epic.framework.implementation;

import java.util.UUID;

import com.epic.config.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.util.EpicLog;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

public class ArchPlatform {
	public static int getAllocatedMemoryKiB() {
		return (int)(android.os.Debug.getNativeHeapAllocatedSize() + 512) / 1024;
	}

	public static int _to_kib(int bytes) {
		return (bytes + 512) / 1024;
	}
	public static long _to_kib(long bytes) {
		return (bytes + 512) / 1024;
	}

	public static long getMilliseconds() {
		return android.os.SystemClock.uptimeMillis();
	}

	public static String getMemstatsString() {
		long jmem = Runtime.getRuntime().totalMemory();
		long nmem = Debug.getNativeHeapAllocatedSize();
		return String.format("%.1fM (%.1fM Java)", (double)(jmem + nmem) / 1024 / 1024, (double)jmem / 1024 / 1024);
	}

	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}
	
	private static String toMib(long bytes) {
		return String.format(bytes > 1024*1024 ? "%.1fM (%d)" : "%.2fM (%d)", (double)bytes / 1024 / 1024, bytes);
	}


	public static void logMemoryStats() {
		String text = "";
		text += "\nLoadedClassCount=" 				+ toMib(android.os.Debug.getLoadedClassCount());
		text += "\nGlobalAllocSize=" 				+ toMib(android.os.Debug.getGlobalAllocSize());
		text += "\nGlobalFreedSize=" 				+ toMib(android.os.Debug.getGlobalFreedSize());
		text += "\nGlobalExternalAllocSize=" 		+ toMib(android.os.Debug.getGlobalExternalAllocSize());
		text += "\nGlobalExternalFreedSize=" 		+ toMib(android.os.Debug.getGlobalExternalFreedSize());
		text += "\nEpicPixels="						+ toMib(EpicBitmap.getGlobalPixelCount()*4);
		text += "\nNativeHeapSize=" 				+ toMib(android.os.Debug.getNativeHeapSize());
		text += "\nNativeHeapFree=" 				+ toMib(android.os.Debug.getNativeHeapFreeSize());
		text += "\nNativeHeapAllocSize=" 			+ toMib(android.os.Debug.getNativeHeapAllocatedSize());
		text += "\nThreadAllocSize=" 				+ toMib(android.os.Debug.getThreadAllocSize());

		text += "\ntotalMemory()=" 					+ toMib(Runtime.getRuntime().totalMemory());
		text += "\nmaxMemory()=" 					+ toMib(Runtime.getRuntime().maxMemory());
		text += "\nfreeMemory()=" 					+ toMib(Runtime.getRuntime().freeMemory());
		
		android.app.ActivityManager.MemoryInfo mi1 = new android.app.ActivityManager.MemoryInfo();
		ActivityManager am = (ActivityManager)EpicApplication.getAndroidContext().getSystemService(Context.ACTIVITY_SERVICE);
		am.getMemoryInfo(mi1);
		text += "\napp.mi.availMem=" 				+ toMib(mi1.availMem);
		text += "\napp.mi.threshold=" 				+ toMib(mi1.threshold);
		text += "\napp.mi.lowMemory=" 				+ mi1.lowMemory;
		
		android.os.Debug.MemoryInfo mi2 = new android.os.Debug.MemoryInfo();		
		Debug.getMemoryInfo(mi2);
		text += "\ndbg.mi.dalvikPrivateDirty=" 		+ toMib(mi2.dalvikPrivateDirty);
		text += "\ndbg.mi.dalvikPss=" 				+ toMib(mi2.dalvikPss);
		text += "\ndbg.mi.dalvikSharedDirty=" 		+ toMib(mi2.dalvikSharedDirty);
		text += "\ndbg.mi.nativePrivateDirty=" 		+ toMib(mi2.nativePrivateDirty);
		text += "\ndbg.mi.nativePss=" 				+ toMib(mi2.nativePss);
		text += "\ndbg.mi.nativeSharedDirty=" 		+ toMib(mi2.nativeSharedDirty);
		text += "\ndbg.mi.otherPrivateDirty=" 		+ toMib(mi2.otherPrivateDirty);
		text += "\ndbg.mi.otherPss" 				+ toMib(mi2.otherPss);
		text += "\ndbg.mi.otherSharedDirty=" 		+ toMib(mi2.otherSharedDirty);

		if(!EpicProjectConfig.isReleaseMode) EpicLog.i("ArchPlatform[android].logStats() - " + text);
	}
}
