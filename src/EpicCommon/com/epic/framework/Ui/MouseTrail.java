package com.epic.framework.Ui;

import com.epic.framework.util.ArchPlatform;

public class MouseTrail {
	public static final int MIN_DRAG_LENGTH = 3;
	private int[] x = new int[10240];
	private int[] y = new int[10240];
	private int[] t = new int[10240];
	private long startTime;
	int l = 0;
	public MouseTrail() {	
	}
	public void clear() {
		l = 0;
	}
	public int size() {
		return l;
	}
	public int x(int i) {
		return x[i];
	}
	public int y(int i) {
		return y[i];
	}

	public int age(int i) {
		return age(i, ArchPlatform.getMilliseconds());
	}
	public int age(int i, long currentMillis) {
		return (int)(currentMillis - startTime) - t[i];
	}
	public void add(int x, int y) {
		if(l == 0 || this.x[l-1] != x || this.y[l-1] != y) {
			if(l == 0) {
				startTime = ArchPlatform.getMilliseconds();
			}
			if(l < this.x.length) {
				this.x[l] = x;
				this.y[l] = y;
				this.t[l] = (int)(ArchPlatform.getMilliseconds() - startTime);
				l++;	
			}
		}
	}
	public boolean isADrag() {
		return l > MIN_DRAG_LENGTH;
	}

//	private EpicBitmap renderCache = null;
//	private int lastRenderCacheIndex = 0;
//	public void render(EpicCanvas canvas, int width, int height, int color) {
//		long currentTime = ArchPlatform.getMilliseconds();
//
//		if(renderCache == null) {
//			// TODO: need to create a bitmap to drow into here.  ideally a 1-bit-per-pixel stencil map
//			renderCache = new 
//		}
//		for(int i = lastRenderCacheIndex; i < size(); i++) {
//
//			int alpha = 248 - age(i, currentTime);
//			if(alpha < 64) {
//				EpicCanvas bitmapCanvas;
//				canvas.drawLine(x[i-1], y[i-1], x[i], y[i], 6, EpicColor.BLACK);
//			}
//			else {
//				lastRenderCacheIndex = i;
//				// TODO // blit the bitmap modulated by the color
//				canvas.dr
//				break;
//			}
//		}
//		for(int i = lastRenderCacheIndex; i < size(); i++) {
//			int alpha = 248 - age(i, currentTime);
//			canvas.drawLine(x[i-1], y[i-1], x[i], y[i], 6, EpicColor.withAlpha(alpha, color));
//		}
//	}
}