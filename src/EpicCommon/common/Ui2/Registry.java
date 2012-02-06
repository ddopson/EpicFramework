package com.epic.framework.common.Ui2;

import java.util.HashMap;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui.EpicFont;
import com.epic.framework.common.Ui.EpicMenu;
import com.epic.framework.common.Ui.EpicPercentLayout;
import com.epic.framework.common.Ui.MouseTrail;
import com.epic.framework.common.Ui2.JSON.JSONArray;
import com.epic.framework.common.Ui2.JSON.JSONException;
import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;


public class Registry {
	private static HashMap<String, EpicObject> registry = new HashMap<String, EpicObject>();
	
	public static EpicObject get(String name) {
		return registry.get(name);
	}

	public static void register(String name, EpicObject object) {
		registry.put(name, object);
	}
	
	public static void processJSON(Object o) throws JSONException {
		if(o instanceof JSONObject) {
			JSONObject data = (JSONObject)o;
			Registry.inflate(data);
		} else if (o instanceof JSONArray) {
			JSONArray array = (JSONArray)o;
			int length = array.length();
			for(int i = 0; i < length; i++) {
				processJSON(array.get(i));
			}
		} else {
			EpicLog.w("WARNING: unable to interpret " + o.getClass().getCanonicalName());
		}
	}

	
	public static EpicObject inflateField(JSONObject data, String fieldName, int flags) {
		Object fieldData = data.opt(fieldName);
		
		if(fieldData == null) {
			if((flags & EpicObject.FIELDMASK_REQUIRED) > 0) {
				throw new EpicObjectInflationException("Field '" + fieldName + "' must be specified");
			} else {
				return null;
			}
		} else if (fieldData == JSONObject.NULL) {
			if((flags & EpicObject.FIELDMASK_NULLABLE) > 0) {
				throw new EpicObjectInflationException("Field '" + fieldName + "' must be non-null");
			} else {
				return null;
			}			
		} else if (fieldData instanceof JSONObject) {
			return Registry.inflate((JSONObject)fieldData);
		} else {
			throw new EpicObjectInflationException("Field '" + fieldName + "' was expected to be an object");			
		}
	}
	
	public static EpicObject inflate(JSONObject data) {
		EpicObject object;
		String name, typeName;
		EpicClass typeObject;
		
		if(data == null) {
			return null;
		}

		name = data.optString("name", null);
		typeName = data.optString("type", null);

		// Three cases:
		// 1) 'name' only - look up in registry
		// 2) 'type' only - create anonymous object
		// 3) 'type' and 'name' - create object and register it
		
		if(typeName != null) {
			// case #2/#3
			EpicObject o = get(typeName);
			if(o == null) {
				throw new EpicObjectInflationException("Type '" + typeName + "' not found in the registry");
			} else if (o instanceof EpicClass) {
				typeObject = (EpicClass)o;
			} else {
				throw new EpicObjectInflationException("Type '" + typeName + "' found in the registry, but was not instanceof EpicClass");
			}
			
			object = typeObject.inflate(data);
			
			if(name != null) {
				// case #3
				register(name, object);
			}
		} else if (name != null) {
			object = get(name);
		}
		else {
			throw new EpicObjectInflationException("Unable to inflate object.  did not find 'type' or 'name'");
		}
		
		object.initialize();
		return object;
	}
}

/*
{
	name: "Screens/MainMenu",
	type: "com.epic.framework.common.Ui.EpicScreen",
	widgets: [
		{
			type: "com.epic.framework.common.Ui.EpicImageView",
			image: "bg_main_menu",
			x: 0,
			y: 0,
			width: 1024,
			height: 768
		},
		{
			type: "com.epic.framework.common.Ui.EpicButtonView",
			image_base: "main_menu_button_game", 
			x: 222,
			y: 300,
			width: 100,
			height: 50
			action: {
				type: "com.rgc.EnsureLoginAction",
				success: {
					name: "actions/doThatThing"
				}
			}
		}
	]
}


*/