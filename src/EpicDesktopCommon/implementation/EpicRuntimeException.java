package com.epic.framework.implementation


public class EpicRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -1998844813797918411L;

	public EpicRuntimeException(String msg) {
		super(msg);
	}

	public EpicRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
