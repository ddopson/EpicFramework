package com.epic.framework.common.Ui;

import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicStopwatch;
import com.epic.framework.common.util.StringHelper;
import com.epic.framework.common.util.exceptions.EpicRuntimeException;
import com.epic.framework.implementation.EpicBitmapImplementation;
import com.epic.framework.implementation.EpicImageBufferImplementation;

public class EpicImageBuffer extends EpicBitmap {
	private final EpicCanvas canvas = new EpicCanvas();
	
	public EpicImageBuffer(String name, int width, int height, boolean opaque) {
		this(name, width, height, opaque, 0, 0, 0, 0);
	}
	
	public EpicImageBuffer(String name, int iwidth, int iheight, boolean opaque, int lpad, int tpad, int rpad, int bpad) {
		super(name, "BUFFER", -1, iwidth+lpad+rpad, iheight+tpad+bpad, lpad, tpad, rpad, bpad);
		EpicImageBufferImplementation implementation = new EpicImageBufferImplementation(iwidth, iheight, opaque);
		this.platformObject = implementation.getPlatformBitmapObject();
		canvas.graphicsObject = implementation.getPlatformGraphicsObject();
//		EpicLog.i("Creating ImageBuffer["+name+"] log:" + iwidth + "x" + iheight);
	}

	public EpicCanvas getCanvas() {
		return canvas;
	}

	@Override
	public EpicBitmapInstance getInstance(int desiredWidth, int desiredHeight) {
		if(this.width != desiredWidth || this.height != desiredHeight) {
			throw EpicFail.invalid_argument("Dimension Mismatch: " + StringHelper.namedArgList("width", width, "height", height, "desiredWidth", desiredWidth, "desiredHeight", desiredHeight));
		}

		return this;
	}

	public int recycle() {
		// NEVER recycle ...
		this.lastRender = EpicStopwatch.getMonotonicN();
		return 0;
	}
}
