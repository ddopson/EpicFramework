package com.epic.framework.common.Ui2;

import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicCanvas;

@EpicInflatableClass
public abstract class EpicWidget extends EpicObject {
	public int x, y, width, height;
	public EpicAction onClick;

	public static int WidgetTypeNative = 1001;
	public static int WidgetTypeVirtual = 1002;
	public abstract int getWidgetType();
}
