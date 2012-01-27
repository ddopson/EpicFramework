package com.epic.framework.implementation;

import java.awt.image.BufferedImage;

public class EpicImageBufferImplementation {
	private final BufferedImage bi;
	public EpicImageBufferImplementation(int width, int height, boolean opaque) {
		bi = new BufferedImage(width, height, opaque ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
	}

	public Object getPlatformBitmapObject() {
		return bi;
	}

	public Object getPlatformGraphicsObject() {
		return bi.getGraphics();
	}

}
