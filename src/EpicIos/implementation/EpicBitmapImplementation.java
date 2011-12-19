package com.epic.framework.implementation;

import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.Ui.EpicBitmap;

public class EpicBitmapImplementation {

	public static void loadBitmap(EpicBitmap epicBitmap) {
		epicBitmap.setPlatformObject(UIImage.imageNamed(epicBitmap.name + "." + epicBitmap.extension));
	}

	public static Object loadBitmap(EpicBitmap epicBitmap, int neededInternalWidth, int neededInternalHeight) {
		return UIImage.imageNamed(epicBitmap.name + "." + epicBitmap.extension);	
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
