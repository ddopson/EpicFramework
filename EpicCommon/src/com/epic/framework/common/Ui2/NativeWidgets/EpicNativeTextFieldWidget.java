package com.epic.framework.common.Ui2.NativeWidgets;

import com.epic.framework.common.EpicFieldInflation;
import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui2.EpicNativeWidget;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.implementation.EpicNativeTextFieldWidgetImplementation;

@EpicInflatableClass()
public class EpicNativeTextFieldWidget extends EpicNativeWidget {
		
	@EpicFieldInflation(ignore=true)
	Object platformObject;
	
	public String getText() {
		String text = EpicNativeTextFieldWidgetImplementation.getText(platformObject);
		if(text != null) {
			return text;
		} else {
			return "";
		}
	}
	
	@Override
	public Object getNativeObject() {
		if(platformObject == null) {
			platformObject = EpicNativeTextFieldWidgetImplementation.createNativeTextField();
			EpicFail.assertNotNull(platformObject);
		}
		return platformObject;
	}
}
