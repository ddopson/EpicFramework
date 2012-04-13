package com.epic.framework.common.Ui2;

import com.epic.framework.common.EpicInflatableClass;

@EpicInflatableClass
public abstract class EpicNativeWidget extends EpicWidget {
	public abstract Object getNativeObject();

	public int getWidgetType() { return EpicWidget.WidgetTypeNative; }
}
