package com.epic.framework.Ui;

import java.security.KeyStore.LoadStoreParameter;
import java.util.HashMap;

import com.epic.framework.util.EpicLog;
import com.epic.resources.EpicFiles;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class EpicFontImplementation {
	private final Typeface typeface;
	private final int textSize;
	
	private static HashMap<String, Typeface> fontMap = new HashMap<String, Typeface>();
	private static final int defaultTextSize = 99;
	
	private EpicFontImplementation(Typeface typeface, int textSize) {
		this.typeface = typeface;
		this.textSize = textSize;
	}
	
	private static Paint fakePaint = new Paint();

	public static Object getFontObjectFromSize(Object baseFontObject, int textSize) {
		EpicFontImplementation baseFont = (EpicFontImplementation)baseFontObject;
		return new EpicFontImplementation(baseFont.typeface, textSize);
	}

	private static Rect _bounds = new Rect();;
	public static int measureHeight(Object fontObject) {
		EpicFontImplementation font = (EpicFontImplementation)fontObject;
		fakePaint.setTypeface(font.typeface);
		fakePaint.setTextSize(font.textSize);
		return (int)(-fakePaint.ascent() + fakePaint.descent());
	}
	
	public static int measureAscent(Object fontObject) {
		EpicFontImplementation font = (EpicFontImplementation)fontObject;
		fakePaint.setTypeface(font.typeface);
		fakePaint.setTextSize(font.textSize);
		return (int)(-fakePaint.ascent());
	}

	public static int measureAdvance(Object fontObject, String text) {
		EpicFontImplementation font = (EpicFontImplementation)fontObject;
		fakePaint.setTypeface(font.typeface);
		fakePaint.setTextSize(font.textSize);
		return (int)fakePaint.measureText(text);
	}

	public static int measureAdvance(Object fontObject, char[] chars, int offset, int length) {
		EpicFontImplementation font = (EpicFontImplementation)fontObject;
		fakePaint.setTypeface(font.typeface);
		fakePaint.setTextSize(font.textSize);
		return (int)fakePaint.measureText(chars, offset, length);
	}

	public static Object getFontObjectFromFile(EpicFile file) {
		if(!fontMap.containsKey(file.getFilename())) {
			fontMap.put(file.getFilename(), Typeface.createFromAsset(EpicAndroidActivity.getCurrentAndroidActivity().getAssets(), file.getFilename()));
		}
		return new EpicFontImplementation(fontMap.get(file.getFilename()), defaultTextSize);
	}

	public static int getSize(Object fontObject) {
		EpicFontImplementation font = (EpicFontImplementation)fontObject;
		return font.textSize;
	}

	public static void setAndroidFont(Paint paint, EpicFont epicFont) {
		EpicFontImplementation font = (EpicFontImplementation)epicFont.fontObject;
		paint.setTypeface(font.typeface);
		paint.setTextSize(font.textSize);
	}

	public static Object getFontObjectFromName(String systemName) {
		return null;
	}
}
