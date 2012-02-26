package com.epic.framework.common.util.exceptions;

public class EpicFrameworkException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicFrameworkException() {
		super("EpicFrameworkException", "no message", null);
	}
	public EpicFrameworkException(Throwable cause) {
		super("EpicFrameworkException", "no message", cause);
	}
	public EpicFrameworkException(String msg) {
		super("EpicFrameworkException", msg, null);
	}
	public EpicFrameworkException(String msg, Throwable cause) {
		super("EpicFrameworkException", msg, cause);
	}
}
