package com.epic.framework.implementation;

import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.UIGraphics;

import com.epic.framework.common.Ui.EpicFile;

public class EpicFontImplementation {
	String name;
	int size;
	private static final int defaultSize = 88; // heh, make sure no one uses the default
	private EpicFontImplementation(String name, int size) {
		this.name = name;
		this.size = size;
	}
	
	public static Object getFontObjectFromSize(Object fontObject, int size) {
		EpicFontImplementation font = (EpicFontImplementation)fontObject;
		return new EpicFontImplementation(font.name, size);
	}

	public static int measureHeight(Object fontObject) {
		EpicFontImplementation font = (EpicFontImplementation)fontObject;
		return font.size;
	}

	public static int measureAscent(Object fontObject) {
		return getSize(fontObject);
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
		c.setTextDrawingMode(3);
	}
	
	public static int measureAdvance(Object fontObject, String text) {
		EpicFontImplementation font = (EpicFontImplementation)fontObject;
		CGContext c = UIGraphics.getCurrentContext();
		c.selectFont(font.name, font.size);
		setInvisibleTextMode(c);
		c.showTextAtPoint(0, 0, text);
		return (int) c.getTextPosition().x;
	}

	public static int measureAdvance(Object fontObject, char[] chars, int offset, int length) {
		return measureAdvance(fontObject, new String(chars));
	}

	public static int getSize(Object fontObject) {
		EpicFontImplementation font = (EpicFontImplementation)fontObject;
		return font.size;
	}

	public static Object getFontObjectFromName(String systemName) {
		return new EpicFontImplementation(systemName, defaultSize);
	}

	public static Object getFontObjectFromFile(EpicFile file) {
		return getFontObjectFromName("Arial");
	}

}
