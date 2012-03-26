package com.epic.framework.common.Ui2;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.epic.framework.common.JSON;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;
import com.epic.framework.vendor.org.json.simple.JSONArray;
import com.epic.framework.vendor.org.json.simple.JSONObject;
import com.epic.framework.vendor.org.json.simple.JSONException;

public class Registry {
	private static HashMap<String, EpicObject> registry = new HashMap<String, EpicObject>();

	public static EpicObject get(String name) {
		return registry.get(name);
	}

	public static void register(String name, EpicObject object) {
		registry.put(name, object);
	}

	public static void processJSONStream(InputStream in) throws JSONException, IOException {
		Object o = JSON.parse(in);
		processJSON(o);
	}
	public static void processJSON(Object o) throws JSONException {
		if(o instanceof JSONObject) {
			JSONObject data = (JSONObject)o;
			Registry.inflate(data);
		} else if (o instanceof JSONArray) {
			JSONArray array = (JSONArray)o;
			int length = array.size();
			for(int i = 0; i < length; i++) {
				processJSON(array.get(i));
			}
		} else {
			EpicLog.w("WARNING: unable to interpret " + o.getClass().getCanonicalName());
		}
	}

	public static EpicObject inflateField(JSONObject data, String fieldName, EpicClass type, int flags) {
		Object fieldData = data.get(fieldName);

		if(fieldData == null) {
			if(data.containsKey(fieldName)) {
				if((flags & EpicObject.FIELDMASK_NULLABLE) > 0) {
					throw new EpicObjectInflationException("Field '" + fieldName + "' must be non-null");
				} else {
					return null;
				}

			} else {
				if((flags & EpicObject.FIELDMASK_REQUIRED) > 0) {
					throw new EpicObjectInflationException("Field '" + fieldName + "' must be specified");
				} else {
					return null;
				}
			}
		} else if (fieldData instanceof JSONObject) {
			return Registry.inflate((JSONObject)fieldData);
		} else if (fieldData instanceof String) {
			String name = (String)fieldData;
			EpicObject object = get(name);
			if(object == null) {
				// TODO: loading order ..... how to handle loading A and B where A.other = "B", and B.other = "A". 
				// We might need a lazy load of object contents or something else "interesting".
				// option 1) we load all objects into the registry uninitialized. only then do we run the actual inflation logic
				// option 2) we insert placeholders for missing references
				// goal 1) good error messages when a name is fat-fingered
				// goal 2) support the {A.other = "B", B.other = "A"} scenario
				throw new EpicObjectInflationException("Error inflating field '" + fieldName + "': No object named '" + name + "' was found in the registry");
			} else if (!type.isInstanceOf(object)) {
				throw new EpicObjectInflationException("Error inflating field '" + fieldName + "': Field expected type '" + type.getName() + "'. The object named '" + name + "' was of type '" + object.type.getName() + "'");				
			}
			return object;
		} else {
			String actualType = fieldData.getClass().getName();
			throw new EpicObjectInflationException("Field '" + fieldName + "' was expected to be an object of type '" + type.getName() + "'. Data was of type '" + actualType + "'");
		}
	}

	public static EpicObject inflate(JSONObject data) {
		EpicObject object;
		String name, typeName;
		EpicClass typeObject;

		if(data == null) {
			return null;
		}

		name = (String)data.get("name");
		typeName = (String)data.get("type");

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

			try {
				object = typeObject.inflate(data);
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}

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