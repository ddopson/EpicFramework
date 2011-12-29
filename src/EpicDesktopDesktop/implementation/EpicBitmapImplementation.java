package com.epic.framework.implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.util.EpicFail;


public class EpicBitmapImplementation {
	static String magicBaseDirectory = null; // will be set during Main()
	
	public static void loadBitmap(EpicBitmap epicBitmap) {
		String path = epicBitmap.getFilename(magicBaseDirectory);
		File file = new File(path);
		if(!file.exists()) {
			String cwd;
			try {
				cwd = new java.io.File( "." ).getCanonicalPath();
			} catch (IOException e) {
				cwd = "ERROR";
			}

			throw EpicFail.invalid_argument("Image file for '" + epicBitmap.getFilename() + "' does not exist at '"+path+"'");
		}
		BufferedImage nativeImage;
		try {
			nativeImage = ImageIO.read(file);
		} catch (IOException e) {
			throw EpicFail.framework("caught IOException while trying to read '" + epicBitmap.getFilename() + "'", e);
		}
		epicBitmap.setPlatformObject(nativeImage);
	}
	
	public static Object getImageBuffer(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	public static Object getBufferCanvasObject(Object bufferObject) {
		BufferedImage bi = (BufferedImage)bufferObject;
		return bi.getGraphics();
	}

	public static Object resize(Object platformObject, int neededInternalWidth, int neededInternalHeight) {
		BufferedImage original = (BufferedImage)platformObject;
		BufferedImage scaled = new BufferedImage(neededInternalWidth, neededInternalHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = scaled.createGraphics();
		graphics.drawImage(original, 0, 0, neededInternalWidth, neededInternalHeight, null);
		graphics.dispose();
		return scaled;
	}

	public static Object loadBitmap(EpicBitmap epicBitmap, int neededInternalWidth, int neededInternalHeight) {
		if(epicBitmap.platformObject == null) {
			loadBitmap(epicBitmap);
		}
		BufferedImage original = (BufferedImage)epicBitmap.platformObject;

		if(original.getWidth() != neededInternalWidth || original.getHeight() != neededInternalHeight) {
			BufferedImage scaled = new BufferedImage(neededInternalWidth, neededInternalHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = scaled.createGraphics();
			graphics.drawImage(original, 0, 0, neededInternalWidth, neededInternalHeight, null);
			graphics.dispose();
			return scaled;
		}
		else {
			return original;
		}
	}

	public static void recycle(Object platformObject) {
	}

	public static EpicBitmap loadBitmapFromUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	
//	public static EpicBitmapImplementation fromPixels(int[] bits, int width, int height, String name) {
//		BufferedImage bitmap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//		bitmap.setRGB(0, 0, width, height, bits, 0, width);
//		EpicBitmapImplementation epicBitmap = new EpicBitmapImplementation();
//		epicBitmap.name = name;
//		epicBitmap.nativeImage = bitmap;
//		return epicBitmap;
//	}
//
//
//	public void getPixels(int[] pixels, int width, int height) {
//		EpicFail.assertEqual(width, nativeImage.getWidth(), "width mismatch");
//		EpicFail.assertEqual(height, nativeImage.getHeight(), "height mismatch");
//		nativeImage.getRGB(0, 0, width, height, pixels, 0, width);
//	}
}
