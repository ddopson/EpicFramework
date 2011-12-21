package com.epic.framework.implementation;


import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.CGFont;
import org.xmlvm.iphone.CGRect;
import org.xmlvm.iphone.UIImage;
import org.xmlvm.iphone.internal.renderer.UITextRenderer;

import com.epic.framework.common.Ui.EpicColor;
import com.epic.framework.common.Ui.EpicFont;
import com.epic.framework.common.util.EpicLog;

public class EpicCanvasImplementation {

	public static void drawBitmapImpl(Object graphicsObject, Object bitmapObject, int x, int y, int alpha, int sx, int sy, int sw, int sh) {
		EpicLog.v("EpicCanvasImplementation.drawBitmap");
		CGContext c = (CGContext) graphicsObject;
		UIImage uiimg = (UIImage) bitmapObject;
		c.setAlpha((float) alpha / 256.0f);
		uiimg.drawInRect(new CGRect(x, y, sw, sh));
		EpicLog.v("EpicCanvasImplementation.drawBitmap - done");
	}	

	public static void drawCircle(Object graphicsObject, int color, int alpha, int x_center, int y_center, int radius) {
		EpicLog.v("EpicCanvasImplementation.drawCircle");
		CGContext c = (CGContext) graphicsObject;
		c.setAlpha(1.0f);
		c.setFillColor(getColorFloatsFromInt(color));
		c.fillEllipseInRect(new CGRect(x_center - radius, y_center - radius, radius * 2, radius * 2));
		EpicLog.v("EpicCanvasImplementation.drawCircle - done");
	}

	public static void drawBorder(Object graphicsObject, int left, int top, int width, int height, int color, int size) {
		// CGContext c = (CGContext) graphicsObject;
		// c.setStrokeColor(color);
		// TODO: draw border ;)
	}

	public static void applyFill(Object graphicsObject, int left, int top, int right, int bottom, int color) {
		EpicLog.v("EpicCanvasImplementation.applyFill");
		CGContext c = (CGContext) graphicsObject;
		float[] values = getColorFloatsFromInt(color);
		c.setFillColor(values);
		c.setAlpha(1.0f);
		c.fillRect(new CGRect(left, top, right - left, bottom - top));
		EpicLog.v("EpicCanvasImplementation.applyFill - done");
	}

	private static float[] getColorFloatsFromInt(int color) {
		float[] values = new float[4];
		values[0] = (float) EpicColor.getRed(color) / 256.0f;
		values[1] = (float) EpicColor.getGreen(color) / 256.0f;
		values[2] = (float) EpicColor.getBlue(color) / 256.0f;
		values[3] = (float) EpicColor.getAlpha(color) / 256.0f;
		return values;
	}

	public static void drawText(Object graphicsObject, String chars, int left, int top, int color) {
		EpicLog.v("EpicCanvasImplementation.drawText");
		CGContext c = (CGContext) graphicsObject;
		c.setAlpha(0.5f);
//		c.setFont((CGFont)EpicFont.FONT_GAME.fontObject);
		c.showTextAtPoint(left, top, chars);
		EpicLog.v("EpicCanvasImplementation.drawText - done");
	}

	public static void drawLine(Object graphicsObject, int x, int y, int x2, int y2, int strokeWidth, int color) {
		// CGContext c = (CGContext) graphicsObject;
		// c.setStrokeColor(color);
		// TODO: draw line
	}

	public static void drawText(Object graphicsObject, String text, int text_left, int text_top, EpicFont font, int color, int rotateBy) {
		// TODO Auto-generated method stub
		drawText(graphicsObject, text, text_left, text_top, color);
	}

	public static void drawText(Object graphicsObject, char[] buffer, int lineStart, int i, int left, int text_top, EpicFont font, int color, int rotateBy) {
		// TODO Auto-generated method stub
		drawText(graphicsObject, buffer.toString(), left, text_top, color);
	}
}
