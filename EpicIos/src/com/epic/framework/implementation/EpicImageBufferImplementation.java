package com.epic.framework.implementation;

import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.CGImage;
import org.xmlvm.iphone.CGRect;
import org.xmlvm.iphone.CGSize;
import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;

public class EpicImageBufferImplementation {
	CGContext platformGraphicsObject = null;
	UIImage platformBitmapObject = null;

	public EpicImageBufferImplementation(int width, int height, boolean opaque) {
		// all the interesting stuff lives in Objective-C
		EpicImageBufferImplementationNative.createImageBuffer(this, width, height, opaque);
		//		CGRect clip = platformGraphicsObject.getClip();
		//		EpicLog.i("Clip is (" + clip.origin.x + ", " + clip.origin.y + ") " + clip.size.width + " x " + clip.size.height);
		//		CGSize bs = platformBitmapObject.getSize();
		//		EpicLog.i("Bitmap size is " + bs.width + " x " + bs.height);
		//		UIImage bg = UIImage.imageNamed(EpicImages.bg_main_menu.getFilename());
		//		CGImage cg = bg.getCGImage();
		//		EpicLog.i("bg size is " + bg.getSize().width + " x " + bg.getSize().height);
		//		EpicLog.i("cg size is " + cg.getWidth() + " x " + cg.getHeight());
		//		CGRect smallrect = new CGRect(0, 0, 480, 320);
		//		platformGraphicsObject.drawImage(smallrect, cg);

	}

	public Object getPlatformBitmapObject() {
		return EpicFail.assertNotNull(platformBitmapObject, "platformBitmapObject shouldn't be null");
	}

	public Object getPlatformGraphicsObject() {
		return EpicFail.assertNotNull(platformGraphicsObject, "platformGraphicsObject shouldn't be null");
	}
}
