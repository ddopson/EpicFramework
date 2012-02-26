package com.epic.framework.implementation;

import java.awt.Dimension;

import com.epic.framework.common.Ui.EpicPlatform;


public class DesktopMainIos {
	public static void main(String[] args) throws InterruptedException {
		EpicPlatformConfig.platform = EpicPlatform.PLATFORM_IOS;
		EpicBitmapImplementation.magicBaseDirectory = "./resources";
		EpicSimulator.currentScreenSize = new Dimension(1024, 768);
		DesktopMain.MainMethod(args);
	}
}