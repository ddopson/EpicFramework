package com.example.HelloUIv2;

import java.io.IOException;
import java.io.InputStream;

import com.epic.framework.common.EpicConfig;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.common.Ui2.InitRoutine;
import com.epic.framework.common.Ui2.Registry;
import com.epic.framework.implementation.DesktopMainUiV2;
import com.epic.framework.vendor.org.json.simple.JSONException;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, JSONException, InterruptedException {
		InitRoutine.init();
		ClassSampleWidget.register();

		InputStream in = Main.class.getResource("testdata.json").openStream();
		Registry.processJSONStream(in);
		EpicConfig.INITIAL_SCREEN = (EpicScreen) Registry.get("screens/MainMenu");
		DesktopMainUiV2.main(args);
	}

}
