package com.epic.framework.common;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicFont;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.common.Ui.EpicSound;

public class EpicConfig {
	public static boolean DEBUG_EPICPLAT = false;
	public static boolean DEBUG_EPICRAND = false;
	public static boolean DEBUG_EPICSTOPWATCH = false;
	public static boolean DEBUG_MENUS = true;
	
	public static int DESIGN_WIDTH = 800;
	public static int DESIGN_HEIGHT = 480;

	public static EpicScreen INITIAL_SCREEN = null;
	
	public static EpicFont RESOURCES_TOAST_FONT = null;
	public static EpicBitmap RESOURCES_TOAST_BG = null;
	public static EpicSound RESOURCES_SCREEN_TRANSITION_SOUND = null;
	public static EpicBitmap RESOURCES_ICON = null;
}
