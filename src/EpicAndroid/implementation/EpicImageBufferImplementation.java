package com.epic.framework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class EpicImageBufferImplementation {
	Bitmap bitmap;
	public EpicImageBufferImplementation(int iwidth, int iheight, boolean opaque) {
		bitmap = Bitmap.createBitmap(iwidth, iheight, Bitmap.Config.ARGB_8888);
	}

	public Object getPlatformBitmapObject() {
		return bitmap;
	}

	public Object getPlatformGraphicsObject() {
		return new Canvas(bitmap);
	}

}
