package com.epic.framework.util;

import java.io.IOException;

import com.epic.framework.Ui.EpicIOImplementation;
import com.epic.framework.serialization.EpicSerializableClass;
import com.epic.framework.serialization.EpicSerializableClassType;
import com.epic.framework.util.exceptions.EpicSerializationException;

public class EpicIO {

	public static boolean isExistsFile(String filename) {
		return EpicIOImplementation.isExistsFile(filename);
	}

	public static void touchFile(String filename) {
		EpicIOImplementation.touchFile(filename);
	}

	public static EpicSerializableClass readFile(String filename, EpicSerializableClassType classType) {
		try {
			return EpicIOImplementation.readFile(filename, classType);
		} catch (EpicSerializationException e) {
			EpicLog.e("error deserializing contents from '" + filename + "'", e);
			return null;
		} catch (IOException e) {
			EpicLog.e("error reading contents from '" + filename + "'", e);
			return null;
		}
	}

	public static void writeFile(String filename, EpicSerializableClass object, EpicSerializableClassType classType) {		
		try{
			EpicIOImplementation.writeFile(filename, object, classType);
		} catch (EpicSerializationException e) {
			EpicLog.e("error serializing contents for '" + filename + "'", e);
			return;
		} catch (IOException e) {
			EpicLog.e("error writing contents to '" + filename + "'", e);
			return;
		}
	}

	public static void deleteFile(String filename) {
		EpicIOImplementation.deleteFile(filename);
	}
}
