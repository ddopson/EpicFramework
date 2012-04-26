package com.epic.framework.implementation;

import javax.swing.JLabel;

public class EpicNativeLabelWidgetImplementation {

	public static Object createNativeLabel(String text) {
		JLabel label = new JLabel(text);
		return label;
	}

	public static String getText(Object platformObject) {
		JLabel label = (JLabel) platformObject;
		return label.getText();
	}
}
