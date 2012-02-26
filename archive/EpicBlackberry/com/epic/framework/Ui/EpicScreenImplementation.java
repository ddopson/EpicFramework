//package com.epic.framework.Ui;
//
//import net.rim.device.api.system.Backlight;
//import net.rim.device.api.system.Characters;
//import net.rim.device.api.system.Display;
//import net.rim.device.api.system.KeyListener;
//import net.rim.device.api.ui.ContextMenu;
//import net.rim.device.api.ui.Graphics;
//import net.rim.device.api.ui.Keypad;
//import net.rim.device.api.ui.MenuItem;
//import net.rim.device.api.ui.Ui;
//import net.rim.device.api.ui.UiApplication;
//import net.rim.device.api.ui.component.Menu;
//import net.rim.device.api.ui.container.MainScreen;
//
//import com.epic.cfg.EpicProjectConfig;
//import com.epic.framework.Ui.EpicMenu.EpicMenuItem;
//import com.epic.framework.Ui.EpicScreen.EpicPlatformScreenObject;
//import com.epic.framework.util.EpicLog;
//
//public class EpicScreenImplementation {
//	public static EpicPlatformScreenObject createPlatformScreenObject(EpicScreen epicScreen) {
//		return new EpicBlackberryMainScreen(epicScreen);
//	}
//	
//	public static void preshow(EpicScreen epicScreen, EpicScreen currentScreen) { }
//	public static void show(EpicScreen epicScreen, EpicScreen currentScreen) {
//		UiApplication.getUiApplication().pushScreen((EpicBlackberryMainScreen) epicScreen.platformObject);
//	}
//

//
//}
