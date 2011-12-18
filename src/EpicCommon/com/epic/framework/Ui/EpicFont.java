
package com.epic.framework.Ui;

import java.util.HashMap;

import com.epic.framework.util.EpicFail;
import com.epic.resources.EpicFiles;
import com.epic.cfg.EpicPlatformConfig;

public class EpicFont {
	private static final HashMap<Integer, EpicFont> sizedFonts = new HashMap<Integer, EpicFont>();
	public static final int VALIGN_CENTER = 1;
	public static final int VALIGN_TOP = 2;
	public static final int VALIGN_BOTTOM = 3;
	
	public static final int HALIGN_RIGHT = 11;
	public static final int HALIGN_LEFT = 12;
	public static final int HALIGN_CENTER = 13;
	
	public static final EpicFont FONT_MAIN = EpicPlatformConfig.platform == EpicPlatformConfig.PLATFORM_BLACKBERRY ? new EpicFont("BBAlpha Sans") : new EpicFont(EpicFiles.Nunito);
	public static final EpicFont FONT_GAME = new EpicFont(EpicFiles.LuckiestGuy);
	
	public final Object fontObject;
	public EpicFont(Object platformObject) {
		this.fontObject = platformObject;
		EpicFail.assertNotNull(fontObject);
	}
	
	private EpicFont(EpicFile file) {
		this(EpicFontImplementation.getFontObjectFromFile(file));
	}
	
	private EpicFont(String systemName) {
		this(EpicFontImplementation.getFontObjectFromName(systemName));
	}
	
	public static EpicFont fromSize(EpicFont baseFont, int size) {
		int width_ratio = size * EpicPlatform.getPlatformWidth() / EpicPlatform.designWidth;
		int height_ratio = size * EpicPlatform.getPlatformHeight() / EpicPlatform.designHeight;
		
		return new EpicFont(EpicFontImplementation.getFontObjectFromSize(baseFont.fontObject, ((width_ratio + height_ratio) / 2)));
//		if(!sizedFonts.containsKey(size)) {
//			EpicFont epicFont = new EpicFont(EpicFontImplementation.getFontObjectFromSize(baseFont, size));
//			sizedFonts.put(size, epicFont);
//		}
//		return sizedFonts.get(size);
	}
	
	public EpicFont withSize(int size) {
		return fromSize(this, size);
	}

	public int measureHeight() {
		return EpicFontImplementation.measureHeight(fontObject);
	}
	
	public int measureAscent() {
		return EpicFontImplementation.measureAscent(fontObject);
	}
	
	public int measureAdvance(String text) {
		return EpicFontImplementation.measureAdvance(fontObject, text);
	}

	public int measureAdvance(char[] chars, int offset, int length) {
		return EpicFontImplementation.measureAdvance(fontObject, chars, offset, length);
	}

	public EpicFont findBestFittingFont(String text, int width, int height) {
		int size = height;
		while(fromSize(this, size).measureHeight() < EpicPlatform.scaleLogicalToRealY(height)) {
			size++;
		}
		while(fromSize(this, size).measureAdvance(text) > EpicPlatform.scaleLogicalToRealX(width)) {
			size--;
		}
		return fromSize(this, size);
	}

	public int getSize() {
		return EpicFontImplementation.getSize(fontObject);
	}
}
