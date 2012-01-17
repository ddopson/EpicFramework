package com.epic.framework.common.Ui;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicStopwatch;
import com.epic.framework.implementation.EpicBitmapImplementation;
import com.epic.framework.implementation.EpicImageBufferImplementation;

public class EpicImageBuffer extends EpicBitmap {
	private final EpicCanvas canvas = new EpicCanvas();
	
	public EpicImageBuffer(String name, int width, int height, boolean opaque) {
		super(name, "BUFFER", -1, width, height, 0, 0, 0, 0);
		EpicImageBufferImplementation implementation = new EpicImageBufferImplementation(width, height, opaque);
		this.platformObject = implementation.getPlatformBitmapObject();
		canvas.graphicsObject = implementation.getPlatformGraphicsObject();
	}

	public EpicCanvas getCanvas() {
		return canvas;
	}

	public Object getPlatformObject(int desiredWidth, int desiredHeight) {
		EpicFail.assertEqual(desiredWidth, this.width, "DesiredWidth != width");
		EpicFail.assertEqual(desiredHeight, this.height, "desiredHeight != height");
		return this.platformObject;
	}

	public int recycle() {
		// NEVER recycle ...
		this.lastRender = EpicStopwatch.getMonotonicN();
		return 0;
	}
}
