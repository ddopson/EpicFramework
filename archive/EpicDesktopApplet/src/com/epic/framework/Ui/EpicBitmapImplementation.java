package com.epic.framework.Ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.epic.framework.util.EpicFail;
import com.epic.framework.util.EpicLog;


public class EpicBitmapImplementation {
	private static String magicBaseDirectory = "./images";

	public static void loadBitmap(EpicBitmap epicBitmap) {
//		File file = new File(epicBitmap.getFilename(magicBaseDirectory));
//		if(!file.exists()) {
//			String cwd;
//			try {
//				cwd = new java.io.File( "." ).getCanonicalPath();
//			} catch (IOException e) {
//				cwd = "ERROR";
//			}
//
//			throw EpicFail.invalid_argument("Image file for '" + epicBitmap.getFilename() + "' does not exist in '"+cwd+"/images/'");
//		}
//		BufferedImage nativeImage;
//		try {
//			nativeImage = ImageIO.read(file);
//		} catch (IOException e) {
//			throw EpicFail.framework("caught IOException while trying to read '" + epicBitmap.getFilename() + "'", e);
//		}
		BufferedImage image;
//		= AppletMain.loadImage(epicBitmap);
		try {
			image = ImageIO.read(EpicBitmapImplementation.class.getResource("/" + epicBitmap.getFilename()));
		} catch (IOException e) {
			throw EpicFail.missing_image("Failed to load '" + epicBitmap.name + "'");
		}
		epicBitmap.setPlatformObject(image);
	}

	public static Object resize(Object platformObject, int neededInternalWidth, int neededInternalHeight) {
		Image original = (Image)platformObject;
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
		Image original = (Image)epicBitmap.platformObject;
		while(original.getWidth(null) == -1) {
			EpicLog.w("Waiting on image load for '" + epicBitmap.name + "'");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		
		if(original.getWidth(null) != neededInternalWidth || original.getHeight(null) != neededInternalHeight) {
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
