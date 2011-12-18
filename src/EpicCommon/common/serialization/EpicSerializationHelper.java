package com.epic.framework.common.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicSerializationException;

public class EpicSerializationHelper {
	public static void serialize(OutputStream outputStream, EpicSerializableClass object, EpicSerializableClassType type) throws IOException, EpicSerializationException {
		EpicFail.assertNotNull(outputStream);
		EpicFail.assertNotNull(type);
		EpicOutputStream epicOutputStream = new EpicOutputStream(outputStream);
		epicOutputStream.serializeObject(object, type);
	}

	public static EpicSerializableClass deserialize(InputStream inputStream, EpicSerializableClassType type) throws IOException, EpicSerializationException {
		EpicFail.assertNotNull(inputStream);
		EpicFail.assertNotNull(type);
		EpicInputStream epicInputStream = new EpicInputStream(inputStream);
		return epicInputStream.serializeObject(null, type);
	}


	public static byte[] serializeToBytes(EpicSerializableClass object, EpicSerializableClassType type) throws IOException, EpicSerializationException {
		EpicFail.assertNotNull(type);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		EpicOutputStream epicOutputStream = new EpicOutputStream(outputStream);
		epicOutputStream.serializeObject(object, type);
		byte[] bytes = outputStream.toByteArray();
		outputStream.close();
		return bytes;
	}

	public static EpicSerializableClass deserializeFromBytes(byte[] bytes, EpicSerializableClassType type) throws IOException, EpicSerializationException {
		EpicFail.assertNotNull(bytes);
		EpicFail.assertNotNull(type);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		EpicInputStream epicInputStream = new EpicInputStream(inputStream);
		return epicInputStream.serializeObject(null, type);
	}
}
