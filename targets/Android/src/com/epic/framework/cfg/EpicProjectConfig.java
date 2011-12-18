package com.epic.framework.cfg;

import com.epic.framework.common.Ui.EpicBitmap;
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
	
	public static final boolean isReleaseMode = true;
	public static final EpicSound screenTransitionSound = null; // EpicSounds.transition
	public static final EpicBitmap toastBackground = null; // EpicImages.toast_bg_dark

	public static final Dimension designDimensions = new Dimension(800, 480);
	public static Dimension getDesignDimensions() {
		return designDimensions;
	}
}
