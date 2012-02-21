package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui2.JSON.JSONException;
import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassEpicButtonWidget extends EpicClass {
	public static final ClassEpicButtonWidget singleton = new ClassEpicButtonWidget();
	
	@Override
	public EpicObject inflate(JSONObject data) {
		EpicButtonWidget object = new EpicButtonWidget();

		try {
			ClassEpicWidget.addFields(object, data);
		} catch (JSONException e) {
			throw new EpicObjectInflationException("suberror", e);
		}
		
		object.action = (EpicAction) Registry.inflateField(data, "action", ClassEpicAction.singleton, EpicObject.FIELD_NULLABLE);
		object.bitmap_default = (EpicBitmap) Registry.inflateField(data, "bitmap_default", ClassEpicBitmap.singleton, EpicObject.FIELD_REQUIRED);
		object.bitmap_focused = (EpicBitmap) Registry.inflateField(data, "bitmap_focused", ClassEpicBitmap.singleton, EpicObject.FIELD_NULLABLE);
		object.bitmap_pressed = (EpicBitmap) Registry.inflateField(data, "bitmap_pressed", ClassEpicBitmap.singleton, EpicObject.FIELD_REQUIRED);
		
		return object;
	}

	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicButtonWidget", singleton);
	}
}
