package com.epic.framework.common.util.exceptions;

public class EpicNullPointerException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicNullPointerException() {
		super("EpicNullPointerException", "no message", null);
	}
	public EpicNullPointerException(Throwable cause) {
		super("EpicNullPointerException", "no message", cause);
	}
	public EpicNullPointerException(String msg) {
		super("EpicNullPointerException", msg, null);
	}
	public EpicNullPointerException(String msg, Throwable cause) {
		super("EpicNullPointerException", msg, null);
	}
}
