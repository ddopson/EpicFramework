package com.epic.framework.common.serialization;


public abstract class EpicSerializableClassType extends EpicClassType {
	/***
	 *
	 * @param stream - either an EpicInputStream or an EpicOutputStream
	 * @param object - object to serialize.  may be null for deserialization from EpicInputStream and should be ignored.
	 * @return the object serialized - either the object read from EpicInputStream, or the object written to EpicOutputStream (same as object passed)
	 */
	public abstract EpicSerializableClass serialize(EpicSerializationStream stream, final EpicSerializableClass object);
}
