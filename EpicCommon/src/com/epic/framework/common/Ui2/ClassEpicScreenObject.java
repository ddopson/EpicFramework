package com.epic.framework.common.Ui2;

import com.epic.framework.vendor.org.json.simple.*;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassEpicScreenObject extends EpicClass {
	public static final ClassEpicScreenObject singleton = new ClassEpicScreenObject();
	
	@Override
	public EpicObject inflate(JSONObject data) {
		EpicScreenObject object = new EpicScreenObject();
		
		Object widgetsData = data.get("widgets");
		if(widgetsData instanceof JSONArray) {
			JSONArray widgetsArray = (JSONArray)widgetsData;
			object.widgets = inflateWidgetArray(widgetsArray);
		} else {
			throw new EpicObjectInflationException("field 'widgets' must be an array");
		}
		
		return object;
	}
	
	public static EpicWidget[] inflateWidgetArray(JSONArray array) {
	int length = array.size();
	EpicWidget[] realArray = new EpicWidget[length];
	for(int i = 0; i < length; i++) {
		Object el = array.get(i);
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
	
	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicScreenObject", singleton);
	}

	@Override
	public Object newArray(int length) {
		// TODO Auto-generated method stub
		return null;
	}

}
