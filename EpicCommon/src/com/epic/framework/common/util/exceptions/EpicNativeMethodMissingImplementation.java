package com.epic.framework.common.util.exceptions;

public class EpicNativeMethodMissingImplementation extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicNativeMethodMissingImplementation() {
		super("EpicNativeMethodMissingImplementation", "no message", null);
	}
	public EpicNativeMethodMissingImplementation(Throwable cause) {
		super("EpicNativeMethodMissingImplementation", "no message", cause);
	}
	public EpicNativeMethodMissingImplementation(String msg) {
		super("EpicNativeMethodMissingImplementation", msg, null);
	}
	public EpicNativeMethodMissingImplementation(String msg, Throwable cause) {
		super("EpicNativeMethodMissingImplementation", msg, cause);
	}
}
