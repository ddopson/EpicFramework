package com.epic.framework.common.Ui2;

import com.epic.framework.vendor.org.json.*;

import com.epic.framework.common.Ui.EpicImage;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassEpicBitmap extends EpicClass {

	public static ClassEpicBitmap singleton = new ClassEpicBitmap();

	@Override
	public EpicObject inflate(JSONObject data) {
		throw new EpicObjectInflationException("Can't inflate EpicBitmap from object yet");
	}
	
	public static EpicImage[] inflateArray(JSONArray array) {
		int length = array.length();
		EpicImage[] realArray = new EpicImage[length];
		for(int i = 0; i < length; i++) {
			Object el = array.opt(i);
			if(el instanceof JSONObject) {
				EpicObject o = Registry.inflate((JSONObject)el);
				if(o instanceof EpicImage) {
					realArray[i] = (EpicImage)o;
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
	
	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicBitmap", singleton);
	}

	@Override
	public Object newArray(int length) {
		// TODO Auto-generated method stub
		return null;
	}

}
