package com.epic.framework.Ui;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;

import com.epic.framework.util.EpicFail;
import com.epic.framework.util.EpicLog;


public class EpicBitmapImplementation {


	public static void loadBitmap(EpicBitmap epicBitmap) {
		EpicLog.i("loading bitmap: " + epicBitmap.name);
		Bitmap bbbitmap = Bitmap.getBitmapResource(epicBitmap.getFilename());
		if(bbbitmap == null) {
			throw EpicFail.missing_image(epicBitmap.getFilename());
		}
		epicBitmap.setPlatformObject(bbbitmap);
	}

	static Object resize(Object sourcePlatformObject, int width, int height) {
		Bitmap original = (Bitmap)sourcePlatformObject;
		Bitmap scaled = new Bitmap(width, height);
		// Bitmap.scaleInto causes weird artifacts with transparency, so we do it the hard way...
		//			this.original.scaleInto(scaled, Bitmap.FILTER_BOX);
		int ow = original.getWidth();
		int oh = original.getHeight();
		int[] originalPix = new int[ow * oh];
		original.getARGB(originalPix, 0, ow, 0, 0, ow, oh);
		int[] scaledPix = new int[width * height];
		int errors=0;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int _ox_12_12 = x * (ow-1);
				int _oy_12_12 = y * (oh-1);
				int iox = _ox_12_12 / (width-1);
				int ioy = _oy_12_12 / (height-1);
				int rem_x = _ox_12_12 % (width-1);
				int rem_y = _oy_12_12 % (height-1);
				int arem_x = (width-1) - rem_x;
				int arem_y = (height-1) - rem_y;
				int k = (width - 1)*(height - 1);

				int red = 0;
				int green = 0;
				int blue = 0;
				int alpha = 0;

				try {
					int p0 = originalPix[ioy * ow + (iox)];
					int a0 = EpicColor.getAlpha(p0);
					int w0 = (arem_x * arem_y * a0 >> 4);
					red += EpicColor.getRed(p0) * w0;
					green += EpicColor.getGreen(p0) * w0;
					blue += EpicColor.getBlue(p0) * w0;
					alpha += w0;

					if(rem_x > 0) {
						int p1 = originalPix[(ioy) * ow + (iox+1)];
						int a1 = EpicColor.getAlpha(p1);
						int w1 = rem_x * arem_y * a1 >> 4;
						red += EpicColor.getRed(p1) * w1;
						green += EpicColor.getGreen(p1) * w1;
						blue += EpicColor.getBlue(p1) * w1;
						alpha += w1;
					}

					if(rem_y > 0) {
						int p2 = originalPix[(ioy+1) * ow + (iox)];
						int a2 = EpicColor.getAlpha(p2);
						int w2 = arem_x * rem_y * a2 >> 4;
						red += EpicColor.getRed(p2) * w2;
						green += EpicColor.getGreen(p2) * w2;
						blue += EpicColor.getBlue(p2) * w2;
						alpha += w2;
					}

					if(rem_x > 0 && rem_y > 0) {
						int p3 = originalPix[(ioy+1) * ow + (iox+1)];
						int a3 = EpicColor.getAlpha(p3);
						int w3 = rem_x * rem_y * a3 >> 4;
						red += EpicColor.getRed(p3) * w3;
						green += EpicColor.getGreen(p3) * w3;
						blue += EpicColor.getBlue(p3) * w3;
						alpha += w3;
					}
					if(alpha > 0) {
						red = red / alpha;
						green = green / alpha;
						blue = blue / alpha;
						scaledPix[y*width + x] = EpicColor.fromInts(alpha * 16 / k, red, green, blue);
					}
					else {
						scaledPix[y*width + x] = EpicColor.TRANSPARENT;
					}
				}
				catch(IndexOutOfBoundsException e) {
					EpicLog.e(e.toString());
					if(errors++ > 10) {
						throw EpicFail.framework("Ack, bugs again");
					}
				}
			}
		}
		scaled.setARGB(scaledPix, 0, width, 0, 0, width, height);
		return scaled;
	}

	public static Object loadBitmap(EpicBitmap epicBitmap, int neededInternalWidth, int neededInternalHeight) {
		EpicLog.i("loading bitmap: " + epicBitmap.name);
		if(epicBitmap.platformObject == null) {
			loadBitmap(epicBitmap);
		}
		Bitmap original = (Bitmap)epicBitmap.platformObject;
		if(original.getWidth() != neededInternalWidth || original.getHeight() != neededInternalHeight) {
			EpicFail.assertEqual(original.getWidth(), epicBitmap.getInternalWidth(), "bitmap width should be same as config says");
			EpicFail.assertEqual(original.getHeight(), epicBitmap.getInternalHeight(), "bitmap height should be same as config says");
			return resize(original, neededInternalWidth, neededInternalHeight);
		}
		else {
			return original;
		}
	}

	public static void recycle(Object platformObject) {	}

	public static Object getImageBuffer(int width, int height) {
		return new Bitmap(width, height);
	}

	public static Object getBufferCanvasObject(Object platformObject) {
		Bitmap bitmap = (Bitmap)platformObject;
		return Graphics.create(bitmap);
	}

	public static EpicBitmap loadBitmapFromUrl(String icon) {
		// TODO Auto-generated method stub
		return null;
	}

	//	public static EpicBitmapImplementation fromPixels(int[] ints, int width, int height, String name) {
	//		EpicLog.v("EpicBitmap.fromPixels() name=" + name + " width=" + width + "height=" + height);
	//		Bitmap bitmap = new Bitmap(width, height);
	//		bitmap.setARGB(ints, 0, width, 0, 0, width, height);
	//		return new EpicBitmapImplementation(bitmap, name);
	//	}

	//	public void getPixels(int[] pixels, int width, int height) {
	//		this.ensureLoaded();
	//		original.getARGB(pixels, 0, width, 0, 0, width, height);
	//	}
}
