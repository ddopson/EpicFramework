package com.epic.framework.Ui;

import com.epic.framework.util.EpicFail;
import com.epic.framework.util.EpicStopwatch;

public class EpicImageBuffer extends EpicBitmap {
	private final EpicCanvas canvas = new EpicCanvas();
	
	public EpicImageBuffer(String name, int width, int height, int lpad, int tpad, int rpad, int bpad) {
		super(name, "BUFFER", -1, width, height, lpad, tpad, rpad, bpad);
		this.platformObject = EpicBitmapImplementation.getImageBuffer(width - lpad - rpad, height - tpad - bpad);
		canvas.graphicsObject = EpicBitmapImplementation.getBufferCanvasObject(this.platformObject);
	}
	
	public EpicImageBuffer(String name, int width, int height) {
		this(name, width, height, 0, 0, 0, 0);
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
