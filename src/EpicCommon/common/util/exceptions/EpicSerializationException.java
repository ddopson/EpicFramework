package com.epic.framework.common.util.exceptions;

public class EpicSerializationException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicSerializationException() {
		super("EpicSerializationException", "no message", null);
	}
	public EpicSerializationException(Throwable cause) {
		super("EpicSerializationException", "no message", cause);
	}
	public EpicSerializationException(String msg) {
		super("EpicSerializationException", msg, null);
	}
	public EpicSerializationException(String msg, Throwable cause) {
		super("EpicSerializationException", msg, null);
	}
}
