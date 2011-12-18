package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicFile;

public class EpicFontImplementation {
	
	public static int measureAdvance(Object fontObject, String text) {
		return -1;
	}

	public static int measureHeight(Object fontObject) {
		return -1;
	}
	public static int measureAdvance(Object fontObject, char[] chars, int offset, int length) {
		return -1;
	}
	public static int getSize(Object fontObject) {
		return -1;
	}
	public static int measureAscent(Object fontObject) {
		return -1;
	}

	public static Object getFontObjectFromFile(EpicFile file) {
		return null;
	}
	public static Object getFontObjectFromSize(Object fontObject, int size) {
		return null;
	}
	public static Object getFontObjectFromName(String systemName) {
		return null;
	}
}
