package com.epic.framework.implementation;

import android.os.Environment;

public 	class SdCardStorage {
	public static boolean storageMounted() {
		String state = Environment.getExternalStorageState();
	    return (state.equals(Environment.MEDIA_MOUNTED) ||
	    		state.equals(Environment.MEDIA_MOUNTED_READ_ONLY));
	}

	public static String getDirectory(String relativePath) {
		return Environment.getExternalStorageDirectory() + "/" + relativePath;
	}
}
