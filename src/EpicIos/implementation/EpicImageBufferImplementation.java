package com.epic.framework.implementation;

import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.CGRect;
import org.xmlvm.iphone.CGSize;
import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.resources.EpicImages;

public class EpicImageBufferImplementation {
	CGContext platformGraphicsObject = null;
	UIImage platformBitmapObject = null;
	
	public EpicImageBufferImplementation(int width, int height, boolean opaque) {
		// all the interesting stuff lives in Objective-C
		EpicImageBufferImplementationNative.createImageBuffer(this, width, height, opaque);
		CGRect clip = platformGraphicsObject.getClip();
		EpicLog.i("Clip is (" + clip.origin.x + ", " + clip.origin.y + ") " + clip.size.width + " x " + clip.size.height);
		CGSize bs = platformBitmapObject.getSize();
		EpicLog.i("Bitmap size is " + bs.width + " x " + bs.height);
		platformGraphicsObject.drawImage(new CGRect(0, 0, 480, 320), ((UIImage)EpicImages.bg_main_menu.getPlatformObject(480, 320)).getCGImage());
		
	}
	
	public Object getPlatformBitmapObject() {
		return EpicFail.assertNotNull(platformBitmapObject, "platformBitmapObject shouldn't be null");
	}

	public Object getPlatformGraphicsObject() {
		return EpicFail.assertNotNull(platformGraphicsObject, "platformGraphicsObject shouldn't be null");
	}
}
