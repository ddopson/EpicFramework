package com.epic.framework.implementation;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.JTextArea;

import com.epic.framework.common.Ui.EpicColor;
import com.epic.framework.common.Ui.EpicFont;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.StringHelper;

public class EpicCanvasImplementation {
	public static boolean hack = false;
	
	public static void drawBitmapImpl(Object graphicsObject, Object bitmapObject, int x, int y, int alpha, int sx, int sy, int sw, int sh, boolean isCropped) {
		Graphics2D graphics = (Graphics2D)graphicsObject;
		Image bitmap = (Image)bitmapObject;
		if(hack) {
			int bw = bitmap.getWidth(null);
			int bh = bitmap.getHeight(null);
			EpicLog.i("drawBitmapImpl(" + StringHelper.namedArgList("x", x, "y", y, "alpha", alpha, "sx", sx, "sy", sy, "sw", sw, "sh", sh, "i.w", bw, "i.h", bh) + ")");
		}
		Composite originalAlpha = (alpha > 0) ? setAlpha(graphics, alpha) : null;
		graphics.drawImage(bitmap, x, y, x+sw, y+sh, sx, sy, sx+sw, sy+sh, null);
		if(alpha > 0) unsetAlpha(graphics, originalAlpha);
	}


	public static int measureHeight(String text, FontRenderContext fontRenderContext, int width) {	
		AttributedString attributedString = new AttributedString(text);
		AttributedCharacterIterator characterIterator = attributedString.getIterator();
		LineBreakMeasurer measurer = new LineBreakMeasurer(characterIterator, fontRenderContext);
		//		int lines = 0;
		int height = 0;
		//		TextLayout textLayout;
		while (measurer.getPosition() < characterIterator.getEndIndex()) {
			// Get line
			TextLayout textLayout = measurer.nextLayout(width);
			EpicLog.v("textLayout: a=" + textLayout.getAscent() + ", d=" + textLayout.getDescent() + ", l=" + textLayout.getLeading());
			height += textLayout.getAscent() + textLayout.getDescent() +  textLayout.getLeading();
		}
		return height;
		//		return lines * (textLayout.getAscent() + textLayout.getDescent() +  textLayout.getLeading());
	}

	private static Ellipse2D.Float eee = new Ellipse2D.Float();
	public static void drawCircle(Object graphicsObject, int color, int alpha, int x_center, int y_center, int radius) {
		Graphics2D graphics = (Graphics2D)graphicsObject;
		eee.setFrame(x_center - radius, y_center - radius, 2*radius, 2*radius);
		graphics.setPaint(getColor(color));
		graphics.fill(eee);
	}

	public static void drawBorder(Object graphicsObject, int left, int top, int width, int height, int color, int size) {
		Graphics2D graphics = (Graphics2D)graphicsObject;
		graphics.setColor(getColor(color));
		graphics.setStroke(new BasicStroke(size));
		graphics.drawRect(left, top, width, height);
	}

	private static Rectangle rrr = new Rectangle();
	public static void applyFill(Object graphicsObject, int left, int top, int width, int height, int color) {
		Graphics2D graphics = (Graphics2D)graphicsObject;
		graphics.setPaint(getColor(color));
		rrr.setBounds(left, top, width, height);
		graphics.fill(rrr);
	}

	//	public void drawLine(int startX, int startY, int stopX, int stopY, int color) {
	//		//TODO: implement
	//	}

	private static Color getColor(int color) {
		return new java.awt.Color(EpicColor.getRed(color), EpicColor.getGreen(color), EpicColor.getBlue(color), EpicColor.getAlpha(color));
	}
	private static Composite setAlpha(Graphics2D graphics, int alpha) {
		Composite composite = graphics.getComposite();
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / (float)255));
		return composite;
	}

	private static void unsetAlpha(Graphics2D graphics, Composite composite) {
		graphics.setComposite(composite);
	}

	private static double RADIANS_PER_DEGREE = 2 * Math.PI / 360;

	private static Graphics2D _drawTextSetup(Object graphicsObject, int text_left, int text_top, EpicFont epicFont, int color, int rotateBy) {
		Graphics2D graphics = (Graphics2D)graphicsObject;
		Font font = (Font)epicFont.fontObject;
		graphics.setFont(font);
		graphics.setColor(getColor(color));
		graphics.rotate(RADIANS_PER_DEGREE * rotateBy, text_left, text_top);
		return graphics;
	}
	
	public static void drawText(Object graphicsObject, String text, int text_left, int text_top, EpicFont epicFont, int color, int rotateBy) {
		Graphics2D graphics = _drawTextSetup(graphicsObject, text_left, text_top, epicFont, color, rotateBy);
		graphics.drawString(text, text_left, text_top + graphics.getFontMetrics().getAscent());		
		graphics.rotate(-1 * RADIANS_PER_DEGREE * rotateBy, text_left, text_top);
	}
	
	public static void drawText(Object graphicsObject, char[] chars, int offset, int length, int text_left, int text_top, EpicFont epicFont, int color, int rotateBy) {
		Graphics2D graphics = _drawTextSetup(graphicsObject, text_left, text_top, epicFont, color, rotateBy);
		graphics.drawChars(chars, offset, length, text_left, text_top + graphics.getFontMetrics().getAscent());
		graphics.rotate(-1 * RADIANS_PER_DEGREE * rotateBy, text_left, text_top);
	}

	public static void drawLine(Object graphicsObject, int x, int y, int x2, int y2, int strokeWidth, int color) {
		Graphics2D graphics = (Graphics2D)graphicsObject;
		graphics.setColor(getColor(color));
		graphics.setStroke(new BasicStroke(strokeWidth));
		graphics.drawLine(x, y, x2, y2);
	}

	public static void init(Object graphicsObject) {
		// optional
	}


	public static void drawTextBox(Object graphicsObject, String text, int left, int top, int width, int height, EpicFont font, int color, int rotateBy) {
		JTextArea ta = new JTextArea(text);
		Graphics2D graphics = (Graphics2D)graphicsObject;
		ta.setLocation(left, top);
		ta.setSize(width, height);
		ta.setOpaque(false);
		ta.setBackground(new Color(Color.TRANSLUCENT));
		ta.setForeground(new Color(color));
		ta.setFont((Font)font.fontObject);
		graphics.translate(left, top);
		graphics.rotate(RADIANS_PER_DEGREE * rotateBy, 0, 0);
		ta.paint(graphics);
		graphics.rotate(-1 * RADIANS_PER_DEGREE * rotateBy, 0, 0);
		graphics.translate(-left, -top);
//		throw EpicFail.not_supported();
	}
}
