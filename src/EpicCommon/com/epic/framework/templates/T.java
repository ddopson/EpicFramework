package com.epic.framework.templates;

import com.epic.framework.serialization.EpicSerializableClass;
import com.epic.framework.serialization.EpicSerializableClassType;

public class T implements EpicSerializableClass {
	public T() { }

	public static final EpicSerializableClassType classType = null;
	public EpicSerializableClassType getClassType() {
		return classType;
	}
}
