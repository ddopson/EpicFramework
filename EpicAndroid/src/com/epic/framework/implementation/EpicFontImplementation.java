package com.epic.framework.implementation;

import java.security.KeyStore.LoadStoreParameter;
import java.util.HashMap;

import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.Ui.EpicFont;
import com.epic.framework.common.util.EpicLog;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class EpicFontImplementation {
	private final Typeface typeface;
	
	private static HashMap<String, Typeface> fontMap = new HashMap<String, Typeface>();
	private static final int defaultTextSize = 99;
	
	private EpicFontImplementation(Typeface typeface, int textSize) {
		this.typeface = typeface;
	}
	
	private static Paint fakePaint = new Paint();

	public static Object getFontObjectFromSize(Object baseFontObject, int textSize) {
		EpicFontImplementation baseFont = (EpicFontImplementation)baseFontObject;
		return new EpicFontImplementation(baseFont.typeface, textSize);
	}

	public static int measureHeight(EpicFont epicFont) {
		fakePaint.setTypeface((Typeface)epicFont.fontObject);
		fakePaint.setTextSize(epicFont.size_absolute);
		return (int)(-fakePaint.ascent() + fakePaint.descent());
	}
	
	public static int measureAscent(EpicFont epicFont) {
		fakePaint.setTypeface((Typeface)epicFont.fontObject);
		fakePaint.setTextSize(epicFont.size_absolute);
		return (int)(-fakePaint.ascent());
	}
	
	public static int measureDescent(EpicFont epicFont) {
		fakePaint.setTypeface((Typeface)epicFont.fontObject);
		fakePaint.setTextSize(epicFont.size_absolute);
		return (int)(-fakePaint.descent());
	}

	public static int measureAdvance(EpicFont epicFont, String text) {
		fakePaint.setTypeface((Typeface)epicFont.fontObject);
		fakePaint.setTextSize(epicFont.size_absolute);
		return (int)fakePaint.measureText(text);
	}

	public static int measureAdvance(EpicFont epicFont, char[] chars, int offset, int length) {
		fakePaint.setTypeface((Typeface)epicFont.fontObject);
		fakePaint.setTextSize(epicFont.size_absolute);
		return (int)fakePaint.measureText(chars, offset, length);
	}

	public static Object getFontObjectFromFile(EpicFile file, int size) {
		if(!fontMap.containsKey(file.getFilename())) {
			fontMap.put(file.getFilename(), Typeface.createFromAsset(EpicAndroidActivity.getCurrentAndroidActivity().getAssets(), file.getFilename()));
		}
		return new EpicFontImplementation(fontMap.get(file.getFilename()), size);
	}

	public static void setAndroidFont(Paint paint, EpicFont epicFont) {
		paint.setTypeface((Typeface)epicFont.fontObject);
		paint.setTextSize(epicFont.size_absolute);
	}

	public static Object getFontObjectFromName(String name, int size) {
		return null;
	}
}
