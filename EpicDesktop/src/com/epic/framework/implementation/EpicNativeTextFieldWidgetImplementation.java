package com.epic.framework.implementation;

import javax.swing.JTextField;

public class EpicNativeTextFieldWidgetImplementation {
	
	public static Object createNativeTextField() {
		JTextField textField = new JTextField();
		return textField;
	}
	
	public static String getText(Object platformObject) {
		JTextField textField = (JTextField) platformObject;
		return textField.getText();
	}
}
