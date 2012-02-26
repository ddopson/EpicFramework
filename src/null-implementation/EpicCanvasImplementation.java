package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicFont;

public class EpicCanvasImplementation {
	
	public static void drawBitmapImpl(Object graphicsObject, Object bitmapObject, int x, int y, int alpha, int sx, int sy, int sw, int sh, boolean isCropped) { }
	public static void drawCircle(Object graphicsObject, int color, int alpha, int x_center, int y_center, int radius) { }
	public static void drawBorder(Object graphicsObject, int left, int top, int right, int bottom, int color, int size) { }
	public static void applyFill(Object graphicsObject, int left, int top, int right, int bottom, int color) { }
	public static void drawText(Object graphicsObject, char[] chars, int offset, int length, int left, int top, EpicFont fontObject, int color, int rotateBy) { }
	public static void drawText(Object graphicsObject, String text, int left, int top, EpicFont fontObject, int color, int rotateBy) { }
	public static void drawLine(Object graphicsObject, int x, int y, int x2, int y2, int strokeWidth, int color) { }
	public static void init(Object graphicsObject) { }
	public static void drawTextBox(Object graphicsObject, String text, int left, int top, int width, int height, EpicFont font, int color, int rotateBy) { }
	
}
