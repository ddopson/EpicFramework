package com.epic.framework.implementation.Ui;

import com.epic.framework.common.Ui.EpicFont;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.widget.TextView;

public class EpicCanvasImplementation {

	private static Rect src = new Rect();
	private static Rect dst = new Rect();
	private static Paint paintDefaults = new Paint();
	private static Paint _paint = new Paint();
	private static Paint paintText = new Paint();

	private static Paint getPaint() {
		_paint.set(paintDefaults);
		return _paint;
	}
	
	private static Paint getPaint(int color, Style style) {
		_paint.set(paintDefaults);
		_paint.setStyle(style);
		_paint.setColor(color);
		return _paint;
	}
	private static Paint getPaint(int color, Style style, int strokeWidth) {
		_paint.set(paintDefaults);
		_paint.setStyle(style);
		_paint.setColor(color);
		_paint.setStrokeWidth(strokeWidth);
		return _paint;
	}
	private static Paint getPaintWithAlpha(int alpha) {
		_paint.set(paintDefaults);
		_paint.setAlpha(alpha);
		return _paint;
	}
	
	public static void drawBitmapImpl(Object graphicsObject, Object bitmapObject, int x, int y, int alpha, int sx, int sy, int sw, int sh) {
		Canvas canvas = (Canvas)graphicsObject;
		Bitmap bitmap = (Bitmap)bitmapObject;
		src.set(sx, sy, sx+sw, sy+sh);
		dst.set(x, y, x + sw, y + sh);
		paintDefaults.setAlpha(alpha);
		canvas.drawBitmap(bitmap, src, dst, paintDefaults);
	}	
	
//	public static void drawBitmapImpl2(Object graphicsObject, Object bitmapObject, int x, int y, int alpha, int sx, int sy, int sw, int sh) {
//		Canvas canvas = (Canvas)graphicsObject;
//		Bitmap bitmap = (Bitmap)bitmapObject;
//		src.set(sx, sy, sx+sw, sy+sh);
//		dst.set(x, y, x + sw, y + sh);
//		paintDefaults.setAlpha(alpha);
//		canvas.draw
//		canvas.drawBitmap(bitmap, src, dst, paintDefaults);
//	}

	public static void drawCircle(Object graphicsObject, int color, int alpha, int x_center, int y_center, int radius) {
		Canvas canvas = (Canvas)graphicsObject;
		Paint paint = getPaint(color, Style.FILL);
		paint.setAlpha(alpha);
		canvas.drawCircle(x_center, y_center, radius, paint);
	}

	public static void drawBorder(Object graphicsObject, int left, int top, int width, int height, int color, int size) {
		Canvas canvas = (Canvas)graphicsObject;
		dst.set(left, top, left + width, top + height);
		canvas.drawRect(dst, getPaint(color, Style.STROKE, size));
	}

	public static void applyFill(Object graphicsObject, int left, int top, int right, int bottom, int color) {
		Canvas canvas = (Canvas)graphicsObject;
		dst.set(left, top, right, bottom);
		canvas.drawRect(dst, getPaint(color, Style.FILL));
	}
	
//	private static Rect _bounds = new Rect();
	private static Canvas _drawTextSetup(Object graphicsObject, int left, int top, EpicFont font, int color, int rotateBy) {
		Canvas canvas = (Canvas)graphicsObject;
		canvas.rotate(rotateBy, left, top);		
		EpicFontImplementation.setAndroidFont(paintText, font);
		paintText.setColor(color);
		paintText.setAntiAlias(true);
		paintText.setTextAlign(Align.LEFT);
//		int real_top = top + (int) paintText.getTextSize();
//		paintText.getTextBounds("JjklqfF", 0, 7, _bounds);
		return canvas;
	}
	
	public static void drawText(Object graphicsObject, String text, int left, int top, EpicFont font, int color, int rotateBy) {
		Canvas canvas = _drawTextSetup(graphicsObject, left, top, font, color, rotateBy);
		canvas.drawText(text, left, top + font.measureAscent(), paintText);
		canvas.rotate(-1 * rotateBy, left, top);
	}

	public static void drawText(Object graphicsObject, char[] chars, int offset, int length, int left, int top, EpicFont font, int color, int rotateBy) {
		Canvas canvas = _drawTextSetup(graphicsObject, left, top, font, color, rotateBy);
		canvas.drawText(chars, offset, length, left, top + font.measureAscent(), paintText);
		canvas.rotate(-1 * rotateBy, left, top);
	}

	public static void drawLine(Object graphicsObject, int x, int y, int x2, int y2, int strokeWidth, int color) {
		Canvas canvas = (Canvas) graphicsObject;
		paintText.setStrokeWidth(strokeWidth);
		paintText.setColor(color);
		canvas.drawLine(x, y, x2, y2, paintText);
	}
}
