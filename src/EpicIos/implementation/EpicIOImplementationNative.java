package com.epic.framework.implementation;

import org.xmlvm.iphone.CGContext;
import org.xmlvm.iphone.CGFont;

import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.util.exceptions.EpicNativeMethodMissingImplementation;

public class EpicIOImplementationNative {
	public static String getFullPath(String filename) {
		throw new EpicNativeMethodMissingImplementation("EpicIOImplementationNative");		
	}
	
	public static void writeFile(String filename, byte[] bytes) {
		throw new EpicNativeMethodMissingImplementation("EpicIOImplementationNative");
	}

	public static byte[] readFile(String filename) {
		throw new EpicNativeMethodMissingImplementation("EpicIOImplementationNative");
	}	
}
