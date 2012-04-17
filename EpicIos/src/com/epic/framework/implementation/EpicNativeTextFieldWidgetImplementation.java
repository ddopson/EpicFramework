package com.epic.framework.implementation;

import org.xmlvm.iphone.UITextField;

public class EpicNativeTextFieldWidgetImplementation extends EpicNativeWidget {
	public static Object createNativeTextField() {
		UITextField textField = new UITextField();
		return textField;
	}
	
	public static String getText(Object platformObject) {
		UITextField textField = (UITextField) platformObject;
		return textField.getText();
	}
}
