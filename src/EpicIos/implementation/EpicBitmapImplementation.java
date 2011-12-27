package com.epic.framework.implementation;

import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.util.EpicLog;

public class EpicBitmapImplementation {

	public static void loadBitmap(EpicBitmap epicBitmap) {
		epicBitmap.setPlatformObject(UIImage.imageNamed(epicBitmap.name + "." + epicBitmap.extension));
	}

	public static Object loadBitmap(EpicBitmap epicBitmap, int neededInternalWidth, int neededInternalHeight) {
		UIImage src = UIImage.imageNamed(epicBitmap.name + "." + epicBitmap.extension);	
		UIImage scaled = EpicImplementationNative.resizeImage(src, neededInternalWidth, neededInternalHeight);
		return scaled;
	}

	public static void recycle(Object platformObject) {
		
	}

	public static EpicBitmap loadBitmapFromUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object getBufferCanvasObject(Object platformObject) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object getImageBuffer(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

}
