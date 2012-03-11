package com.epic.framework.common.Ui2;


import com.epic.framework.build.EpicInflatableClass;
import com.epic.framework.vendor.org.json.JSONException;
import com.epic.framework.vendor.org.json.JSONObject;

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
