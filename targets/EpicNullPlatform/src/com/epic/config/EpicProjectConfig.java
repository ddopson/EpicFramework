package com.epic.config;

import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.common.types.Dimension;

	public class EpicProjectConfig {
	public static void onApplicationStart() { }

	public static EpicScreen getInitialScreenObject(String screen, String extra) {
		return null;
	}

	public static EpicSound getBackgroundMusic() {
		return null;
	}
	
	public static boolean getIsTitlebarDisabled() {
		return true;
	}
	
	public static boolean isReleaseMode = true;

	public static final Dimension designDimensions = new Dimension(800, 480);
	public static Dimension getDesignDimensions() {
		return designDimensions;
	}
}
