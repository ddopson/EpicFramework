package com.epic.framework.implementation;

import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicBitmapInstance;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicBufferedReader;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;

public class EpicBitmapImplementation {

	public static Object loadBitmap(EpicBitmapInstance eb) {
		// DDOPSON-2012-01-01 - we should always "scale" the bitmap into a buffer, else it might be decompressed on every render
		// see http://developer.apple.com/library/ios/#qa/qa1708/_index.html
		// DDOPSON-2012-01-14 - also, note that during the scaling, the bitmap gets converted from kCGImageAlphaLast ==> kCGImageAlphaPremultipliedFirst

		UIImage src = UIImage.imageNamed(eb.parent.getFilename());
		EpicFail.assertNotNull(src, "src");
		UIImage scaled = EpicBitmapImplementationNative.resizeImage(src, eb.iwidth, eb.iheight);//, epicBitmap.opaque);
		EpicFail.assertNotNull(scaled, "scaled");
//		EpicLog.i("Image: " + epicBitmap.name);
		EpicCanvasImplementationNative.inspectImage(src);
//		EpicCanvasImplementationNative.inspectImage(scaled);
		return scaled;
	}

	public static void recycle(Object platformObject) {
		
	}

	public static EpicBitmap loadBitmapFromUrl(String url) {
		throw EpicFail.not_implemented();
	}
}
