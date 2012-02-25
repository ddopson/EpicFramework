package com.epic.framework.implementation;

import com.epic.config.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicPlatform;


public class DesktopMainAndroid {
	public static void main(String[] args) throws InterruptedException {
		EpicPlatformConfig.platform = EpicPlatform.PLATFORM_ANDROID;
		EpicBitmapImplementation.magicBaseDirectory = "./resources";
		DesktopMain.MainMethod(args);
	}
}