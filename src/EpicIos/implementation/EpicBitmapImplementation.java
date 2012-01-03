package com.epic.framework.implementation;

import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.util.EpicFail;

public class EpicBitmapImplementation {

	public static void loadBitmap(EpicBitmap epicBitmap) {
		// DDOPSON-2012-01-01 - we should always "scale" the bitmap into a buffer, else it might be decompressed on every render
		// see http://developer.apple.com/library/ios/#qa/qa1708/_index.html
		
		Object platformObject = loadBitmap(epicBitmap, epicBitmap.internal_width, epicBitmap.internal_height);
		epicBitmap.setPlatformObject(platformObject);
	}

	public static Object loadBitmap(EpicBitmap epicBitmap, int neededInternalWidth, int neededInternalHeight) {
		UIImage src = UIImage.imageNamed(epicBitmap.name + "." + epicBitmap.extension);	
		EpicFail.assertNotNull(src, "src");
		UIImage scaled = EpicBitmapImplementationNative.resizeImage(src, neededInternalWidth, neededInternalHeight);
		EpicFail.assertNotNull(scaled, "scaled");
		return scaled;
	}

	public static void recycle(Object platformObject) {
		
	}

	public static EpicBitmap loadBitmapFromUrl(String url) {
		throw EpicFail.not_implemented();
	}

	public static Object getBufferCanvasObject(Object platformObject) {
		throw EpicFail.not_implemented();
	}

	public static Object getImageBuffer(int i, int j) {
		throw EpicFail.not_implemented();
	}

}
