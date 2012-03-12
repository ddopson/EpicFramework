package com.epic.framework.implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.epic.framework.common.Ui.EpicImage;
import com.epic.framework.common.Ui.EpicImageFromResource;
import com.epic.framework.common.Ui.EpicImageFromUrl;
import com.epic.framework.common.Ui.EpicImageInstance;
import com.epic.framework.common.util.EpicFail;


public class EpicBitmapImplementation {
	static String magicBaseDirectory = null; // will be set during Main()
	
	private static BufferedImage _loadBitmap(EpicImageFromResource image) {
		String path = image.getFilename(magicBaseDirectory);
		File file = new File(path);
		if(!file.exists()) {
			throw EpicFail.invalid_argument("Image file for '" + image.getFilename() + "' does not exist at '"+path+"'");
		}
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			throw EpicFail.framework("caught IOException while trying to read '" + image.getFilename() + "'", e);
		}
	}

	public static Object loadBitmap(EpicImageFromResource image, EpicImageInstance instance) {
		BufferedImage original = _loadBitmap(image);

		if(original.getWidth() != instance.iwidth || original.getHeight() != instance.iheight) {
			BufferedImage scaled = new BufferedImage(instance.iwidth, instance.iheight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = scaled.createGraphics();
			graphics.drawImage(original, 0, 0, instance.iwidth, instance.iheight, null);
			graphics.dispose();
			return scaled;
		}
		else {
			return original;
		}
	}

	public static void recycle(Object platformObject) {
	}

	public static EpicImageInstance loadImageFromUrl(EpicImageFromUrl image) {
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
