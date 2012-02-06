package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui2.JSON.JSONObject;

public abstract class EpicClass extends EpicObject {
	protected EpicClass() {
		this.type = ClassEpicClass.singleton;
	}
	
	public abstract EpicObject inflate(JSONObject data);
}
