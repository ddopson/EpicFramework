package com.epic.framework.implementation;

import com.epic.framework.common.util.exceptions.EpicNativeMethodMissingImplementation;

public abstract class EpicPlatformImplementationNative {	
	public static void setupDebugHandlers() {
		throw new EpicNativeMethodMissingImplementation("EpicPlatformImplementationNative");
	}
	public static String getUniqueDeviceId() {
		throw new EpicNativeMethodMissingImplementation("EpicPlatformImplementationNative");
	}
	public static void runOnUiThread(Runnable runnable) {
		throw new EpicNativeMethodMissingImplementation("EpicPlatformImplementationNative");
	}
	public static void loginToFacebook() {
		throw new EpicNativeMethodMissingImplementation("EpicPlatformImplementationNative");
	}
	public static int isNetworkAvailable() {
		throw new EpicNativeMethodMissingImplementation("EpicPlatformImplementationNative");
	}
	public static void postToWall(String title) {
		throw new EpicNativeMethodMissingImplementation("EpicPlatformImplementationNative");
	}
	public static void setAppBadge(int newCount) {
		throw new EpicNativeMethodMissingImplementation("EpicPlatformImplementationNative");		
	}
}
