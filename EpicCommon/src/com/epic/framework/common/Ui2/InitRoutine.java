package com.epic.framework.common.Ui2;

import java.io.InputStream;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui2.JSON.JSONException;
import com.epic.framework.common.Ui2.JSON.JSONTokener;

public class InitRoutine {
	public static void init() throws JSONException {
		ClassEpicButtonWidget.register();
		ClassEpicBitmap.register();
		ClassEpicAction.register();
		ClassEpicClass.register();
//		ClassEnsureLoginAction.register();
//		ClassStartGameAction.register();
		ClassEpicScreenObject.register();
		ClassEpicWidget.register();
		ClassEpicBackgroundWidget.register();
		
//		EpicImages.init();
	}
}
