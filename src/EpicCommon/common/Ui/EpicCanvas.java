package com.epic.framework.common.Ui;

import com.epic.config.EpicProjectConfig;
import com.epic.framework.common.util.*;
import com.epic.framework.implementation.EpicCanvasImplementation;
import com.epic.framework.implementation.EpicFontImplementation;

public class EpicCanvas {

	//	public static final int defaultTextFont = EpicFont.FONT_MAIN;
	public static final int defaultTextRotate = 0;
	public static final int defaultHAlign = EpicFont.HALIGN_LEFT;
	public static final int defaultVAlign = EpicFont.VALIGN_TOP;

	public static final int NO_ALPHA = 255;
	private static boolean debugRendering = false;

	Object graphicsObject;

	private static final int BUFFER_SIZE = 8192;
	private static final char[] buffer = new char[BUFFER_SIZE];
	public static final int DESIGN_WIDTH = EpicProjectConfig.getDesignDimensions().width;
	public static final int DESIGN_HEIGHT = EpicProjectConfig.getDesignDimensions().height;

	static EpicCanvas staticCanvas = new EpicCanvas();

	public static EpicCanvas get(Object graphicsObject) {
		staticCanvas.graphicsObject = graphicsObject;
		EpicCanvasImplementation.init(graphicsObject);
		return staticCanvas;
	}

	public static void toggleDebugRendering() {
		debugRendering = !debugRendering;
	}


