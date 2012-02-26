package com.epic.framework.common.util.exceptions;

public class EpicStringFormatException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicStringFormatException() {
		super("EpicStringFormatException", "no message", null);
	}
	public EpicStringFormatException(Throwable cause) {
		super("EpicStringFormatException", "no message", cause);
	}
	public EpicStringFormatException(String msg) {
		super("EpicStringFormatException", msg, null);
	}
	public EpicStringFormatException(String msg, Throwable cause) {
		super("EpicStringFormatException", msg, cause);
	}
}
