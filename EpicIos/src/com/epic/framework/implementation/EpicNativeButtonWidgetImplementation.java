package com.epic.framework.implementation;

import org.xmlvm.iphone.UIButton;
import org.xmlvm.iphone.UIButtonType;
import org.xmlvm.iphone.UIControlState;

public class EpicNativeButtonWidgetImplementation extends EpicNativeWidget {
	public static Object createNativeButton(String text) {
		UIButton button = UIButton.buttonWithType(UIButtonType.InfoDark);
		button.setTitle(text, UIControlState.Normal);
		return button;
	}
}
