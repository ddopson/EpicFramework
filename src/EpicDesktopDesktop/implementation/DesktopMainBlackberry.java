package com.epic.framework.implementation;

import com.epic.config.EpicProjectConfig;

public class DesktopMainBlackberry {
	public static void main(String[] args) throws InterruptedException {
		EpicProjectConfig.isReleaseMode = false;
		EpicPlatformConfig.platform = EpicPlatformConfig.PLATFORM_BLACKBERRY;
		EpicBitmapImplementation.magicBaseDirectory = "./images-blackberry";
		DesktopMain.MainMethod(args);
	}
}