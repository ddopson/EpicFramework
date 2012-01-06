package com.epic.framework.implementation;

import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.UIGraphics;
import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.Ui.EpicFont;
import com.epic.framework.common.util.EpicLog;

public class EpicFontImplementation {
	public static Object getFontObjectFromFile(EpicFile file) {
		if(file.getFilename().equals("Nunito.ttf")) {
			return getFontObjectFromName("Nunito");	
		} else if(file.getFilename().equals("LuckiestGuy.ttf")) {
			return getFontObjectFromName("Luckiest Guy");	
		} else {
			EpicLog.e("Do not recognize font name: " + file.getFilename() + ", trying to load anyways...");
			return getFontObjectFromName(file.getFilename());
		}
	}

	public static Object getFontObjectFromName(String systemName) {
		return EpicFontImplementationNative.CGFontCreateFromName(systemName);
	}
	
	public static Object getFontObjectFromSize(EpicFont font, int size) {
		return font.fontObject;
	}

	public static int measureHeight(EpicFont font) {
		int ascent = EpicFontImplementationNative.CGFontGetAscent(font.fontObject);
		int descent = EpicFontImplementationNative.CGFontGetDescent(font.fontObject);
		int unitsPerEm = EpicFontImplementationNative.CGFontGetUnitsPerEm(font.fontObject);
		int height = (ascent - descent) * font.size_relative / unitsPerEm;
		return (int)height;
	}

	public static int measureAscent(EpicFont font) {
		int ascent = EpicFontImplementationNative.CGFontGetAscent(font.fontObject);
		int unitsPerEm = EpicFontImplementationNative.CGFontGetUnitsPerEm(font.fontObject);
		return ascent * font.size_relative / unitsPerEm;
	}

	public static int measureDescent(EpicFont font) {
		int descent = EpicFontImplementationNative.CGFontGetDescent(font.fontObject);
		int unitsPerEm = EpicFontImplementationNative.CGFontGetUnitsPerEm(font.fontObject);
		return descent * font.size_relative / unitsPerEm;
	}

	private static void setInvisibleTextMode(CGContext c) {
		// DDOPSON-2011-12-22 - Native code wrapper is a bit busted here ...
		//		- (void) setTextDrawingModexMw :(int)mode
		//		{
		//		  if (mode == 1) {
		//		    CGContextSetTextDrawingMode(context, kCGTextInvisible);
		//		  } else {
		//		    CGContextSetTextDrawingMode(context, kCGTextFill);
		//		  }
		//		}
		// YET .....
		// CGContext.kCGTextInvisible == 3 !!!!!
		// So we do what works...
		c.setTextDrawingMode(1);
	}
	
	public static int measureAdvance(EpicFont font, String text) {
		return measureAdvance(UIGraphics.getCurrentContext(), font, text);
	}
	
	public static int measureAdvance(Object graphicsObject, EpicFont font, String text) {
		CGContext context = (CGContext) graphicsObject;
		setInvisibleTextMode(context);
		setFont(font, context);
		context.showTextAtPoint(0, 0, text);
		int advance = (int) context.getTextPosition().x;
//		EpicLog.d("Advance['" + text + "']@" + font.size_absolute + "(" + font.size_relative + ")" + " = " + advance);
		return advance;
	}

	public static int measureAdvance(Object graphicsObject, EpicFont font, char[] chars, int offset, int length) {
		return measureAdvance(graphicsObject, font, new String(chars, offset, length));
	}

	public static void setFont(EpicFont font, CGContext context) {
		// TODO: this call causes no font to show up
//		EpicFontImplementationNative.CGContextSetFont(font.fontObject, context);
		context.setFontSize(font.size_relative);
	}
}
