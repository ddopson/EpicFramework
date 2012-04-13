package com.epic.framework.build;

import java.util.ArrayList;

import com.epic.framework.vendor.org.json.simple.JSONArray;
import com.epic.framework.vendor.org.json.simple.JSONObject;

public class EpicClassDescription {
	String qualifiedName;
	String parent;
	boolean isAbstract;
	boolean inflatable;
	ArrayList<EpicFieldDescription> fields = new ArrayList<EpicFieldDescription>();
	
	public String getGeneratedClassName() {
		return qualifiedName.replaceAll("[.]([^.]+)$", ".Class$1");
	}
	
	public JSONObject toJSON() {
		final JSONObject o = new JSONObject();
		final int di = qualifiedName.lastIndexOf(".");
		o.put("name", qualifiedName.substring(di + 1));
		o.put("fullname", qualifiedName);
		o.put("package", qualifiedName.substring(0, di));
		o.put("parent", parent);
		o.put("abstract", isAbstract);
		o.put("inflatable", inflatable);
		final JSONArray fieldsJson = new JSONArray();
		for(final EpicFieldDescription fd : this.fields) {
			fieldsJson.add(fd.toJSON());
		}
		o.put("fields", fieldsJson);
		return o;
	}
}
