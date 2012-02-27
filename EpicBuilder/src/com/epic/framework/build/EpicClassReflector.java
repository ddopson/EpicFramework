package com.epic.framework.build;

import java.lang.reflect.*;
import org.json.*;

public class EpicClassReflector {
	public EpicClassReflector() {
		
	}
	
	public String inspect(Class<?> clazz) throws JSONException {
		JSONObject classDescription = new JSONObject();
		classDescription.put("name", clazz.getSimpleName());
		classDescription.put("package", clazz.getPackage().getName());
		classDescription.put("abstract", Modifier.isAbstract(clazz.getModifiers()));
		classDescription.put("parent", clazz.getSuperclass().getName());
		JSONArray fields = new JSONArray();
		classDescription.put("fields", fields);
		for(Field f : clazz.getDeclaredFields()) {
			if(!Modifier.isStatic(f.getModifiers())) { // exclude static fields
				JSONObject jf = new JSONObject();
				jf.put("name", f.getName());
				jf.put("type", f.getType().getName());
				jf.put("modifiers", f.getModifiers());
				fields.put(jf);
			}
		}
		return classDescription.toString();
	}
	
	// TODO:
	// inheritance - need to initialize fields of base classes
	// arrays
	
	// DONE:
	// basics
}
