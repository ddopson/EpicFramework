package com.epic.framework.common.Ui;

import java.util.LinkedList;

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



	//	private static LinkedList<EpicBitmap> preloadQueueBitmap = new LinkedList<EpicBitmap>();
	//	private static LinkedList<Integer> preloadQueueWidth = new LinkedList<Integer>();
	//	private static LinkedList<Integer> preloadQueueHeight = new LinkedList<Integer>();
	//	static Runnable bgThread = null;
	//
	//	private void _preload(int width, int height) {
	//		EpicLog.i("preloading SOON: " + this.name);
	//		synchronized(preloadQueueBitmap) {
	//			preloadQueueBitmap.add(this);
	//			preloadQueueWidth.add(width);
	//			preloadQueueHeight.add(height);
	//		}
	//		if(bgThread == null) {
	//			bgThread = new Runnable() {
	//				public void run() {
	//					boolean done = false;
	//					do {
	//						EpicBitmap bitmap;
	//						Integer width;
	//						Integer height;
	//						synchronized(preloadQueueBitmap) {
	//							bitmap = preloadQueueBitmap.poll();
	//							width = preloadQueueWidth.poll();
	//							height = preloadQueueHeight.poll();
	//							if(bitmap == null) {
	//								// we are going to exit
	//								bgThread = null;
	//								done = true;
	//							}
	//						}
	//						if(bitmap != null) {
	//							bitmap.getPlatformObject(width, height);
	//							if(preloadQueueBitmap.peek() == null) {
	//								try {
	//									Thread.sleep(20);
	//								} catch (InterruptedException e) { }
	//							}
	//						}
	//					} while(!done);
	//					EpicLog.i("SOON loader finished.");
	//				}
	//			};
	//			EpicPlatform.runInBackground(bgThread);
	//		}
	//	}
}
