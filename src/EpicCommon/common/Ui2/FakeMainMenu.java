package com.epic.framework.common.Ui2;

import java.io.InputStream;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.common.Ui2.JSON.JSONArray;
import com.epic.framework.common.Ui2.JSON.JSONException;
import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.Ui2.JSON.JSONTokener;
import com.epic.framework.common.util.EpicLog;
import com.epic.resources.EpicImages;

public class FakeMainMenu extends EpicScreenObject {
	
	public static void main(String[] args) throws JSONException {
		
		EpicBitmap b = EpicImages.rare_seed_chain_reaction; // trigger static init
		
		InputStream in = FakeMainMenu.class.getResourceAsStream("testdata.json");
		JSONTokener tokenizer = new JSONTokener(in);
		Object o = tokenizer.nextValue();
		Registry.processJSON(o);
		
		//		JSONObject data = new JSONObject();
		EpicScreen mm = (EpicScreen)Registry.get("screens/MainMenu");
		EpicLog.i("Hello Wordl");
	}
	
	// TODO:
	// inheritance - need to initialize fields of base classes
	// actions
}
