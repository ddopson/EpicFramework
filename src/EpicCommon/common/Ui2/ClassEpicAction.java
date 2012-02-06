package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassEpicAction extends EpicClass {

	@Override
	public EpicObject inflate(JSONObject data) {
		throw new EpicObjectInflationException("Can't inflate abstract class");
	}
	
	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicAction", new ClassEpicAction());
	}
	
	public static EpicAction inflateField(JSONObject data, String fieldName, int flags) {
		Object fieldObject = data.opt(fieldName);
		if(fieldObject == null) {
			throw new EpicObjectInflationException("Field '" + fieldName + "' must be specified, even if it is null");			
		} else if(fieldObject == JSONObject.NULL) {
			return null;	
		} else if (fieldObject instanceof JSONObject) {
			EpicObject o = Registry.inflate((JSONObject)fieldObject);
			if(o instanceof EpicAction) {
				return (EpicAction)o;
			} else {
				throw new EpicObjectInflationException("Field '" + fieldName + "' must be of type 'EpicAction'");
			}
		} else {
			throw new EpicObjectInflationException("Field '" + fieldName + "' must be of type 'EpicAction'. case2.");
		}
	}
}
