package com.epic.framework.implementation;

import javax.swing.JButton;

public class EpicNativeButtonWidgetImplementation {
	public static Object createNativeButton(String text) {
		JButton button = new JButton(text);
		return button;
	}
}
