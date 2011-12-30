package com.epic.framework.implementation;

import com.epic.config.EpicProjectConfig;


public class DesktopMainAndroid {
	public static void main(String[] args) throws InterruptedException {
		EpicProjectConfig.isReleaseMode = false;
		EpicPlatformConfig.platform = EpicPlatformConfig.PLATFORM_ANDROID;
		EpicBitmapImplementation.magicBaseDirectory = "./resources";
		DesktopMain.MainMethod(args);
	}
}