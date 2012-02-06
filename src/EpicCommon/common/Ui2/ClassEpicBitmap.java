package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui2.JSON.JSONArray;
import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;
import com.epic.resources.EpicImages;

public class ClassEpicBitmap extends EpicClass {

	public static ClassEpicBitmap singleton = new ClassEpicBitmap();

	@Override
	public EpicObject inflate(JSONObject data) {
		throw new EpicObjectInflationException("Can't inflate EpicBitmap from object yet");
	}
	
	public static EpicBitmap[] inflateArray(JSONArray array) {
		int length = array.length();
		EpicBitmap[] realArray = new EpicBitmap[length];
		for(int i = 0; i < length; i++) {
			Object el = array.opt(i);
			if(el instanceof JSONObject) {
				EpicObject o = Registry.inflate((JSONObject)el);
				if(o instanceof EpicBitmap) {
					realArray[i] = (EpicBitmap)o;
				} else {
					throw new EpicObjectInflationException("element " + i + " is not of type EpicBitmap");
				}
			} else if(el == null || el == JSONObject.NULL) {
				realArray[i] = null;
			} else {
				throw EpicFail.unhandled_case();
			}
		}
		return realArray;
	}
	
	public static EpicBitmap inflateField(JSONObject data, String fieldName, int flags) {
		Object fieldData = data.opt(fieldName);

		if (fieldData instanceof String) {
			return EpicBitmap.lookupByNameOrThrow((String)fieldData);
		} else {
			EpicObject fieldObject = Registry.inflateField(data, fieldName, flags);
			if(fieldObject instanceof EpicBitmap) {
				return (EpicBitmap)fieldObject;
			} else {
				throw new EpicObjectInflationException("Field '" + fieldName + "' must be of type 'EpicBitmap'");
			}			
		}
	}
	
	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicBitmap", singleton);
	}

}
