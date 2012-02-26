package com.epic.framework.util;

import java.io.InputStream;

public class EpicFileImplementation {
	public static InputStream openInputStream(String filename) {
		return EpicFileImplementation.class.getResourceAsStream("/" + filename);
	}

	public static void executeFile(String apkPath) {

	}
}
