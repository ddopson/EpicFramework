package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassEpicAction extends EpicClass {
	public static final ClassEpicAction singleton = new ClassEpicAction();
	
	@Override
	public EpicObject inflate(JSONObject data) {
		throw new EpicObjectInflationException("Can't inflate abstract class");
	}
	
	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.EpicAction", singleton);
	}
}
