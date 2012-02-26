package com.epic.framework.Ui;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public class EpicCanvasImplementation {
	public static void drawBitmapImpl(Object graphicsObject, Object bitmapObject, int x, int y, int alpha, int sx, int sy, int sw, int sh) {
		Graphics graphics = (Graphics)graphicsObject;
		Bitmap bitmap = (Bitmap)bitmapObject;
		if(alpha == EpicCanvas.NO_ALPHA) {
			graphics.rop(Graphics.ROP_SRC_ALPHA, x, y, sw, sh, bitmap, sx, sy);
		}
		else {
			graphics.setGlobalAlpha(alpha);
			graphics.rop(Graphics.ROP_SRC_ALPHA_GLOBALALPHA, x, y, sw, sh, bitmap, sx, sy);
			graphics.setGlobalAlpha(255);
		}
	}

	public static void drawCircle(Object graphicsObject, int color, int alpha, int x_center, int y_center, int radius) {
		Graphics graphics = (Graphics)graphicsObject;
		graphics.setColor(color);
		graphics.setGlobalAlpha(alpha);
		graphics.fillEllipse(x_center, y_center, x_center + radius, y_center, x_center, y_center + radius, 0, 360);
		graphics.setGlobalAlpha(255);
	}

	public static void drawBorder(Object graphicsObject, int left, int top, int right, int bottom, int color, int size) {
		Graphics graphics = (Graphics)graphicsObject;
		graphics.setStrokeWidth(size);
		graphics.setColor(color);
		graphics.drawRect(left, top, right, bottom);
	}

	public static void applyFill(Object graphicsObject, int left, int top, int right, int bottom, int color) {
		Graphics graphics = (Graphics)graphicsObject;
		graphics.setColor(color);
		graphics.setGlobalAlpha(EpicColor.getAlpha(color));
		graphics.rop(Graphics.ROP_CONST_GLOBALALPHA, left, top, right - left, bottom - top, null, -1, -1);
		graphics.fillRect(left, top, right-left, bottom-top);
		graphics.setGlobalAlpha(255);
	}

	public static void drawText(Object graphicsObject, char[] chars, int offset, int length, int left, int top, EpicFont fontObject, int color, int rotateBy) {
		Graphics graphics = (Graphics)graphicsObject;
		Font font = (Font)fontObject.fontObject;
		graphics.setColor(color);
		graphics.setFont(font);
		graphics.setGlobalAlpha(EpicColor.getAlpha(color));
		graphics.drawText(chars, offset, length, left, top, 0, -1);
		graphics.setGlobalAlpha(255);
		// TODO: implement rotateBy
	}
	public static void drawText(Object graphicsObject, String text, int left, int top, EpicFont fontObject, int color, int rotateBy) {
		Graphics graphics = (Graphics)graphicsObject;
		Font font = (Font)fontObject.fontObject;
		graphics.setColor(color);
		graphics.setFont(font);
		graphics.setGlobalAlpha(EpicColor.getAlpha(color));
		graphics.drawText(text, left, top);
		graphics.setGlobalAlpha(255);
		// TODO: implement rotateBy
	}

	public static void drawLine(Object graphicsObject, int x, int y, int x2, int y2, int strokeWidth, int color) {
		Graphics graphics = (Graphics)graphicsObject;
		graphics.setColor(color);
		graphics.drawLine(x, y, x2, y2);
	}
}
