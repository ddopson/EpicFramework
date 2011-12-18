package com.epic.framework.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EpicFileImplementation {
	public static InputStream openInputStream(String filename) {
		try {
			return new FileInputStream("./files/" + filename);
		} catch (FileNotFoundException e) {
			throw EpicFail.missing_image(filename);
		}
	}

	public static void executeFile(String apkPath) {

	}
}
