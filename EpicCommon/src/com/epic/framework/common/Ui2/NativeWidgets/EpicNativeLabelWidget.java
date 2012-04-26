package com.epic.framework.common.Ui2.NativeWidgets;

import com.epic.framework.common.EpicFieldInflation;
import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui2.EpicNativeWidget;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.implementation.EpicNativeLabelWidgetImplementation;

@EpicInflatableClass()
public class EpicNativeLabelWidget extends EpicNativeWidget {
		
	@EpicFieldInflation(ignore=true)
	Object platformObject;
	String text;
	
	public EpicNativeLabelWidget(String text) {
		this.text = text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		String text = EpicNativeLabelWidgetImplementation.getText(platformObject);
		if(text != null) {
			return text;
		} else {
			return "";
		}
	}
	
	@Override
	public Object getNativeObject() {
		if(platformObject == null) {
			platformObject = EpicNativeLabelWidgetImplementation.createNativeLabel(this.text);
			EpicFail.assertNotNull(platformObject);
		}
		return platformObject;
	}
}
