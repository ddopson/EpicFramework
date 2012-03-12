package com.epic.framework.implementation;

import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.Ui.EpicImageFromResource;
import com.epic.framework.common.Ui.EpicImageFromUrl;
import com.epic.framework.common.Ui.EpicImageInstance;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicBufferedReader;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;

public class EpicBitmapImplementation {

	public static Object loadBitmap(EpicImageFromResource image, EpicImageInstance instance) {
		// DDOPSON-2012-01-01 - we should always "scale" the bitmap into a buffer, else it might be decompressed on every render
		// see http://developer.apple.com/library/ios/#qa/qa1708/_index.html
		// DDOPSON-2012-01-14 - also, note that during the scaling, the bitmap gets converted from kCGImageAlphaLast ==> kCGImageAlphaPremultipliedFirst

		UIImage src = UIImage.imageNamed(image.getFilename());
		EpicFail.assertNotNull(src, "src");
		UIImage scaled = EpicBitmapImplementationNative.resizeImage(src, instance.iwidth, instance.iheight);//, epicBitmap.opaque);
		EpicFail.assertNotNull(scaled, "scaled");
//		EpicLog.i("Image: " + epicBitmap.name);
//		EpicCanvasImplementationNative.inspectImage(src);
//		EpicCanvasImplementationNative.inspectImage(scaled);
		return scaled;
	}

	public static void recycle(Object platformObject) {
		
	}

	public static EpicImageInstance loadImageFromUrl(EpicImageFromUrl image) {
		throw EpicFail.not_implemented();
	}
}
