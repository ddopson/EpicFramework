package com.epic.framework.common.Ui2;

import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicCanvas;

@EpicInflatableClass
public abstract class EpicVirtualWidget extends EpicWidget {
	public abstract void onPaint(EpicCanvas canvas);
	
	public int getWidgetType() { return EpicWidget.WidgetTypeVirtual; }
}
