package com.epic.framework.implementation;

import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.util.EpicFail;

public abstract class EpicImplementationNative {	
	public static UIImage resizeImage(UIImage src, int width, int height) {
		throw EpicFail.not_implemented();
	}

	public static void setupDebugHandlers() {
		throw EpicFail.not_implemented();
	}
}
