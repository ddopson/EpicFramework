package com.epic.framework.util;

public class EpicRuntimeException extends RuntimeException {
	public EpicRuntimeException() {
		super();
	}
	public EpicRuntimeException(String msg) {
		super(msg);
	}

	public EpicRuntimeException(String msg, Throwable cause) {
		super(msg + " caused by: " + cause.getClass().getName() + ": " + cause.getMessage());
	}
}
