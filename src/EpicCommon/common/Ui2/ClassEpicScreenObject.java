package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui2.JSON.JSONArray;
import com.epic.framework.common.Ui2.JSON.JSONException;
import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassEpicScreenObject extends EpicClass {
	public static final ClassEpicScreenObject singleton = new ClassEpicScreenObject();
	
	@Override
	public EpicObject inflate(JSONObject data) {
		EpicScreenObject object = new EpicScreenObject();
		
		Object widgetsData = data.opt("widgets");
		if(widgetsData instanceof JSONArray) {
			JSONArray widgetsArray = (JSONArray)widgetsData;
			object.widgets = ClassEpicWidget.inflateArray(widgetsArray);
		} else {
			throw new EpicObjectInflationException("field 'widgets' must be an array");
		}
		
		return object;
	}
	
	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicScreenObject", singleton);
	}

}