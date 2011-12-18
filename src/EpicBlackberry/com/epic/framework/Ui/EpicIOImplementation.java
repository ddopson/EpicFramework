package com.epic.framework.Ui;

import java.io.IOException;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;

import com.epic.framework.serialization.EpicSerializableClass;
import com.epic.framework.serialization.EpicSerializableClassType;
import com.epic.framework.serialization.EpicSerializationHelper;
import com.epic.framework.util.exceptions.EpicSerializationException;

public class EpicIOImplementation {
	public static void writeFile(String filename, byte[] bytes) {
		PersistentObject obj = PersistentStore.getPersistentObject(filename.hashCode());
        obj.setContents(bytes);
        obj.commit();
	}

	public static boolean isExistsFile(String filename) {
		PersistentObject obj = PersistentStore.getPersistentObject(filename.hashCode());
		Object contents = obj.getContents();
		return contents != null;
	}

	public static void touchFile(String filename) {
		PersistentObject obj = PersistentStore.getPersistentObject(filename.hashCode());
		if(obj.getContents() == null) {
			obj.setContents(new byte[] {});
		}
		obj.commit();
	}

	public static void deleteFile(String filename) {
		PersistentStore.destroyPersistentObject(filename.hashCode());
	}

	public static void writeFile(String filename, EpicSerializableClass object, EpicSerializableClassType classType) throws EpicSerializationException, IOException {
		byte[] bytes = EpicSerializationHelper.serializeToBytes(object, classType);
		PersistentObject obj = PersistentStore.getPersistentObject(filename.hashCode());
        obj.setContents(bytes);
        obj.commit();
	}

	public static EpicSerializableClass readFile(String filename, EpicSerializableClassType classType) throws EpicSerializationException, IOException {
		byte[] bytes;
		PersistentObject obj = PersistentStore.getPersistentObject(filename.hashCode());
		Object contents = obj.getContents();
		if(contents == null) {
			throw new IOException("'file' does not exist");
		}
		else if( ! (contents instanceof byte[])) {
			throw new IOException("invalid 'file' contents - should be byte[] - actually are " + contents.getClass().getName());
		}
		bytes = (byte[])obj.getContents();
		return EpicSerializationHelper.deserializeFromBytes(bytes, classType);
	}
}
