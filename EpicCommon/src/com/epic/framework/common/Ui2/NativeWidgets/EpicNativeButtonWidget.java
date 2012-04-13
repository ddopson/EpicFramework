package com.epic.framework.common.Ui2.NativeWidgets;

import com.epic.framework.common.EpicFieldInflation;
import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui2.EpicAction;
import com.epic.framework.common.Ui2.EpicNativeWidget;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.implementation.EpicNativeButtonWidgetImplementation;

@EpicInflatableClass()
public class EpicNativeButtonWidget extends EpicNativeWidget {
	
	String text;
	
	@EpicFieldInflation(ignore=true)
	Object platformObject;
	
	void foo () {
		ClassEpicNativeButtonWidget f;
	}
	
	@Override
	public Object getNativeObject() {
		if(platformObject == null) {
			platformObject = EpicNativeButtonWidgetImplementation.createNativeButton(text);
			EpicFail.assertNotNull(platformObject);
		}
		return platformObject;
	}
}
