package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui2.JSON.JSONArray;
import com.epic.framework.common.Ui2.JSON.JSONException;
import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassEpicWidget extends EpicClass {
	public static final ClassEpicWidget singleton = new ClassEpicWidget();
	
	@Override
	public EpicObject inflate(JSONObject data) {
		throw new EpicObjectInflationException("Can't inflate abstract class EpicWidget");
	}

	public static EpicWidget[] inflateArray(JSONArray array) {
		int length = array.length();
		EpicWidget[] realArray = new EpicWidget[length];
		for(int i = 0; i < length; i++) {
			Object el = array.opt(i);
			if(el instanceof JSONObject) {
				EpicObject o = Registry.inflate((JSONObject)el);
				if(o instanceof EpicWidget) {
					realArray[i] = (EpicWidget)o;
				} else {
					throw new EpicObjectInflationException("element " + i + " is not of type EpicWidget");
				}
			} else if(el == null || el == JSONObject.NULL) {
				realArray[i] = null;
			} else {
				throw EpicFail.unhandled_case();
			}
		}
		return realArray;
	}
	
	public static void addFields(EpicWidget object, JSONObject data) throws JSONException {
		object.x = data.getInt("x");
		object.y = data.getInt("y");
		object.width = data.getInt("width");
		object.height = data.getInt("height");
	}
	
	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicWidget", singleton);
	}
}
