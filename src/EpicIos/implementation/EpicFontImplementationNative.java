package com.epic.framework.implementation;

import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.CGFont;

import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.util.exceptions.EpicNativeMethodMissingImplementation;

public class EpicFontImplementationNative {
	
	public static CGFont CGFontCreateFromName(String name) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");
	}

	public static void CGContextSetFont(Object fontObject, CGContext context) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");
	}
	
	public static int CGFontGetAscent(Object fontObject) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");
	}

	public static int CGFontGetDescent(Object fontObject) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");
	}	

	public static int CGFontGetUnitsPerEm(Object fontObject) {
		throw new EpicNativeMethodMissingImplementation("EpicFontImplementationNative");
	}	
}
