package com.epic.framework.implementation.Ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;

import android.content.Context;

import com.epic.framework.common.serialization.EpicSerializableClass;
import com.epic.framework.common.serialization.EpicSerializableClassType;
import com.epic.framework.common.serialization.EpicSerializationHelper;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicSerializationException;

public class EpicIOImplementation {

	public static boolean isExistsFile(String filename) {
		try {
			EpicApplication.getAndroidContext().openFileInput(filename).close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			EpicLog.e("SHOULDN't throw IOException here.  opened, but failed to close...");
			return false;
		}
	}
	public static void touchFile(String filename) {
		try {
			EpicApplication.getAndroidContext().openFileOutput(filename, Context.MODE_PRIVATE).close();
		}
		catch (FileNotFoundException e) {
			EpicLog.e("File not found for " + filename);
		}
		catch (IOException e) {
			EpicLog.e(e.toString() + " while loading " + filename);
		}
	}

	public static void deleteFile(String filename) {
		EpicApplication.getAndroidContext().deleteFile(filename);
	}

	public static EpicSerializableClass readFile(String filename, EpicSerializableClassType type) throws EpicSerializationException, IOException {
		FileInputStream fileInput = EpicApplication.getAndroidContext().openFileInput(filename);
		EpicSerializableClass object = EpicSerializationHelper.deserialize(fileInput, type);
		fileInput.close();
		return object;
	}
	
	public static void writeFile(String filename, EpicSerializableClass object, EpicSerializableClassType type) throws EpicSerializationException, IOException {
		FileOutputStream fileOutput = EpicApplication.getAndroidContext().openFileOutput(filename, Context.MODE_PRIVATE);
		EpicSerializationHelper.serialize(fileOutput, object, type);
		fileOutput.close();
	}
}
