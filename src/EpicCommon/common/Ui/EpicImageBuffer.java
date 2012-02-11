package com.epic.framework.common.Ui;

import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicStopwatch;
import com.epic.framework.common.util.exceptions.EpicRuntimeException;
import com.epic.framework.implementation.EpicBitmapImplementation;
import com.epic.framework.implementation.EpicImageBufferImplementation;

public class EpicImageBuffer extends EpicBitmap {
	private final EpicCanvas canvas = new EpicCanvas();
	
	public EpicImageBuffer(String name, int width, int height, boolean opaque) {
		super(name, "BUFFER", -1, EpicPlatform.scaleLogicalToRenderX(width), EpicPlatform.scaleLogicalToRenderY(height), 0, 0, 0, 0);
		EpicImageBufferImplementation implementation = new EpicImageBufferImplementation(this.width, this.height, opaque);
		this.platformObject = implementation.getPlatformBitmapObject();
		canvas.graphicsObject = implementation.getPlatformGraphicsObject();
		EpicLog.i("Creating ImageBuffer["+name+"] log:" + width + "x" + height + " -> " + this.width + "x" + this.height);
	}

	public EpicCanvas getCanvas() {
		return canvas;
	}

	public EpicBitmapInstance getPlatformObject(int desiredWidth, int desiredHeight) {
		EpicFail.assertEqual(desiredWidth, this.width, "DesiredWidth != width for");
		EpicFail.assertEqual(desiredHeight, this.height, "desiredHeight != height");
		
		return this;
	}

	public int recycle() {
		// NEVER recycle ...
		this.lastRender = EpicStopwatch.getMonotonicN();
		return 0;
	}
}
