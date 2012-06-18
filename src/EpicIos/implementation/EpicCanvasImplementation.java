package com.epic.framework.implementation;


import org.xmlvm.iphone.CGDataProvider;
import org.xmlvm.iphone.CGFont;
import org.xmlvm.iphone.CGPoint;
import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.CGRect;
import org.xmlvm.iphone.CGSize;
import org.xmlvm.iphone.UIFont;
import org.xmlvm.iphone.UIImage;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui.EpicColor;
import com.epic.framework.common.Ui.EpicFont;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.StringHelper;

public class EpicCanvasImplementation {
	static {
		// DDOPSON-2011-12-21 - Workaround for Apple Bug:
		// See http://www.cocoabuilder.com/archive/cocoa/293268-cgcontextselectfont-spinlock.html
		UIFont.systemFontOfSize(12);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// HELPERS
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private static CGRect dst = new CGRect(0,0,0,0);
	private static CGRect getDstRect(int x, int y, int w, int h) {
		dst.origin.x = (float)x;
		dst.origin.y = (float)y;
		dst.size.width = (float)w;
		dst.size.height = (float)h;
		return dst;
	}

	private static CGPoint dstp = new CGPoint(0,0);
	private static CGPoint getDstPoint(int x, int y) {
		dstp.x = (float)x;
		dstp.y = (float)y;
		return dstp;
	}

	private static float[] colorFloats = new float[4];
	private static float[] getColorFloatsFromInt(int color) {
		colorFloats[0] = (float) EpicColor.getRed(color) / 255.0f;
		colorFloats[1] = (float) EpicColor.getGreen(color) / 255.0f;
		colorFloats[2] = (float) EpicColor.getBlue(color) / 255.0f;
		colorFloats[3] = (float) EpicColor.getAlpha(color) / 255.0f;
		return colorFloats;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean hack = false;
	
	public static void drawBitmapImpl(Object graphicsObject, Object bitmapObject, int x, int y, int alpha, int sx, int sy, int sw, int sh, boolean isCropped) {
		CGContext c = (CGContext) graphicsObject;
		UIImage uiimg = (UIImage) bitmapObject;

//		if(hack) {
//			CGSize size = uiimg.getSize();
//			EpicLog.i("drawBitmapImpl(" + StringHelper.namedArgList("x", x, "y", y, "alpha", alpha, "sx", sx, "sy", sy, "sw", sw, "sh", sh, "i.w", (int)size.width, "i.h", (int)size.height) + ")");
//		}

		if(isCropped) EpicCanvasImplementationNative.setCrop(c, x, y, sw, sh);
		EpicCanvasImplementationNative.drawImage(c, uiimg, x-sx, y-sy, alpha);
		if(isCropped) EpicCanvasImplementationNative.restoreContext(c);
	}	

	public static void drawCircle(Object graphicsObject, int color, int alpha, int x_center, int y_center, int radius) {
		CGContext c = (CGContext) graphicsObject;
		c.setFillColor(getColorFloatsFromInt(color));
		c.fillEllipseInRect(new CGRect(x_center - radius, y_center - radius, radius * 2, radius * 2));
	}

	public static void drawBorder(Object graphicsObject, int left, int top, int width, int height, int color, int size) {
		CGContext c = (CGContext) graphicsObject;
		c.setStrokeColor(getColorFloatsFromInt(color));
		c.setLineWidth(size);
		c.strokeRect(getDstRect(left, top, width, height));
	}

	public static void applyFill(Object graphicsObject, int left, int top, int width, int height, int color) {
		CGContext c = (CGContext) graphicsObject;
		c.setFillColor(getColorFloatsFromInt(color));
		c.setAlpha(1.0f);
		c.fillRect(new CGRect(left, top, width, height));
	}

	public static void drawLine(Object graphicsObject, int x, int y, int x2, int y2, int strokeWidth, int color) {
		CGContext c = (CGContext) graphicsObject;
		c.setStrokeColor(getColorFloatsFromInt(color));
		c.setLineWidth(strokeWidth);
		c.beginPath();
		c.moveToPoint(x, y);
		c.addLineToPoint(x2, y2);
		c.strokePath();
	}

	public static void drawTextBox(Object graphicsObject, String text, int x, int y, int width, int height, EpicFont font, int color, int rotateBy) {
		CGContext c = (CGContext) graphicsObject;
		c.setFillColor(getColorFloatsFromInt(color));
		c.setStrokeColor(getColorFloatsFromInt(color));
		EpicCanvasImplementationNative.drawTextInRect(text, font.fontObject, x, y, width, height);
	}
	public static void drawText(Object graphicsObject, String text, int left, int top, EpicFont font, int color, int rotateBy) {
		CGContext c = (CGContext) graphicsObject;
		c.setFillColor(getColorFloatsFromInt(color));
		c.setStrokeColor(getColorFloatsFromInt(color));
		EpicCanvasImplementationNative.drawTextAtPoint(text, font.fontObject, left, top);
	}

	public static void drawText(Object graphicsObject, char[] buffer, int offset, int length, int left, int top, EpicFont font, int color, int rotateBy) {
		throw EpicFail.not_supported();
	}

	public static void init(Object graphicsObject) {
	}
}
