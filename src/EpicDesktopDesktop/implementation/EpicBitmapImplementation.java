package com.epic.framework.implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicBitmapInstance;
import com.epic.framework.common.util.EpicFail;


public class EpicBitmapImplementation {
	static String magicBaseDirectory = null; // will be set during Main()
	
	private static BufferedImage _loadBitmap(EpicBitmapInstance eb) {
		String path = eb.parent.getFilename(magicBaseDirectory);
		File file = new File(path);
		if(!file.exists()) {
			throw EpicFail.invalid_argument("Image file for '" + eb.parent.getFilename() + "' does not exist at '"+path+"'");
		}
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			throw EpicFail.framework("caught IOException while trying to read '" + eb.parent.getFilename() + "'", e);
		}
	}

	public static Object loadBitmap(EpicBitmapInstance eb) {
		BufferedImage original = _loadBitmap(eb);

		if(original.getWidth() != eb.iwidth || original.getHeight() != eb.iheight) {
			BufferedImage scaled = new BufferedImage(eb.iwidth, eb.iheight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = scaled.createGraphics();
			graphics.drawImage(original, 0, 0, eb.iwidth, eb.iheight, null);
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
