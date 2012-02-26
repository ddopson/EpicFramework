package com.epic.framework.implementation;

import java.awt.Dimension;

import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicScreen;
//import com.epic.framework.common.Ui2.InitRoutine;
import com.epic.framework.common.Ui2.Registry;
import com.epic.framework.common.Ui2.JSON.JSONException;


public class DesktopMainUiV2 {
	public static void main(String[] args) throws InterruptedException, JSONException {
//		InitRoutine.init();
		EpicPlatformConfig.platform = EpicPlatform.PLATFORM_IOS;
		EpicBitmapImplementation.magicBaseDirectory = "./resources";
		EpicSimulator.currentScreenSize = new Dimension(1024, 768);
		DesktopMain.MainMethod(args);
//		EpicPlatform.changeScreen((EpicScreen)Registry.get("screens/MainMenu"));
	}
}