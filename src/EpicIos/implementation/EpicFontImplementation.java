package com.epic.framework.implementation;

import org.xmlvm.iphone.UIFont;

import com.epic.framework.common.Ui.EpicFile;

public class EpicFontImplementation {

	public static Object getFontObjectFromSize(Object fontObject, int i) {
		UIFont font = (UIFont) fontObject;
		return font.fontWithSize(i);
	}

	public static int measureHeight(Object fontObject) {
		UIFont font = (UIFont) fontObject;
		return (int) font.pointSize();
	}

	public static int measureAscent(Object fontObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int measureAdvance(Object fontObject, String text) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int measureAdvance(Object fontObject, char[] chars, int offset, int length) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int getSize(Object fontObject) {
		UIFont font = (UIFont) fontObject;
		return (int) font.pointSize();
	}

	public static Object getFontObjectFromName(String systemName) {
		UIFont font = UIFont.fontWithNameSize(systemName, 24);
		return font;
	}

	public static Object getFontObjectFromFile(EpicFile file) {
		// TODO Auto-generated method stub
		return null;
	}

}
