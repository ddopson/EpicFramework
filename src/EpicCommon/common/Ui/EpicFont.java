
package com.epic.framework.common.Ui;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.StringHelper;
import com.epic.framework.implementation.EpicFontImplementation;
import com.epic.resources.EpicFiles;

public class EpicFont {
	//	private static final HashMap<Integer, EpicFont> sizedFonts = new HashMap<Integer, EpicFont>();
	public static final int VALIGN_CENTER = 1;
	public static final int VALIGN_TOP = 2;
	public static final int VALIGN_BOTTOM = 3;

	public static final int HALIGN_RIGHT = 11;
	public static final int HALIGN_LEFT = 12;
	public static final int HALIGN_CENTER = 13;

	public static final int MAX_SIZE = 120;

	public static final EpicFont FONT_MAIN = EpicPlatform.isBlackberry() ? EpicFont.fromName("BBAlpha Sans", 12) : EpicFont.fromFile(EpicFiles.Nunito, 12);
	public static final EpicFont FONT_GAME = EpicFont.fromFile(EpicFiles.LuckiestGuy, 12);

	public final String name;
	public final int size_absolute, size_relative, height, ascent, descent;
	public final Object fontObject;
	private final EpicFont[] sizes;

	private EpicFont(EpicFont base, String name, Object fontObject, int size) {
		this.name = name;
		this.fontObject = fontObject;
		this.size_absolute = size;
		this.size_relative = relsize(size);
		this.height = EpicFontImplementation.measureHeight(this);
		this.ascent = EpicFontImplementation.measureAscent(this);
		this.descent = EpicFontImplementation.measureDescent(this);
		this.sizes = base == null ? new EpicFont[MAX_SIZE + 1] : base.sizes;
		if(base == null) {
			EpicLog.i("Creating a new Font Class: " + name);
		}
		this.sizes[size] = this;
		EpicFail.assertNotNull(fontObject);
		EpicLog.i("Creating new Font Object of size " + size + " (" + StringHelper.namedArgList("size_relative", size_relative, "height", height, "ascent", ascent, "descent", descent) + ")");
	}

	private static int relsize(int size) {
		int width_ratio = size * EpicPlatform.getPlatformWidth() / EpicPlatform.designWidth;
		int height_ratio = size * EpicPlatform.getPlatformHeight() / EpicPlatform.designHeight;
		int relsize = (width_ratio + height_ratio) / 2;
		return relsize;
	}

	public static EpicFont fromFile(EpicFile file, int size) {
		Object platformObject = EpicFontImplementation.getFontObjectFromFile(file);
		return new EpicFont(null, file.getFilename(), platformObject, size);
	}

	public static EpicFont fromName(String name, int size) {
		Object platformObject = EpicFontImplementation.getFontObjectFromName(name);
		return new EpicFont(null, name, platformObject, size);
	}

	public EpicFont findBestFittingFont(String text, int width, int height) {
		EpicLog.i("EpicFont.findBestFittingFont(" + StringHelper.namedArgList("text", text, "width", width, "height", height) + ")");
		int size = height;
		while(size < MAX_SIZE && this.withSize(size).height < EpicPlatform.scaleLogicalToRealY(height)) {
			size++;
		}
		while(size > 1 && this.withSize(size).measureAdvance(text) > EpicPlatform.scaleLogicalToRealX(width)) {
			size--;
		}
		return this.withSize(size);
	}

	public EpicFont withSize(int size) {
		EpicFail.assertBounds(1, size, MAX_SIZE, "Font size must be 1 to MAX_SIZE");
		if(this.sizes[size] == null) {
			Object platformObject = EpicFontImplementation.getFontObjectFromSize(this, relsize(size));
			this.sizes[size] = new EpicFont(this, this.name, platformObject, size);
		}
		return this.sizes[size];
	}

	public int measureAdvance(String text) {
		return EpicFontImplementation.measureAdvance(this, text);
	}

	public String getFriendlyName() {
		if(this.name.equals("Nunito.ttf")) {
			return "Nunito";
		} else if(this.name.equals("LuckiestGuy.ttf")) {
			return "Luckiest Guy";
		} else {
			return this.name.split("\\.")[0];
		}
	}
}
