package com.epic.framework.common.Ui2;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.common.Ui2.JSON.JSONArray;
import com.epic.framework.common.Ui2.JSON.JSONException;
import com.epic.framework.common.Ui2.JSON.JSONObject;
import com.epic.framework.common.Ui2.JSON.JSONTokener;
import com.epic.framework.common.util.EpicLog;

public class FakeMainMenu {
	
	public static void main(String[] args) throws JSONException {	
		InitRoutine.init();
		
		//		JSONObject data = new JSONObject();
		EpicScreen mm = (EpicScreen)Registry.get("screens/MainMenu");
		EpicLog.i("Hello Wordl");
		JSONArray classesJson = new JSONArray();
		
		@SuppressWarnings("unchecked")
		Class<? extends EpicObject>[] classes = (Class<? extends EpicObject>[])new Class[] {
//			EnsureLoginAction.class,
//			EpicAction.class,
//			EpicBackgroundWidget.class,
//			EpicBitmap.class,
//			EpicButtonWidget.class,
//			EpicClass.class,
			EpicScreenObject.class,
//			EpicWidget.class,
//			StartGameAction.class
		};
		for (Class<? extends EpicObject> clazz : classes) {
			classesJson.put(inspect(clazz));
		}
		System.out.println(classesJson.toString(2));
	}

	public static JSONObject inspect(Class<? extends EpicObject> clazz) throws JSONException {
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
				if(f.getAnnotation(OptionalField.class) != null) {
					EpicLog.i("optional: " + f.getName());
				}
				fields.put(jf);
			}
		}
		return classDescription;
	}
	
	// TODO:
	// inheritance - need to initialize fields of base classes
	// actions
}
