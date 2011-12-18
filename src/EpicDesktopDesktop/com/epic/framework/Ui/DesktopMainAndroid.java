package com.epic.framework.Ui;

import com.epic.cfg.EpicPlatformConfig;
import com.epic.cfg.EpicProjectConfig;

public class DesktopMainAndroid {
	public static void main(String[] args) throws InterruptedException {
		EpicProjectConfig.isReleaseMode = false;
		EpicPlatformConfig.platform = EpicPlatformConfig.PLATFORM_ANDROID;
		EpicBitmapImplementation.magicBaseDirectory = "./images-android";
		DesktopMain.MainMethod(args);
	}
}