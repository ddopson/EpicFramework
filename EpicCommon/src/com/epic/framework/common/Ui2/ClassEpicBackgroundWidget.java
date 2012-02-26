package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassEpicBackgroundWidget extends EpicClass {
	public static final ClassEpicBackgroundWidget singleton = new ClassEpicBackgroundWidget();
	
	@Override
	public EpicObject inflate(JSONObject data) {
		EpicBackgroundWidget object = new EpicBackgroundWidget();

		object.bitmap = (EpicBitmap) Registry.inflateField(data, "bitmap", ClassEpicBitmap.singleton, EpicObject.FIELD_REQUIRED);
		
		return object;
	}

	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicBackgroundWidget", singleton);
	}

}
