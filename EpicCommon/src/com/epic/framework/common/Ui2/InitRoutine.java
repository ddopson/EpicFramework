package com.epic.framework.common.Ui2;

import com.epic.framework.vendor.org.json.JSONException;

public class InitRoutine {
	public static void init() throws JSONException {
		ClassEpicButtonWidget.register();
		ClassEpicBitmap.register();
		ClassEpicAction.register();
		ClassEpicClass.register();
		ClassEpicScreenObject.register();
		ClassEpicWidget.register();
		ClassEpicBackgroundWidget.register();
	}
}
