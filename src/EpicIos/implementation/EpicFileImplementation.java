package com.epic.framework.implementation;

import java.io.InputStream;

public class EpicFileImplementation {
	public static InputStream openInputStream(String filename) {
		Class c = new EpicFileImplementation().getClass();
		return c.getResourceAsStream("/" + filename);
	}

	public static void executeFile(String apkPath) {
		
	}
}
