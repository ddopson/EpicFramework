package com.epic.framework.common.util.exceptions;

public class EpicNoSuchElementException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicNoSuchElementException() {
		super("EpicNoSuchElementException", "no message", null);
	}
	public EpicNoSuchElementException(Throwable cause) {
		super("EpicNoSuchElementException", "no message", cause);
	}
	public EpicNoSuchElementException(String msg) {
		super("EpicNoSuchElementException", msg, null);
	}
	public EpicNoSuchElementException(String msg, Throwable cause) {
		super("EpicNoSuchElementException", msg, null);
	}
}
