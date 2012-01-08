package com.epic.framework.implementation;

import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.CGPoint;
import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.util.exceptions.EpicNativeMethodMissingImplementation;

public class EpicCanvasImplementationNative {
	public static UIImage drawImage(UIImage src, int width, int height) {
		throw new EpicNativeMethodMissingImplementation("EpicBitmapImplementationNative");
	}

	public static void setCrop(CGContext graphicsObject, int x, int y, int sw, int sh) {
		throw new EpicNativeMethodMissingImplementation("EpicBitmapImplementationNative");
	}

	public static void restoreContext(CGContext graphicsObject) {
		throw new EpicNativeMethodMissingImplementation("EpicBitmapImplementationNative");
	}
}
