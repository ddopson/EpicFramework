package com.epic.framework.common.templates;

import com.epic.framework.common.serialization.EpicSerializableClass;
import com.epic.framework.common.serialization.EpicSerializableClassType;

public class T implements EpicSerializableClass {
	public T() { }

	public static final EpicSerializableClassType classType = null;
	public EpicSerializableClassType getClassType() {
		return classType;
	}
}
