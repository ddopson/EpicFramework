package com.epic.framework.implementation;

import com.epic.framework.common.util.exceptions.EpicNativeMethodMissingImplementation;

public class EpicFontImplementationNative {
	
	public static Object UIFont(String name, int size) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");
	}

	public static Object UIFontWithSize(Object fontObject, int size) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");		
	}

	public static int measureAscent(Object fontObject) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");
	}

	public static int measureDescent(Object fontObject) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");
	}	

	public static int measureAdvance(Object fontObject, String text) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");		
	}
}
