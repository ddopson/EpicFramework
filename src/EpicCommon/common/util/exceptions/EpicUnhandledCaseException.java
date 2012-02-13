package com.epic.framework.common.util.exceptions;

public class EpicUnhandledCaseException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicUnhandledCaseException() {
		super("EpicUnhandledCaseException", "no message", null);
	}
	public EpicUnhandledCaseException(Throwable cause) {
		super("EpicUnhandledCaseException", "no message", cause);
	}
	public EpicUnhandledCaseException(String msg) {
		super("EpicUnhandledCaseException", msg, null);
	}
	public EpicUnhandledCaseException(String msg, Throwable cause) {
		super("EpicUnhandledCaseException", msg, cause);
	}
}
