package com.epic.framework.common.Ui;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.StringHelper;
import com.epic.framework.implementation.EpicImageBufferImplementation;

public class EpicImageBuffer extends EpicImageInstance {
	private final EpicCanvas canvas = new EpicCanvas();
	
	public EpicImageBuffer(String name, boolean opaque, int width, int height, int lpad, int tpad, int rpad, int bpad) {
		super(name, opaque, width, height, lpad, tpad, rpad, bpad);
		EpicImageBufferImplementation implementation = new EpicImageBufferImplementation(this.iwidth, this.iheight, opaque);
		this.platformObject = implementation.getPlatformBitmapObject();
		canvas.graphicsObject = implementation.getPlatformGraphicsObject();
//		EpicLog.i("Creating ImageBuffer["+name+"] log:" + iwidth + "x" + iheight);
	}

	public EpicCanvas getCanvas() {
		return canvas;
	}

	@Override
	public EpicImageInstance getInstance(int desiredWidth, int desiredHeight) {
		if(this.width != desiredWidth || this.height != desiredHeight) {
			throw EpicFail.invalid_argument("Dimension Mismatch: " + StringHelper.namedArgList("width", width, "height", height, "desiredWidth", desiredWidth, "desiredHeight", desiredHeight));
		}

		return this;
	}
}