	public static int calculateTranslationAnimation(int startPos, int finishPos, int startTime, int duration, int t) {
		if(t < startTime) {
			return startPos;
		}
		else if(t >= startTime + duration) {
			return finishPos;
		}
		else {
			int a = t - startTime;
			int b = duration - a;
			return (startPos * b + finishPos * a) / duration;
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// HELPERS
	////////////////////////////////////////////////////////////////////////////////
	
	static final int sx(int x) {
		return x * EpicPlatform.renderWidth / EpicPlatform.designWidth;
	}

	static final int sy(int y) {
		return y * EpicPlatform.renderHeight / EpicPlatform.designHeight;
	}
	
	private static int max(int a, int b) {
		return a > b ? a : b;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	// INTERFACE
	////////////////////////////////////////////////////////////////////////////////
	public final void drawFullscreenBitmap(EpicBitmap image) {
		_drawBitmapSubsetWithGlobalAlpha(image, 0, 0, EpicPlatform.renderWidth, EpicPlatform.renderHeight, NO_ALPHA, 0, 0, 0, 0);
	}
	public final void drawFullscreenBitmap(EpicBitmap image, int alpha) {
		_drawBitmapSubsetWithGlobalAlpha(image, 0, 0, EpicPlatform.renderWidth, EpicPlatform.renderHeight, alpha, 0, 0, 0, 0);
	}
	public final void drawBitmap(EpicBitmap image, int left, int top, int width, int height) {
		_drawBitmapSubsetWithGlobalAlpha(image, sx(left), sy(top), sx(width), sy(height), NO_ALPHA, 0, 0, 0, 0);
	}
	public final void drawBitmapWithGlobalAlpha(EpicBitmap image, int left, int top, int width, int height, int alpha) {
		_drawBitmapSubsetWithGlobalAlpha(image, sx(left), sy(top), sx(width), sy(height), alpha, 0, 0, 0, 0);
	}
	public final void drawBitmapSubset(EpicBitmap image, int left, int top, int width, int height, int lcrop, int tcrop, int rcrop, int bcrop) {
		_drawBitmapSubsetWithGlobalAlpha(image, sx(left), sy(top), sx(width), sy(height), NO_ALPHA, sx(lcrop), sy(tcrop), sx(rcrop), sy(bcrop));
	}
	public final void drawBitmapSubsetWithGlobalAlpha(EpicBitmap image, int left, int top, int width, int height, int alpha, int lcrop, int tcrop, int rcrop, int bcrop) {
		_drawBitmapSubsetWithGlobalAlpha(image, sx(left), sy(top), sx(width), sy(height), alpha, sx(lcrop), sy(tcrop), sx(rcrop), sy(bcrop));
	}

	public final void drawCircle(int color, int alpha, int x_center, int y_center, int radius) {
		EpicCanvasImplementation.drawCircle(graphicsObject, color, alpha, sx(x_center), sy(y_center), sx(radius));
	}

	public final void drawBorder(int x, int y, int width, int height, int color, int size) {
		EpicCanvasImplementation.drawBorder(graphicsObject, sx(x), sy(y), sx(width), sy(height), color, size);
	}

	public final void applyFullscreenFill(int color) {
		EpicCanvasImplementation.applyFill(graphicsObject, 0, 0, EpicPlatform.renderWidth, EpicPlatform.renderHeight, color);
	}
	public final void applyFill(int x, int y, int width, int height, int color) {
		EpicCanvasImplementation.applyFill(graphicsObject, sx(x), sy(y), sx(width), sy(height), color);
	}

	public final void drawLine(int x, int y, int x2, int y2, int strokeWidth, int color) {
		EpicCanvasImplementation.drawLine(graphicsObject, sx(x), sy(y), sx(x2), sy(y2), strokeWidth, color);
	}

	public final void drawText(String text, int x, int y, int width, int height, EpicFont font, int color, int halign, int valign, int rotateBy) {
		_drawTextSingleLine(text, sx(x), sy(y), sx(width), sy(height), font, color, halign, valign, rotateBy);
	}
	public final void drawText(String text, int left, int top, int width, int height, EpicFont font, int color) {
		drawText(text, left, top, width, height, font, color, defaultHAlign, defaultVAlign, defaultTextRotate);
	}
	public final void drawText(String text, int left, int top, int width, int height, EpicFont font, int color, int halign) {
		drawText(text, left, top, width, height, font, color, halign, defaultVAlign, defaultTextRotate);
	}
	public final void drawText(String text, int left, int top, int width, int height, EpicFont font, int color, int halign, int valign) {
		drawText(text, left, top, width, height, font, color, halign, valign, defaultTextRotate);
	}
	public final void drawTextBox(String text, int left, int top, int width, int height, EpicFont font, int color) {
		drawTextBox(text, left, top, width, height, font, color, defaultTextRotate);
	}
	public final void drawTextBox(String text, int left, int top, int width, int height, EpicFont font, int color, int rotateBy) {
		_drawTextBox(text, sx(left), sy(top), sx(width), sy(height), font, color, rotateBy);
	}

	////////////////////////////////////////////////////////////////////////////////
	// IMPLEMENTATION
	////////////////////////////////////////////////////////////////////////////////

	final void _drawBitmapSubsetWithGlobalAlpha(EpicBitmap image, int left, int top, int width, int height, int alpha, int lcrop, int tcrop, int rcrop, int bcrop) {
		if(alpha == 0) {
			return; // invisible
		}
		EpicFail.assertNotNull(image);
		if((tcrop + bcrop) == height || (lcrop + rcrop) == width) {
			return;
		}
		if((tcrop + bcrop) > height || (lcrop + rcrop) > width) {
			throw EpicFail.invalid_argument(StringHelper.namedArgList("image", image.name, "left", left, "top", top, "width", width, "height", height, "alpha", alpha, "lcrop", lcrop, "rcrop", rcrop, "bcrop", bcrop, "tcrop", tcrop));
		}
//		EpicLog.v("EpicCanvas._drawBitmapSubsetWithGlobalAlpha(" + StringHelper.namedArgList("image", image.name, "left", left, "top", top, "width", width, "height", height, "alpha", alpha, "lcrop", lcrop, "rcrop", rcrop, "bcrop", bcrop, "tcrop", tcrop) + ")");
		int lpad = image.lpad;
		int tpad = image.tpad;
		int rpad = image.rpad;
		int bpad = image.bpad;
		Object bitmapObject = image.getPlatformObject(width, height);

		if(image.width != width || image.height != height) {
			lpad = (lpad * width + (image.width>>1)) / image.width;
			tpad = (tpad * height + (image.height>>1)) / image.height;
			rpad = (rpad * width + (image.width>>1)) / image.width;
			bpad = (bpad * height + (image.height>>1)) / image.height;
			bitmapObject = image.getPlatformObject(width, height);
		}

		boolean isCropped = lcrop > 0 || tcrop > 0 || rcrop > 0 || bcrop > 0;
		int sx = max(lcrop - lpad, 0);
		int sy = max(tcrop - tpad, 0);
		int dlp = max(lcrop, lpad);
		int dtp = max(tcrop, tpad);
		int drp = max(rcrop, rpad);
		int dbp = max(bcrop, bpad);

		int sw = width - dlp - drp;
		int sh = height - dtp - dbp;
		if(sw < 0 || sh < 0) {
			// DDOPSON-2010-10-15
			// crop and pad play together well until you crop into the opposing pad
			// so there is a legit case where the sh pops out as negative
			// so suppose that 1/3 of the image is transparent both top and bottom
			// draw the whole thing and the render window will be .333 to .666
			// if you "crop" 1/4 of the bottom, no big deal, you cropped transparency, renders same as if you drew the whole thing
			// if you crop 1/2 of the bottom, then the render window is adjusted to be from .333-0.5
			// BUT, if you crop 4/5, then the render window is from .333 - 0.25.  that's a negative window
			// really, nothing should render here as you are only drawing transparent pixels, so just returning is correct
			return;
		}
		EpicStopwatch.reportPixelsPushed(image, width, height, sw * sh);
		EpicCanvasImplementation.drawBitmapImpl(graphicsObject, bitmapObject, left + dlp, top + dtp, alpha, sx, sy, sw, sh, isCropped);
		if(debugRendering) {
			EpicCanvasImplementation.drawBorder(graphicsObject, left + sx, top + sy, sw, sh, EpicColor.RED, 1);
		}
	}

	final void _drawTextSingleLine(String text, int x, int y, int width, int height, EpicFont font, int color, int halign, int valign, int rotateBy) {
		EpicFail.assertNotNull(text);
		EpicFail.assertNotNull(font);

		//		if(autoResize) {
		//			width = width * this.real_width / EpicCanvas.DESIGN_WIDTH;
		//			height = height * this.real_height / EpicCanvas.DESIGN_HEIGHT;
		//			x = x * this.real_width / EpicCanvas.DESIGN_WIDTH;
		//			y = y * this.real_height / EpicCanvas.DESIGN_HEIGHT;
		//		}

		switch(halign) {
		case EpicFont.HALIGN_CENTER:
		case EpicFont.HALIGN_LEFT:
		case EpicFont.HALIGN_RIGHT:
			break;
		default:
			throw EpicFail.invalid_argument("halign = " + halign);
		}
		switch(valign) {
		case EpicFont.VALIGN_BOTTOM:
		case EpicFont.VALIGN_CENTER:
		case EpicFont.VALIGN_TOP:
			break;
		default:
			throw EpicFail.invalid_argument("halign = " + halign);
		}

		int advance = EpicFontImplementation.measureAdvance(graphicsObject, font, text);
		int text_left;
		switch(halign) {
		case EpicFont.HALIGN_RIGHT:
			text_left = x + width - advance;
			break;
		case EpicFont.HALIGN_LEFT:
			text_left = x;
			break;
		case EpicFont.HALIGN_CENTER:
			text_left = x + (width - advance) / 2;
			break;
		default:
			throw EpicFail.unhandled_case();
		}

		int text_top;
		int textHeight = font.ascent;
		switch(valign) {
		case EpicFont.VALIGN_TOP:
			text_top = y;
			break;
		case EpicFont.VALIGN_BOTTOM:
			text_top = y + height - textHeight;
			break;
		case EpicFont.VALIGN_CENTER:
			text_top = y + (height - textHeight) / 2;
			break;
		default:
			throw EpicFail.unhandled_case();
		}
		if(debugRendering) {
			EpicCanvasImplementation.drawBorder(graphicsObject, x, y, width, height, EpicColor.BLUE, 2);
			EpicCanvasImplementation.drawBorder(graphicsObject, text_left, text_top, advance, textHeight, EpicColor.BLUE, 2);
		}
		EpicCanvasImplementation.drawText(graphicsObject, text, text_left, text_top, font, color, rotateBy);
	}

	final void _drawTextBox(String text, int left, int top, int width, int height, EpicFont font, int color, int rotateBy) {
		EpicFail.assertTrue(text.length() < BUFFER_SIZE, "text.length() is bigger than BUFFER_SIZE.  length=" + text.length());

//		text.getChars(0, text.length(), buffer, 0);
		
		for(int i = 0; i < buffer.length; ++i) {
			buffer[i] = '\0';
		}
		
		for(int i = 0; i < text.length(); ++i) {
			buffer[i] = text.charAt(i);
		}
		
		int textSize = font.size_absolute;

		while(textSize > 1 && height < __drawTextBox(buffer, 0, text.length(), left, top, width, height, font, color, true, rotateBy)) {
			font = font.withSize(--textSize);
		}
		__drawTextBox(buffer, 0, text.length(), left, top, width, height, font, color, false, rotateBy);
	}

	final int __drawTextBox(char[] chars, int offset, int length, int left, int top, int width, int height, EpicFont font, int color, boolean measureOnly, int rotateBy) {
//		EpicLog.d("drawTextBox(" + StringHelper.namedArgList("chars", chars, "offset", offset, "length", length, "width", width, "height", height) + ")");
		if(debugRendering) {
			EpicCanvasImplementation.drawBorder(graphicsObject, left, top, width, height, EpicColor.GREEN, 2);
		}

		int p = offset, plast = offset, lineStart = offset, advance = 0;
		int lineHeight = font.height;
		int text_top = top;
		do {
			while(p < length && buffer[p] == ' ') {
				p++;
			}
			while(p < length && buffer[p] != ' ' && buffer[p] != '\n') {
				p++;
			}
			advance = EpicFontImplementation.measureAdvance(graphicsObject, font, buffer, lineStart, p - lineStart);
			if(advance > width || p == length || buffer[p] == '\n') {
				if(advance > width) {
					p = plast;
					if(plast == lineStart) {
						if(measureOnly) {
							return Integer.MAX_VALUE;
						}
						else {
							throw EpicFail.framework("Textbox too small.  would have to wrap a single word");
						}
					}
				}
				if(!measureOnly) {
					EpicCanvasImplementation.drawText(graphicsObject, buffer, lineStart, p - lineStart, left, text_top, font, color, rotateBy);
				}
				text_top += lineHeight;
				while(p < length && buffer[p] == ' ') {
					p++;
				}
				lineStart = p;
			}
			plast = p;
		} while(p < length);
		return text_top - top;
	}

}
