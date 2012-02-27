package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicPlatform;

public class DesktopMainBlackberry {
	public static void main(String[] args) throws InterruptedException {
		EpicPlatformConfig.platform = EpicPlatform.PLATFORM_BLACKBERRY;
		EpicBitmapImplementation.magicBaseDirectory = "./images-blackberry";
		DesktopMain.MainMethod(args);
	}
}