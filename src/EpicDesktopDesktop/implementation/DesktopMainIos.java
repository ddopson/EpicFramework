package com.epic.framework.implementation;

import com.epic.config.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicPlatform;


public class DesktopMainIos {
	public static void main(String[] args) throws InterruptedException {
		EpicProjectConfig.isReleaseMode = false;
		EpicPlatformConfig.platform = EpicPlatform.PLATFORM_IOS;
		EpicBitmapImplementation.magicBaseDirectory = "./resources";
		DesktopMain.MainMethod(args);
	}
}