package com.epic.framework.common.Ui;


public class EpicBitmapPreloader {
	public int sum = 0;
	public boolean prepareOnly = false;
	public void preload(EpicBitmap bitmap, int width, int height) {
		if(!prepareOnly) {
			bitmap.getPlatformObject(EpicPlatform.scaleLogicalToRenderX(width), EpicPlatform.scaleLogicalToRenderY(height));
		}
		sum += EpicPlatform.scaleLogicalToRenderX(width) * EpicPlatform.scaleLogicalToRenderY(height);
	}
	public void preload(EpicAnimationSequence animation, int width, int height) {
		for(int n = 0; n < animation.frames.length; n++) {
			this.preload(animation.getFrame(n), width, height);
		}
	}
	public int getSum() {
		return sum;
	}
}
