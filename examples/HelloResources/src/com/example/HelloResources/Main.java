package com.example.HelloResources;


import java.io.FileInputStream;
import java.io.IOException;

import com.epic.framework.vendor.org.json.JSONObject;
import com.epic.framework.vendor.org.json.JSONTokener;

import com.epic.framework.common.EpicConfig;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.common.Ui2.ClassEpicScreenObject;
import com.epic.framework.common.Ui2.InitRoutine;
import com.epic.framework.common.Ui2.Registry;
import com.epic.framework.implementation.DesktopMainUiV2;
import com.epic.framework.vendor.org.json.JSONException;
import com.epic.resources.EpicImages;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws JSONException
	 * @throws InterruptedException
	 * @throws org.json.JSONException 
	 */
	public static void main(String[] args) throws IOException, JSONException, InterruptedException {
		InitRoutine.init();
		EpicImages.register();

		String base = "/workspace/EpicFramework/examples/HelloResources/resources/json";
		Registry.processJSONStream(new FileInputStream(base + "/screens/MainMenu.json"));
		
		JSONObject config = new JSONObject(new JSONTokener(new FileInputStream(base + "/index.json")));
		EpicConfig.INITIAL_SCREEN = (EpicScreen) Registry.inflateField(config, "INITIAL_SCREEN", ClassEpicScreenObject.singleton, 0);
		
		DesktopMainUiV2.main(args);
	}
}
