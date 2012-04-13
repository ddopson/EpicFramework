package com.epic.framework.build;

import com.epic.framework.vendor.org.json.simple.JSONObject;

public class EpicFieldDescription {
	String name;
	String type;
	public Object toJSON() {
		JSONObject o = new JSONObject();
		o.put("name", name);
		o.put("type", type);
		return o;
	}
}