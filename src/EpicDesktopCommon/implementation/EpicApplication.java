package com.epic.framework.implementation;

public class EpicApplication {
	public static void blackberryMain(Object whoGivesACrap) {
		// do nothing on Android.  only needed on BB.  Android starts app using an intent and a big gooey framework
	}

	public static void androidLaunchMarketplace(String url) {

	}

	public static void killProcess() {
		System.exit(0);
	}

	public static boolean isTouchEnabledDevice() {
		return true;
	}

	public static String getApplicationVersion() {
		return null;
	}

	public static String getListingId() {
		return null;
	}

//	private static Typeface font;
//	public static Typeface getFont() {
//		if(font == null) {
//			font = Typeface.createFromAsset(context.getAssets(), "font.ttf");
//		}
//		return font;
//	}
}
