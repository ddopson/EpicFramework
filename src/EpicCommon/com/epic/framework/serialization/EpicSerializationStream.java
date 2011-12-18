package com.epic.framework.serialization;

import com.epic.framework.util.exceptions.EpicSerializationException;

public abstract class EpicSerializationStream {
	public abstract boolean isInput();

	public EpicInputStream getInputStream() {
		if(isInput()) {
			return (EpicInputStream)this;
		}
		else {
			return null;
		}
	}

	public abstract int serializeInt8(int value) throws EpicSerializationException;
	public abstract int serializeInt16(int value) throws EpicSerializationException;
	public abstract int serializeInt32(int value) throws EpicSerializationException;
	public abstract float serializeFloat(float value) throws EpicSerializationException;
	public abstract boolean[] serializeBoolArray(boolean[] array) throws EpicSerializationException;
	public abstract int[] serializeIntArray(int[] array) throws EpicSerializationException;

	public boolean serializeBool(boolean value) throws EpicSerializationException {
		return serializeInt8(value ? 1 : 0) == 0 ? false : true;
	}
	public boolean isOutput() {
		return !isInput();
	}

	public EpicSerializableClass serializeObject(EpicSerializableClass object, EpicSerializableClassType type) {
		return type.serialize(this, object);
	}

	public EpicSerializableClass serializeNullableObject(EpicSerializableClass object, EpicSerializableClassType type) {
		boolean isNull = serializeBool(object == null);
		if(isNull) {
			return null;
		}
		else {
			return serializeObject(object, type);
		}
	}
	
	public abstract String serializeString(String string) throws EpicSerializationException;

}
