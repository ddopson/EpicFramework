package com.epic.framework.common.util.exceptions;

public class EpicUnexpectedDataException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicUnexpectedDataException() {
		super("EpicUnexpectedDataException", "no message", null);
	}
	public EpicUnexpectedDataException(Throwable cause) {
		super("EpicUnexpectedDataException", "no message", cause);
	}
	public EpicUnexpectedDataException(String msg) {
		super("EpicUnexpectedDataException", msg, null);
	}
	public EpicUnexpectedDataException(String msg, Throwable cause) {
		super("EpicUnexpectedDataException", msg, cause);
	}
}
