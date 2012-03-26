package com.epic.framework.implementation;

import java.awt.Dimension;
import java.io.IOException;


import com.epic.framework.common.EpicConfig;
import com.epic.framework.common.JSON;
import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicScreen;
//import com.epic.framework.common.Ui2.InitRoutine;
import com.epic.framework.common.Ui2.ClassEpicScreenObject;
import com.epic.framework.common.Ui2.InitRoutine;
import com.epic.framework.common.Ui2.Registry;
import com.epic.framework.vendor.org.json.simple.*;

public class DesktopMainUiV2 {
	public static void main(EpicFile configFile) throws InterruptedException, JSONException, IOException {
		InitRoutine.init();
		EpicPlatformConfig.platform = EpicPlatform.PLATFORM_IOS;
		EpicFileImplementation.magicBaseDirectory = "./resources/files";
		EpicBitmapImplementation.magicBaseDirectory = "./resources/images";
		EpicSimulator.currentScreenSize = new Dimension(1024, 768);
		Registry.processConfig(configFile);
		DesktopMain.MainMethod();
	}
}