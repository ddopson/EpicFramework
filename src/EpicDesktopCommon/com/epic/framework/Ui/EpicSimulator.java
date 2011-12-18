package com.epic.framework.Ui;

import java.awt.Dimension;



@SuppressWarnings("serial")
public class EpicSimulator {
	private static final long serialVersionUID = 1L;

	static Dimension[] supportedScreenSizes = new Dimension[] {
		new Dimension(800, 480), // Incredible, Thunderbolt
		new Dimension(854, 480), // Droid
		new Dimension(480, 360), // BlackBerry
		new Dimension(1280, 800), // Xoom
		new Dimension(1024, 768) // iPad, iPad2
	};

	private static Dimension currentScreenSize = supportedScreenSizes[0];

	public static Dimension getCurrentScreenSize() {
		return currentScreenSize;
	}

	public static void setScreenSize(Dimension screenSize) {
		currentScreenSize = screenSize;
	}
}
