package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.Ui.EpicFont;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;

public class EpicFontImplementation {
	public static Object getFontObjectFromFile(EpicFile file, int size) {
		if(file.getFilename().equals("Nunito.ttf")) {
			return getFontObjectFromName("Nunito", size);	
		} else if(file.getFilename().equals("LuckiestGuy.ttf")) {
			return getFontObjectFromName("Luckiest Guy", size);	
		} else {
			EpicLog.e("Do not recognize font name: " + file.getFilename() + ", trying to load anyways...");
			return getFontObjectFromName(file.getFilename(), size);
		}
	}

	public static Object getFontObjectFromName(String systemName, int size) {
		return EpicFontImplementationNative.UIFont(systemName, size);
	}

	public static Object getFontObjectFromSize(EpicFont font, int size) {
		return  EpicFontImplementationNative.UIFontWithSize(font.fontObject, size);
	}

	public static int measureHeight(EpicFont font) {
		return measureAscent(font) + measureDescent(font);
	}

	public static int measureAscent(EpicFont font) {
		return EpicFontImplementationNative.measureAscent(font.fontObject);
	}

	public static int measureDescent(EpicFont font) {
		return -EpicFontImplementationNative.measureDescent(font.fontObject);
	}

	public static int measureAdvance(EpicFont font, String text) {
		return EpicFontImplementationNative.measureAdvance(font.fontObject, text);
	}

	public static int measureAdvance(Object graphicsObject, EpicFont font, String text) {
		return EpicFontImplementationNative.measureAdvance(font.fontObject, text);
	}

	public static int measureAdvance(Object graphicsObject, EpicFont font, char[] chars, int offset, int length) {
		throw EpicFail.not_supported();
	}
}
