package com.epic.framework.implementation;

import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.CGPoint;
import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.util.exceptions.EpicNativeMethodMissingImplementation;

public class EpicCanvasImplementationNative {
	private static final String __className = "EpicCanvasImplementationNative";
	public static void drawImage(CGContext c, UIImage src, int x, int y, int alpha) {
		throw new EpicNativeMethodMissingImplementation(__className);
	}

	public static void setCrop(CGContext graphicsObject, int x, int y, int sw, int sh) {
		throw new EpicNativeMethodMissingImplementation(__className);
	}

	public static void restoreContext(CGContext graphicsObject) {
		throw new EpicNativeMethodMissingImplementation(__className);
	}

	public static void inspectImage(UIImage uii) {
		throw new EpicNativeMethodMissingImplementation(__className);
	}

	public static void drawTextAtPoint(String text, Object fontObject, int x, int y) {
		throw new EpicNativeMethodMissingImplementation(__className);
	}
	
	public static void drawTextInRect(String text, Object fontObject, int x, int y, int width, int height) {
		throw new EpicNativeMethodMissingImplementation(__className);
	}
}
