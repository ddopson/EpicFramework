package com.epic.framework.implementation;

import java.io.IOException;

import org.xmlvm.iphone.NSErrorHolder;
import org.xmlvm.iphone.NSFileManager;
import org.xmlvm.iphone.NSSearchPathDirectory;

import com.epic.framework.common.serialization.EpicSerializableClass;
import com.epic.framework.common.serialization.EpicSerializableClassType;
import com.epic.framework.common.serialization.EpicSerializationHelper;
import com.epic.framework.common.util.exceptions.EpicSerializationException;

public class EpicIOImplementation {
	public static void writeFile(String filename, byte[] bytes) {
		EpicIOImplementationNative.writeFile(filename, bytes);
	}

	public static boolean isExistsFile(String filename) {
		return EpicIOImplementationNative.isExistsFile(filename) == 1;
	}

	public static void touchFile(String filename) {
		writeFile(filename, new byte[] { });
	}

	public static void deleteFile(String filename) {
		String fullPath = EpicIOImplementationNative.getFullPath(filename);
		NSFileManager.defaultManager().removeItemAtPath(fullPath, new NSErrorHolder());
	}

	public static void writeFile(String filename, EpicSerializableClass object, EpicSerializableClassType classType) throws EpicSerializationException, IOException {
		byte[] bytes = EpicSerializationHelper.serializeToBytes(object, classType);
		EpicIOImplementationNative.writeFile(filename, bytes);
	}

	public static EpicSerializableClass readFile(String filename, EpicSerializableClassType classType) throws EpicSerializationException, IOException {
		byte[] bytes = EpicIOImplementationNative.readFile(filename);
		if(bytes == null) return null;
		return EpicSerializationHelper.deserializeFromBytes(bytes, classType);
	}
}
