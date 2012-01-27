package com.epic.framework.implementation;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.Ui.EpicFont;
import com.epic.framework.common.util.EpicFail;
import com.epic.resources.EpicFiles;

public class EpicFontImplementation {
	private static final BufferedImage fakeBitmap = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	private static final Graphics2D fakeGraphics = fakeBitmap.createGraphics();

	public static Object getFontObjectFromName(String name, int size) {
		return EpicFontImplementation.getFontObjectFromFile(EpicFiles.Nunito, size);
	}
	
	public static Object getFontObjectFromFile(EpicFile file, int size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, file.openAsInputStream());
		} catch (FontFormatException e) {
			throw EpicFail.framework("FontFormatException", e);
		} catch (IOException e) {
			throw EpicFail.framework("IOException reading font", e);
		}
	}
	
	public static Object getFontObjectFromSize(EpicFont epicFont, int size) {
		Font font = (Font)epicFont.fontObject;
		return font.deriveFont((float)size);
	}
	
	public static int measureHeight(EpicFont font) {
		return measureAscent(font) + measureDescent(font);
	}

	public static int measureAscent(EpicFont font) {
		return fakeGraphics.getFontMetrics((Font)font.fontObject).getAscent();
	}

	public static int measureDescent(EpicFont font) {
		return fakeGraphics.getFontMetrics((Font)font.fontObject).getDescent();
	}
	
	public static int measureAdvance(EpicFont font, String text) {
		FontMetrics metrics = fakeGraphics.getFontMetrics((Font)font.fontObject);
		return metrics.stringWidth(text);
	}
	
	public static int measureAdvance(EpicFont font, char[] chars, int offset, int length) {
		FontMetrics metrics = fakeGraphics.getFontMetrics((Font)font.fontObject);
		return metrics.charsWidth(chars, offset, length);
	}
}
