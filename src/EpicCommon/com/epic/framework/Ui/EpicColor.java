package com.epic.framework.Ui;

import com.epic.framework.util.EpicFail;

public class EpicColor {
	public static final int RED    = 0xffff0000;
	public static final int BLACK  = 0xff000000;
	public static final int LTGRAY = 0xffcccccc;
	public static final int DKGRAY = 0xff444444;
	public static final int BLUE   = 0xff0000ff;
	public static final int YELLOW = 0xffffff00;
	public static final int ORANGE = 0xffdd8800;
	public static final int GREEN  = 0xff00cc00;
	public static final int WHITE  = 0xffffffff;
	public static final int OLIVE  = 0xff5b5849;
	public static final int TRANSPARENT = 0;

	public static int withAlpha(int alpha, int color) {
		return (color & 0x00ffffff) + (alpha << 24);
	}

	public static int getAlpha(int color) {
		return (color >> 24) & 0x000000ff;
	}
	public static int getRed(int color) {
		return (color >> 16) & 0x000000ff;
	}
	public static int getGreen(int color) {
		return (color >> 8) & 0x000000ff;
	}
	public static int getBlue(int color) {
		return (color >> 0) & 0x000000ff;
	}

	public static int fromInts(int alpha, int red, int green, int blue) {
		if(alpha > 255) {
			throw EpicFail.invalid_argument("alpha("+alpha+") > 255");
		}
		if(red > 255) {
			throw EpicFail.invalid_argument("red("+red+") > 255");
		}
		if(green > 255) {
			throw EpicFail.invalid_argument("green("+green+") > 255");
		}
		if(blue > 255) {
			throw EpicFail.invalid_argument("blue("+blue+") > 255");
		}
		return (alpha << 24)
		+ (red << 16)
		+ (green << 8)
		+ (blue << 0);
	}

}
