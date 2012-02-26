package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassEpicClass extends EpicClass {
	public static ClassEpicClass singleton = new ClassEpicClass();
	
	@Override
	public EpicObject inflate(JSONObject data) {
		throw new EpicObjectInflationException("Can't inflate EpicClass objects");
	}
	
	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicClass", singleton);
	}

}
