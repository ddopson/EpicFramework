package com.epic.framework.Ui;

import java.io.IOException;

import com.epic.framework.serialization.EpicSerializableClass;
import com.epic.framework.serialization.EpicSerializableClassType;
import com.epic.framework.util.exceptions.EpicSerializationException;

public class EpicIOImplementation {

	public static boolean isExistsFile(String filename) {
		return false;
	}

	public static void touchFile(String filename) {
	}

	public static EpicSerializableClass readFile(String filename, EpicSerializableClassType classType) throws EpicSerializationException, IOException {
		return null;
	}

	public static void writeFile(String filename, EpicSerializableClass object, EpicSerializableClassType classType) throws EpicSerializationException, IOException {
	}

	public static void deleteFile(String filename) {
	}
}
