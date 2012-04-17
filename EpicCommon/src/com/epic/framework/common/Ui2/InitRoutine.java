package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui2.NativeWidgets.ClassEpicNativeButtonWidget;
import com.epic.framework.common.Ui2.VirtualWidgets.ClassEpicBackgroundWidget;
import com.epic.framework.vendor.org.json.simple.JSONException;

public class InitRoutine {
	public static void init() throws JSONException {
		ClassEpicAction.register();
		ClassEpicClass.register();
		ClassEpicScreenObject.register();
		ClassEpicWidget.register();
		ClassEpicBackgroundWidget.register();
		ClassEpicNativeButtonWidget.register();
		ClassEpicRestRequest.register();
	}
}
