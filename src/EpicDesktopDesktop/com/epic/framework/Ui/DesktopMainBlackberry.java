package com.epic.framework.Ui;

import com.epic.cfg.EpicPlatformConfig;
import com.epic.cfg.EpicProjectConfig;

public class DesktopMainBlackberry {
	public static void main(String[] args) throws InterruptedException {
		EpicProjectConfig.isReleaseMode = false;
		EpicPlatformConfig.platform = EpicPlatformConfig.PLATFORM_BLACKBERRY;
		EpicBitmapImplementation.magicBaseDirectory = "./images-blackberry";
		DesktopMain.MainMethod(args);
	}
}