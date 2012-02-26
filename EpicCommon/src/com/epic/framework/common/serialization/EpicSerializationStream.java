package com.epic.framework.common.serialization;

import com.epic.framework.common.util.exceptions.EpicSerializationException;

public interface EpicSerializationStream {
	public boolean isInput();
	public int serializeInt8(int value) throws EpicSerializationException;
	public int serializeInt16(int value) throws EpicSerializationException;
	public int serializeInt32(int value) throws EpicSerializationException;
	public float serializeFloat(float value) throws EpicSerializationException;
	public boolean[] serializeBoolArray(boolean[] array) throws EpicSerializationException;
	public int[] serializeIntArray(int[] array) throws EpicSerializationException;
	public EpicInputStream getInputStream();
	public boolean serializeBool(boolean value) throws EpicSerializationException;
	public boolean isOutput();
	public EpicSerializableClass serializeObject(EpicSerializableClass object, EpicSerializableClassType type);
	public EpicSerializableClass serializeNullableObject(EpicSerializableClass object, EpicSerializableClassType type);
	public String serializeString(String string) throws EpicSerializationException;
}
