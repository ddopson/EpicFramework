package com.epic.framework.serialization;

import java.io.IOException;
import java.io.InputStream;

import com.epic.framework.util.exceptions.EpicSerializationException;

public class EpicInputStream extends EpicSerializationStream {
	InputStream inputStream;
	public EpicInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public boolean isInput() {
		return true;
	}

	public int readInt8() throws EpicSerializationException {
		try {
			return inputStream.read();
		} catch (IOException e) {
			throw new EpicSerializationException(e);
		}
	}

	public int readInt16() throws EpicSerializationException {
		try {
			int byte0 = inputStream.read();
			int byte1 = inputStream.read();
			return byte0 + (byte1 << 8);
		} catch (IOException e) {
			throw new EpicSerializationException(e);
		}
	}

	public final int readInt32() throws EpicSerializationException {
		try {
			int byte0 = inputStream.read();
			int byte1 = inputStream.read();
			int byte2 = inputStream.read();
			int byte3 = inputStream.read();
			return byte0 + (byte1 << 8) + (byte2 << 16) + (byte3 << 24);
		} catch (IOException e) {
			throw new EpicSerializationException(e);
		}
	}

	public final int[] readIntArray() {
		int size = readInt32();
		if(size == -1) {
			return null;
		}
		if(size < -1) {
			throw new EpicSerializationException("Attempt to read int[] results in an array with negative length.");
		}
		int[] array = new int[size];
		for(int i = 0; i < size; i++) {
			array[i] = readInt32();
		}
		return array;
	}

	public float readFloat() throws EpicSerializationException {
		int bits = readInt32();
		return Float.intBitsToFloat(bits);
	}

	public EpicSerializableClass readObject(EpicSerializableClassType type) throws EpicSerializationException {
		return type.serialize(this, null);
	}
	
	public String readString() throws EpicSerializationException {
		int length = readInt32();
		if(length == -1) {
			return null;
		}
		if(length < -1) {
			throw new EpicSerializationException("Attempt to read string results in a String with negative length.");
		}
		byte[] buffer = new byte[length];
		try {
			inputStream.read(buffer);
			return new String(buffer);
		} catch (IOException e) {
			throw new EpicSerializationException(e);
		}
	}

	public int serializeInt8(int value) throws EpicSerializationException {
		return readInt8();
	}

	public int serializeInt16(int value) throws EpicSerializationException {
		return readInt16();
	}

	public int serializeInt32(int value) throws EpicSerializationException {
		return readInt32();
	}

	public float serializeFloat(float value) throws EpicSerializationException {
		return readFloat();
	}

	public boolean[] serializeBoolArray(boolean[] array) throws EpicSerializationException {
		int bits = 0;
		int s = 0;
		try {
			for(int i = 0; i < array.length; i++) {
				if(s == 0) {
					bits = inputStream.read();
					s = 8;
				}
				array[i] = ((bits & 0x1) == 1 ? true : false);
				bits >>= 1;
				s--;
			}
		} catch (IOException e) {
			throw new EpicSerializationException(e);
		}		
		return array;
	}

	public int[] serializeIntArray(int[] array) throws EpicSerializationException {
		return readIntArray();
	}

	public String serializeString(String string) throws EpicSerializationException {
		return readString();
	}
}
