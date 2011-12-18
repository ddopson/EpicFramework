package com.epic.framework.implementation.util;

public class EpicRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -1998844813797918422L;
	public final String message;
	public final String className;
	public EpicRuntimeException(String className, String msg, Throwable cause) {
		super(msg, cause);
		this.className = className;
		this.message = msg;
	}
}

