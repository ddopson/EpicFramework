package com.epic.framework.implementation;

import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.util.EpicFail;

public class EpicBitmapImplementation {

	public static void loadBitmap(EpicBitmap epicBitmap) {
		UIImage img = UIImage.imageNamed(epicBitmap.name + "." + epicBitmap.extension);
		EpicFail.assertNotNull(img, "img");
		epicBitmap.setPlatformObject(img);
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
