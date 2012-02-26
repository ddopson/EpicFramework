package com.epic.framework.implementation;

import java.io.IOException;

import com.epic.framework.common.serialization.EpicSerializableClass;
import com.epic.framework.common.serialization.EpicSerializableClassType;
import com.epic.framework.common.util.exceptions.EpicSerializationException;

public class EpicIOImplementation {

	public static void writeFile(String filename, byte[] bytes) { }
	public static boolean isExistsFile(String filename) { return false; }
	public static void touchFile(String filename) { }
	public static void deleteFile(String filename) { }
	public static void writeFile(String filename, EpicSerializableClass object, EpicSerializableClassType classType) throws EpicSerializationException, IOException { }
	public static EpicSerializableClass readFile(String filename, EpicSerializableClassType classType) throws EpicSerializationException, IOException { return null; }

}