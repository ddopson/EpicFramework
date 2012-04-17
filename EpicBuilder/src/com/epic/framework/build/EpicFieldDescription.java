package com.epic.framework.build;

import com.epic.framework.vendor.org.json.simple.JSONObject;

public class EpicFieldDescription {
	public String name;
	public String type;
	public boolean isInflatable;
	
	public Object toJSON() {
		JSONObject o = new JSONObject();
		o.put("name", name);
		o.put("type", type);
		o.put("isInflatable", isInflatable);
		return o;
	}
}