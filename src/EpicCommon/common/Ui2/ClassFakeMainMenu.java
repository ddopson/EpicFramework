package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui2.JSON.JSONObject;

public class ClassFakeMainMenu extends EpicClass {

	@Override
	public EpicObject inflate(JSONObject data) {
		return null;
	}
	
	public static void register() {
		Registry.register("com.epic.framework.common.Ui2.FakeMainMenu", new ClassFakeMainMenu());
	}
}
