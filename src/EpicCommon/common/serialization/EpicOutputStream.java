package com.epic.framework.common.serialization;

import java.io.IOException;
import java.io.OutputStream;

import com.epic.framework.common.util.exceptions.EpicSerializationException;

public class EpicOutputStream extends EpicSerializationStream{
	OutputStream outputStream;
	public EpicOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public boolean isInput() {
		return false;
	}

	public void writeInt8(int value) throws EpicSerializationException {
		try {
			int byte0 = (value & 0x000000ff);
			outputStream.write(byte0);
		} catch (IOException e) {
			throw new EpicSerializationException(e);
		}
	}

	public void writeInt16(int value) throws EpicSerializationException {
		try {
			int byte0 = (value & 0x000000ff);
			int byte1 = (value & 0x0000ff00) >> 8;
			outputStream.write(byte0);
			outputStream.write(byte1);
		} catch (IOException e) {
			throw new EpicSerializationException(e);
		}
	}

	public void writeInt32(int value) throws EpicSerializationException {
		try {
			int byte0 = (value & 0x000000ff);
			int byte1 = (value & 0x0000ff00) >> 8;
			int byte2 = (value & 0x00ff0000) >> 16;
			int byte3 = (value & 0xff000000) >> 24;
			//			EpicLog.vf("Writing Int32: %d %d %d %d", byte0, byte1, byte2, byte3);
			outputStream.write(byte0);
			outputStream.write(byte1);
			outputStream.write(byte2);
			outputStream.write(byte3);
		} catch (IOException e) {
			throw new EpicSerializationException(e);
		}
	}

	public void writeIntArray(int[] array) {
		writeInt32(array.length);
		for(int i = 0; i < array.length; i++) {
			writeInt32(array[i]);
		}
	}

	public void writeFloat(float value) throws EpicSerializationException {
		int bits = Float.floatToIntBits(value);
		writeInt32(bits);
	}

	public void writeObject(EpicSerializableClass object, EpicSerializableClassType type) {
		type.serialize(this, object);
	}

	public void writeString(String string) throws EpicSerializationException {
		if(string == null) {
			writeInt32(-1);
		}
		else {
			writeInt32(string.length());
			for(int i = 0; i < string.length(); i++) {
				writeInt8(string.charAt(i));
			}
		}
	}

	public int serializeInt8(int value) throws EpicSerializationException {
		writeInt8(value);
		return value;
	}

	public int serializeInt16(int value) throws EpicSerializationException {
		writeInt16(value);
		return value;
	}

	public int serializeInt32(int value) throws EpicSerializationException {
		writeInt32(value);
		return value;
	}

	public float serializeFloat(float value) throws EpicSerializationException {
		writeFloat(value);
		return value;
	}

	public boolean[] serializeBoolArray(boolean[] array) throws EpicSerializationException {
		try {
			int bits = 0;
			int s = 0;
			for(int i = 0; i < array.length; i++) {
				bits += array[i] ? (1 << s) : 0;
				s++;
				if(s == 8) {
					outputStream.write(bits);
					s = 0;
					bits = 0;
				}
			}
			if(s > 0) {
				outputStream.write(bits);				
			}
			return array;
		} catch (IOException e) {
			throw new EpicSerializationException(e);
		}		
	}

	public int[] serializeIntArray(int[] array) throws EpicSerializationException {
		writeIntArray(array);
		return array;
	}

	public String serializeString(String string) throws EpicSerializationException {
		writeString(string);
		return string;
	}
}
