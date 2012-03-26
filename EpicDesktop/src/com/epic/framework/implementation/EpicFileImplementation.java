package com.epic.framework.implementation;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;

public class EpicFileImplementation {
	public static String magicBaseDirectory = "";
	public static File magicBaseDirectory2 = new File(new File("").getAbsoluteFile(), "resources/files");
	public static InputStream openInputStream(String filename) {
		File file = new File(magicBaseDirectory2, filename);
		file = file.getAbsoluteFile();
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			EpicLog.i("failed to get " + file.getAbsolutePath());
			throw EpicFail.missing_file(filename, e);
		}
	}

	public static void executeFile(String apkPath) {

	}
}
