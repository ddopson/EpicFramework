package com.epic.framework.implementation

public class SdCardStorage {
	private static String baseDirectory = "/workspace/foobar";

	public static boolean storageMounted() {
		return true;
	}

	public static String getDirectory(String relativePath) {
		return baseDirectory + "/" + relativePath;
	}
}
