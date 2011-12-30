package com.epic.framework.implementation;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.util.EpicFail;
import com.epic.resources.EpicFiles;

public class EpicFontImplementation {
	private static final BufferedImage fakeBitmap = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	private static final Graphics2D fakeGraphics = fakeBitmap.createGraphics();

	public static Object getFontObjectFromFile(EpicFile file) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, file.openAsInputStream());
		} catch (FontFormatException e) {
			throw EpicFail.framework("FontFormatException", e);
		} catch (IOException e) {
			throw EpicFail.framework("IOException reading font", e);
		}
	}
	public static Object getFontObjectFromSize(Object fontObject, int size) {
		Font font = (Font)fontObject;
		return font.deriveFont((float)size);
	}
	public static int measureHeight(Object fontObject) {
		Font font = (Font)fontObject;
		FontMetrics metrics = fakeGraphics.getFontMetrics(font);
		return metrics.getAscent() + metrics.getDescent();
	}
	public static int measureAscent(Object fontObject) {
		Font font = (Font)fontObject;
		FontMetrics metrics = fakeGraphics.getFontMetrics(font);
		return metrics.getAscent();
	}

	public static int measureAdvance(Object fontObject, String text) {
		Font font = (Font)fontObject;
		FontMetrics metrics = fakeGraphics.getFontMetrics(font);
		return metrics.stringWidth(text);
	}
	
	public static int measureAdvance(Object fontObject, char[] chars, int offset, int length) {
		Font font = (Font)fontObject;
		FontMetrics metrics = fakeGraphics.getFontMetrics(font);
		return metrics.charsWidth(chars, offset, length);
	}
	
	public static int getSize(Object fontObject) {
		Font font = (Font)fontObject;
		return font.getSize();
	}
	public static Object getFontObjectFromName(String systemName) {
		return EpicFontImplementation.getFontObjectFromFile(EpicFiles.Nunito);
	}
}
