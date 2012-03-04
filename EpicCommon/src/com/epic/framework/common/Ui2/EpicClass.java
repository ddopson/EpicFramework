package com.epic.framework.common.Ui2;

import org.json.JSONException;
import org.json.JSONObject;

import com.epic.framework.build.EpicInflatableClass;

@EpicInflatableClass
public abstract class EpicClass extends EpicObject {
	protected EpicClass() {
		this.type = ClassEpicClass.singleton;
	}
	
	public abstract EpicObject inflate(JSONObject data) throws JSONException;

	public String getName() { return ""; }
	
	public abstract Object newArray(int length);
	
	public boolean isInstanceOf(EpicObject object) { return true; }
}
