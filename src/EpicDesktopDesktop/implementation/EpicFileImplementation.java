package com.epic.framework.implementation;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.epic.framework.common.util.EpicFail;

public class EpicFileImplementation {
	public static InputStream openInputStream(String filename) {
		try {
			return new FileInputStream("./resources/" + filename);
		} catch (FileNotFoundException e) {
			throw EpicFail.missing_image(filename);
		}
	}

	public static void executeFile(String apkPath) {

	}
}
