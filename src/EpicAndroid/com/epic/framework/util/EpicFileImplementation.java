package com.epic.framework.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.epic.framework.Ui.EpicApplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;

public class EpicFileImplementation {

	public static InputStream openInputStream(String filename) {
		try {
			return EpicApplication.getAndroidContext().getAssets().open(filename);
		} catch (IOException e) {
			throw EpicFail.missing_image(filename);
		}
	}

	public static void executeFile(String apkPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + "/WordFarmUpdates");
		dir.mkdirs();
		File file = new File(dir, apkPath);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		EpicApplication.getAndroidContext().startActivity(intent);				
	}
}
