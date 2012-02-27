package com.example.HelloUIv2;

import com.epic.framework.common.Ui2.EpicClass;
import com.epic.framework.common.Ui2.EpicObject;
import com.epic.framework.common.Ui2.Registry;
import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class ClassSampleWidget extends EpicClass {
	public static final ClassSampleWidget singleton = new ClassSampleWidget();
	
	@Override
	public EpicObject inflate(JSONObject data) {
		return new SampleWidget();
	}
	
	public static void register() {
		Registry.register("com.example.HelloUIv2.SampleWidget", singleton);
	}
}
