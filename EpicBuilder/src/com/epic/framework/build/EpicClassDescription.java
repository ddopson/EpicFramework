package com.epic.framework.build;

import java.util.ArrayList;
import java.util.Collection;

import com.epic.framework.vendor.org.json.simple.JSONArray;
import com.epic.framework.vendor.org.json.simple.JSONObject;

public class EpicClassDescription {
	String inflationArgumentsType;
	String inflationMode;
	String qualifiedName;
	String parent;
	boolean isAbstract;
	boolean inflatable;
	Collection<EpicFieldDescription> fields = new ArrayList<EpicFieldDescription>();
	
	public String getGeneratedClassName() {
		return qualifiedName.replaceAll("[.]([^.]+)$", ".Class$1");
	}
	
	public String getShortName() {
		int di = qualifiedName.lastIndexOf(".");
		return qualifiedName.substring(di + 1);
	}
	
	public String getPackage() {
		int di = qualifiedName.lastIndexOf(".");
		return qualifiedName.substring(0, di);
	}
	
	public JSONObject toJSON() {
		final JSONObject o = new JSONObject();
		o.put("shortName", getShortName());
		o.put("shortGeneratedName", "Class" + getShortName());
		o.put("qualifiedName", qualifiedName);
		o.put("inflationMode", inflationMode);
		o.put("inflationArgumentsType", inflationArgumentsType);
		o.put("package", getPackage());
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
