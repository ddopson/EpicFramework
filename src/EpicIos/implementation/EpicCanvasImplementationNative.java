package com.epic.framework.implementation;

import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.CGPoint;
import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.util.exceptions.EpicNativeMethodMissingImplementation;

public class EpicCanvasImplementationNative {
	public static void drawImage(CGContext c, UIImage src, int x, int y, int alpha) {
		throw new EpicNativeMethodMissingImplementation("EpicBitmapImplementationNative");
	}

	public static void setCrop(CGContext graphicsObject, int x, int y, int sw, int sh) {
		throw new EpicNativeMethodMissingImplementation("EpicBitmapImplementationNative");
	}

	public static void restoreContext(CGContext graphicsObject) {
		throw new EpicNativeMethodMissingImplementation("EpicBitmapImplementationNative");
	}

	public static void inspectImage(UIImage uii) {
		throw new EpicNativeMethodMissingImplementation("EpicBitmapImplementationNative");
	}
}
