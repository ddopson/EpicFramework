package com.epic.framework.common.Ui2;

import java.io.InputStream;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui2.JSON.JSONException;
import com.epic.framework.common.Ui2.JSON.JSONTokener;
import com.epic.resources.EpicImages;

public class InitRoutine {
	public static void init() throws JSONException {
		ClassEpicButtonWidget.register();
		ClassEpicBitmap.register();
		ClassEpicAction.register();
		ClassEpicClass.register();
		ClassEnsureLoginAction.register();
		ClassActionChangeScreen.register();
		ClassStartGameAction.register();
		ClassEpicScreenObject.register();
		ClassEpicWidget.register();
		ClassEpicBackgroundWidget.register();
		
		
		EpicBitmap b = EpicImages.rare_seed_chain_reaction; // trigger static init
		
		InputStream in = FakeMainMenu.class.getResourceAsStream("testdata.json");
		JSONTokener tokenizer = new JSONTokener(in);
		Object o = tokenizer.nextValue();
		Registry.processJSON(o);

	}
}
