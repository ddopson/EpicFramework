package com.epic.framework.Ui;

import com.epic.framework.util.EpicFail;
import com.epic.framework.util.EpicLog;
import com.epic.framework.util.EpicRuntimeException;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.FontManager;

public class EpicFontImplementation {
	
	public static int measureAdvance(Object fontObject, String text) {
		Font font = (Font)fontObject;
		return font.getAdvance(text);
	}

	public static int measureHeight(Object fontObject) {
		Font font = (Font)fontObject;
		return font.getHeight();
	}
	public static int measureAdvance(Object fontObject, char[] chars, int offset, int length) {
		Font font = (Font)fontObject;
		return font.getAdvance(chars, offset, length);
	}
	public static int getSize(Object fontObject) {
		Font font = (Font)fontObject;
		// TODO: no size on BB fonts, i think its height?
		return font.getHeight();
	}
	public static int measureAscent(Object fontObject) {
		Font font = (Font)fontObject;
		return font.getAscent();
	}

	private static String getCodeName(int code) {
		switch(code) {
		case FontManager.DUPLICATE_DATA:
			return "DUPLICATE_DATA";
		case FontManager.DUPLICATE_NAME:
			return "DUPLICATE_NAME";
		case FontManager.EXCEEDS_LIMIT:
			return "EXCEEDS_LIMIT";
		case FontManager.FAILED_TO_LOAD_FILE:
			return "FAILED_TO_LOAD_FILE";
		case FontManager.FONTS_ARRAY_FULL:
			return "FONTS_ARRAY_FULL";
		case FontManager.MISS_RESOURCE:
			return "MISS_RESOURCE";
		case FontManager.MISSING_TYPEFACE_NAME:
			return "MISSING_TYPEFACE_NAME";
		case FontManager.NO_FONT_DATA:
			return "NO_FONT_DATA";
		case FontManager.NO_PERMISSION:
			return "NO_PERMISSION";
		case FontManager.READ_FAIL:
			return "READ_FAIL";
		default:
			return "UNKNOWN";
		}
	}

	public static Object getFontObjectFromFile(EpicFile file) {
		EpicLog.v("TESTTESTTEST - testing the font system");
		try {
			FontFamily f = FontFamily.forName("boogelybogelyboo");
			EpicLog.v("grr, no exception. f = " + f + ", f.name=" + f.getName() + ", f.tfn=" + f.getTypefaceType());
		} catch (ClassNotFoundException e1) {
			EpicLog.v("As hoped for, we catch ClassNotFoundException");
		}
		
		int ret = FontManager.getInstance().load(file.openAsInputStream(), file.getFilename(), FontManager.APPLICATION_FONT);
//		if(ret != FontManager.SUCCESS) {
//			throw EpicFail.framework("Error loading font '" + file.getFilename() + "'.  errcode=" + ret + " (" + getCodeName(ret) + ")");
//		}
		try {
			Font f = FontFamily.forName(file.getFilename()).getFont(0, 12);
			return f;
		} catch (ClassNotFoundException e) {
			throw EpicFail.framework("Font Loading Failure.  FontFamily.forName failed", e);
		}
	}
	public static Object getFontObjectFromSize(Object fontObject, int size) {
		Font font = (Font)fontObject;
		return font.derive(Font.PLAIN, size);
	}
	public static Object getFontObjectFromName(String systemName) {
		try {
			FontFamily f = FontFamily.forName(systemName);
			EpicLog.v("Font " + systemName + " loaded. f = " + f + ", f.name=" + f.getName() + ", f.tfn=" + f.getTypefaceType());
			return f.getFont(Font.PLAIN, 12);
		} catch (ClassNotFoundException e1) {
			throw new EpicRuntimeException("Missing native font " + systemName);
		}
	}
}
