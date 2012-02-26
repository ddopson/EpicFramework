//package com.epic.framework.common.Ui2;
//
//import com.epic.framework.common.Ui2.JSON.JSONObject;
//import com.epic.framework.common.util.exceptions.EpicObjectInflationException;
//
//public class ClassStartGameAction extends EpicClass {
//	public static ClassStartGameAction singleton = new ClassStartGameAction();
//	
//	@Override
//	public EpicObject inflate(JSONObject data) {
//		return StartGameAction.singleton;
//	}
//	
//	public static void register() {
//		Registry.register("com.epic.framework.common.Ui2.StartGameAction", singleton);
//	}
//}
