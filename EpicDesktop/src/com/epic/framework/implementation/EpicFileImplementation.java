package com.epic.framework.implementation;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;

public class EpicFileImplementation {
	public static InputStream openInputStream(String filename) {
		try {
			return new FileInputStream("./resources/" + filename);
		} catch (FileNotFoundException e) {
			File f = new File("./resources/" + filename);
			EpicLog.i("failed to get " + f.getAbsolutePath());
			throw EpicFail.missing_image(filename, e);
		}
	}

	public static void executeFile(String apkPath) {

	}
}
