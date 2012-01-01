package com.epic.framework.implementation;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.exceptions.EpicNativeMethodMissingImplementation;

public abstract class EpicPlatformImplementationNative {	
	public static void setupDebugHandlers() {
		throw EpicFail.not_implemented();
	}
	public static String getUniqueDeviceId() {
		throw new EpicNativeMethodMissingImplementation("UniqueIdGenerator");
	}
}
