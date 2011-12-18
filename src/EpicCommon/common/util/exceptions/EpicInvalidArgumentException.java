package com.epic.framework.common.util.exceptions;

public class EpicInvalidArgumentException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicInvalidArgumentException() {
		super("EpicInvalidArgumentException", "no message", null);
	}
	public EpicInvalidArgumentException(Throwable cause) {
		super("EpicInvalidArgumentException", "no message", cause);
	}
	public EpicInvalidArgumentException(String msg) {
		super("EpicInvalidArgumentException", msg, null);
	}
	public EpicInvalidArgumentException(String msg, Throwable cause) {
		super("EpicInvalidArgumentException", msg, null);
	}
}
