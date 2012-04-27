package com.epic.framework.implementation;

import org.xmlvm.iphone.UIButton;
import org.xmlvm.iphone.UIButtonType;
import org.xmlvm.iphone.UIControlState;

import com.epic.framework.common.Ui2.NativeWidgets.EpicNativeButtonWidget;

public class EpicNativeButtonWidgetImplementation extends EpicNativeWidget {
	public static Object createNativeButton(EpicNativeButtonWidget epicNativeButtonWidget) {
		UIButton button = UIButton.buttonWithType(UIButtonType.InfoDark);
		button.setTitle(epicNativeButtonWidget.text, UIControlState.Normal);
		return button;
	}
}
